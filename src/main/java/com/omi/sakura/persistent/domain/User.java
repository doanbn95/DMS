package com.omi.sakura.persistent.domain;

import com.omi.sakura.common.utils.DeleteEnum;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "create_person")
    private String createPerson;

    @Column(name = "create_day")
    private Date createDay;

    @Column(name = "first_time")
    private Integer firstTime;

    @Column(name = "delete_status")
    private Integer deleteStatus;

}
