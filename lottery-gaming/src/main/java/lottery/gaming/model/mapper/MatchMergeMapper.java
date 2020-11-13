package lottery.gaming.model.mapper;

import lottery.gaming.model.vo.MatchVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MatchMergeMapper {

    int updateSourceMatchRef(@Param("source") String source,int matchId, int refId);

    int updateMatch(@Param("match") MatchVO matchVO);

    List<MatchVO> getMatches(int matchId1, int matchId2);

    int deleteMatch(int matchId);

}
