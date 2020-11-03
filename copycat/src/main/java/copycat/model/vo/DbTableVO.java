package copycat.model.vo;

import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.deserialization.ColumnType;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class DbTableVO {
    //=3, 15, 15, 17
    private long id;

    private String name;

    private String database;

    private byte[] columnTypes;

    public DbTableVO() {
    }

    public DbTableVO(TableMapEventData data) {
        this.id = data.getTableId();
        this.database = data.getDatabase();
        this.name = data.getTable();
        this.columnTypes = data.getColumnTypes();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColumnTypes(byte[] columnTypes) {
        this.columnTypes = columnTypes;
    }

    public byte[] getColumnTypes() {
        return columnTypes;
    }

}
