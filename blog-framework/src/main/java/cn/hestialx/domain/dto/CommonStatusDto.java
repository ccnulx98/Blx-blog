package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author lixu
 * @create 2023-03-16-16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonStatusDto {
    @NotNull
    private Long id;
    @Pattern(regexp="[012]")
    @Length(min = 1,max = 1)
    private String status;
}
