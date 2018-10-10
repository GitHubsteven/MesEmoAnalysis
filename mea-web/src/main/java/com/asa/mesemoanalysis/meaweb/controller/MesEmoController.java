package com.asa.mesemoanalysis.meaweb.controller;

import main.com.mea.bean.constant.MesEmoAnaUrlConstant;
import main.com.mea.bean.pojo.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 19:38 2018/10/10.
 */
@RestController
@RequestMapping(MesEmoAnaUrlConstant.Root)
public class MesEmoController {
    @RequestMapping(MesEmoAnaUrlConstant.MesEmoAna.UPLOAD_DATA)
    public BaseResponse uploadData() {
        return new BaseResponse();
    }
}