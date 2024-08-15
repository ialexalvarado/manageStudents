package com.gestion.alumnos.gestionalumnos.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name= "dateOfBirth", nullable= false)
    private Date dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumer", length = 15)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "enrollmentDate", nullable = false)
    private Date enrollmentDate;

}
