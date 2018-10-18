package com.mea.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mea.bean.constant.MesEmoAnaUrlConstant;
import com.mea.bean.model.user.UserModel;
import com.mea.bean.pojo.JsonObj;
import com.mea.service.user.IUserService;
import com.mea.web.util.JacksonUtils;
import com.mea.web.util.RedisUtil;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2CodecSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 15:23 2018/10/12.
 */
@RestController
@RequestMapping(MesEmoAnaUrlConstant.User.ROOT)
public class UserController {
    private static final ObjectMapper OM = new ObjectMapper();

    @Autowired
    private IUserService iUserService;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(MesEmoAnaUrlConstant.User.LOGIN)
    public JsonObj login(@RequestBody Map<String, Object> params) {
        String name = (String) params.get("name");
        String password = (String) params.get("pwd");

        iUserService.login(name, password);
        JsonObj resp = new JsonObj();
        resp.setCode("1");
        resp.setData(name + "login ...");
        resp.setMessage("success");
        return resp;
    }

    @RequestMapping(MesEmoAnaUrlConstant.User.CHECK)
    public JsonObj check(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        Object user = redisUtil.get("user:" + id);
        JsonObj resp = new JsonObj();
        resp.setCode("1");
        resp.setData(user);
        resp.setMessage("success");
        return resp;
    }

    @RequestMapping(MesEmoAnaUrlConstant.User.ADD)
    public JsonObj add(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        UserModel user = new UserModel();
        user.setAge((int) (Math.random() * 10));
        user.setMale("f");
        user.setName("userName");
        user.setPassword("1234");
        redisUtil.set("user:" + id, JacksonUtils.bean2Json(user));
        JsonObj resp = new JsonObj();
        resp.setCode("1");
        resp.setMessage("success");
        return resp;
    }
}