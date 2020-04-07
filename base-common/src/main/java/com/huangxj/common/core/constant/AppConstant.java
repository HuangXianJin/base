package com.huangxj.common.core.constant;

/**
 * @ClassName AppConstant
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-07-24 15:30
 * @Version V1.0
 **/
public interface AppConstant {

    String SUPER_USER = "super";

    String APPLICATION_VERSION = "1.0";

    String BASE_PACKAGES = "com.huangxj.base";

    String APPLICATION_BASE_NAME = "base";

    String APPLICATION_SYSTEM_NAME = "system";

    String APPLICATION_GATEWAY_NAME = "gateway";
    /**
     * token有效期，默认12小时
     */
    int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 7;
    /**
     * token有效期，默认7天
     */
    int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 7;

    /**
     * 默认接口分类
     */
    public final static String DEFAULT_API_CATEGORY = "default";

    /**
     * 状态:0-无效 1-有效
     */
    public final static int ENABLED = 1;
    public final static int DISABLED = 0;

    /**
     * 状态:0-无效 1-有效
     */
    public final String ENABLED_STRING = "1";
    public final String DISABLED_STINRG = "0";
}
