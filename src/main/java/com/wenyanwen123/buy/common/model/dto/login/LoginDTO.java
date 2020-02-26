package com.wenyanwen123.buy.common.model.dto.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 登陆
 * @Author liww
 * @Date 2020/2/17
 * @Version 1.0
 */
@Data
@ApiModel(value = "LoginDTO", description = "登陆参数")
public class LoginDTO {

    @ApiModelProperty(value = "手机号码", name = "phoneNum")
    private String phoneNum;

    @ApiModelProperty(value = "密码", name = "password")
    private String password;

}
