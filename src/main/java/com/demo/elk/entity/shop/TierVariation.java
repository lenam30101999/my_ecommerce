package com.demo.elk.entity.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "tier_variation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TierVariation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "tierVariation")
    private List<Option> options;

    @JoinColumn(name = "item_basic_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private ItemBasic itemBasic;
}
