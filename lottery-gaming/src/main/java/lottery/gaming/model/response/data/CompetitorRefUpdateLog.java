package lottery.gaming.model.response.data;

import lottery.gaming.model.vo.MainCompetitorRefVO;

import java.util.List;
import java.util.Map;

public class CompetitorRefUpdateLog {

    private List<MainCompetitorRefVO> beforeData;

    private MainCompetitorRefVO updatedData;

    private MainCompetitorRefVO deletedData;

    private Map<String, Integer> refIdChanged;

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

    public Map<String, Integer> getRefIdChanged() {
        return refIdChanged;
    }

    public void setRefIdChanged(Map<String, Integer> refIdChanged) {
        this.refIdChanged = refIdChanged;
    }

}
