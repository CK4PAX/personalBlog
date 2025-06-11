/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ck4pax.personalBlog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author unbroken
 */
@Controller
public class MyErrorController implements ErrorController{
    
    @RequestMapping("/error")
    public String handlerError(){
        return "error";
    }
    
}
