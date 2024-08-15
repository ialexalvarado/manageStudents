package com.gestion.alumnos.gestionalumnos.dto;

import java.util.Date;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequestDTO {

    @NotEmpty(message = "FirstName is required")
    private String firstName;

    @NotEmpty(message = "lastName is required")
    private String lastName;

    @NotNull(message = "dateOfBirth cannot be null")
    private Date dateOfBirth;

    private String email;

    @Size(max = 15, message = "phoneNumber have a limit of 15 characters")
    private String phoneNumber;

    private String address;

    @NotNull(message = "enrollmentDate cannot be null")
    private Date enrollmentDate;

}
