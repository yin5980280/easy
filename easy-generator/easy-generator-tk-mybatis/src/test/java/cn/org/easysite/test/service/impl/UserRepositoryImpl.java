package cn.org.easysite.test.service.impl;

import org.springframework.stereotype.Service;

import cn.org.easysite.spring.boot.tk.mybatis.core.repository.impl.AbstractBaseLogicRepositoryImpl;
import cn.org.easysite.test.entity.User;
import cn.org.easysite.test.service.UserRepository;

@Service("userService")
public class UserRepositoryImpl extends AbstractBaseLogicRepositoryImpl<User> implements UserRepository {
}
