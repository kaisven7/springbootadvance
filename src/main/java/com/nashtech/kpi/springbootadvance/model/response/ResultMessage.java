package com.nashtech.kpi.springbootadvance.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultMessage {

    private String statusCode;
    private String carName;
    private String message;


}
