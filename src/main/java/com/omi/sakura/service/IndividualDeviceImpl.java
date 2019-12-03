package com.omi.sakura.service;

import com.omi.sakura.common.utils.CapitalizeText;
import com.omi.sakura.common.utils.DateUtils;
import com.omi.sakura.common.utils.DeleteEnum;
import com.omi.sakura.common.utils.MasterTypeEnum;
import com.omi.sakura.persistent.domain.IndividualDevice;
import com.omi.sakura.persistent.domain.Staff;
import com.omi.sakura.persistent.domain.UserPrincipal;
import com.omi.sakura.persistent.repository.IndividualDeviceRepository;
import com.omi.sakura.persistent.repository.StaffRepository;
import com.omi.sakura.request.IndividualDeviceRequest;

import com.omi.sakura.request.MasterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class IndividualDeviceImpl implements IndividualDeviceService {

    @Autowired
    IndividualDeviceRepository individualDeviceRepository;
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    private MasterService masterService;

    @Autowired
    private CommonService commonService;

    @Override
    public List<IndividualDevice> findAll() {
        return individualDeviceRepository.findAllByDeleteStatus(DeleteEnum.False.getStatus());
    }

    @Override
    public void create(IndividualDeviceRequest request) {
        IndividualDevice individualDevice = new IndividualDevice(request);
        Staff flag = staffRepository.findByEmail(request.getStaffEmail());
        MasterRequest masterRequest = MasterRequest.builder()
                .type(MasterTypeEnum.BOPHAN.getCode())
                .value(request.getStaffDepartment().trim())
                .build();
        masterService.create(masterRequest);
        individualDevice.setCreatePerson(commonService.getUserName());
        individualDevice.setDeleteStatus(DeleteEnum.False.getStatus());
        if (flag == null) {
            Staff newStaff = new Staff();
            newStaff.setName(StringUtils.capitalize(request.getStaffName().trim()));
            newStaff.setDepartment(request.getStaffDepartment().trim());
            newStaff.setEmail(request.getStaffEmail().trim());
            newStaff = staffRepository.save(newStaff);
            individualDevice.setStaff(newStaff);
            individualDevice.setStaffId(newStaff.getId());
        } else {
            flag.setName(CapitalizeText.formatText(request.getStaffName().trim()));
            flag.setDepartment(CapitalizeText.formatText(request.getStaffDepartment().trim()));
            flag = staffRepository.save(flag);
            individualDevice.setStaffId(flag.getId());
            individualDevice.setStaff(flag);
        }
        individualDeviceRepository.save(individualDevice);
    }

    @Override
    public IndividualDeviceRequest edit(Long id) {


        IndividualDevice individualDevice = individualDeviceRepository.findOne(id);
        IndividualDeviceRequest individualDeviceRequest = new IndividualDeviceRequest();
        individualDeviceRequest.setId(individualDevice.getId());
        individualDeviceRequest.setCodeDevice(individualDevice.getCodeDevice());
        individualDeviceRequest.setCodeDeviceOld(individualDevice.getCodeDevice());
        individualDeviceRequest.setDetail(individualDevice.getDetail());
        individualDeviceRequest.setStaffId(individualDevice.getStaffId());
        individualDeviceRequest.setStaffDepartment(individualDevice.getStaff().getDepartment());
        individualDeviceRequest.setStaffName(individualDevice.getStaff().getName());
        individualDeviceRequest.setStaffEmail(individualDevice.getStaff().getEmail());
        individualDeviceRequest.setStartDate(DateUtils.convertDateToString(individualDevice.getStartDate()));
        individualDeviceRequest.setEndDate(DateUtils.convertDateToString(individualDevice.getEndDate()));
        individualDeviceRequest.setReason(individualDevice.getReason());
        individualDeviceRequest.setCreatePerson(individualDevice.getCreatePerson());
        individualDeviceRequest.setEditPerson(individualDevice.getEditPerson());
        return individualDeviceRequest;
    }

    @Override
    public void update(IndividualDeviceRequest individualDeviceRequest) {
        IndividualDevice individualDevice = new IndividualDevice();
        IndividualDevice flag1 = individualDeviceRepository.getOne(individualDeviceRequest.getId());
        individualDevice.setId(individualDeviceRequest.getId());
        individualDevice.setCodeDevice(individualDeviceRequest.getCodeDevice().trim());
        individualDevice.setDetail(StringUtils.capitalize(individualDeviceRequest.getDetail().trim()));
        individualDevice.setStartDate(DateUtils.convertToDate(individualDeviceRequest.getStartDate()));
        individualDevice.setEndDate(DateUtils.convertToDate(individualDeviceRequest.getEndDate()));
        individualDevice.setReason(StringUtils.capitalize(individualDeviceRequest.getReason().trim()));
        MasterRequest masterRequest = MasterRequest.builder()
                .type(MasterTypeEnum.BOPHAN.getCode())
                .value(individualDeviceRequest.getStaffDepartment().trim())
                .build();
        masterService.create(masterRequest);
        individualDevice.setEditPerson(commonService.getUserName());
        individualDevice.setCreatePerson(flag1.getCreatePerson());
        individualDevice.setDeleteStatus(flag1.getDeleteStatus());
        Staff flag = staffRepository.findByEmail(individualDeviceRequest.getStaffEmail());
        Staff staff = flag == null ? new Staff(individualDeviceRequest) : flag;
        if (flag == null) {
            staff.setName(individualDeviceRequest.getStaffName().trim());
            staff.setDepartment(individualDeviceRequest.getStaffDepartment().trim());
            staff.setEmail(individualDeviceRequest.getStaffEmail().trim());
        } else {
            staff.setId(flag.getId());
            staff.setEmail(flag.getEmail());
            staff.setName(individualDeviceRequest.getStaffName());
            staff.setDepartment(individualDeviceRequest.getStaffDepartment().trim());
        }
        staffRepository.save(staff);
        individualDevice.setStaff(staff);
        individualDevice.setStaffId(staff.getId());
        individualDeviceRepository.save(individualDevice);
    }

    @Override
    public void delete(Long id) {
        IndividualDevice device = individualDeviceRepository.getOne(id);
        device.setDeleteStatus(DeleteEnum.True.getStatus());
        individualDeviceRepository.save(device);
    }


}
