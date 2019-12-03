package com.omi.sakura.service;

import com.omi.sakura.common.utils.DateUtils;
import com.omi.sakura.common.utils.DeleteEnum;
import com.omi.sakura.persistent.domain.Device;
import com.omi.sakura.persistent.repository.DeviceRepository;
import com.omi.sakura.request.DeviceRequest;
import com.omi.sakura.response.DeviceReturnDateResponse;
import com.omi.sakura.response.TypeCountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;


    @Override
    public List<Device> findAll() {
        return deviceRepository.findAllByDeleteStatusIs(DeleteEnum.False.getStatus());
    }

    @Override
    public List<Device> findAllDeviceByDetailType(String detailType) {
        return deviceRepository.findAllByDetailType(detailType);
    }

    @Override
    public List<DeviceReturnDateResponse> findByReturnDate() {
        List<DeviceReturnDateResponse> result = new ArrayList<>();
        List<Device> start = deviceRepository.findDeviceReturnDate(DateUtils.convertDateToString(DateUtils.getCurrentDate()));
        for (Device device : start) {
            DeviceReturnDateResponse response = DeviceReturnDateResponse.builder()
                    .id(device.getId())
                    .codeDevice(device.getCode())
                    .staffEmail(deviceRepository.getEmailByDeviceId(device.getId()))
                    .returnDate(device.getReturnDate())
                    .build();
            result.add(response);
        }
        return result;
    }


    public List<TypeCountResponse> countDeviceByType() {
        List<TypeCountResponse> result = new ArrayList<>();
        Object[][] totalOfType = deviceRepository.countByType();
        TypeCountResponse item;
        for (Object[] totalAvailable : totalOfType) {
            item = new TypeCountResponse();
            item.setType((String) totalAvailable[0]);
            item.setCountType(((BigInteger) totalAvailable[1]).intValue());
            result.add(item);
        }
        return result;
    }

    @Override
    public DeviceRequest getDevice(String code) {
        Device device = deviceRepository.findByCodeAndDeleteStatusIs(code, DeleteEnum.False.getStatus());
        return device == null ? null : new DeviceRequest(device);
    }
}
