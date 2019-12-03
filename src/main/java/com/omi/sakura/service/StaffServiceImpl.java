package com.omi.sakura.service;

import com.omi.sakura.common.utils.Constants;
import com.omi.sakura.persistent.domain.Staff;
import com.omi.sakura.persistent.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Staff findStaff(String value, String screen) {
        Staff staff;
        if ("email".equals(screen)) {
            staff = staffRepository.findByEmail(value);
        } else {
            staff = null;
        }
        return staff;
    }
}
