package com.omi.sakura.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class MailRequest {

    @Pattern(regexp = "^[a-zA-Z0-9._]+@ominext.com$", message = "{user.name.notOmiMail}")
    private String email;

}
