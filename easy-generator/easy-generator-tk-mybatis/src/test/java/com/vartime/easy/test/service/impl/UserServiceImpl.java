package com.easy.site.test.service.impl;

import com.easy.site.spring.boot.tk.mybatis.core.service.impl.AbstractBaseLogicServiceImpl;
import com.easy.site.test.entity.User;
import com.easy.site.test.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends AbstractBaseLogicServiceImpl<User> implements UserService {
}
