package com.demo.elk.dto.userDTO;

import com.demo.elk.entity.types.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountStateDTO {

    @JsonProperty("user_id")
    private int userId;

    private State state;
}
