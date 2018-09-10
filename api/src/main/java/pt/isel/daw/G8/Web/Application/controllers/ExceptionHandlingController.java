package pt.isel.daw.G8.Web.Application.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.daw.G8.Web.Application.ProblemJson;
import pt.isel.daw.G8.Web.Application.exceptions.BadRequestException;
import pt.isel.daw.G8.Web.Application.exceptions.NotFoundException;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemJson> notFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
        return new ResponseEntity<>(
                new ProblemJson(
                        "NOT FOUND",
                        HttpStatus.NOT_FOUND.value(),
                        "The resource not exist in Database!"
                ), headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemJson> badRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
        return new ResponseEntity<>(
                new ProblemJson(
                        "BAD REQUEST",
                        HttpStatus.BAD_REQUEST.value(),
                        "The resource not exist in Database!"
                ), headers, HttpStatus.BAD_REQUEST);
    }
}
