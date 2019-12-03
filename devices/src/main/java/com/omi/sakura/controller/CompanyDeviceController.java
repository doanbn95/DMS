package com.omi.sakura.controller;

import com.omi.sakura.common.utils.*;
import com.omi.sakura.persistent.domain.CompanyDevice;
import com.omi.sakura.persistent.domain.Master;
import com.omi.sakura.request.CompanyDeviceRequest;
import com.omi.sakura.response.CompanyDeviceResponse;
import com.omi.sakura.response.ErrorInfo;
import com.omi.sakura.service.CommonService;
import com.omi.sakura.service.CompanyDeviceService;
import com.omi.sakura.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/companydevice", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class CompanyDeviceController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private CompanyDeviceService companyDeviceService;
    @Autowired
    private MasterService masterService;


    @GetMapping("/list")
    public ModelAndView getList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("companydevice/list");
        return mav;
    }

    @GetMapping("/list/table")
    @ResponseBody
    public ResponseEntity<CompanyDeviceResponse> getTestDevice() {
        List<CompanyDevice> result = companyDeviceService.findWorkAndOffice();
        CompanyDeviceResponse response = new CompanyDeviceResponse();
        response.setList(result);
        response.setRole(commonService.getRole());
        return result.isEmpty() ? new ResponseEntity<>(response, HttpStatus.NO_CONTENT) : new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/create")
    public ModelAndView viewCrete() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("companydevice/create");
        mav.addObject("company", new CompanyDeviceRequest());
        mav.addObject("departments", masterService.findMasterByType(MasterTypeEnum.BOPHAN.getCode()));
        mav.addObject("typeDevices", WorkOfficeEnum.values());
        mav.addObject("statusDevices", StatusEnum.values());
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView viewEdit(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("companydevice/edit");
        mav.addObject("statusDevices", StatusEnum.values());
        mav.addObject("typeDevices", WorkOfficeEnum.values());
        mav.addObject("departments", masterService.findMasterByType(MasterTypeEnum.BOPHAN.getCode()));
        CompanyDeviceRequest company = companyDeviceService.edit(id);
        List<Master> detailTypeValues = new ArrayList<>();
        if (company.getTypeDevice() == TypeDeviceEnum.VAN_PHONG.getText()) {
            detailTypeValues = masterService.findMasterByType(MasterTypeEnum.VAN_PHONG.getCode());
        } else if (company.getTypeDevice() == TypeDeviceEnum.LAM_VIEC.getText()) {
            detailTypeValues = masterService.findMasterByType(MasterTypeEnum.LAM_VIEC.getCode());
        }
        mav.addObject("detailTypeValues", detailTypeValues);
        boolean flag = false;
        for (Master value : detailTypeValues) {
            if (value.getValue().equals(company.getDetailType())) {
                flag = true;
                break;
            }
        }
        mav.addObject("flag", flag);
        mav.addObject("company", company);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteDevice(@PathVariable Long id) {
        companyDeviceService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    public ResponseEntity getDetail(@PathVariable("id") Long id) {
        CompanyDeviceRequest request = companyDeviceService.edit(id);
        return request != null ?
                new ResponseEntity<>(request, HttpStatus.OK) : new ResponseEntity<>(request, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@Valid @RequestBody CompanyDeviceRequest iRequest,
                                      BindingResult result) {

        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }
        if (!commonService.checkCode(iRequest.getCodeDevice(), null, Constants.COMPANYDEVICE)) {
            errorList.add(new ErrorInfo("Mã thiết bị đã tồn tại", "codeDevice"));
        }
        return commonService.create(iRequest, errorList, Constants.COMPANYDEVICE);
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@Valid @RequestBody CompanyDeviceRequest request,
                                      BindingResult result) {

        List<ErrorInfo> errorList = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorList.add(new ErrorInfo(fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }
        if (!commonService.checkCode(request.getCodeDevice(), request.getCodeDeviceOld(), Constants.COMPANYDEVICE)) {
            errorList.add(new ErrorInfo("Mã thiết bị đã tồn tại", "codeDevice"));
        }
        return commonService.update(request, errorList, Constants.COMPANYDEVICE);
    }

}
