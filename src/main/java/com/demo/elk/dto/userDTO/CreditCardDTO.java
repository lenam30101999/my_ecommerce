package com.demo.elk.dto.userDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreditCardDTO {
    private int id;

    @Length(min = 10, max = 12, message = "Thẻ định dạng không đúng")
    @JsonProperty("credit_card_number")
    private String creditCardNumber;

    @NotNull
    @JsonProperty("bank_name")
    private String bankName;

    @NotNull
    @JsonProperty("credit_type")
    private String creditType;

    @JsonProperty("total_money")
    private Long totalMoney;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("created_at")
    private String createdAt;
}
