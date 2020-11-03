package copycat.model.mapper;

import java.util.List;

public interface ReplicationMapper {

    int addData(List<List<Object>> rows);

}
