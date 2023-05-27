package com.alura.foro.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404 () {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400 (MethodArgumentNotValidException e) {

        // Obtengo cuáles son los errores en la bad Request
        List<DatosErroresValidacion> errores = e.getFieldErrors().stream().map(DatosErroresValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    //Creo una clase record privada
    private record DatosErroresValidacion(String campos, String error){
        public DatosErroresValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
