package com.vartime.easy.test.service.impl;

import com.vartime.easy.spring.boot.tk.mybatis.core.service.impl.AbstractBaseLogicServiceImpl;
import com.vartime.easy.test.entity.User;
import com.vartime.easy.test.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends AbstractBaseLogicServiceImpl<User> implements UserService {
}