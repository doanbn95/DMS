package com.omi.sakura.controller;

import com.omi.sakura.common.utils.Constants;
import com.omi.sakura.persistent.domain.Staff;
import com.omi.sakura.request.MailRequest;
import com.omi.sakura.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(path = "/staff", produces = MediaType.APPLICATION_JSON_VALUE)
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping("/get/{code}")
    @ResponseBody
    public Map<String, Object> getStaff(@PathVariable("code") String code) {

        Map<String, Object> map = new HashMap<>();
        Staff staff = staffService.findStaff(code, "code");
        if (staff == null) {
            map.put("status", Constants.ERROR);
        } else {
            map.put("status", Constants.SUCCESS);
            map.put("staff", staff);
        }
        return map;
    }

    @PostMapping("/getemail")
    @ResponseBody
    public Map<String, Object> getStaffByEmail(@RequestBody MailRequest mailRequest) {

        Map<String, Object> map = new HashMap<>();
        Staff staff = staffService.findStaff(mailRequest.getEmail(), "email");
        if (staff == null) {
            map.put("status", Constants.ERROR);
        } else {
            map.put("status", Constants.SUCCESS);
            map.put("staff", staff);
        }
        return map;
    }
}
