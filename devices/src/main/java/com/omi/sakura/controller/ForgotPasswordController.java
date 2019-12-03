package com.omi.sakura.controller;

import com.omi.sakura.common.utils.Constants;
import com.omi.sakura.request.MailRequest;
import com.omi.sakura.response.ErrorInfo;
import com.omi.sakura.service.MailService;
import com.omi.sakura.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ForgotPasswordController {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @GetMapping("/forgotPassword")
    public ModelAndView init() {

        ModelAndView mav = new ModelAndView();
        mav.addObject("mailRequest", new MailRequest());
        mav.setViewName("forgotPassword");
        return mav;

    }

    @PostMapping(value = "/forgotPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> MailSend(@Valid @RequestBody MailRequest mailRequest, BindingResult result) {
        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }
        if (userService.findByMail(mailRequest.getEmail()) == null) {
            errorList.add(new ErrorInfo("Email này chưa có trong hệ thống", "email"));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (!CollectionUtils.isEmpty(errorList)) {
            map.put("status", Constants.ERROR);
            map.put("message", errorList);
        } else {
            if (mailService.forgotPassword(mailRequest.getEmail())) {
                map.put("status", Constants.SUCCESS);
            }
        }
        return map;
    }

}