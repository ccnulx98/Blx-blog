package cn.hestialx.utils;

import cn.hestialx.domain.vo.HotArticleVo;
import cn.hestialx.entity.Article;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lixu
 * @create 2023-02-17-15:57
 */
public class BeanCopyUtils {
    public static <T> T copyBean(Object resource,Class<T> clazz){
        try {
            T target = clazz.newInstance();
            BeanUtils.copyProperties(resource,target);
            return target;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <R,T> List<T> copyBean(List<R> resourceList,Class<T> tClazz){
        List<T> targetList = resourceList.stream()
                .map((a) -> copyBean(a, tClazz))
                .collect(Collectors.toList());
        return targetList;
    }
}
