package cn.hestialx.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lixu
 * @create 2023-02-15-20:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "热点文章展示VO")
public class HotArticleVo {
    private Long id;
    /**
     * 标题
     */
    @ApiModelProperty(notes = "文章标题")
    private String title;
    /**
     * 访问量
     */
    @ApiModelProperty(notes = "访问量")
    private Long viewCount;
}
