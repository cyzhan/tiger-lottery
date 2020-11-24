package lottery.gaming.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lottery.gaming.domain.service.DemoService;
import lottery.gaming.model.vo.CompetitorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.Scanner;

@RestController
@RequestMapping("/b-source")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    @Qualifier("xmlMapper")
    private XmlMapper xmlMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("betradar")
    private WebClient webClient;

    @Autowired
    private DemoService demoService;

    @GetMapping(path= {"/xml-test"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> version() {
        try {
            InputStream targetStream = getClass().getResourceAsStream("/sample2.xml");
            Scanner s = new Scanner(targetStream).useDelimiter("\\A");
            String rs = s.hasNext() ? s.next() : "";

//            String startStr = "<group name=";
//            String endStr = "</group>";
//            int index1 = result.indexOf(startStr);
//            int index2 = result.lastIndexOf("</group>");
//            String rs2 = result.substring(index1, index2 + endStr.length());
//            CompetitorWrapper competitorWrapper = xmlMapper.readValue(rs2 , CompetitorWrapper.class);
            int index1 = 1;
            int index2 = 0;
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<root>");
            while (index1 > 0){
                index1 = rs.indexOf("<competitor");
                if (index1 < 0){
                    break;
                }
                index2 = rs.indexOf("</competitor>");
                stringBuilder.append(rs, index1, index2 + "</competitor>".length());
                rs = rs.substring(index2 + "</competitor>".length());
            }
            stringBuilder.append("</root>");
            rs = stringBuilder.toString();
            CompetitorWrapper wrapper = xmlMapper.readValue(rs, CompetitorWrapper.class);
            logger.info("-------");
            logger.info(rs);
            return Mono.just(objectMapper.writeValueAsString(wrapper));
        }catch (Exception e){
            e.printStackTrace();
            return Mono.just("Sdf");
        }
    }

    @GetMapping(path = {"/v1/sports/{lang}/tournaments/{type}:tournament:{id}/info.xml"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> insertData(@PathVariable("lang") String lang, @PathVariable("type") String type, @PathVariable("id") Integer id) {
        webClient.get()
                .uri("v1/sports/{lang}/tournaments/{type}:tournament:{id}/info.xml", lang, type, id)
                .header("x-access-token", "cBrk0GvJEO21qPhy8Q")
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(s -> {
                    logger.info(s);
                    try {
                        int row = demoService.addCompetitorRef(id,s);
                        logger.info("affected row = " + row);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
        return Mono.just("ok");

    }
}
