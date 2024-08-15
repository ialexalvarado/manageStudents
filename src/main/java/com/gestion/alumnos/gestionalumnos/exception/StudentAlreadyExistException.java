package com.gestion.alumnos.gestionalumnos.exception;

public class StudentAlreadyExistException extends RuntimeException{
    public StudentAlreadyExistException(String message){
        super(message);
    }
}
