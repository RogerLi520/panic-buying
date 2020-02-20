package com.wenyanwen123.buy.commons.parameter.rp.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 注册
 * @Author liww
 * @Date 2020/2/18
 * @Version 1.0
 */
@Data
@ApiModel(value = "RegisterRp", description = "注册请求参数")
public class RegisterRp {

    @ApiModelProperty(value = "手机号码", name = "phoneNum")
    private String phoneNum;

    @ApiModelProperty(value = "密码", name = "password")
    private String password;

}
