package lottery.gaming.model.mapper;

import lottery.gaming.model.vo.CompetitorVO;
import lottery.gaming.model.vo.CompetitorWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitorMapper {

    int addCompetitorRef(@Param("tournamentId") int tournamentId, @Param("competitors") List<CompetitorVO> CompetitorVO);

}
