package com.nashtech.kpi.springbootadvance.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name= "error")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private String errorMessage;
    private List<String> details;

}
