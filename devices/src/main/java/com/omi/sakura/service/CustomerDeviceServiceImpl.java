package com.omi.sakura.service;

import com.omi.sakura.common.utils.DateUtils;
import com.omi.sakura.common.utils.DeleteEnum;
import com.omi.sakura.common.utils.MasterTypeEnum;
import com.omi.sakura.persistent.domain.CustomerDevice;
import com.omi.sakura.persistent.domain.Device;
import com.omi.sakura.persistent.domain.Staff;
import com.omi.sakura.persistent.domain.UserPrincipal;
import com.omi.sakura.persistent.repository.CustomerDeviceRepository;
import com.omi.sakura.persistent.repository.DeviceRepository;
import com.omi.sakura.persistent.repository.StaffRepository;
import com.omi.sakura.request.CustomerDeviceRequest;
import com.omi.sakura.request.MasterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDeviceServiceImpl implements CustomerDeviceService {

    @Autowired
    private CustomerDeviceRepository customerDeviceRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private MasterService masterService;

    @Autowired
    private CommonService commonService;

    @Override
    public List<CustomerDevice> findAll() {
        return customerDeviceRepository.findByDeleteStatusIs(DeleteEnum.False.getStatus());
    }

    @Override
    public CustomerDeviceRequest edit(Long id) {
        CustomerDevice customerDevice = customerDeviceRepository.getOne(id);
        CustomerDeviceRequest customerDeviceRequest = new CustomerDeviceRequest();
        customerDeviceRequest.setId(customerDevice.getId());
        customerDeviceRequest.setDeviceCode(customerDevice.getDevice().getCode());
        customerDeviceRequest.setHandOverDay(DateUtils.convertDateToString(customerDevice.getDevice().getHandTime()));
        customerDeviceRequest.setCustomerName(customerDevice.getCustomerName());
        customerDeviceRequest.setStaffEmail(customerDevice.getStaff().getEmail());
        customerDeviceRequest.setStaffName(customerDevice.getStaff().getName());
        customerDeviceRequest.setStaffDepartment(customerDevice.getStaff().getDepartment());
        customerDeviceRequest.setDescription(customerDevice.getDevice().getDescription());
        customerDeviceRequest.setCodeOld(customerDevice.getDevice().getCode());
        customerDeviceRequest.setCreatePerson(customerDevice.getDevice().getCreatePerson());
        customerDeviceRequest.setEditPerson(customerDevice.getDevice().getEditPerson());
        return customerDeviceRequest;
    }


    @Override
    public void create(CustomerDeviceRequest request) {
        Device device = new Device(request);
        device.setCreatePerson(commonService.getUserName());
        Staff flag = staffRepository.findByEmail(request.getStaffEmail());
        Staff staff = flag == null ? new Staff(request) : flag;
        staff.setDepartment(request.getStaffDepartment());
        staff.setName(request.getStaffName());
        MasterRequest masterRequest = MasterRequest.builder()
                .type(MasterTypeEnum.BOPHAN.getCode())
                .value(request.getStaffDepartment().trim())
                .build();

        device = deviceRepository.save(device);
        staff = staffRepository.save(staff);
        masterService.create(masterRequest);
        CustomerDevice customerDevice = new CustomerDevice();
        customerDevice.setDeviceId(device.getId());
        customerDevice.setStaffId(staff.getId());
        customerDevice.setDeleteStatus(2);
        customerDevice.setStaff(staff);
        customerDevice.setDevice(device);
        customerDevice.setCustomerName(request.getCustomerName());
        customerDeviceRepository.save(customerDevice);
    }

    @Override
    public void update(CustomerDeviceRequest customerDeviceRequest) {
        CustomerDevice customerDevice = customerDeviceRepository.getOne(customerDeviceRequest.getId());
        Device device = customerDevice.getDevice();
        device.setCode(customerDeviceRequest.getDeviceCode());
        device.setCreatePerson(customerDevice.getDevice().getCreatePerson());
        device.setEditPerson(commonService.getUserName());
        device.setDescription(customerDeviceRequest.getDescription());
        device.setHandTime(DateUtils.convertToDate(customerDeviceRequest.getHandOverDay()));
        Staff staff = staffRepository.findByEmail(customerDeviceRequest.getStaffEmail());
        if (staff != null) {
            staff.setEmail(customerDeviceRequest.getStaffEmail());
            staff.setName(customerDeviceRequest.getStaffName());
            staff.setDepartment(customerDeviceRequest.getStaffDepartment());
        } else {
            staff = new Staff();
            staff.setEmail(customerDeviceRequest.getStaffEmail());
            staff.setName(customerDeviceRequest.getStaffName());
            staff.setDepartment(customerDeviceRequest.getStaffDepartment());
        }
        deviceRepository.saveAndFlush(device);
        staffRepository.saveAndFlush(staff);
        customerDevice.setDevice(device);
        customerDevice.setStaff(staff);
        customerDevice.setCustomerName(customerDeviceRequest.getCustomerName());
        customerDeviceRepository.save(customerDevice);
    }

    @Override
    public void delete(Long id) {
        CustomerDevice customerDevice = customerDeviceRepository.getOne(id);
        Device device = deviceRepository.getOne(customerDevice.getDeviceId());
        device.setDeleteStatus(1);
        deviceRepository.save(device);
        customerDevice.setDevice(device);
        customerDevice.setDeleteStatus(1);
        customerDeviceRepository.save(customerDevice);
    }

}