package lottery.gaming.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lottery.common.model.vo.ResultVO;
import lottery.gaming.model.vo.KeyValueVO;
import lottery.gaming.model.vo.RedisDemoVO;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/redis")
public class RedisDemoController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StringRedisTemplate  stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping(path= {"/key"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> addKey(@RequestBody KeyValueVO keyValueVO) throws Exception {
        stringRedisTemplate.opsForValue().set(keyValueVO.getKey(), keyValueVO.getValue());
//        stringRedisTemplate.execute(new RedisCallback<Object>() {
//            @Override
//            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                return null;
//            }
//        });
        return Mono.just(ResultVO.ok());
    }

    @GetMapping(path= {"/key/{key}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> getByKey(@PathVariable String key) throws Exception {
        Optional<String> optional = Optional.ofNullable(stringRedisTemplate.opsForValue().get(key));
        Map<String, String> map = new HashMap<>();
        return optional.map(s -> {
            map.put(key, s);
            return Mono.just(ResultVO.of(map));
        }).orElseGet(() -> Mono.just(ResultVO.error(0, "key not found")));
    }

    @PostMapping(path= {"/key/{key}/object"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResultVO> addKeyObject(@PathVariable String key, @RequestBody RedisDemoVO redisDemoVO) throws Exception {
        redisTemplate.opsForValue().set(key, redisDemoVO);
        return Mono.just(ResultVO.ok());
    }

}
