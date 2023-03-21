package cn.hestialx.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author lixu
 * @create 2023-02-28-17:26
 */
@Getter
public enum UploadPathEnum {
    avatar("avatar/","头像路径"),
    article("article/","文章图片");
    String path;
    String desc;

    UploadPathEnum(String path,String desc){
        this.path = path;
        this.desc = desc;
    }
}
