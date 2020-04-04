package cn.org.easysite.test.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.org.easysite.spring.boot.tk.mybatis.core.mapper.CommonMapper;
import cn.org.easysite.test.entity.User;

@Mapper
public interface UserMapper extends CommonMapper<User> {
}
