package com.omi.sakura.common.utils;

public enum DeleteEnum {

    True(1),

    False(2);

    private int status;

    DeleteEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
