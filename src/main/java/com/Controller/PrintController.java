package com.Controller;

import com.Service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/printer")
public class PrintController {


    @Autowired
    private PrintService service;

    @GetMapping("/Hi")
    public String message() {
        return "welcome";
    }

    @GetMapping("/test")
    public String testPrint() {
        try {
            service.printSample();
            return "Print success!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: " + e.getMessage();
        }
    }
}
