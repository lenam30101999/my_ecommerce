package com.demo.elk.controller;

import com.demo.elk.dto.authentication.RoleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseController {

    @CrossOrigin(maxAge = 3600, origins = "*")
    @PostMapping
    public ResponseEntity<?> addListRole(@RequestBody RoleDTO roleDTO) {
        return iRoleService.addNewRole(roleDTO);
    }

    @CrossOrigin(maxAge = 3600, origins = "*")
    @GetMapping
    public ResponseEntity<?> getAlRole() {
        return iRoleService.getAllRole();
    }

}
