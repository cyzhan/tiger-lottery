package lottery.gaming.model.response.data;

import lottery.gaming.model.vo.MainCompetitorRefVO;

import java.util.List;

public class CompetitorRefUpdateLog {

    private List<MainCompetitorRefVO> beforeData;

    private MainCompetitorRefVO updatedData;

    private MainCompetitorRefVO deletedData;

    public List<MainCompetitorRefVO> getBeforeData() {
        return beforeData;
    }

    public void setBeforeData(List<MainCompetitorRefVO> beforeData) {
        this.beforeData = beforeData;
    }

    public MainCompetitorRefVO getUpdatedData() {
        return updatedData;
    }

    public void setUpdatedData(MainCompetitorRefVO updatedData) {
        this.updatedData = updatedData;
    }

    public MainCompetitorRefVO getDeletedData() {
        return deletedData;
    }

    public void setDeletedData(MainCompetitorRefVO deletedData) {
        this.deletedData = deletedData;
    }

}
