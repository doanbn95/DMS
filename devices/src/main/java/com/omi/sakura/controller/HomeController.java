package com.omi.sakura.controller;


import com.omi.sakura.persistent.domain.Device;
import com.omi.sakura.persistent.domain.User;
import com.omi.sakura.persistent.domain.UserPrincipal;
import com.omi.sakura.response.DeviceReturnDateResponse;
import com.omi.sakura.service.DeviceService;
import com.omi.sakura.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @GetMapping({"", "/", "/checkUser"})
    public ModelAndView checkUser() {

        ModelAndView mav = new ModelAndView();
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = ((UserPrincipal) principal).getUser().getId();
        User user = userService.findById(id);
        if (user.getFirstTime() == 1) {
            mav.setViewName("redirect:/changePassword");
            return mav;
        }
        mav.setViewName("redirect:/home");
        return mav;

    }

    @RequestMapping("/fragments/header")
    public ModelAndView checkUserRole() {
        ModelAndView mav = new ModelAndView();
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = ((UserPrincipal) principal).getUser().getRole();
        mav.addObject("role", role);
        mav.setViewName("/fragments/header");
        return mav;
    }

    @GetMapping("/home")
    public ModelAndView home() {

        ModelAndView mav = new ModelAndView();
        List<DeviceReturnDateResponse> devices = deviceService.findByReturnDate();
        mav.addObject("listDevice", devices);
        mav.addObject("number", devices.size());
        mav.addObject("totalDevice", deviceService.countDeviceByType());
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = ((UserPrincipal) principal).getUser().getRole();
        mav.setViewName("home");
        return mav;

    }

    @GetMapping("/listDevice/{detailType}")
    @ResponseBody
    public ResponseEntity<List<Device>> listDevice(@PathVariable String detailType) {
        List<Device> device = deviceService.findAllDeviceByDetailType(detailType);
        ResponseEntity<List<Device>> responseEntity = new ResponseEntity<List<Device>>(device, HttpStatus.OK);
        return responseEntity;

    }
    @GetMapping("/import/{url}")
    @ResponseBody
    public Map<String,Object> importFile(@PathVariable("url") String url){
        Map<String,Object> map= new HashMap<>();
        return map;
    }



}