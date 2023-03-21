package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.TagListDto;
import cn.hestialx.domain.dto.TagUpdateDto;
import cn.hestialx.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sg_tag(标签)】的数据库操作Service
* @createDate 2023-03-14 17:38:08
*/
public interface TagService extends IService<Tag> {

    ResponseResult listTagPage(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTags(List<Integer> ids);

    ResponseResult getTagById(Integer id);

    ResponseResult updateTagById(TagUpdateDto tagUpdateDto);

    ResponseResult getAllTags();
}
