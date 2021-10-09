package com.demo.elk.entity.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "voucher_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "voucher_code")
    private String voucherCode;

    private String label;
}
