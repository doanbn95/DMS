package com.omi.sakura.persistent.domain;

import com.omi.sakura.common.utils.CapitalizeText;
import com.omi.sakura.common.utils.DateUtils;
import com.omi.sakura.request.CompanyDeviceRequest;
import com.omi.sakura.request.CustomerDeviceRequest;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "devices")
public class Device {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;


    @Column(name = "type")
    private int type;

    @Column(name = "description")
    private String description;

    @Column(name = "type_detail")
    private String detailType;

    @Column(name = "status")
    private int status;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "provide_unit")
    private String provideUnit;

    @Column(name = "delete_status")
    private Integer deleteStatus;

    @Column(name = "cost_buy")
    private String buyCost;

    @Column(name = "buy_Date")
    private Date buyDate;

    @Column(name = "warranty_time")
    private Date warrantyTime;

    @Column(name = "hand_time")
    private Date handTime;

    @Column(name = "address_warranty")
    private String addressWarranty;
    @Column(name = "bought_cost")
    private String boughtCost;
    @Column(name = "bought_date")
    private Date boughtDate;
    @Column(name = "create_person")
    private String createPerson;
    @Column(name = "edit_person")
    private String editPerson;

    public Device(CompanyDeviceRequest request) {
        this.code = request.getCodeDevice().trim();
        this.type = Integer.parseInt(request.getTypeDevice());
        this.description = CapitalizeText.convertText(request.getDescription().trim());
        this.detailType = request.getDetailType().trim();
        this.status = Integer.parseInt(request.getStatus());
        this.returnDate = StringUtils.isEmpty(request.getReturnDate()) ? null : DateUtils.convertToDate(request.getReturnDate());
        this.provideUnit = CapitalizeText.convertText(request.getProvideUnit().trim());
        this.deleteStatus = 2;
        this.buyCost = request.getBuyCost();
        this.buyDate = StringUtils.isEmpty(request.getBuyDate()) ? null : DateUtils.convertToDate(request.getBuyDate());
        this.warrantyTime = StringUtils.isEmpty(request.getWarrantyTime()) ? null : DateUtils.convertToDate(request.getWarrantyTime());
        this.handTime = StringUtils.isEmpty(request.getHandTime()) ? null : DateUtils.convertToDate(request.getHandTime());
        this.addressWarranty = CapitalizeText.convertText(request.getAddressWarranty().trim());
        this.boughtCost = request.getBoughtCost();
        this.boughtDate = StringUtils.isEmpty(request.getBoughtDate()) ? null : DateUtils.convertToDate(request.getBoughtDate());
        this.createPerson = request.getCreatePerson();
        this.editPerson = request.getEditPerson();
    }

    public Device(CustomerDeviceRequest request) {
        this.code = request.getDeviceCode().trim();
        this.description = CapitalizeText.convertText(request.getDescription().trim());
        this.deleteStatus = 2;
        this.handTime = request.getHandOverDay() == null ? null : DateUtils.convertToDate(request.getHandOverDay());
    }
}

