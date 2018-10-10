package main.com.mea.bean.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 19:48 2018/10/10.
 */
@Setter
@Getter
public class BaseResponse implements Serializable {
    private String code;
    private String message;
    private Object data;
}