package com.demo.elk.service;

import com.demo.elk.dto.authentication.RoleDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRoleService {

    ResponseEntity<?> addNewRole(RoleDTO roleDTO);

    ResponseEntity<?> getAllRole();
}
