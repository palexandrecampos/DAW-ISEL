package pt.isel.daw.G8.Web.Application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
//@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {

    @GetMapping(value = "/home")
    public ResponseEntity home() {
        return ResponseEntity.ok("Welcome Home!");
    }
}
