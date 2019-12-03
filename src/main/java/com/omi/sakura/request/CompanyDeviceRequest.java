package com.omi.sakura.request;

import com.omi.sakura.common.utils.DateUtils;
import com.omi.sakura.common.utils.StatusEnum;
import com.omi.sakura.common.utils.TypeDeviceEnum;
import com.omi.sakura.common.validation.*;
import com.omi.sakura.persistent.domain.CompanyDevice;
import lombok.*;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldMatchReturnDate(first = "status", second = "typeDevice", three = "returnDate", message = "Vui lòng nhập ngày trả")
@FieldMatchBuyCost(first = "status", second = "buyCost", message = "Vui lòng nhập giá thanh lý")
@FieldMatchBuyDate(first = "status", second = "buyDate", message = "Vui lòng nhập ngày thanh lý")
@FieldMatchHandTime(first = "status", second = "handTime", message = "Vui lòng nhập ngày bàn giao")
@FeildMatchCharacterSpec(first = "status", second = "buyCost", message = "Vui lòng nhập lại giá thanh lý")
@FieldMatchReturnDateEqualsThanHandTime(first = "status", second = "typeDevice", three = "handTime", four = "returnDate", message = "Ngày trả sau hoặc bằng ngày bàn giao")
public class CompanyDeviceRequest extends BaseRequest {

    private Long id;

    @Size(max = 30, min = 3, message = "{companyDevice.codeDevice.Size}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{companyDevice.codeDevice.Pattern}")
    @NotBlank(message = "Vui lòng nhập mã tài sản")
    private String codeDevice;

    @NotBlank(message = "{companyDevice.typeDevice.notBlank}")
    private String typeDevice;

    @NotEmpty(message = "{companyDevice.detailType.notBlank}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{companyDevice.detailType.Pattern}")
    @Size(max = 30, message = "{companyDevice.detailType.Size}")
    private String detailType;

    @NotEmpty(message = "{companyDevice.description.notBlank}")
    @Pattern(regexp = "^[^!@#$%^&*()?\":{}|<>]*$", message = "{companyDevice.description.Pattern}")
    @Size(max = 100, min = 10, message = "{companyDevice.description.Size}")
    private String description;

    @NotBlank(message = "{companyDevice.provideUnit.notBlank}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{companyDevice.provideUnit.Pattern}")
    @Size(max = 30, min = 3, message = "{companyDevice.provideUnit.Size}")
    private String provideUnit;

    private String buyCost;

    private String buyDate;
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{companyDevice.addressWaranty.Pattern}")
    private String addressWarranty;

    @NotBlank(message = "{companyDevice.status.notBlank}")
    private String status;

    private String returnDate;

    private String warrantyTime;

    private String handTime;
    private String createPerson;
    private String editPerson;

    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{companyDevice.staffName.Pattern}")
    @Size(max = 30, min = 5, message = "{companyDevice.staffName.Size}")
    @NotEmpty(message = "{companyDevice.staffName.notBlank}")
    private String staffName;

    @NotEmpty(message = "{companyDevice.staffDepartment.notBlank}")
    @Pattern(regexp = "^[^!@#$%^&*(),.?\":{}|<>]*$", message = "{companyDevice.staffDepartment.Pattern}")
    @Size(min = 3, max = 30, message = "{companyDevice.staffDepartment.Size}")
    private String staffDepartment;


    @NotEmpty(message = "{companyDevice.staffEmail.notBlank}")
    @Pattern(regexp = "^[a-zA-Z0-9_.]+@ominext.com$", message = "{companyDevice.staffEmail.notMail}")
    @Size(max = 30, message = "{companyDevice.staffEmail.Size}")
    private String staffEmail;

    private String codeDeviceOld;

    private Long staffId;

    private String boughtCost;

    private String boughtDate;

    private int deleteStatus;

    public CompanyDeviceRequest(CompanyDevice device) {
        this.id = device.getId();
        this.codeDevice = device.getDevice().getCode();
        this.typeDevice = TypeDeviceEnum.textOfCode(device.getDevice().getType());
        this.detailType = device.getDevice().getDetailType();
        this.description = device.getDevice().getDescription();
        this.provideUnit = device.getDevice().getProvideUnit();
        this.buyCost = device.getDevice().getBuyCost();
        this.buyDate = device.getDevice().getBuyDate() == null ? null : DateUtils.convertDateToString(device.getDevice().getBuyDate());
        this.addressWarranty = device.getDevice().getAddressWarranty();
        this.status = StatusEnum.textOfCode(device.getDevice().getStatus());
        this.returnDate = device.getDevice().getReturnDate() == null ? null : DateUtils.convertDateToString(device.getDevice().getReturnDate());
        this.warrantyTime = device.getDevice().getWarrantyTime() == null ? null : DateUtils.convertDateToString(device.getDevice().getWarrantyTime());
        this.handTime = device.getDevice().getHandTime() == null ? null : DateUtils.convertDateToString(device.getDevice().getHandTime());
        this.staffName = device.getStaff().getName();
        this.staffDepartment = device.getStaff().getDepartment();
        this.staffEmail = device.getStaff().getEmail();
        this.codeDeviceOld = device.getDevice().getCode();
        this.staffId = device.getStaffId();
        this.boughtCost = device.getDevice().getBoughtCost();
        this.boughtDate = device.getDevice().getBoughtDate() == null ? null : DateUtils.convertDateToString(device.getDevice().getBoughtDate());
        this.editPerson = device.getDevice().getEditPerson();
        this.createPerson = device.getDevice().getCreatePerson();
        this.deleteStatus = device.getDeleteStatus();
    }
}
