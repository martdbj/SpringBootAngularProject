package com.martdbj.backend.web;

import com.martdbj.backend.DTOs.CompanyDTO;
import com.martdbj.backend.DTOs.DeviceDTO;
import com.martdbj.backend.models.Company;
import com.martdbj.backend.models.Device;
import com.martdbj.backend.service.DeviceService;
import com.martdbj.backend.service.DeviceServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/devices")
@CrossOrigin(origins = "http://localhost:4200")
public class DeviceController {

    DeviceServiceImplementation deviceService;

    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getCompanies() {
        return new ResponseEntity<>(deviceService.getDevices(), HttpStatus.OK);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable String serialNumber) {
        return new ResponseEntity<>(deviceService.getDeviceBySerialNumber(serialNumber), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DeviceDTO> saveDevice(@RequestBody Device device) {
        return new ResponseEntity<>(deviceService.saveDevice(device), HttpStatus.CREATED);
    }

    @PutMapping("/{serialNumber}")
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody Device device, @PathVariable String serialNumber) {
        return new ResponseEntity<>(deviceService.updateDevice(device, serialNumber), HttpStatus.OK);
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<HttpStatus> deleteDevice(@PathVariable String serialNumber) {
        deviceService.deleteDevice(serialNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
