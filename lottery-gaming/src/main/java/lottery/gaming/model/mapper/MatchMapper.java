package lottery.gaming.model.mapper;

import lottery.gaming.model.io.MatchHomeAwayUpdateIO;
import lottery.gaming.model.vo.MatchIdPair;
import lottery.gaming.model.vo.MatchVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MatchMapper {

    int updateSourceMatchRef(@Param("sourceTable") String sourceTable, int mainMatchId, int newRefId);

    int updateMatch(@Param("matchId1") int matchId1,@Param("matchVO2") MatchVO matchVO2);

    List<MatchVO> getMatches(int matchId1, int matchId2);

    @Delete("DELETE FROM inno_odds.refactor_prematch WHERE id = #{matchId}")
    int deleteMatch(int matchId);

    @Update("UPDATE inno_odds.refactor_prematch SET row_status = 2 WHERE id = #{matchId}")
    int softDelete(int matchId);

    List<MatchIdPair> getMergeCandidatePair(@Param("sportId") int sportId,
                                            @Param("date") String date,
                                            @Param("source1") String source1,
                                            @Param("source2") String source2);



    int matchHomeUpdate(@Param("matchHomeAwayUpdateIO") MatchHomeAwayUpdateIO matchHomeAwayUpdateIO);

    int matchAwayUpdate(@Param("matchHomeAwayUpdateIO") MatchHomeAwayUpdateIO matchHomeAwayUpdateIO);

}
