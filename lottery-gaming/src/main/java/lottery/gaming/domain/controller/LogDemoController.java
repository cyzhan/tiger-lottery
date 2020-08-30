package lottery.gaming.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lottery.common.model.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/log")
public class LogDemoController {

    private static final Logger logger = LoggerFactory.getLogger(LogDemoController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(path= {"/test1/{msg}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> getByKey(@PathVariable String msg) {
        logger.info("msg = {}", msg);
        return Mono.just(ResultVO.ok());
    }


}
