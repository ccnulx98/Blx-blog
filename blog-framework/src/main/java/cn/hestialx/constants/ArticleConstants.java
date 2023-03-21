package cn.hestialx.constants;

/**
 * @author lixu
 * @create 2023-02-17-15:48
 */
public class ArticleConstants {
    /**
     * 文章状态：
     * 正式发布的文章
     */
    public static int ARTICLE_STATUS_NORMAL = 0;
    /**
     * 文章状态：
     * 草稿
     */
    public static int ARTICLE_STATUS_DRAFT = 1;
    /**
     * redis文章访问量前缀
     *
     */
    public static String REDIS_VIEWCOUNT = "article:viewcount:";
    /**
     * 文章访问量锁前缀
     */
    public static String ARTICLE_VIEWCOUNT_LOCK = "article:viewcount:lock:";
    /**
     * 文章访问量锁value
     */
    public static String ARTICLE_VIEWCOUNT_LOCK_VALUE = "lock";
    /**
     * 文章访问量锁超时时间
     */
    public static Long ARTICLE_VIEWCOUNT_LOCK_EXPIRE = 10L;
}
