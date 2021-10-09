package com.demo.elk.entity.user;

import com.demo.elk.entity.BaseEntity;
import com.demo.elk.entity.types.BankName;
import com.demo.elk.entity.types.CreditType;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "credit_card")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "credit_card_number", nullable = false, unique = true)
    private String creditCardNumber;

    @Enumerated(EnumType.STRING)
    private BankName bankName;

    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    @Column(name = "total_money")
    private Long totalMoney;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

}
