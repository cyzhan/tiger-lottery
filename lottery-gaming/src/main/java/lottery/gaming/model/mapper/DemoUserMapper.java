package lottery.gaming.model.mapper;

import lottery.gaming.model.io.DemoUserIO;
import lottery.gaming.model.io.PageIO;
import lottery.gaming.model.po.DemoUserPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DemoUserMapper {

    int addUser(DemoUserIO demoUserPO);

    @Select("SELECT a.*, b.* FROM sandbox.user AS a inner join sandbox.user_info AS b on a.id = b.user_id limit #{offset}, #{pageSize}")
    List<DemoUserPO> getUsers(PageIO pageIO);

    @Select("SELECT a.*, b.* FROM sandbox.user AS a inner join sandbox.user_info AS b on a.id = b.user_id where a.id = #{id}")
    DemoUserPO getUser(@Param("id") Integer id);

}
