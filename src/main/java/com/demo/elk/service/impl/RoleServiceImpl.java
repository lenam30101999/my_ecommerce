package com.demo.elk.service.impl;

import com.demo.elk.dto.ResponseDTO;
import com.demo.elk.dto.authentication.RoleDTO;
import com.demo.elk.entity.role.Role;
import com.demo.elk.exception.MessageResponse;
import com.demo.elk.service.BaseService;
import com.demo.elk.service.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl extends BaseService implements IRoleService {

    @Override
    public ResponseEntity<?> addNewRole(RoleDTO roleDTO) {
        ResponseDTO response;
        List<String> roleNames = roleDTO.getRoleName();

        List<Role> roles =
                roleNames.stream().map(name -> Role.builder().name(name).build()).collect(Collectors.toList());
        roles = iRoleRepository.saveAll(roles);
        if (roles.isEmpty()) {
            response = new ResponseDTO(MessageResponse.ADD_NOT_SUCCESS, HttpStatus.BAD_REQUEST.value());
        }else {
            response = new ResponseDTO(MessageResponse.ADD_SUCCESS, HttpStatus.OK.value());
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAllRole() {
        List<Role> roles = iRoleRepository.findAll();
        ResponseDTO response = new ResponseDTO(roles, BLANK_CHARACTER, HttpStatus.OK.value(), BLANK_CHARACTER);
        return ResponseEntity.ok(response);
    }
}
