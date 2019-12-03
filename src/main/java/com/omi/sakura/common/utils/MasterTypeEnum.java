package com.omi.sakura.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public enum MasterTypeEnum implements Values {
    BOPHAN(1, "Bộ phận"),

    LAM_VIEC(2, "Thiết bị làm việc"),

    TEST(3, "Thiết bị test"),

    VAN_PHONG(4, "Thiết bị văn phòng"),

    TRANG_THAI(5, "Trạng thái");

    private static final Map<Integer, String> BY_ATOMIC_NUMBER = new HashMap<>();

    static {
        for (MasterTypeEnum e : values()) {
            BY_ATOMIC_NUMBER.put(e.getCode(), e.getText());
        }
    }

    private int code;

    private String text;

}
