package com.omi.sakura.service;

import com.omi.sakura.persistent.domain.CustomerDevice;
import com.omi.sakura.request.CustomerDeviceRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerDeviceService {

    List<CustomerDevice> findAll();

    void create(CustomerDeviceRequest customerDeviceForm);

    void delete(Long id);

    CustomerDeviceRequest edit(Long id);

    void update(CustomerDeviceRequest customerDeviceForm);

}