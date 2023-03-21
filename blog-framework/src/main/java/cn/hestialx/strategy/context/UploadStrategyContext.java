package cn.hestialx.strategy.context;

import cn.hestialx.enums.UploadStrategyEnum;
import cn.hestialx.strategy.UploadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;
import static cn.hestialx.enums.UploadStrategyEnum.getStrategy;

/**
 * @author lixu
 * @create 2023-02-28-15:49
 */

@Component
public class UploadStrategyContext {
    @Value("${upload.mode}")
    private String model;


    @Autowired
    private Map<String, UploadStrategy> uploadStrategyMap;

    /**
     * 执行上传策略
     *
     * @param file 文件
     * @param path 路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(MultipartFile file, String path) {
        return uploadStrategyMap.get(getStrategy(model)).uploadFile(file, path);
    }


    /**
     * 执行上传策略
     *
     * @param fileName    文件名称
     * @param inputStream 输入流
     * @param path        路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(String fileName, InputStream inputStream, String path) {
        return uploadStrategyMap.get(getStrategy(model)).uploadFile(fileName, inputStream, path);
    }

}
