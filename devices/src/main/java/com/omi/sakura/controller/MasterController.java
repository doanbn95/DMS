package com.omi.sakura.controller;

import com.omi.sakura.persistent.domain.Master;
import com.omi.sakura.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/master", produces = MediaType.APPLICATION_JSON_VALUE)
public class MasterController {
    @Autowired
    private MasterService service;

    @GetMapping("/{type}")
    @ResponseBody
    public ResponseEntity<List<Master>> listMaster(@PathVariable("type") int type) {
        List<Master> result = service.findMasterByType(type);
        return result.isEmpty() ?
                new ResponseEntity<>(result, HttpStatus.NO_CONTENT) : new ResponseEntity<>(result, HttpStatus.OK);
    }
}
