package com.omi.sakura.request;

import com.omi.sakura.common.utils.DateUtils;
import com.omi.sakura.persistent.domain.Device;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceRequest {

    private Long id;

    private String code;

    private int type;

    private String description;

    private String detailType;

    private int status;

    private String returnDate;

    private String provideUnit;

    private String buyCost;

    private String buyDate;

    private String warrantyTime;

    private String handTime;

    private String addressWarranty;

    private String reasonWarranty;

    public DeviceRequest(Device device) {
        this.setCode(device.getCode());
        this.setType(device.getType());
        this.setDescription(device.getDescription());
        this.setDetailType(device.getDetailType());
        this.setStatus(device.getStatus());
        this.setReturnDate(device.getReturnDate() == null ? null : DateUtils.convertDateToString(device.getReturnDate()));
        this.setProvideUnit(device.getProvideUnit());
        this.setBuyCost(device.getBuyCost());
        this.setBuyDate(device.getBuyDate() == null ? null : DateUtils.convertDateToString(device.getBuyDate()));
        this.setWarrantyTime(device.getWarrantyTime() == null ? null : DateUtils.convertDateToString(device.getWarrantyTime()));
        this.setHandTime(device.getHandTime() == null ? null : DateUtils.convertDateToString(device.getHandTime()));
        this.setAddressWarranty(device.getAddressWarranty());
    }
}
