package lottery.gaming.domain.service;

import lottery.gaming.model.io.DemoUserIO;
import lottery.gaming.model.mapper.DemoUserMapper;
import lottery.gaming.model.po.DemoUserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoService {

    @Autowired
    private DemoUserMapper demoUserMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<DemoUserPO> getUsers(DemoUserIO demoUserIO) {
        return demoUserMapper.getUsers(demoUserIO);
    }

    public DemoUserPO getUser(Integer id) {
        return demoUserMapper.getUser(id);
    }

    public int addUser(DemoUserIO userIO) throws Exception {
        return demoUserMapper.addUser(userIO);
    }

}
