package lottery.gaming.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lottery.common.model.vo.ResultVO;
import lottery.gaming.common.Source;
import lottery.gaming.model.io.MatchHomeAwayUpdateIO;
import lottery.gaming.model.mapper.MatchMapper;
import lottery.gaming.model.vo.MatchIdPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MatchService {

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @Autowired
    private MatchMapper matchMapper;

    @Autowired
    private MatchSubService matchSubService;


    public int mergeToOneMatch(int matchId1, int matchId2) throws Exception {
        return matchSubService.mergeToOneMatch(matchId1, matchId2);
    }

    public int batchMergeByDate(int sportId, String date) {
        List<MatchIdPair> matchIdPairs = matchMapper.getMergeCandidatePair(sportId, date);
        int mergedMatchCount = 0;
        for (MatchIdPair item : matchIdPairs) {
            try {
                if (item.getId1() > item.getId2()) {
                    mergedMatchCount = mergedMatchCount + matchSubService.mergeToOneMatch(item.getId1(), item.getId2());
                } else {
                    mergedMatchCount = mergedMatchCount + matchSubService.mergeToOneMatch(item.getId2(), item.getId1());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return mergedMatchCount;
    }

    public ResultVO updateMatchHomeAway(MatchHomeAwayUpdateIO matchHomeAwayUpdateIO) throws Exception {
        if (matchHomeAwayUpdateIO.getSourceIds().size() == 0){
            return ResultVO.error(0, "ids is empty array");
        }
        String source = "";
        switch (matchHomeAwayUpdateIO.getSource()){
            case "a":
                source = Source.A.value();
                break;
            case "b":
                source = Source.B.value();
                break;
            case "c":
                source = Source.C.value();
                break;
            default:
                return ResultVO.error(0, String.format("unknown source : %s", matchHomeAwayUpdateIO.getSource()));
        }

        matchHomeAwayUpdateIO.setSourceMatchTable(String.format("refactor_%s_prematch", source));
        matchHomeAwayUpdateIO.setSourceCompetitorTable(String.format("%s_competitors", source));
        int updateRows = matchSubService.updateMatchHomeAway(matchHomeAwayUpdateIO);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("updatedRows", updateRows);
        return ResultVO.of(objectNode);
    }



}
