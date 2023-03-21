package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.CommonStatusDto;
import cn.hestialx.domain.dto.LinkAddDto;
import cn.hestialx.domain.dto.LinkListDto;
import cn.hestialx.entity.Link;
import cn.hestialx.service.LinkService;
import cn.hestialx.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-20-16:37
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @GetMapping("/list")
    public ResponseResult listAllLinkPages(int pageNum, int pageSize, LinkListDto linkListDto){
        return linkService.listAllLinkPages(pageNum,pageSize,linkListDto);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody LinkAddDto linkAddDto){
        return linkService.addLink(linkAddDto);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkAddDto linkAddDto){
        linkService.updateById(BeanCopyUtils.copyBean(linkAddDto, Link.class));
        return ResponseResult.okResult();
    }

    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody CommonStatusDto commonStatusDto){
        Link link = BeanCopyUtils.copyBean(commonStatusDto, Link.class);
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("{ids}")
    public ResponseResult deleteLinks(@PathVariable("ids") List<Long> ids){
        linkService.removeByIds(ids);
        return ResponseResult.okResult();
    }

}
