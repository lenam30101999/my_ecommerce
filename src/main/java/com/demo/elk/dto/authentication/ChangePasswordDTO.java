package com.demo.elk.dto.authentication;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordDTO {
  @NotNull
  @Size(min = 6, max = 80)
  private String oldPassword;

  @NotNull
  @Size(min = 6, max = 80)
  private String newPassword;

  @NotNull
  @Size(min = 6, max = 80)
  private String confirmPassword;
}
