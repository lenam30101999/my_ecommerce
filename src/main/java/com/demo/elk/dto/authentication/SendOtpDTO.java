package com.demo.elk.dto.authentication;

import com.demo.elk.annotation.ValidateUsername;
import com.demo.elk.util.ToLowerCaseDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SendOtpDTO {
  @NotNull
  @Size(min = 6, max = 50)
  @ValidateUsername
  @JsonDeserialize(using = ToLowerCaseDeserializer.class)
  private String username;

  @JsonProperty("type")
  private String otpType;
}
