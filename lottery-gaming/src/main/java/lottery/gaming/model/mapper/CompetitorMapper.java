package lottery.gaming.model.mapper;

import lottery.gaming.model.io.CompetitorMergeIO;
import lottery.gaming.model.response.data.CompetitorRefUpdateLog;
import lottery.gaming.model.vo.CompetitorIdPairVO;
import lottery.gaming.model.vo.CompetitorVO;
import lottery.gaming.model.vo.MainCompetitorRefVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface CompetitorMapper {

    int addCompetitorRef(@Param("tournamentId") int tournamentId, @Param("ctitors") List<CompetitorVO> CompetitorVO);

    List<MainCompetitorRefVO> getMainCompetitorInfo(@Param("sportId") int sportId, @Param("competitors") int...competitorIds);

    @Update("UPDATE sport_info.${table} SET ref_id = #{refId} WHERE id = #{id}")
    int updateRefId(@Param("table")String table, @Param("id") int id, @Param("refId") int refId);

    @Delete("DELETE FROM sport_info.competitors WHERE id = #{competitorId}")
    int deleteCompetitor(@Param("competitorId") int competitorId);

    int insertMergeLog(List<Map<String, String>> logs);

    List<CompetitorMergeIO> getMergeCandidateByHomeDiff(@Param("sportId") int sportId,
                                                        @Param("date") String date,
                                                        @Param("source1") String source1,
                                                        @Param("source2") String source2);

    List<CompetitorMergeIO> getMergeCandidateByAwayDiff(@Param("sportId") int sportId,
                                                        @Param("date") String date,
                                                        @Param("source1") String source1,
                                                        @Param("source2") String source2);

}
