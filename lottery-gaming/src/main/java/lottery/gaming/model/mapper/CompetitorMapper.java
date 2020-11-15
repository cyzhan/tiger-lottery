package lottery.gaming.model.mapper;

import lottery.gaming.model.vo.CompetitorVO;
import lottery.gaming.model.vo.CompetitorWrapper;
import lottery.gaming.model.vo.MainCompetitorRefVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CompetitorMapper {

    int addCompetitorRef(@Param("tournamentId") int tournamentId, @Param("ctitors") List<CompetitorVO> CompetitorVO);

    List<MainCompetitorRefVO> getMainCompetitorInfo(@Param("sportId") int sportId, @Param("competitors") int...competitorIds);

    @Update("UPDATE sport_info.${table} SET refId = #{refId} WHERE id = #{id}")
    int updateRefId(@Param("table")String table, @Param("id") int id, @Param("refId") int refId);

    @Delete("DELETE FROM sport_info.competitor WHERE id = #{mainCompetitorId}")
    int deleteCompetitor(@Param("mainCompetitorId") int mainCompetitorId);

}
