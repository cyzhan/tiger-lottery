package lottery.gaming.domain.service;

import lottery.gaming.model.mapper.MatchMergeMapper;
import lottery.gaming.model.vo.MatchIdPair;
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

    @Autowired
    private MatchMergeSubService matchMergeSubService;


    public int mergeToOneMatch(int matchId1, int matchId2) throws Exception {
        return matchMergeSubService.mergeToOneMatch(matchId1, matchId2);
    }

    public int batchMergeByDate(int sportId, String date) {
        List<MatchIdPair> matchIdPairs = matchMergeMapper.getMergeCandidatePair(sportId, date);
        int mergedMatchCount = 0;
        for (MatchIdPair item : matchIdPairs) {
            try {
                if (item.getId1() > item.getId2()) {
                    mergedMatchCount = mergedMatchCount + matchMergeSubService.mergeToOneMatch(item.getId1(), item.getId2());
                } else {
                    mergedMatchCount = mergedMatchCount + matchMergeSubService.mergeToOneMatch(item.getId2(), item.getId1());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return mergedMatchCount;
    }



}
