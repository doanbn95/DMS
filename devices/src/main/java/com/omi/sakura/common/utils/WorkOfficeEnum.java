package com.omi.sakura.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum WorkOfficeEnum implements Values {

    LAM_VIEC(2, "Thiết bị làm việc"),

    VAN_PHONG(4, "Thiết bị văn phòng");

    private static final Map<Integer, String> BY_ATOMIC_NUMBER = new HashMap<>();

    static {
        for (WorkOfficeEnum e : values()) {
            BY_ATOMIC_NUMBER.put(e.getCode(), e.getText());
        }
    }

    private int code;

    private String text;

}
