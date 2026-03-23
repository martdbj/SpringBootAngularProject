package com.martdbj.backend.DTOs;

import java.util.List;

public record CompanyDTO(
        String id,
        String name,
        String address,
        List<String> employeesId
) {
}
