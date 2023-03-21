package cn.hestialx.constants;

/**
 * @author lixu
 * @create 2023-02-17-15:48
 */
public class SystemConstants {
    /**
     * 博客前台系统
     * 用户信息 redis 前缀
     */
    public static String BLOG_USER_REDIS_PREFIX = "blog:user:";
    /**
     * 博客后台系统
     * 用户信息 redis 前缀
     */
    public static String BLOG_ADMIN_REDIS_PREFIX = "blog:admin:";
    /**
     * 通用正常状态
     */
    public static String SYSTEM_STATUS_NORMAL = "0";

    /**
     * 用户类型
     * 普通用户
     */
    public static String NORMAL_USRE_TYPE = "0";
    /**
     * 用户类型
     * 管理员用户
     */
    public static String ADMIN_USRE_TYPE = "1";
}
