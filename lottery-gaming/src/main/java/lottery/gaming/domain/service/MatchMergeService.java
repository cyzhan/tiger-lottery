package lottery.gaming.domain.service;

import lottery.gaming.domain.controller.MatchMergeController;
import lottery.gaming.model.mapper.MatchMergeMapper;
import lottery.gaming.model.vo.MatchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MatchMergeService {

    private static final Logger logger = LoggerFactory.getLogger(MatchMergeService.class);

    @Autowired
    private MatchMergeMapper matchMergeMapper;

    @Transactional
    public int doMerge(int matchId1, int matchId2){
        List<MatchVO> matchVOs = matchMergeMapper.getMatches(matchId1, matchId2);
        MatchVO matchVO1 = matchVOs.get(0).getId() == matchId1? matchVOs.remove(0):matchVOs.remove(1);
        MatchVO matchVO2 = matchVOs.remove(0);
        if ((matchVO1.getTournamentId() != matchVO2.getTournamentId())){
            return 0;
        }
        if (matchVO1.getHomeId() != matchVO2.getHomeId()){
            return 0;
        }
        if (matchVO1.getAwayId() != matchVO2.getAwayId()){
            return 0;
        }
        if (matchVO1.getScheduled() != matchVO2.getScheduled()){
            return 0;
        }
        if (matchVO1.getBet188Id() != null && matchVO2.getBet188Id() != null){
            return 0;
        }
        if (matchVO1.getBetradarId() != null && matchVO2.getBetradarId() != null){
            return 0;
        }
        if (matchVO1.getBetgeniusId() != null && matchVO2.getBetgeniusId() != null){
            return 0;
        }
        if (matchVO1.getBet188Id() == null && matchVO2.getBet188Id() != null){
            matchVO1.setBet188Id(matchVO2.getBet188Id());
            matchMergeMapper.updateSourceMatchRef("refactor_bet188_prematch", matchVO2.getBet188Id(), matchId1);
        }
        if (matchVO1.getBetradarId() == null && matchVO2.getBetradarId() != null){
            matchVO1.setBetradarId(matchVO2.getBetradarId());
            matchMergeMapper.updateSourceMatchRef("refactor_betradar_prematch", matchVO2.getBetradarId(), matchId1);
        }
        if (matchVO1.getBetgeniusId() == null && matchVO2.getBetgeniusId() != null){
            matchVO1.setBetgeniusId(matchVO2.getBetgeniusId());
            matchMergeMapper.updateSourceMatchRef("refactor_betgenius_prematch", matchVO2.getBetgeniusId(), matchId1);
        }

        int updateRow = matchMergeMapper.updateMatch(matchVO1);
        int deleteRow = matchMergeMapper.deleteMatch(matchId2);
        logger.info("updateRow = {}, deletedRow = {}", updateRow, deleteRow);
        return 1;
    }



}
