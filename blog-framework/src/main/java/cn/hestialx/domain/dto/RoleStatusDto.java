package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author lixu
 * @create 2023-03-19-11:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleStatusDto {
    @NotNull
    private Long roleId;
    @Pattern(regexp="[01]")
    @Length(min = 1,max = 1)
    private String status;
}
