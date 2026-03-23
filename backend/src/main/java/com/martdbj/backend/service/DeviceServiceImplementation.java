package com.martdbj.backend.service;

import com.martdbj.backend.DTOs.DeviceDTO;
import com.martdbj.backend.exception.EntityNotFoundException;
import com.martdbj.backend.mappers.DeviceMapper;
import com.martdbj.backend.models.Device;
import com.martdbj.backend.models.Employee;
import com.martdbj.backend.repository.DeviceRepository;
import com.martdbj.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@AllArgsConstructor
@Service
public class DeviceServiceImplementation implements DeviceService {

    private final MongoTemplate mongoTemplate;
    DeviceRepository deviceRepository;
    EmployeeRepository employeeRepository;
    DeviceMapper deviceMapper;

    @Override
    public List<DeviceDTO> getDevices() {
        return deviceRepository.findAll()
                .stream()
                .map(deviceMapper::toDTO)
                .toList();
    }

    @Override
    public DeviceDTO getDeviceBySerialNumber(String serialNumber) {
        return deviceMapper.toDTO(deviceRepository.findById(serialNumber)
                .orElseThrow(() -> new EntityNotFoundException(serialNumber, Device.class))
        );
    }

    @Override
    public DeviceDTO saveDevice(Device device) {

        return deviceMapper.toDTO(deviceRepository.save(device));
    }

    @Override
    public DeviceDTO updateDevice(Device device, String serialNumber) {
        if (!deviceRepository.existsById(serialNumber)) throw new EntityNotFoundException(serialNumber, Device.class);
        device.setSerialNumber(serialNumber);
        return deviceMapper.toDTO(deviceRepository.save(device));
    }

    @Override
    public void deleteDevice(String serialNumber) {
        Device device = mongoTemplate.findById(serialNumber, Device.class);
        assert device != null;

        Query employeeQuery = new Query(where("_id").is(device.getEmployeeId()));
        Update updateQuery = new Update().pull("devicesID", device.getSerialNumber());
        mongoTemplate.updateFirst(employeeQuery, updateQuery, Employee.class);

        Query deviceQuery = new Query(where("_id").is(device.getSerialNumber()));
        mongoTemplate.remove(deviceQuery, Device.class);
    }
}
