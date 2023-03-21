package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.TagListDto;
import cn.hestialx.domain.dto.TagUpdateDto;
import cn.hestialx.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-10-8:29
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult getTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.listTagPage(pageNum,pageSize,tagListDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult getAllTag(){
        return tagService.getAllTags();
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto listDto){
        return tagService.addTag(listDto);
    }

    @DeleteMapping("{ids}")
    public ResponseResult deleteTags(@PathVariable("ids") List<Integer> ids){
        return tagService.deleteTags(ids);
    }

    @GetMapping("{id}")
    public ResponseResult getTagById(@PathVariable("id")Integer id){
        return tagService.getTagById(id);
    }

    @PutMapping
    public ResponseResult updateTagById(@RequestBody TagUpdateDto tagUpdateDto){
        return tagService.updateTagById(tagUpdateDto);
    }


}
