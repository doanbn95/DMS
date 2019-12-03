package com.omi.sakura.controller;

import com.omi.sakura.common.utils.Constants;
import com.omi.sakura.common.utils.MasterTypeEnum;
import com.omi.sakura.persistent.domain.CustomerDevice;
import com.omi.sakura.persistent.domain.Master;
import com.omi.sakura.persistent.domain.UserPrincipal;
import com.omi.sakura.request.CustomerDeviceRequest;
import com.omi.sakura.response.CustomerDeviceResponse;
import com.omi.sakura.response.ErrorInfo;
import com.omi.sakura.service.CommonService;
import com.omi.sakura.service.CustomerDeviceService;
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
@RequestMapping(path = "/customerdevice", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerDeviceController {

    @Autowired
    private CustomerDeviceService customerDeviceService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MasterService masterService;

    @GetMapping("/create")
    public ModelAndView viewCreate() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("customerdevice/create");
        mav.addObject("customerDeviceRequest", new CustomerDeviceRequest());
        mav.addObject("departments", masterService.findMasterByType(MasterTypeEnum.BOPHAN.getCode()));
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView viewList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("customerdevice/list");
        return mav;
    }

    @GetMapping("/customerdevices")
    @ResponseBody
    public ResponseEntity<CustomerDeviceResponse> getCompanyTest() {
        List<CustomerDevice> result = customerDeviceService.findAll();
        CustomerDeviceResponse customerDeviceResponse = new CustomerDeviceResponse();
        customerDeviceResponse.setCustomerDeviceList(result);
        customerDeviceResponse.setRole(commonService.getRole());
        return result.isEmpty() ? new ResponseEntity<>(customerDeviceResponse, HttpStatus.NO_CONTENT) : new ResponseEntity<>(customerDeviceResponse, HttpStatus.OK);
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity deleteCustomerDevice(@PathVariable Long id) {
        customerDeviceService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> createCustomerDevice(@Valid @RequestBody CustomerDeviceRequest customerDeviceForm, BindingResult result) {
        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }

        if (!commonService.checkCode(customerDeviceForm.getDeviceCode(), null)) {
            errorList.add(new ErrorInfo("Mã thiết bị đã tồn tại", "deviceCode"));
        }
        return commonService.create(customerDeviceForm, errorList, Constants.CUSTOMERDEVICE);
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateCustomerDevice(@Valid @RequestBody CustomerDeviceRequest customerDeviceRequest, BindingResult result) {
        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }

        if (!commonService.checkCode(customerDeviceRequest.getDeviceCode(), customerDeviceRequest.getCodeOld())) {
            errorList.add(new ErrorInfo("Mã thiết bị đã tồn tại", "deviceCode"));
        }
        return commonService.update(customerDeviceRequest, errorList, Constants.CUSTOMERDEVICE);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editCustomerDevice(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        CustomerDeviceRequest customerDeviceRequest = customerDeviceService.edit(id);
        List<Master> masterList = masterService.findMasterByType(MasterTypeEnum.BOPHAN.getCode());
        mav.addObject("customerDeviceRequest", customerDeviceRequest);
        mav.addObject("departments", masterList);
        mav.setViewName("customerdevice/edit");
        return mav;
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<CustomerDeviceRequest> listDevice(@PathVariable("id") Long id) {
        CustomerDeviceRequest device = customerDeviceService.edit(id);
        return device != null ?
                new ResponseEntity<>(device, HttpStatus.OK) : new ResponseEntity<>(device, HttpStatus.NOT_FOUND);
    }
}