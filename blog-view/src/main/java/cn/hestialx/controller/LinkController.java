package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lixu
 * @create 2023-02-20-17:17
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult<List> listAllLinks(){
        return linkService.listAllPassLinks();
    }
}
