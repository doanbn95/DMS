package com.omi.sakura.service;

import com.omi.sakura.persistent.domain.IndividualDevice;
import com.omi.sakura.request.IndividualDeviceRequest;

import java.util.List;

public interface IndividualDeviceService {

    List<IndividualDevice> findAll();

    void create(IndividualDeviceRequest individualDeviceRequest);

    IndividualDeviceRequest edit(Long id);

    void update(IndividualDeviceRequest individualDeviceRequest);

    void delete(Long id);

}
