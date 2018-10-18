package com.mea.bean.constant;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 19:41 2018/10/10.
 */
public interface MesEmoAnaUrlConstant {
    String ROOT = "/mes/emo/ana/";

    interface MesEmoAna {
        String UPLOAD_DATA = "upload_data";
    }

    interface User {
        String ROOT = "/mes/emo/ana/user";
        String REGISTER = "/register";
        String LOGIN = "/login";
        String CHECK = "/check";
        String ADD = "/add";
    }
}