package com.demo.elk.repository.jpa;

import com.demo.elk.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User user);

    User findUserByEmail(String email);

    Optional<User> findById(int id);

    User findUserByUsernameOrPhoneNumberOrEmail(String username, String phoneNumber, String email);

    User findUserByUsername(String username);

    User findUserByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.id IN :user_ids")
    List<User> findUser(@Param("user_ids") List<Integer> userIds);

}
