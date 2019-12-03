package com.omi.sakura.response;

import com.omi.sakura.persistent.domain.CustomerDevice;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDeviceResponse {
    List<CustomerDevice> customerDeviceList;
    String role;
}
