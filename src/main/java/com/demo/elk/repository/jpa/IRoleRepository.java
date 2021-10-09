package com.demo.elk.repository.jpa;

import com.demo.elk.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);

    @Query("SELECT r FROM Role r WHERE r.name IN :names")
    Optional<List<Role>> findAllByNames(@Param("names") List<String> names);
}
