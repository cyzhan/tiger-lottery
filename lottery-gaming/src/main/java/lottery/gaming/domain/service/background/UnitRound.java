package lottery.gaming.domain.service.background;

import io.netty.util.concurrent.SingleThreadEventExecutor;
import lottery.gaming.domain.controller.DemoController;
import lottery.gaming.domain.service.DemoService;
import lottery.gaming.model.mapper.TournamentMapper;
import lottery.gaming.model.vo.Tournament;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.*;

@Component
public class UnitRound {

    private static final Logger logger = LoggerFactory.getLogger(UnitRound.class);

    @Autowired
    private TournamentMapper tournamentMapper;

    @Autowired
    private DemoService demoService;

    @Autowired
    private WebClient webClient;

    @PostConstruct
    public void aaa(){
//        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
//        executorService.scheduleAtFixedRate(this::b, 5,120, TimeUnit.SECONDS);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->b());
    }


    public void b() {
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Integer> ids = tournamentMapper.getTournamentIds();
        ids.forEach(id-> {
            webClient.get()
                    .uri("v1/sports/{lang}/tournaments/{type}:tournament:{id}/info.xml", "en", "sr", id)
                    .header("x-access-token", "")
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(s -> {
                        logger.info(s);
                        try {
                            int row = demoService.addCompetitorRef(id,s);
                            int updated = tournamentMapper.update(id);
                            logger.info("affected row = {}, updated = {}", row, updated);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });

            try {
                Thread.sleep(40*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
