package com.omi.sakura.request;

import com.omi.sakura.common.validation.FieldMatchHandTime;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@FieldMatchHandTime(first = "status", second = "handTime", message = "Vui lòng nhập ngày bàn giao")
public class CustomerDeviceRequest extends BaseRequest {

    private Long id;

    @NotBlank(message = "{customerDevice.deviceCode.notBlank}")
    @Size(max = 30, min = 5, message = "{customerDevice.codeDevice.Size}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{customerDevice.deviceCode.pattern}")
    private String deviceCode;

    @NotBlank(message = "{customerDevice.customerName.notBlank}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{customerDevice.customerName.pattern}")
    @Size(max = 30, min = 5, message = "{customerDevice.customerName.Size}")
    private String customerName;

    @NotBlank(message = "{customerDevice.description.notBlank}")
    @Size(min = 10, max = 100, message = "{individualDevice.detail.Size}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{customerDevice.description.pattern}")
    private String description;

    @NotBlank(message = "{customerDevice.staffEmail.notBlank}")
    @Size(max = 30, message = "{customerDevice.staffEmail.Size}")
    @Pattern(regexp = "^[a-zA-Z0-9_.]+@ominext.com$", message = "{customerDevice.staffEmail.notBlank}")
    private String staffEmail;

    @NotBlank(message = "{customerDevice.staffName.notBlank}")
    @Size(min = 5, max = 30, message = "{individualDevice.staffName.Size}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{customerDevice.staffName.pattern}")
    private String staffName;

    @NotBlank(message = "{customerDevice.department.notBlank}")
    @Size(max = 30, min = 3, message = "{individualDevice.staffDepartment.Size}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{customerDevice.staffDepartment.pattern}")
    private String staffDepartment;

    @NotBlank(message = "{customerDevice.handOverDay.notBlank}")
    private String handOverDay;

    private String codeOld;

    private Long staffId;
    private String createPerson;
    private String editPerson;

}