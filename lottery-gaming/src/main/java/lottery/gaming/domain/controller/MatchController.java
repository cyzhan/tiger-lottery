package lottery.gaming.domain.controller;

import lottery.common.model.vo.ResultVO;
import lottery.gaming.domain.service.MatchService;
import lottery.gaming.model.io.MatchHomeAwayUpdateIO;
import lottery.gaming.model.io.MergeMatchIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/match")
public class MatchController {

    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MatchService matchService;

    @PutMapping(path = {"merge"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> merge(@RequestBody MergeMatchIO mergeMatchIO) throws Exception  {
        try {
            matchService.mergeToOneMatch(mergeMatchIO.getMatchId1(), mergeMatchIO.getMatchId2());
        }catch (Exception e){
            e.printStackTrace();
        }
        return Mono.just(ResultVO.ok());
    }

    @PutMapping(path = {"merge/sport/{sportId}/date/{date}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> merge(@PathVariable("sportId") int sportId, @PathVariable("date") String date) throws Exception  {
        try {
            sdf.parse(date);
        }catch (Exception e){
            return Mono.just(ResultVO.error(0, "not valid date format"));
        }
        int mergedMatchCount = matchService.batchMergeByDate(sportId, date);
        logger.info("mergedMatchCount = {}", mergedMatchCount);
        return Mono.just(ResultVO.ok());
    }

    @PutMapping(path = {"home-and-away"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> updateMatchHomeAway(@RequestBody MatchHomeAwayUpdateIO io) {
        try {
            return Mono.just(ResultVO.of(matchService.updateMatchHomeAway(io)));
        }catch (Exception e){
            return Mono.just(ResultVO.error(0, e.getMessage()));
        }
    }

}
