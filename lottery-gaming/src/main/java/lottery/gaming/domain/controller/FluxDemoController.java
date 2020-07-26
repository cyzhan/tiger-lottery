package lottery.gaming.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lottery.common.model.vo.ResultVO;
import lottery.gaming.domain.service.FluxDemoService;
import lottery.gaming.model.po.DemoUserPO;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import java.time.Duration;

@RestController
@RequestMapping("/flux")
@Validated
public class FluxDemoController {

    @Autowired
    private FluxDemoService fluxDemoService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Get one
     * @param name
     * @return
     * @throws Exception
     */
    @GetMapping(path= {"/test/{name}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultVO> getByName(@PathVariable("name") @Length(max = 16, min = 4, message = "incorrect input length") String name) throws Exception {
        return Mono.just(fluxDemoService.getUser(name));
    }

    /**
     * Get list
     * @return
     * @throws Exception
     */
    @GetMapping(path= {"/test"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultVO> getAll(@RequestParam("pageIndex") @Min(1) Integer pageIndex,
                                 @RequestParam("pageSize") @Min(10) Integer pageSize) throws Exception {
        System.out.println(String.format("pageIndex = %s, pageSize = %s", pageIndex, pageSize));
        System.out.println(String.format("offSet = %s", (pageIndex-1)*pageSize));
        return Mono.just(fluxDemoService.getUsers());
    }

    @PostMapping(path = {"/test"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultVO> newOne(@RequestBody @Validated DemoUserPO demoUserPO) throws ConstraintViolationException, Exception {
        System.out.println(String.format("json = %s", objectMapper.writeValueAsString(demoUserPO)));
        return Mono.just(ResultVO.ok());
    }

    /**
     * Serve Send Event sample
     * @return
     * @throws Exception
     */
    @GetMapping(path= {"/sse"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DemoUserPO> sseTest() throws Exception {
        return Flux.interval(Duration.ofSeconds(3)).map(s -> fluxDemoService.getRandomUser());
    }

    public void sdte(){
        Mono.just("dfa").flatMap(s -> Mono.just(s.toUpperCase()));
    }


}
