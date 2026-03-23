package com.martdbj.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("Devices")
public class Device {

    @Id
    private String serialNumber;

    @NotBlank
    @Size(max = 255, message = "Name cannot be longer than 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Name cannot contain special characters")
    private String name;

    @NotBlank
    private int type;

    private String employeeId;

    public Device(String name, int type, String employeeId) {
        this.name = name;
        this.type = type;
        this.employeeId = employeeId;
    }
}
