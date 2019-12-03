package com.omi.sakura.response;

import com.omi.sakura.persistent.domain.IndividualDevice;
import lombok.Data;

import java.util.List;

@Data
public class IndualDeviceResponse {
    List<IndividualDevice> list;
    String role;
}

