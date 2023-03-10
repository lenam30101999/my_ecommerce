package com.demo.elk.dto.authentication;

import com.demo.elk.annotation.ValidateUsername;
import com.demo.elk.util.ToLowerCaseDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ForgotPwdDTO {

  @NotNull
  @ValidateUsername
  @JsonDeserialize(using = ToLowerCaseDeserializer.class)
  private String username;

  @JsonProperty("new_password")
  @NotNull
  @Size(min = 6, max = 50)
  private String newPassword;

  @JsonProperty("new_confirm_password")
  @NotNull
  @Size(min = 6, max = 50)
  private String newConfirmPassword;

  @NotNull private Integer otp;
}
