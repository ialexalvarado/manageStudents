package com.gestion.alumnos.gestionalumnos.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.alumnos.gestionalumnos.dto.CreateStudentRequestDTO;
import com.gestion.alumnos.gestionalumnos.entities.Student;
import com.gestion.alumnos.gestionalumnos.exception.StudentAlreadyExistException;
import com.gestion.alumnos.gestionalumnos.exception.StudentNotFoundException;
import com.gestion.alumnos.gestionalumnos.services.StudentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Validated
@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                    .orElseGet(()-> ResponseEntity.notFound().build());

    }

    @GetMapping("/search")
    public ResponseEntity<Student> getStudentByFirstNameAndLastName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        Optional<Student> student = studentService.findByFirstNameAndLastName(firstName, lastName);
        return student.map(ResponseEntity::ok)
                    .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    
    

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody CreateStudentRequestDTO student) {
        Optional<Student> studentFounded = studentService.findByFirstNameAndLastName(student.getFirstName(), student.getLastName());
        if(studentFounded.isPresent()){
            throw new StudentAlreadyExistException("Student "+ student.getFirstName()+" "+ student.getLastName() + " already exists");
        }
        Student newRecord= Student.builder()
                            .firstName(student.getFirstName())
                            .lastName(student.getLastName())
                            .dateOfBirth(student.getDateOfBirth())
                            .email(student.getEmail())
                            .phoneNumber(student.getPhoneNumber())
                            .address(student.getAddress())
                            .enrollmentDate(student.getEnrollmentDate())
                            .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.saveOrUpdateStudent(newRecord));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody CreateStudentRequestDTO studentDetails) {
        Optional<Student> student = studentService.getStudentById(id);
        if(student.isPresent()){
            Student updateStudent= student.get();
            updateStudent.setFirstName(studentDetails.getFirstName());
            updateStudent.setLastName(studentDetails.getLastName());
            updateStudent.setDateOfBirth(studentDetails.getDateOfBirth());
            updateStudent.setEmail(studentDetails.getEmail());
            updateStudent.setPhoneNumber(studentDetails.getPhoneNumber());
            updateStudent.setAddress(studentDetails.getAddress());
            updateStudent.setEnrollmentDate(studentDetails.getEnrollmentDate());

            studentService.saveOrUpdateStudent(updateStudent);
            return ResponseEntity.ok(updateStudent);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        Optional<Student> student= studentService.getStudentById(id);
        if(student.isPresent()){
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        }else{
            throw new StudentNotFoundException("Student with id "+id+" not found");
        }


    }
    
    
}
