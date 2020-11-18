package lottery.gaming.domain.service;

import lottery.common.model.vo.ResultVO;
import lottery.gaming.model.io.CompetitorMergeIO;
import lottery.gaming.model.io.MatchHomeAwayUpdateIO;
import lottery.gaming.model.mapper.CompetitorMapper;
import lottery.gaming.model.response.data.CompetitorRefUpdateLog;
import lottery.gaming.model.vo.MainCompetitorRefVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CompetitorSubService {

    private static final Logger logger = LoggerFactory.getLogger(CompetitorSubService.class);

    @Autowired
    private CompetitorMapper competitorMapper;


    @Transactional(rollbackFor = Exception.class)
    public ResultVO mergeCompetitors(CompetitorMergeIO competitorMergeIO) {
        List<MainCompetitorRefVO> mainCompetitorRefVOSList =
                competitorMapper.getMainCompetitorInfo(competitorMergeIO.getSportId(), competitorMergeIO.getCompetitorId1(), competitorMergeIO.getCompetitorId2());
        if (mainCompetitorRefVOSList.size() != 2){
            return ResultVO.error(0, "聯數量大於2");
        }

        MainCompetitorRefVO vo1 = mainCompetitorRefVOSList.remove(mainCompetitorRefVOSList.get(0).getaId() != null ? 0 : 1);
        MainCompetitorRefVO vo2 = mainCompetitorRefVOSList.remove(0);
        if (vo1.getbId() != null || vo2.getaId() != null){
            return ResultVO.error(0, "複雜關聯");
        }

        int updateRow = competitorMapper.updateRefId("betradar_competitors", vo2.getbId(), vo1.getId());
        int deletedRow = competitorMapper.deleteCompetitor(vo2.getId());

        logger.info("updateRow = {}, deletedRow = {}", updateRow, deletedRow);
        mainCompetitorRefVOSList.add(vo1);
        mainCompetitorRefVOSList.add(vo2);
        CompetitorRefUpdateLog competitorRefUpdateLog = new CompetitorRefUpdateLog();
        competitorRefUpdateLog.setBeforeData(mainCompetitorRefVOSList);
        MainCompetitorRefVO updatedVO1 = competitorMapper.getMainCompetitorInfo(competitorMergeIO.getSportId(), vo1.getId()).get(0);
        competitorRefUpdateLog.setUpdatedData(updatedVO1);
        competitorRefUpdateLog.setDeletedData(vo2);
        return ResultVO.of(competitorRefUpdateLog);
    }

}
