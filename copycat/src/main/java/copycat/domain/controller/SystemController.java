package copycat.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lottery.common.model.vo.ResultVO;
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
@RequestMapping("/system")
@Validated
public class SystemController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(path= {"/health"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> health() throws Exception {
        return Mono.just(ResultVO.ok());
    }

}
