package com.wenyanwen123.buy.commons.config;

import org.springframework.context.annotation.Configuration;

/**
 * 解决跨域问题springboot所需配置
 */
@Configuration
public class CORSConfiguration {

    /*@Bean
    public WebMvcConfigurer CORSConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://192.168.202.169:4444")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        // 设置是否允许跨域传cookie
                        .allowCredentials(true).exposedHeaders(HttpHeaders.SET_COOKIE)
                        // 设置缓存时间，减少重复响应
                        .maxAge(3600);
            }
        };
    }*/

    /*@Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置你要允许的网站域名，如果全允许则设为 *
        config.addAllowedOrigin("http://localhost:4200");
        // 如果要限制 HEADER 或 METHOD 请自行更改
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config); // new CorsFilter(source)
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        // 这个顺序很重要哦，为避免麻烦请设置在最前
        bean.setOrder(0);
        return bean;
    }*/

}
