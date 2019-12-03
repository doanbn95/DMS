package com.omi.sakura.controller;

import com.omi.sakura.common.utils.Constants;
import com.omi.sakura.request.PasswordRequest;
import com.omi.sakura.response.ErrorInfo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    @GetMapping("/changePassword")
    public ModelAndView init() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("passwordRequest", new PasswordRequest());
        mav.setViewName("changePassword");
        return mav;
    }

    @PostMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> MailSend(@Valid @RequestBody PasswordRequest passwordRequest, BindingResult result) {
        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (!CollectionUtils.isEmpty(errorList)) {
            map.put("status", Constants.ERROR);
            map.put("message", errorList);
        } else {
            map.put("status", Constants.SUCCESS);
            userService.changePassword(passwordRequest.getNewPassword());
        }
        return map;
    }


}