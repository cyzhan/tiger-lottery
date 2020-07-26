package lottery.gaming.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
/**
 * mybatis 掃描 mapper 目標
 */
@Configuration
@MapperScan(basePackages={"lottery.gaming.model.mapper"})
public class MybatisConfig {

    /**
     * sql攔截器啟動
     * @return
     */
//    @Bean
//    public MybatisPageInterceptor mybatisPageInterceptor() {
//        return new MybatisPageInterceptor();
//    }

}