package cn.org.easysite.test.dao.mapper;

import cn.org.easysite.spring.boot.tk.mybatis.core.mapper.CommonMapper;
import cn.org.easysite.test.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends CommonMapper<User> {
}
