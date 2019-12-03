package com.omi.sakura.persistent.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "customer_device")
public class CustomerDevice {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", insertable = false,updatable = false)
    private Long deviceId;

    @Column(name = "staff_id", insertable = false,updatable = false)
    private Long staffId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name="delete_status")
    private int deleteStatus;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false)
    private Device device;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false)
    private Staff staff;

}