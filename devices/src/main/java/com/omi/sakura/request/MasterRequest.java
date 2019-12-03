package com.omi.sakura.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MasterRequest extends BaseRequest {

    private Long id;

    private String value;

    private int type;

}
