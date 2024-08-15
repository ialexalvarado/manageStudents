package com.gestion.alumnos.gestionalumnos.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.alumnos.gestionalumnos.entities.Student;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Student> findByFirstName(String firstName);
}
