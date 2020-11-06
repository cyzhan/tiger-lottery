package lottery.gaming.model.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TournamentMapper {

    @Select("SELECT taurnament_id \n" +
            "FROM betradar_tmp bt \n" +
            "where bt.updated = 0\n" +
            "order BY bt.id ASC \n" +
            "LIMIT 200;")
    List<Integer> getTournamentIds();

    @Update("UPDATE sport_info.betradar_tmp AS a SET a.updated = 1 WHERE a.taurnament_id=#{id} AND a.updated = 0")
    int update(@Param("id") int id);

}
