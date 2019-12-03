package com.omi.sakura.request;

import com.omi.sakura.common.validation.FieldMatchEndDate;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@FieldMatchEndDate(date1 = "startDate", date2 = "endDate", message = "{individualDevice.endDate.before}")
public class IndividualDeviceRequest extends BaseRequest {

    private Long id;

    @Size(min = 5, max = 30, message = "{individualDevice.staffName.Size}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{individualDevice.staffName.Pattern}")
    @NotBlank(message = "{individualDevice.staffName.notBlank}")
    private String staffName;

    @NotBlank(message = "{individualDevice.staffDepartment.notBlank}")
    @Size(max = 30, min = 3, message = "{individualDevice.staffDepartment.Size}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{individualDevice.staffDepartment.Pattern}")
    private String staffDepartment;

    @NotBlank(message = "{individualDevice.staffEmail.notBlank}")
    @Pattern(regexp = "^[a-zA-Z0-9_.]+@ominext.com", message = "{individualDevice.staffEmail.notMail}")
    @Size(max = 30, message = "{individualDevice.staffEmail.Size}")
    private String staffEmail;

    @NotBlank(message = "{individualDevice.codeDevice.notBlank}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{individualDevice.codeDevice.Pattern}")
    @Size(max = 30, min = 3, message = "{individualDevice.codeDevice.Size}")
    private String codeDevice;

    @NotBlank(message = "{individualDevice.detail.notBlank}")
    @Pattern(regexp = "^[^!@#$%^&*()?\":{}|<>]*$", message = "{individualDevice.detail.Pattern}")
    @Size(min = 10, max = 100, message = "{individualDevice.detail.Size}")
    private String detail;

    @NotBlank(message = "{individualDevice.startDate.notBlank}")
    private String startDate;

    @NotBlank(message = "{individualDevice.endDate.notBlank}")
    private String endDate;

    @NotBlank(message = "{individualDevice.reason.notBlank}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{individualDevice.reason.Pattern}")
    @Size(min = 10, max = 100, message = "{individualDevice.reason.Size}")
    private String reason;

    private Long staffId;

    private String codeDeviceOld;
    private String createPerson;
    private String editPerson;
}
