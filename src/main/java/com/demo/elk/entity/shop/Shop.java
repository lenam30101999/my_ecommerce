package com.demo.elk.entity.shop;

import com.demo.elk.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "shop")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "algorithm")
    private String algorithm;

    @Column(name = "total_count")
    private long totalCount;

    @OneToMany(mappedBy = "shop", orphanRemoval = true)
    private List<Item> items;
}
