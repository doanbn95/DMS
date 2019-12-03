package com.omi.sakura.controller;

import com.omi.sakura.request.DeviceRequest;
import com.omi.sakura.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/device", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/get/{code}")
    @ResponseBody
    public ResponseEntity<DeviceRequest> listDevice(@PathVariable String code) {
        DeviceRequest device = deviceService.getDevice(code.trim());
        return device != null ?
                new ResponseEntity<>(device, HttpStatus.OK) : new ResponseEntity<>(device, HttpStatus.NOT_FOUND);
    }

}

