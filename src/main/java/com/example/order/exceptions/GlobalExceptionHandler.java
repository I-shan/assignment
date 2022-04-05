package com.example.order.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice

public class GlobalExceptionHandler extends Throwable {
    @ExceptionHandler(Exception.class)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handle400(Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customers/error_400");
        mv.addObject("exception",ex.getMessage());
        return mv;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handle500(Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customers/error_500");
        mv.addObject("exception",ex.getMessage());
        return mv;
    }
}



