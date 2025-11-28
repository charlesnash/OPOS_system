package com.Controller;

import com.Service.JposXmlService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jpos")
public class JposController {

    private final JposXmlService service;

    public JposController(JposXmlService service) {
        this.service = service;
    }

    @GetMapping("/device")
    public List<String> getDevices() {
        return service.getAllDevices();
    }

    @PostMapping("/add-usb/{ldn}")
    public String addDevice(@PathVariable String ldn) {
        boolean saved = service.addUsbDevice(ldn);
        return saved ? "Device added: " + ldn : "Device already exists or failed!";
    }
}

