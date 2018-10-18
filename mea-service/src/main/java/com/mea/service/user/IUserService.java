package com.mea.service.user;

import com.mea.bean.model.user.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 15:33 2018/10/12.
 */
public interface IUserService {
    /**
     * log interface
     *
     * @param name name
     * @param pwd  password
     */
    void login(String name, String pwd);

    /**
     * register
     *
     * @param user register user info
     */
    void register(UserModel user);

    /**
     * list all users
     *
     * @return all users
     */
    List<UserModel> listUsers();
}