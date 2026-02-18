package com.example.crud.exception;

public class DatosLoginIncorrectosException extends RuntimeException {
    public DatosLoginIncorrectosException(String mensaje) {
        super(mensaje);
    }
}
