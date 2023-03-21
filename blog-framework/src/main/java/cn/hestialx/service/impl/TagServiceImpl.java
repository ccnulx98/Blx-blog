package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.TagListDto;
import cn.hestialx.domain.dto.TagUpdateDto;
import cn.hestialx.domain.vo.PageVo;
import cn.hestialx.domain.vo.TagVo;
import cn.hestialx.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.Tag;
import cn.hestialx.service.TagService;
import cn.hestialx.mapper.TagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sg_tag(标签)】的数据库操作Service实现
* @createDate 2023-03-14 17:38:08
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult listTagPage(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(tagListDto.getName()),Tag::getName,tagListDto.getName())
                .like(StringUtils.isNotBlank(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> tagPage = new Page<>(pageNum,pageSize);
        page(tagPage, queryWrapper);
        PageVo pageVo = new PageVo(tagPage.getRecords(),tagPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTags(List<Integer> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Integer id) {
        Tag tag = getById(id);
        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult updateTagById(TagUpdateDto tagUpdateDto) {
        Tag tag = BeanCopyUtils.copyBean(tagUpdateDto, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAllTags() {
        List<Tag> list = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBean(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

}




