package com.omi.sakura.controller;

import com.omi.sakura.common.utils.Constants;
import com.omi.sakura.common.utils.MasterTypeEnum;
import com.omi.sakura.persistent.domain.User;
import com.omi.sakura.persistent.domain.UserPrincipal;
import com.omi.sakura.request.UserRequest;
import com.omi.sakura.response.ErrorInfo;
import com.omi.sakura.service.CommonService;
import com.omi.sakura.service.MasterService;
import com.omi.sakura.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("@permission.hasRole({'ROLE_ADMIN'})")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MasterService masterService;

    @GetMapping("/create")
    public ModelAndView createUserView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/create");
        mav.addObject("departments", masterService.findMasterByType(MasterTypeEnum.BOPHAN.getCode()));
        UserRequest request = new UserRequest();
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByMail(principal.getUsername());
        request.setCreatePerson(user.getName());
        mav.addObject("userRequest", request);
        return mav;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> createUser(@Valid @RequestBody UserRequest userRequest, BindingResult result) {

        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }
        if (!userService.checkUserNameExist(userRequest.getUserName(), null)) {
            errorList.add(new ErrorInfo("Email đã tồn tại", "userName"));
        }
        return commonService.create(userRequest, errorList, Constants.USER);

    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateUser(@Valid @RequestBody UserRequest userRequest, BindingResult result) {

        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }
        if (!userService.checkUserNameExist(userRequest.getUserName(), userRequest.getUserNameOld())) {
            errorList.add(new ErrorInfo("Tên đăng nhập đã tồn tại", "userName"));
        }

        return commonService.update(userRequest, errorList, Constants.USER);

    }

    @GetMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("userRequest", userService.edit(id));
        mav.addObject("departments", masterService.findMasterByType(MasterTypeEnum.BOPHAN.getCode()));
        mav.setViewName("user/edit");
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ModelAndView listUser() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("allUsers", userService.findAll());
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = ((UserPrincipal) principal).getUser().getId();
        mav.addObject("loginId", id);
        mav.setViewName("user/list");
        return mav;
    }

}
