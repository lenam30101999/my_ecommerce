package com.demo.elk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {
    public Object data;

    public Object message;

    @JsonProperty("status_code")
    public Object statusCode;

    public Object description;

    public ResponseDTO(Object message, Object statusCode) {
        this.data = "";
        this.message = message;
        this.statusCode = statusCode;
        this.description = "";
    }
}
