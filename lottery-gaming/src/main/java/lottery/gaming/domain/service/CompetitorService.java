package lottery.gaming.domain.service;

import lottery.gaming.model.mapper.CompetitorMapper;
import lottery.gaming.model.vo.MainCompetitorRefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompetitorService {

    @Autowired
    private CompetitorMapper competitorMapper;

    public int mergeCompetitors(int sportId, int competitorId1, int competitorId2) {
        List<MainCompetitorRefVO> mainCompetitorRefVOSList = competitorMapper.getMainCompetitorInfo(sportId, competitorId1, competitorId2);
        if (mainCompetitorRefVOSList.size() != 2){
            return 0;
        }

        MainCompetitorRefVO vo1 = mainCompetitorRefVOSList.remove(mainCompetitorRefVOSList.get(0).getaId() != null ? 0 : 1);
        MainCompetitorRefVO vo2 = mainCompetitorRefVOSList.remove(0);
        if (vo1.getbId() != null || vo2.getaId() != null){
            return 0;
        }

        int updateRow = competitorMapper.updateRefId("betradar_competitors", vo2.getbId(), vo1.getId());
        int deletedRow = competitorMapper.deleteCompetitor(vo2.getId());
        return 1;
    }
}
