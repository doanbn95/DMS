package com.omi.sakura.service;

import com.omi.sakura.persistent.domain.CompanyDevice;
import com.omi.sakura.request.CompanyDeviceRequest;

import java.util.List;

public interface CompanyDeviceService {

    List<CompanyDevice> findWorkAndOffice();

    List<CompanyDevice> findTest();

    CompanyDeviceRequest edit(Long id);

    void create(CompanyDeviceRequest companyDeviceRequest);

    void update(CompanyDeviceRequest companyDeviceRequest);

    void delete(Long id);
}
