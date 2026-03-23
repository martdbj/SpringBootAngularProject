package com.martdbj.backend.DTOs;

public record DeviceDTO(
        String serialNumber,
        String name,
        int type,
        String employeeId
) {
}
