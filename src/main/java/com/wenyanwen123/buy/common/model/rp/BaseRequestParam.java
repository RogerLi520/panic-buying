package com.wenyanwen123.buy.common.model.rp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Desc 基础请求参数，控制层所有自定义参数类都要继承此类
 * @Author liww
 * @Date 2019/6/13
 * @Version 1.0
 */
@Data
@ApiModel(value = "BaseRequestParam", description = "基础请求参数")
public class BaseRequestParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分页大小", name = "pageSize")
    private Integer pageSize;

    @ApiModelProperty(value = "当前页", name = "currPage")
    private Integer currPage;

    @ApiModelProperty(value = "分类", name = "category")
    private byte category;

    @ApiModelProperty(value = "关键字", name = "keyword")
    private String keyword;

    @ApiModelProperty(value = "开始时间", name = "beginDate")
    private Integer beginDate;

    @ApiModelProperty(value = "结束时间", name = "endDate")
    private Integer endDate;

}
