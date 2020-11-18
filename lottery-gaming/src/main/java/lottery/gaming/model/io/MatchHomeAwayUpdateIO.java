package lottery.gaming.model.io;

import java.util.List;

public class MatchHomeAwayUpdateIO {

    private int sportId;

    private String date;

    private String source;

    private String sourceMatchTable;

    private String sourceCompetitorTable;

    private List<Integer> sourceIds;

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceMatchTable() {
        return sourceMatchTable;
    }

    public void setSourceMatchTable(String sourceMatchTable) {
        this.sourceMatchTable = sourceMatchTable;
    }

    public String getSourceCompetitorTable() {
        return sourceCompetitorTable;
    }

    public void setSourceCompetitorTable(String sourceCompetitorTable) {
        this.sourceCompetitorTable = sourceCompetitorTable;
    }

    public List<Integer> getSourceIds() {
        return sourceIds;
    }

    public void setSourceIds(List<Integer> sourceIds) {
        this.sourceIds = sourceIds;
    }

}
