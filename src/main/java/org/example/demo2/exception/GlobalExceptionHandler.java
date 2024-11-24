package org.example.demo2.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        e.printStackTrace(); // Loghează excepția în consolă pentru depanare
        model.addAttribute("errorMessage", e.getMessage());
        return "error"; // Redirecționează către pagina de eroare
    }
}
