package com.gestion.alumnos.gestionalumnos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.alumnos.gestionalumnos.entities.Student;
import com.gestion.alumnos.gestionalumnos.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{
    
    @Autowired
    private StudentRepository repository;

    @Override
    public Optional<Student> findByFirstNameAndLastName(String firstName, String lastName){
        return repository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Student saveOrUpdateStudent(Student student){
        return repository.save(student);
    }

    @Override
    public List<Student> getAllStudents(){
        return repository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id){
        return repository.findById(id);
    }

    @Override
    public void deleteStudent(Long id){
        repository.deleteById(id);
    }
}
