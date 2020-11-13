package copycat.domain.backgroud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.ColumnType;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import copycat.model.mapper.ReplicationMapper;
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

    @Autowired
    private ReplicationMapper replicationMapper;

    @PostConstruct
    public void init(){
        try {
            BinaryLogClient client = new BinaryLogClient("mysql-lekima-demo.ceshi22.com", 3306, "sptlekima", "k5UVKriq");
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
//            case "UPDATE_ROWS":
//            {
//                WriteRowsEventData data = event.getData();
//                DbTableVO tableVO = tableIdVOMap.get(data.getTableId());
//                List<String> rowsStr = new ArrayList<>();
//                StringBuilder stringBuilder = new StringBuilder();
//                data.getRows().forEach(row -> {
//                    ArrayNode arrayNode = objectMapper.createArrayNode();
//                    stringBuilder.append("(");
//                    for (int i = 0; i < row.length; i++) {
//                        if (row[i] instanceof byte[]) {
//                            String s = new String((byte[]) row[i], StandardCharsets.UTF_8);
//                            stringBuilder.append("'").append(s).append("'");
//                            arrayNode.add(s);
//                        }
////                        else if (tableVO.getColumnTypes()[i] == ColumnType.TIMESTAMP_V2.getCode()) {
////                            Date date = new Date(((Long) row[i]));
////                            String dateString = sdf.format(date);
////                            arrayNode.add(dateString);
////                            stringBuilder.append("'").append(dateString).append("'");
////                        } else {
////                            arrayNode.add(Long.parseLong(row[i].toString()));
////                            stringBuilder.append(row[i].toString());
////                        }
//
//                        if (i != row.length - 1) {
//                            stringBuilder.append(",");
//                        }
//                    }
//                    stringBuilder.append(")");
//                    rowsStr.add(stringBuilder.toString());
//                    stringBuilder.setLength(0);
//                    logger.info("transfer data = {}", arrayNode.toPrettyString());
//                });
            case "WRITE_ROWS":
                {
                WriteRowsEventData data = event.getData();
                DbTableVO tableVO = tableIdVOMap.get(data.getTableId());
                List<String> rowsStr = new ArrayList<>();
                StringBuilder stringBuilder = new StringBuilder();
                data.getRows().forEach(row -> {
                    ArrayNode arrayNode = objectMapper.createArrayNode();
                    stringBuilder.append("(");
                    for (int i = 0; i < row.length; i++) {
                        if (row[i] instanceof byte[]) {
                            String s = new String((byte[]) row[i], StandardCharsets.UTF_8);
                            stringBuilder.append("'").append(s).append("'");
                            arrayNode.add(s);
                        }
//                        else if (tableVO.getColumnTypes()[i] == ColumnType.TIMESTAMP_V2.getCode()) {
//                            Date date = new Date(((Long) row[i]));
//                            String dateString = sdf.format(date);
//                            arrayNode.add(dateString);
//                            stringBuilder.append("'").append(dateString).append("'");
//                        } else {
//                            arrayNode.add(Long.parseLong(row[i].toString()));
//                            stringBuilder.append(row[i].toString());
//                        }

                        if (i != row.length - 1) {
                            stringBuilder.append(",");
                        }
                    }
                    stringBuilder.append(")");
                    rowsStr.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                    logger.info("transfer data = {}", arrayNode.toPrettyString());
                });
//                int affectedRows = replicationMapper.addData(rowsStr);
//                logger.info("replicated row = " + affectedRows);
            }
            break;
//            case  "UPDATE_ROWS":{
//                UpdateRowsEventData data = event.getData();
////                EventType.
//            }
//            break;
        }
    }
}
