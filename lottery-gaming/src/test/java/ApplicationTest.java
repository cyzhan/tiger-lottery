import lottery.gaming.common.Source;
import lottery.gaming.model.vo.MatchIdPair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ApplicationTest {

    @Test
    public void method1(){
        List<String> sources = Arrays.stream(Source.values()).map(Source::value).collect(Collectors.toList());
        int size = sources.size();
        String source1 = "";
        String source2 = "";
        int mergedMatchCount = 0;
        List<MatchIdPair> matchIdPairs;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size ; j++) {
                source1 = sources.get(i);
                source2 = sources.get(j);
                System.out.println("source1 = " + source1 + ", source2 = " + source2);
            }
        }
    }

}
