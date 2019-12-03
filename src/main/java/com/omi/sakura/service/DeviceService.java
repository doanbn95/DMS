package com.omi.sakura.service;

import com.omi.sakura.persistent.domain.Device;
import com.omi.sakura.request.DeviceRequest;
import com.omi.sakura.response.DeviceReturnDateResponse;
import com.omi.sakura.response.TypeCountResponse;

import java.util.List;

public interface DeviceService {

    List<Device> findAll();

    List<DeviceReturnDateResponse> findByReturnDate();

    List<Device> findAllDeviceByDetailType(String detailType);

    List<TypeCountResponse> countDeviceByType();

    DeviceRequest getDevice(String code);

}

