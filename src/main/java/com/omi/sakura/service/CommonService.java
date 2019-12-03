package com.omi.sakura.service;

import com.omi.sakura.common.utils.Constants;
import com.omi.sakura.common.utils.DeleteEnum;
import com.omi.sakura.persistent.domain.Device;
import com.omi.sakura.persistent.domain.Staff;
import com.omi.sakura.persistent.domain.UserPrincipal;
import com.omi.sakura.persistent.repository.DeviceRepository;
import com.omi.sakura.persistent.repository.IndividualDeviceRepository;
import com.omi.sakura.persistent.repository.StaffRepository;
import com.omi.sakura.request.*;
import com.omi.sakura.response.ErrorInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omi.sakura.response.IndualDeviceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CommonService {

    @Autowired
    private UserService userService;

    @Autowired
    private IndividualDeviceService individualDeviceService;

    @Autowired
    private CompanyDeviceService companyDeviceService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private IndividualDeviceRepository individualDeviceRepository;
    @Autowired
    private CustomerDeviceService customerDeviceService;

    public Map<String, Object> create(BaseRequest request, List<ErrorInfo> errorList, String screen) {

        Map<String, Object> map = new HashMap<String, Object>();

        if (CollectionUtils.isEmpty(errorList)) {
            switch (screen) {
                case Constants.USER:
                    userService.create((UserRequest) request);
                    break;
                case Constants.INDIVIDUALDEVICE:
                    individualDeviceService.create((IndividualDeviceRequest) request);
                    break;
                case Constants.COMPANYDEVICE:
                    companyDeviceService.create((CompanyDeviceRequest) request);
                    break;
                case Constants.CUSTOMERDEVICE:
                    customerDeviceService.create((CustomerDeviceRequest) request);
                    break;

            }
            map.put("status", 200);
            map.put("message", errorList);
            return map;
        } else {
            map.put("status", 101);
            map.put("message", errorList);
            return map;
        }
    }

    public Map<String, Object> update(BaseRequest request, List<ErrorInfo> errorList, String screen) {

        Map<String, Object> map = new HashMap<String, Object>();

        if (CollectionUtils.isEmpty(errorList)) {
            switch (screen) {
                case Constants.USER:
                    userService.update((UserRequest) request);
                    break;
                case Constants.INDIVIDUALDEVICE:
                    individualDeviceService.update((IndividualDeviceRequest) request);
                    break;
                case Constants.COMPANYDEVICE:
                    companyDeviceService.update((CompanyDeviceRequest) request);
                    break;
                case Constants.CUSTOMERDEVICE:
                    customerDeviceService.update((CustomerDeviceRequest) request);
                    break;
            }
            map.put("status", Constants.SUCCESS);
            return map;
        } else {
            map.put("status", Constants.ERROR);
            map.put("message", errorList);
            return map;
        }
    }

    public boolean checkCode(String code, String codeOld) {

        if (!StringUtils.isEmpty(code.trim())) {
            Device flag = deviceRepository.findByCodeAndDeleteStatusIs(code.trim(), DeleteEnum.False.getStatus());
            if (codeOld == null) {
                return flag == null;
            } else {
                return code.trim().equals(codeOld.trim()) || flag == null;
            }
        }
        return true;
    }

    public boolean checkCode(String code, String codeOld, String screen) {
        if (!StringUtils.isEmpty(code.trim())) {
            Object flag = new Object();
            if (Constants.INDIVIDUALDEVICE.equals(screen)) {
                flag = individualDeviceRepository.findByCodeDevice(code.trim());
            } else {
                flag = deviceRepository.findByCodeAndDeleteStatusIs(code.trim(), DeleteEnum.False.getStatus());
            }
            if (codeOld == null) {
                return flag == null;
            } else {
                return code.trim().equals(codeOld.trim()) || flag == null;
            }
        }
        return true;
    }

    public String getUserName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserPrincipal) principal).getUser().getName();
    }

    public String getRole() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserPrincipal) principal).getUser().getRole();
    }

    public Sort orderBy() {
        return new Sort(Sort.Direction.DESC, "id");
    }


}
