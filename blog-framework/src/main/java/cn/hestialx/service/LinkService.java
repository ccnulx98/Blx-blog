package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.LinkAddDto;
import cn.hestialx.domain.dto.LinkListDto;
import cn.hestialx.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2023-02-20 17:14:45
*/
public interface LinkService extends IService<Link> {

    ResponseResult<List> listAllPassLinks();

    ResponseResult listAllLinkPages(int pageNum, int pageSize, LinkListDto linkListDto);

    ResponseResult addLink(LinkAddDto linkAddDto);

}
