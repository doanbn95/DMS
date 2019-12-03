package com.omi.sakura.common.utils;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeDeviceEnum implements Values {

    TEST(3, "Thiết bị test"),

    LAM_VIEC(2, "Thiết bị làm việc"),

    VAN_PHONG(4, "Thiết bị văn phòng");

    private static final Map<Integer, String> BY_ATOMIC_NUMBER = new HashMap<>();

    static {
        for (TypeDeviceEnum e : values()) {
            BY_ATOMIC_NUMBER.put(e.getCode(), e.getText());
        }
    }

    private int code;

    private String text;

    public static String textOfCode(int code) {
        return BY_ATOMIC_NUMBER.get(code);
    }

}
