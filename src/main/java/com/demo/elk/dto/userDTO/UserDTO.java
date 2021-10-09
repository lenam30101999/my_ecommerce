package com.demo.elk.dto.userDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String uid;

    private String username;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    @JsonProperty("full_name")
    private String fullName;

    private Set<String> roles;
}
