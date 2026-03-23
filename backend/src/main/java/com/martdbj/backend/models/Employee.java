package com.martdbj.backend.models;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Document("Employees")
public class Employee {

    @Id
    private String id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    @Size(max = 255, message = "Name cannot be more than 255 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Name cannot contain special characters")
    private String name;

    @Email(message = "Please type a valid email")
    private String email;

    private String companyId;

    private List<String> devicesID;

    public Employee(String name, String email, String companyId, List<String> devicesID) {
        this.name = name;
        this.email = email;
        this.companyId = companyId;

        this.devicesID = Objects.requireNonNullElseGet(devicesID, ArrayList::new);
    }

    public Employee() {
        this.devicesID = new ArrayList<>();
    }
}
