package com.omi.sakura.response;

import com.omi.sakura.persistent.domain.CompanyDevice;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDeviceResponse {
    List<CompanyDevice> list;
    String role;
}
