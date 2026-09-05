package com.uade.ecommerce.Exception;

public class ArgumentInvalidException extends RuntimeException {

    public ArgumentInvalidException(String mensaje) {
        super(mensaje);
    }
}