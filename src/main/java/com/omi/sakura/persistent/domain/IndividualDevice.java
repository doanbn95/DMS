package com.omi.sakura.persistent.domain;

import com.omi.sakura.common.utils.CapitalizeText;
import com.omi.sakura.common.utils.DateUtils;
import com.omi.sakura.request.IndividualDeviceRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "indual_devices")
@NoArgsConstructor
@AllArgsConstructor
public class IndividualDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "staff_id", insertable = false, updatable = false)
    private Long staffId;

    @Column(name = "code_device")
    private String codeDevice;

    @Column(name = "detail")
    private String detail;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "reason")
    private String reason;
    @Column(name = "create_person")
    private String createPerson;
    @Column(name = "edit_person")
    private String editPerson;
    @Column(name = "delete_status")
    private int deleteStatus;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false)
    private Staff staff;


    public IndividualDevice(IndividualDeviceRequest request) {
        this.id = null;
        this.codeDevice = request.getCodeDevice().trim();
        this.detail = CapitalizeText.formatText(request.getDetail().trim());
        this.startDate = StringUtils.isEmpty(request.getStartDate()) ? null : DateUtils.convertToDate(request.getStartDate());
        this.endDate = StringUtils.isEmpty(request.getEndDate()) ? null : DateUtils.convertToDate(request.getEndDate());
        this.reason = CapitalizeText.formatText(request.getReason());
        this.staffId = request.getStaffId();
    }

}
