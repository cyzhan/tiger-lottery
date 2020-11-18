package lottery.gaming.domain.controller;

import lottery.common.model.vo.ResultVO;
import lottery.gaming.domain.service.CompetitorService;
import lottery.gaming.model.io.CompetitorMergeIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/competitor")
public class CompetitorController {

    private static final Logger logger = LoggerFactory.getLogger(CompetitorController.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private CompetitorService competitorService;

    /**
     *  整合兩支球隊
     */
    @PutMapping(path = {"merge"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> merge(@RequestBody CompetitorMergeIO mergeIO) throws Exception {
        return Mono.just(competitorService.mergeCompetitors(mergeIO));
    }

    /**
     *  查詢該日期賽事,
     *  聯賽/開賽時間/主隊相同/客隊不同 => 客隊必為同一球隊, 進行整合
     *  聯賽/開賽時間/客隊相同/主隊不同 => 主隊必為同一球隊, 進行整合
     */
    @PutMapping(path = {"merge/sport/{sportId}/date/{date}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> merge(@PathVariable("sportId") int sportId, @PathVariable("date") String date) throws Exception {
        try {
            sdf.parse(date);
        }catch (Exception e){
            return Mono.just(ResultVO.error(0, "not valid date format"));
        }
        return Mono.just(competitorService.mergeCompetitorsByDate(sportId, date));
    }



}
