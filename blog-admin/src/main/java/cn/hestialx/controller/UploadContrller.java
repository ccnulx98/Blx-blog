package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.enums.UploadPathEnum;
import cn.hestialx.strategy.context.UploadStrategyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lixu
 * @create 2023-02-28-17:05
 */
@RestController
@Slf4j
public class UploadContrller {
    @Autowired
    UploadStrategyContext uploadStrategyContext;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("img") MultipartFile multipartFile){
        log.info("Fill:{}",multipartFile);
        String path = uploadStrategyContext.executeUploadStrategy(multipartFile, UploadPathEnum.article.getPath());
        return ResponseResult.okResult(path);
    }


}
