package copycat.domain.backgroud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.github.shyiko.mysql.binlog.event.deserialization.ColumnType;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import copycat.model.vo.DbTableVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BinLogService {

    private static final Logger logger = LoggerFactory.getLogger(BinLogService.class);

    private static final Map<Long, DbTableVO> tableIdVOMap  = new HashMap<>();

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        try {
            BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "repliuser", "asdf9876");
//            client.setBinlogFilename("mysql-bin.000001");
//            client.setBinlogPosition(4);
            EventDeserializer eventDeserializer = new EventDeserializer();
            eventDeserializer.setCompatibilityMode(
                    EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                    EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
            );
            client.setEventDeserializer(eventDeserializer);
            client.registerEventListener(event -> {
                logger.info("eventType = {}, data = {}",
                    event.getHeader().getEventType().toString(),
                    event.getData().toString());
                eventHandler(event);
            });
            client.connect();
            logger.info("client connected");
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void eventHandler(Event event){
        switch (event.getHeader().getEventType().toString()) {
            case "TABLE_MAP": {
                TableMapEventData data = event.getData();
                tableIdVOMap.putIfAbsent(data.getTableId(), new DbTableVO(data));
            }
            break;
            case "WRITE_ROWS":{
                WriteRowsEventData data = event.getData();
//                data.getIncludedColumns();
                DbTableVO tableVO = tableIdVOMap.get(data.getTableId());
                List<List<Object>> insertData = new ArrayList<>();
                data.getRows().forEach(row -> {
                    ArrayNode arrayNode = objectMapper.createArrayNode();
                    List<Object> objects = new ArrayList<>();
                    for (int i = 0; i < row.length; i++) {
                        if (row[i] instanceof byte[]){
                            String s = new String((byte[]) row[i], StandardCharsets.UTF_8);
                            arrayNode.add(s);
                            objects.add(s);
                      }
                        else if (tableVO.getColumnTypes()[i] == ColumnType.TIMESTAMP_V2.getCode()){
                            Date date = new Date(((Long) row[i]));
                            String dateString = sdf.format(date);
                            arrayNode.add(dateString);
                            objects.add(dateString);
                        } else {
                            arrayNode.add(Long.parseLong(row[i].toString()));
                            objects.add(Long.parseLong(row[i].toString()));
                        }
                    }
                    logger.info("transfer data = {}", arrayNode.toPrettyString());
                });
            }
            break;
        }
    }
}
