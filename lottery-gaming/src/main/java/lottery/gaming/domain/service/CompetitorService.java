package lottery.gaming.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lottery.common.model.vo.ResultVO;
import lottery.gaming.common.Source;
import lottery.gaming.model.io.CompetitorMergeIO;
import lottery.gaming.model.mapper.CompetitorMapper;
import lottery.gaming.model.response.data.CompetitorRefUpdateLog;
import lottery.gaming.model.vo.CompetitorIdPairVO;
import lottery.gaming.model.vo.MainCompetitorRefVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CompetitorService {

    private static final Logger logger = LoggerFactory.getLogger(CompetitorService.class);

    @Autowired
    private CompetitorMapper competitorMapper;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @Autowired
    private CompetitorSubService competitorSubService;

    public ResultVO mergeCompetitors(CompetitorMergeIO competitorMergeIO) {
        return competitorSubService.mergeCompetitors(competitorMergeIO);
    }

    public ResultVO mergeCompetitorsByDate(int sportId, String date){
        List<String> sources = Arrays.stream(Source.values()).map(Source::value).collect(Collectors.toList());
        int size = sources.size();
        String source1 = "";
        String source2 = "";
        List<CompetitorMergeIO> list = null;
        List<Integer> mergeIds = new ArrayList<>();
        List<Map<String,String>> logs = new ArrayList<>();
        Map<String, String> log;
        ResultVO resultVO;
        CompetitorRefUpdateLog competitorRefUpdateLog;
        for (int i = 0; i < sources.size(); i++) {
            for (int j = i + 1; j < size; j++) {
                source1 = sources.get(i);
                source2 = sources.get(j);
                list = competitorMapper.getMergeCandidateByHomeDiff(sportId, date, source1, source2);
                list.addAll(competitorMapper.getMergeCandidateByAwayDiff(sportId, date, source1, source2));
                if (list.size() == 0){
                    continue;
                }

                for (CompetitorMergeIO competitorMergeIO : list) {
                    try {
                        resultVO = competitorSubService.mergeCompetitors(competitorMergeIO);
                        if (resultVO.getCode() == 1 && resultVO.getData() instanceof CompetitorRefUpdateLog) {
                            competitorRefUpdateLog = (CompetitorRefUpdateLog) resultVO.getData();
                            mergeIds.add(competitorRefUpdateLog.getUpdatedData().getId());
                            log = new HashMap<>();
                            log.put("before", objectMapper.writeValueAsString(competitorRefUpdateLog.getBeforeData()));
                            log.put("updated", objectMapper.writeValueAsString(competitorRefUpdateLog.getUpdatedData()));
                            log.put("deleted", objectMapper.writeValueAsString(competitorRefUpdateLog.getDeletedData()));
                            logs.add(log);
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        }
        if (logs.size() > 0){
            competitorMapper.insertMergeLog(logs);
        }
//        List<CompetitorMergeIO> list = competitorMapper.getMergeCandidateByHomeDiff(sportId, date);
//        list.addAll(competitorMapper.getMergeCandidateByAwayDiff(sportId, date));
//        if (list.size() == 0){
//            return ResultVO.error(404, "無可整併的隊伍");
//        }
//        List<Integer> mergeIds = new ArrayList<>();
//        List<Map<String,String>> logs = new ArrayList<>();
//        Map<String, String> log;
//        ResultVO resultVO;
//        CompetitorRefUpdateLog competitorRefUpdateLog;
//        for (CompetitorMergeIO competitorMergeIO : list) {
//            try {
//                resultVO = competitorSubService.mergeCompetitors(competitorMergeIO);
//                if (resultVO.getCode() == 1 && resultVO.getData() instanceof CompetitorRefUpdateLog) {
//                  competitorRefUpdateLog = (CompetitorRefUpdateLog) resultVO.getData();
//                    mergeIds.add(competitorRefUpdateLog.getUpdatedData().getId());
//                    log = new HashMap<>();
//                    log.put("before", objectMapper.writeValueAsString(competitorRefUpdateLog.getBeforeData()));
//                    log.put("updated", objectMapper.writeValueAsString(competitorRefUpdateLog.getUpdatedData()));
//                    log.put("deleted", objectMapper.writeValueAsString(competitorRefUpdateLog.getDeletedData()));
//                    logs.add(log);
//                }
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }
//        }
//        if (logs.size() > 0){
//            competitorMapper.insertMergeLog(logs);
//        }

        return ResultVO.of(mergeIds);
    }

}
