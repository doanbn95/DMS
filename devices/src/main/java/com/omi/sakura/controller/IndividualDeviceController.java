package com.omi.sakura.controller;

import com.omi.sakura.common.utils.Constants;
import com.omi.sakura.common.utils.MasterTypeEnum;
import com.omi.sakura.persistent.domain.IndividualDevice;
import com.omi.sakura.persistent.domain.Master;
import com.omi.sakura.persistent.domain.UserPrincipal;
import com.omi.sakura.request.IndividualDeviceRequest;
import com.omi.sakura.response.ErrorInfo;
import com.omi.sakura.response.IndualDeviceResponse;
import com.omi.sakura.service.CommonService;
import com.omi.sakura.service.IndividualDeviceService;
import com.omi.sakura.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/individualdevice", produces = MediaType.APPLICATION_JSON_VALUE)
public class IndividualDeviceController {
    @Autowired
    private IndividualDeviceService individualDeviceService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MasterService masterService;

    @GetMapping("/list")
    public ModelAndView getList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("individualdevice/list");
        return mav;
    }

    @GetMapping("/list/table")
    @ResponseBody
    public ResponseEntity<IndualDeviceResponse> getTable() {
        List<IndividualDevice> result = individualDeviceService.findAll();
        IndualDeviceResponse respone = new IndualDeviceResponse();
        respone.setList(result);
        respone.setRole(commonService.getRole());
        return result.isEmpty() ? new ResponseEntity<>(respone, HttpStatus.NO_CONTENT) : new ResponseEntity<>(respone, HttpStatus.OK);
    }

    @GetMapping("/create")
    public ModelAndView viewCreate() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("individualdevice/create");
        mav.addObject("individualDeviceRequest", new IndividualDeviceRequest());
        mav.addObject("departments", masterService.findMasterByType(MasterTypeEnum.BOPHAN.getCode()));
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteDevice(@PathVariable Long id) {
        individualDeviceService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@Valid @RequestBody IndividualDeviceRequest individualDeviceRequest,
                                      BindingResult result) {

        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }
        if (!commonService.checkCode(individualDeviceRequest.getCodeDevice(), null, Constants.INDIVIDUALDEVICE)) {
            errorList.add(new ErrorInfo("Mã thiết bị đã tồn tại", "codeDevice"));
        }
        return commonService.create(individualDeviceRequest, errorList, Constants.INDIVIDUALDEVICE);
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@Valid @RequestBody IndividualDeviceRequest individualDeviceRequest,
                                      BindingResult result) {

        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }
        if (!commonService.checkCode(individualDeviceRequest.getCodeDevice(), individualDeviceRequest.getCodeDeviceOld(), Constants.INDIVIDUALDEVICE)) {
            errorList.add(new ErrorInfo("Mã thiết bị đã tồn tại", "codeDevice"));
        }

        return commonService.update(individualDeviceRequest, errorList, Constants.INDIVIDUALDEVICE);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        IndividualDeviceRequest request = individualDeviceService.edit(id);
        List<Master> masterList = masterService.findMasterByType(MasterTypeEnum.BOPHAN.getCode());
        mav.setViewName("individualdevice/edit");
        mav.addObject("individualDeviceRequest", request);
        mav.addObject("departments", masterList);
        return mav;
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<IndividualDeviceRequest> listDevice(@PathVariable("id") Long id) {
        IndividualDeviceRequest device = individualDeviceService.edit(id);
        return device != null ?
                new ResponseEntity<>(device, HttpStatus.OK) : new ResponseEntity<>(device, HttpStatus.NOT_FOUND);
    }

}
