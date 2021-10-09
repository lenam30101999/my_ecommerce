package com.demo.elk.controller;

import com.demo.elk.service.AuthService;
import com.demo.elk.service.IRoleService;
import com.demo.elk.service.ShopService;
import com.demo.elk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired protected UserService userService;
    @Autowired protected AuthService authService;
    @Autowired protected ShopService shopService;
    @Autowired protected IRoleService iRoleService;
}
