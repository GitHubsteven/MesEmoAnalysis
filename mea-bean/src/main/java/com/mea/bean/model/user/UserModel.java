package com.mea.bean.model.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 16:30 2018/10/12.
 */
@Setter
@Getter
public class UserModel implements Serializable {
    private String name;
    private String password;
    private String male;
    private Integer age;
}