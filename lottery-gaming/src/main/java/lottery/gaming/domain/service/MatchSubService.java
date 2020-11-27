package lottery.gaming.domain.service;

import lottery.gaming.model.io.MatchHomeAwayUpdateIO;
import lottery.gaming.model.mapper.MatchMapper;
import lottery.gaming.model.vo.MatchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MatchSubService {

    private static final Logger logger = LoggerFactory.getLogger(MatchSubService.class);

    @Autowired
    private MatchMapper matchMapper;

    @Transactional(rollbackFor = Exception.class)
    public int mergeToOneMatch(int matchId1, int matchId2) throws Exception {
        List<MatchVO> matchVOs = matchMapper.getMatches(matchId1, matchId2);
        MatchVO matchVO1 = matchVOs.get(0).getId() == matchId1? matchVOs.remove(0):matchVOs.remove(1);
        MatchVO matchVO2 = matchVOs.remove(0);
        //Timestamp.valueOf(matchVO1.getScheduled()).getTime();
        //logger.info("matchVO1 scheduled = {}, unix timestampe = {}", matchVO1.getScheduled(), Timestamp.valueOf(matchVO1.getScheduled()).getTime());
        if ((matchVO1.getTournamentId() != matchVO2.getTournamentId())){
            return 0;
        }
        if (matchVO1.getHomeId() != matchVO2.getHomeId()){
            return 0;
        }
        if (matchVO1.getAwayId() != matchVO2.getAwayId()){
            return 0;
        }
        if (!matchVO1.getScheduled().equals(matchVO2.getScheduled())){
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
        }        String sourceTable = "";
        if (matchVO1.getBet188Id() == null && matchVO2.getBet188Id() != null){
            sourceTable = "refactor_bet188_prematch";
            matchMapper.updateSourceMatchRef("refactor_bet188_prematch", matchId2, matchId1);
            logger.info("update table = {}, id ={}, ref_id from {} to {}", sourceTable, matchVO2.getBet188Id(), matchId2, matchId1);
        }
        if (matchVO1.getBetradarId() == null && matchVO2.getBetradarId() != null){
            sourceTable = "refactor_betradar_prematch";
            matchMapper.updateSourceMatchRef(sourceTable, matchId2, matchId1);
            logger.info("update table = {}, id ={}, ref_id from {} to {}", sourceTable, matchVO2.getBetradarId(), matchId2, matchId1);
        }
        if (matchVO1.getBetgeniusId() == null && matchVO2.getBetgeniusId() != null){
            sourceTable = "refactor_betgenius_prematch";
            matchMapper.updateSourceMatchRef(sourceTable, matchId2, matchId1);
            logger.info("update table = {}, id ={}, ref_id from {} to {}", sourceTable, matchVO2.getBetgeniusId(), matchId2, matchId1);
        }

        int deleteRow = matchMapper.deleteMatch(matchId2);
        int updateRow = matchMapper.updateMatch(matchId1, matchVO2);
//        int deleteRow = matchMapper.softDelete(matchId2);
        logger.info("updateRow = {}, deletedRow = {}", updateRow, deleteRow);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateMatchHomeAway(MatchHomeAwayUpdateIO matchHomeAwayUpdateIO) throws Exception {
        int awayUpdated = matchMapper.matchAwayUpdate(matchHomeAwayUpdateIO);
        int homeUpdated = matchMapper.matchHomeUpdate(matchHomeAwayUpdateIO);
        return awayUpdated + homeUpdated;
    }

}
