package com.omi.sakura.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest extends BaseRequest {

    private Long id;

    @NotBlank(message = "{user.mail.notBlank}")
    @Pattern(regexp = "^[a-zA-Z0-9._]+@ominext.com", message = "{user.name.notOmiMail}")
    private String userName;

    @NotBlank(message = "{user.role.notBlank}")
    private String role;

    @NotBlank(message = "{user.name.notBlank}")
    @Size(min = 5, max = 30, message = "{user.name.Size}")
    private String name;

    @NotBlank(message = "{user.department.notBlank}")
    @Size(min = 3, max = 30, message = "{user.department.Size}")
    private String department;

    @NotBlank(message = "{user.createPerson.notBlank}")

    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{user.createPerson.Pattern}")
    @Size(min = 3, max = 30, message = "{user.createPerson.Size}")
    private String createPerson;

    private String createDay;

    private String userNameOld;

}
