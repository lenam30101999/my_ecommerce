package com.demo.elk.entity.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "add_on_deal_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddOnDealInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "add_on_deal_label")
    private String addOnDealLabel;

    @Column(name = "sub_type")
    private int subType;

    private int status;
}
