package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.constants.LinkConstants;
import cn.hestialx.domain.dto.LinkAddDto;
import cn.hestialx.domain.dto.LinkListDto;
import cn.hestialx.domain.vo.LinkVo;
import cn.hestialx.domain.vo.PageVo;
import cn.hestialx.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.Link;
import cn.hestialx.service.LinkService;
import cn.hestialx.mapper.LinkMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2023-02-20 17:14:45
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult<List> listAllPassLinks() {
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.eq(Link::getStatus, LinkConstants.LINK_STATUS_PASSED);
        List<Link> list = list(linkLambdaQueryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBean(list, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult listAllLinkPages(int pageNum, int pageSize, LinkListDto linkListDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(linkListDto.getStatus()),Link::getStatus,linkListDto.getStatus())
                .like(StringUtils.isNotBlank(linkListDto.getName()),Link::getName,linkListDto.getName());
        Page<Link> linkPage = new Page<>(pageNum, pageSize);
        page(linkPage,queryWrapper);
        PageVo pageVo = new PageVo(linkPage.getRecords(), linkPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(LinkAddDto linkAddDto) {
        Link link = BeanCopyUtils.copyBean(linkAddDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }
}




