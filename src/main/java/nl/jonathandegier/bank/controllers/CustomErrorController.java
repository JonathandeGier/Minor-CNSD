package nl.jonathandegier.bank.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        System.out.println("error here:");
        System.out.println(request.getPathInfo());
        System.out.println(request.getPathTranslated());
        System.out.println(request.getMethod());
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL().toString());
        System.out.println(request.getServletPath());
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }

}
