package com.martdbj.backend.DTOs;


import java.util.List;

public record EmployeeDTO (
        String id,
        String name,
        String email,
        String companyId,
        List<String> devicesId
) {

}
