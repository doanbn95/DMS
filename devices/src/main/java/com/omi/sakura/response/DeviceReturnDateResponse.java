package com.omi.sakura.response;

import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DeviceReturnDateResponse {

    private Long id;

    private String codeDevice;

    private String staffEmail;

    private Date returnDate;

}
