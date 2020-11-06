package lottery.gaming.domain.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lottery.gaming.model.io.DemoUserIO;
import lottery.gaming.model.mapper.CompetitorMapper;
import lottery.gaming.model.mapper.DemoUserMapper;
import lottery.gaming.model.po.DemoUserPO;
import lottery.gaming.model.vo.CompetitorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    @Autowired
    private DemoUserMapper demoUserMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CompetitorMapper competitorMapper;



    @Autowired
    @Qualifier("xmlMapper")
    private XmlMapper xmlMapper;

    public List<DemoUserPO> getUsers(DemoUserIO demoUserIO) {
        return demoUserMapper.getUsers(demoUserIO);
    }

    public DemoUserPO getUser(Integer id) {
        return demoUserMapper.getUser(id);
    }

    public int addUser(DemoUserIO userIO) throws Exception {
        return demoUserMapper.addUser(userIO);
    }

    @Transactional
    public int addCompetitorRef(int id, String rs) throws Exception {
        int index1 = 1;
        int index2 = 0;
        if (!rs.contains("<competitor")){
            logger.info("id = {} has not competitor info", id);
            return 0;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<root>");
        int loopCount = 0;
        while (index1 >= 0){
            index1 = rs.indexOf("<competitor");
            if (index1 < 0){
                break;
            }
            index2 = rs.indexOf("</competitor>");
            stringBuilder.append(rs, index1, index2 + "</competitor>".length());
            rs = rs.substring(index2 + "</competitor>".length());
            loopCount++;
        }
        logger.info("loop count = " + loopCount);
        stringBuilder.append("</root>");
        rs = stringBuilder.toString();
        CompetitorWrapper wrapper = xmlMapper.readValue(rs, CompetitorWrapper.class);

        wrapper.getCompetitor().forEach(item -> {
            item.setId(item.getId().split(":")[2]);
        });
        return competitorMapper.addCompetitorRef(id, wrapper.getCompetitor());
    }

}
