package cn.org.easysite.test.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import cn.org.easysite.spring.boot.tk.mybatis.core.entity.BaseLogicEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Table(name = "t_user")
public class User extends BaseLogicEntity {
    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "user_name")
    private String userName;
}
