package com.wenyanwen123.buy.commons.parameter.rp.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: TODO
 * @Author liww
 * @Date 2020/2/17
 * @Version 1.0
 */
@Data
@ApiModel(value = "LoginRp", description = "登陆请求参数")
public class LoginRp {

    @ApiModelProperty(value = "手机号码", name = "phoneNum")
    private String phoneNum;

    @ApiModelProperty(value = "密码", name = "password")
    private String password;

}
