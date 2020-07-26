package lottery.gaming.model.io;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DemoUserIO extends PageIO{

    private Integer id;

    private String name;

    private Integer age;

    private String password;

    private long createTime;

    private long updateTime;

    private String email;

    private String phone;

}
