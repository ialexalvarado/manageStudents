package com.gestion.alumnos.gestionalumnos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.gestion.alumnos.gestionalumnos.controllers.StudentController;
import com.gestion.alumnos.gestionalumnos.dto.CreateStudentRequestDTO;
import com.gestion.alumnos.gestionalumnos.entities.Student;
import com.gestion.alumnos.gestionalumnos.exception.StudentNotFoundException;
import com.gestion.alumnos.gestionalumnos.services.StudentService;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudentById_Success(){
        Long id= 1L;
        Student student= new Student();
        student.setId(id);
        when(studentService.getStudentById(id)).thenReturn(Optional.of(student));

        ResponseEntity<Student> response= studentController.getStudentById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }

    @Test
    void testGetStudentById_NotFound(){
        Long id= 1L;
        when(studentService.getStudentById(id)).thenReturn(Optional.empty());

        ResponseEntity<Student> response= studentController.getStudentById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateStudent(){
        CreateStudentRequestDTO dto= new CreateStudentRequestDTO();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");

        Student student= new Student();
        student.setFirstName("Juan");
        student.setLastName("Perez");

        when(studentService.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName())).thenReturn(Optional.empty());
        when(studentService.saveOrUpdateStudent(student)).thenReturn(student);

        ResponseEntity<Student> response= studentController.createStudent(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(student, response.getBody());
    }

    @Test
    public void testUpdateStudent_Found(){
        Long studentId= 1L;
        CreateStudentRequestDTO dto= new CreateStudentRequestDTO();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");

        Student existingStudent= new Student();
        existingStudent.setId(studentId);
        existingStudent.setFirstName("alejandro");
        existingStudent.setLastName("alvarado");

        Student updatedStudent= new Student();
        updatedStudent.setId(studentId);
        updatedStudent.setFirstName(dto.getFirstName());
        updatedStudent.setLastName(dto.getLastName());

        when(studentService.getStudentById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentService.saveOrUpdateStudent(updatedStudent)).thenReturn(updatedStudent);

        ResponseEntity<Student> response = studentController.updateStudent(studentId, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedStudent, response.getBody());
    }

    @Test
    public void testUpdateStudent_NotFound(){
        Long studentId= 1L;
        CreateStudentRequestDTO dto= new CreateStudentRequestDTO();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");

        when(studentService.getStudentById(studentId)).thenReturn(Optional.empty());

        ResponseEntity<Student> response= studentController.updateStudent(studentId, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteStudent_Found(){
        Long studentId = 1L;
        Student student= new Student();
        student.setId(studentId);

        when(studentService.getStudentById(studentId)).thenReturn(Optional.of(student));

        ResponseEntity<Void> response= studentController.deleteStudent(studentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    public void testDeleteStudent_NotFound() {
        Long studentId = 1L;
    
        // Configura el mock para retornar Optional.empty()
        when(studentService.getStudentById(studentId)).thenReturn(Optional.empty());
    
        StudentNotFoundException thrown= assertThrows(StudentNotFoundException.class,() -> { studentController.deleteStudent(studentId); });
    
        // Verifica que el estado de respuesta es NOT_FOUND
        assertEquals("Student with id "+studentId+" not found", thrown.getMessage());
    }
}
