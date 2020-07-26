package lottery.gaming.model.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DemoUserPO {

    private Integer id;

    private String name;

    @Min(value = 18, message = "out of range")
    private Integer age;

    @Length(min = 8, max = 30, message = "dfadff")
    private String password;

    private Long createTime;

    private Long updateTime;

    private String email;

    private String phone;

}
