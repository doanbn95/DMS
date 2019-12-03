package com.omi.sakura.service;

import com.omi.sakura.common.utils.CapitalizeText;
import com.omi.sakura.common.utils.DeleteEnum;
import com.omi.sakura.common.utils.MasterTypeEnum;
import com.omi.sakura.persistent.domain.CompanyDevice;
import com.omi.sakura.persistent.domain.Device;
import com.omi.sakura.persistent.domain.Staff;
import com.omi.sakura.persistent.domain.UserPrincipal;
import com.omi.sakura.persistent.repository.CompanyDeviceRepository;
import com.omi.sakura.persistent.repository.DeviceRepository;
import com.omi.sakura.persistent.repository.StaffRepository;
import com.omi.sakura.request.CompanyDeviceRequest;
import com.omi.sakura.request.MasterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyDeviceServiceImpl implements CompanyDeviceService {

    @Autowired
    private CompanyDeviceRepository companyDeviceRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private MasterService masterService;

    @Autowired
    private CommonService commonService;

    @Override
    public List<CompanyDevice> findWorkAndOffice() {
        List<CompanyDevice> source = companyDeviceRepository.findAllByDeleteStatusOrderById(DeleteEnum.False.getStatus());
        List<CompanyDevice> result = new ArrayList<>();
        for (CompanyDevice value : source) {
            if (value.getDevice().getType() == 2 || value.getDevice().getType() == 4) {
                result.add(value);
            }
        }
        return result;
    }


    @Override
    public List<CompanyDevice> findTest() {
        List<CompanyDevice> source = companyDeviceRepository.findAllByDeleteStatusOrderById(DeleteEnum.False.getStatus());
        List<CompanyDevice> result = new ArrayList<>();
        for (CompanyDevice value : source) {
            if (value.getDevice().getType() == 3) {
                result.add(value);
            }
        }
        return result;
    }

    @Override
    public CompanyDeviceRequest edit(Long id) {
        CompanyDevice companyDevice = companyDeviceRepository.findOne(id);
        return new CompanyDeviceRequest(companyDevice);
    }


    @Override
    public void create(CompanyDeviceRequest request) {
        Device device = new Device(request);
        device.setCreatePerson(commonService.getUserName());
        Staff flag = staffRepository.findByEmail(request.getStaffEmail());
        Staff staff = flag == null ? new Staff(request) : flag;
        staff.setDepartment(request.getStaffDepartment().trim());
        staff.setName(request.getStaffName());
        device = deviceRepository.save(device);
        staff = staffRepository.save(staff);
        CompanyDevice companyDevice = new CompanyDevice();
        companyDevice.setDeviceId(device.getId());
        companyDevice.setStaffId(staff.getId());
        companyDevice.setStaff(staff);
        companyDevice.setDevice(device);
        companyDevice.setDeleteStatus(DeleteEnum.False.getStatus());
        MasterRequest masterRequest = MasterRequest.builder()
                .type(MasterTypeEnum.BOPHAN.getCode())
                .value(CapitalizeText.formatText(request.getStaffDepartment().trim()))
                .build();
        masterService.create(masterRequest);
        companyDeviceRepository.save(companyDevice);
    }

    @Override
    public void update(CompanyDeviceRequest request) {
        MasterRequest masterRequest = MasterRequest.builder()
                .type(MasterTypeEnum.BOPHAN.getCode())
                .value(CapitalizeText.formatText(request.getStaffDepartment().trim()))
                .build();
        masterService.create(masterRequest);
        CompanyDevice companyDevice = companyDeviceRepository.getOne(request.getId());
        Device device = new Device(request);
        device.setId(companyDevice.getDeviceId());
        device.setCreatePerson(companyDevice.getDevice().getCreatePerson());
        device.setEditPerson(commonService.getUserName());
        device.setDeleteStatus(companyDevice.getDevice().getDeleteStatus());
        Staff flag = staffRepository.findByEmail(request.getStaffEmail());
        Staff staff = flag == null ? new Staff(request) : flag;
        if (flag == null) {
            staff = new Staff(request);
        } else {
            staff.setId(flag.getId());
            staff.setEmail(flag.getEmail());
            staff.setName(request.getStaffName());
            staff.setDepartment(CapitalizeText.formatText(request.getStaffDepartment().trim()));
        }
        companyDevice.setDevice(device);
        companyDevice.setStaff(staff);
        deviceRepository.save(device);
        staffRepository.save(staff);
        companyDevice.setStaffId(staff.getId());
        companyDevice.setDeviceId(device.getId());
        companyDeviceRepository.save(companyDevice);
    }

    @Override
    public void delete(Long id) {
        CompanyDevice companyDevice = companyDeviceRepository.findOne(id);
        companyDevice.setDeleteStatus(DeleteEnum.True.getStatus());
        Device device = deviceRepository.findOne(companyDevice.getDeviceId());
        device.setDeleteStatus(DeleteEnum.True.getStatus());
        deviceRepository.save(device);
        companyDevice.setDevice(device);
        companyDeviceRepository.save(companyDevice);
    }

}
