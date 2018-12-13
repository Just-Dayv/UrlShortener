package com.interswitch.urlshortener.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.interswitch.urlshortener.controller.model.Error;
import com.interswitch.urlshortener.controller.model.Response;
import com.interswitch.urlshortener.exception.InvalidRequestException;
import com.interswitch.urlshortener.exception.RequestRejectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ApiAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleValidationException(MethodArgumentNotValidException e) {

        BindingResult result = e.getBindingResult();
        List<FieldError> errorList = result.getFieldErrors();
        List<Error> errors = new ArrayList<>();
        for (FieldError fieldError : errorList) {
            errors.add(new Error(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        Response response = new Response("400","Bad request",errors);

        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Response response = new Response("400", e.getLocalizedMessage(), null);
        if (e.getCause() != null) {
            String message = e.getCause().getMessage();
            if (e.getCause() instanceof JsonMappingException) {
                String[] arr = message.split("\\(");
                if (arr.length > 0) {
                    String temp = arr[0];
                    String[] arr2 = message.split("\\[");
                    if (arr2.length > 1) {
                        message = temp + " (field: [" + arr2[1];
                    } else {
                        message = temp;
                    }
                }
            }

            if (e.getCause() instanceof JsonParseException) {
                String[] arr = message.split("at");
                if (arr.length > 0) {
                    String temp = arr[0];
                    JsonParseException jpe = (JsonParseException) e.getCause();
                    message = temp + " [line: " + jpe.getLocation().getLineNr() + ", column: " + jpe.getLocation().getColumnNr() + "]";
                }
            }
            response = new Response("400", message, null);

        }
        return response;
    }

    @ExceptionHandler(RequestRejectedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleUrlDuplicationException (RequestRejectedException e)
    {
        Response response = new Response("400", e.getLocalizedMessage(),null);
        return response;
    }
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleInvalidUrlException (InvalidRequestException e)
    {
        Response response = new Response("400", e.getLocalizedMessage(),null);
        return response;
    }



}
