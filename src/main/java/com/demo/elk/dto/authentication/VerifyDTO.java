package com.demo.elk.dto.authentication;

import com.demo.elk.util.ToLowerCaseDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VerifyDTO {

  @NotNull
  @JsonDeserialize(using = ToLowerCaseDeserializer.class)
  private String username;

  @NotNull
  @JsonProperty("active_otp")
  private Integer activeOtp;
}
