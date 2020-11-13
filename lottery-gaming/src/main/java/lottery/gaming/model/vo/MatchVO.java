package lottery.gaming.model.vo;

public class MatchVO {

    private int id;

    private int tournamentId;

    private int homeId;

    private int awayId;

    private long scheduled;

    private Integer bet188Id;

    private Integer betradarId;

    private Integer betgeniusId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public int getAwayId() {
        return awayId;
    }

    public void setAwayId(int awayId) {
        this.awayId = awayId;
    }

    public long getScheduled() {
        return scheduled;
    }

    public void setScheduled(long scheduled) {
        this.scheduled = scheduled;
    }

    public Integer getBet188Id() {
        return bet188Id;
    }

    public void setBet188Id(Integer bet188Id) {
        this.bet188Id = bet188Id;
    }

    public Integer getBetradarId() {
        return betradarId;
    }

    public void setBetradarId(Integer betradarId) {
        this.betradarId = betradarId;
    }

    public Integer getBetgeniusId() {
        return betgeniusId;
    }

    public void setBetgeniusId(Integer betgeniusId) {
        this.betgeniusId = betgeniusId;
    }

}
