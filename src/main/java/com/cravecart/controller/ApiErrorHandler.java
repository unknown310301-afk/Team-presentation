package com.cravecart.controller;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class ApiErrorHandler {
 @ExceptionHandler(NoSuchElementException.class) ResponseEntity<Map<String,String>> notFound(NoSuchElementException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<Map<String,String>> badRequest(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
 @ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class) ResponseEntity<Map<String,String>> missing(org.springframework.dao.EmptyResultDataAccessException e){return ResponseEntity.status(404).body(Map.of("error","Requested record was not found."));}
}
