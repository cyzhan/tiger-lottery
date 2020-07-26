package lottery.gaming.domain.service;

import lottery.common.model.vo.ResultVO;
import lottery.gaming.model.po.DemoUserPO;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class FluxDemoService {

    private static final List<String> names;

    private static final SecureRandom random = new SecureRandom();;

    static {
        names = new ArrayList<>();
        names.add("jeff");
        names.add("jess");
        names.add("bruce");
        names.add("boss");
    }

    public ResultVO getUser(String name) throws Exception {
        if (!names.contains(name)){
            throw new Exception("user not found");
        }

        if ("boss".equals(name)){
            return ResultVO.error(0, "unAuthorized");
        }

        DemoUserPO demoUserPO = new DemoUserPO();
        demoUserPO.setId(1);
        demoUserPO.setAge(20);
        demoUserPO.setName(name);
        return ResultVO.of(demoUserPO);
    }

    public DemoUserPO getRandomUser() {
        int randomInt = random.nextInt(4);
        DemoUserPO demoUserPO = new DemoUserPO();
        demoUserPO.setId(1);
        demoUserPO.setAge(20);
        demoUserPO.setName(names.get(randomInt));
        return demoUserPO;
    }

    public ResultVO getUsers() throws Exception {
        List<DemoUserPO> demoUserPOs = new ArrayList<>();
        names.forEach(s -> {
            DemoUserPO demoUserPO = new DemoUserPO();
            demoUserPO.setId(1);
            demoUserPO.setAge(20);
            demoUserPO.setName(s);
            demoUserPOs.add(demoUserPO);
        });
        return ResultVO.of(demoUserPOs);
    }



}
