package com.producto.producto.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonError(HttpMessageNotReadableException ex) {

        String mensaje = "Error en los datos enviados";
        String sugerencia = "Verifica el formato de los campos";
        String valor = "";

        String error = ex.getMessage();

        if (error.contains("Double")) {
            mensaje = "El campo 'precio' tiene un valor inválido";
            sugerencia = "Debe ser un número sin comillas, por ejemplo: 4000 o 4000.0";

            // Intentar extraer el valor (simple)
            if (error.contains("\"")) {
                valor = error.split("\"")[1];
            }
        }

        return ResponseEntity.ok(
                Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "mensaje", mensaje,
                        "valor_recibido", valor,
                        "sugerencia", sugerencia
                )
        );
    }
}