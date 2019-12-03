package com.omi.sakura.common.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum implements Values {

    MOI(1, "Mới"),
    DANG_SU_DUNG(2, "Đang sử dụng"),
    LUU_KHO(3, "Lưu kho"),
    BAO_HANH(4, "Đang bảo hành"),
    THANH_LY(5, "Thanh lý");

    private static final Map<Integer, String> BY_ATOMIC_NUMBER = new HashMap<>();

    static {
        for (StatusEnum e : values()) {
            BY_ATOMIC_NUMBER.put(e.getCode(), e.getText());
        }
    }

    private final int code;

    private final String text;

    public static String textOfCode(int code) {
        return BY_ATOMIC_NUMBER.get(code);
    }

}
