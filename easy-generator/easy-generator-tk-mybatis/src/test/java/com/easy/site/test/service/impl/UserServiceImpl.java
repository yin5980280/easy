package cn.org.easysite.test.service.impl;

import cn.org.easysite.spring.boot.tk.mybatis.core.service.impl.AbstractBaseLogicServiceImpl;
import cn.org.easysite.test.entity.User;
import cn.org.easysite.test.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends AbstractBaseLogicServiceImpl<User> implements UserService {
}
