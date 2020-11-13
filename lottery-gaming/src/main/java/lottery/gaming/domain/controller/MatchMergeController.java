package lottery.gaming.domain.controller;

import lottery.common.model.vo.ResultVO;
import lottery.gaming.domain.service.MatchMergeService;
import lottery.gaming.model.io.MergeMatchIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/match-merge")
public class MatchMergeController {

    private static final Logger logger = LoggerFactory.getLogger(MatchMergeController.class);

    @Autowired
    private MatchMergeService matchMergeService;

    @PutMapping(path = {""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> merge(@RequestBody MergeMatchIO mergeMatchIO) {
        matchMergeService.doMerge(mergeMatchIO.getMatchId1(), mergeMatchIO.getMatchId2());
        return Mono.just(ResultVO.ok());
    }

}
