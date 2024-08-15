package com.gestion.alumnos.gestionalumnos.services;

import java.util.List;
import java.util.Optional;

import com.gestion.alumnos.gestionalumnos.entities.Student;

public interface StudentService {
    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
    Student saveOrUpdateStudent(Student student);
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
    void deleteStudent(Long id);
}
