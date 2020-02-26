package com.wenyanwen123.buy.common.util;

/**
 * EhCache缓存空间
 */
public interface EhCacheSpaces {

    /**
     * 登录用户缓存空间
     */
    String LOGIN_USER_CACHE = "LoginUserCache";

    /**
     * 系统轮播
     */
    String SYT_BANNER = "SysBanner";

    /**
     * 系统配置缓存
     */
    String CONFIG_CACHE = "ConfigCache";

    /**
     * 数据字典缓存空间
     */
    String DICTIONARY_CACHE = "DictionaryCache";

    /**
     * 区域缓存空间
     */
    String REGION_CACHE = "SysRegion";

    /**
     * 短信验证码缓存空间
     */
    String SMS_CODE_CACHE = "SmsCodeCache";

    /**
     * 短信验证码缓存空间
     */
    String GOODS_PROPERTY_CACHE = "GoodsPropertyCache";

    /**
     * 系统好物推荐/设计师新品缓存空间
     */
    String GOODS_RECOMMEND_CACHE = "GoodsRecommendCache";

    /**
     * 系统推荐设计师缓存空间
     */
    String USER_RECOMMEND_CACHE = "UserRecommendCache";

    /**
     * 系统区域缓存空间
     */
    String SYT_REGION_CACHE = "SysRegionCache";

}
