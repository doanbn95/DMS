package com.omi.sakura.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class PasswordRequest {

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "{password.notValid}")
    private String newPassword;

}
