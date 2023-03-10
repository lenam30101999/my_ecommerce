package com.demo.elk.entity.user;

import com.demo.elk.entity.role.Role;
import com.demo.elk.entity.types.State;
import com.demo.elk.entity.types.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @Column(name = "login_failure", columnDefinition = "0", nullable = false)
    private Integer loginFailure;

    @Column(name = "remote_address", columnDefinition = "0.0.0.0", nullable = false)
    private String remoteAddress;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<CreditCard> creditCards;
}
