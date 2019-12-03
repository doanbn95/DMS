package com.omi.sakura.persistent.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private int type;

}
