package lottery.gaming.model.mapper;

import lottery.gaming.model.io.MatchHomeAwayUpdateIO;
import lottery.gaming.model.vo.MatchIdPair;
import lottery.gaming.model.vo.MatchVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MatchMapper {

    int updateSourceMatchRef(@Param("sourceTable") String sourceTable, int mainMatchId, int newRefId);

    int updateMatch(@Param("matchId1") int matchId1,@Param("matchVO2") MatchVO matchVO2);

    List<MatchVO> getMatches(int matchId1, int matchId2);

    int deleteMatch(int matchId);

    List<MatchIdPair> getMergeCandidatePair(@Param("sportId") int sportId, @Param("date") String date);

    int matchHomeUpdate(@Param("matchHomeAwayUpdateIO") MatchHomeAwayUpdateIO matchHomeAwayUpdateIO);

    int matchAwayUpdate(@Param("matchHomeAwayUpdateIO") MatchHomeAwayUpdateIO matchHomeAwayUpdateIO);

}
