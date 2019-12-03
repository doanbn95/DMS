package com.omi.sakura.persistent.domain;

import com.omi.sakura.common.utils.CapitalizeText;
import com.omi.sakura.request.CompanyDeviceRequest;
import com.omi.sakura.request.CustomerDeviceRequest;
import com.omi.sakura.request.IndividualDeviceRequest;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "staff")
@Data
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "email")
    private String email;

    public Staff(CompanyDeviceRequest request) {
        this.name = CapitalizeText.convertText(request.getStaffName().trim());
        this.department = CapitalizeText.formatText(request.getStaffDepartment().trim());
        this.email = request.getStaffEmail().trim();
        this.id = request.getStaffId();
    }

    public Staff(CustomerDeviceRequest request) {
        this.name = CapitalizeText.convertText(request.getStaffName().trim());
        this.department = CapitalizeText.formatText(request.getStaffDepartment().trim());
        this.email = request.getStaffEmail().trim();
    }

    public Staff(IndividualDeviceRequest request) {
        this.name = CapitalizeText.convertText(request.getStaffName().trim());
        this.department = CapitalizeText.formatText(request.getStaffDepartment().trim());
        this.email = request.getStaffEmail().trim();
        this.id = request.getStaffId();
    }

    public Staff() {

    }
}
