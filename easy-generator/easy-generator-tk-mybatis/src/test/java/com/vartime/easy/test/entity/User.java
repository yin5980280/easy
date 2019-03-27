package com.easy.site.test.entity;

import com.easy.site.spring.boot.tk.mybatis.core.entity.BaseLogicEntity;
import javax.persistence.*;
import lombok.*;

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
