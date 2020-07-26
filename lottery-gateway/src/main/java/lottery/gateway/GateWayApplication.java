package lottery.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GateWayApplication.class);
        springApplication.run(args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .host("**.somehost.org:8090")
                        //.path("/get")
                        //.filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://127.0.0.1:8080"))
                .build();
    }

}
