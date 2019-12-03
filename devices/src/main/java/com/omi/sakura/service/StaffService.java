package com.omi.sakura.service;

import com.omi.sakura.persistent.domain.Staff;

import java.util.List;

public interface StaffService {

    List<Staff> findAll();

    Staff findStaff(String value, String screen);

}
