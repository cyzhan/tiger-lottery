package lottery.gaming.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompetitorWrapper {

    private int sportId;

    private int tournamentId;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CompetitorVO> competitor;

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public List<CompetitorVO> getCompetitor() {
        return competitor;
    }

    public void setCompetitor(List<CompetitorVO> competitor) {
        this.competitor = competitor;
    }

}
