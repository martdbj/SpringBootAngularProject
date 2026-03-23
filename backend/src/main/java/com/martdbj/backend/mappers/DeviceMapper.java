package com.martdbj.backend.mappers;

import com.martdbj.backend.DTOs.DeviceDTO;
import com.martdbj.backend.models.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public DeviceDTO toDTO(Device device) {
        return new DeviceDTO(
                device.getSerialNumber(),
                device.getName(),
                device.getType(),
                device.getEmployeeId()
        );
    }
}
