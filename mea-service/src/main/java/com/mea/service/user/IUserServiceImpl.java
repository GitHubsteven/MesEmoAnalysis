package com.mea.service.user;

import com.mea.bean.model.user.UserModel;
import com.mea.service.MeaBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 15:34 2018/10/12.
 */
@Service
public class IUserServiceImpl extends MeaBaseService implements IUserService {
    /**
     * log interface
     *
     * @param name name
     * @param pwd  password
     */
    @Override
    public void login(String name, String pwd) {
        System.out.println("============login:" + name);
    }

    /**
     * register
     *
     * @param user register user info
     */
    @Override
    public void register(UserModel user) {

    }

    /**
     * list all users
     *
     * @return all users
     */
    @Override
    public List<UserModel> listUsers() {
        return null;
    }
}