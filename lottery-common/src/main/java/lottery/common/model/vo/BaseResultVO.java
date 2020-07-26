package lottery.common.model.vo;

public class BaseResultVO {

    public static BaseResultVO ok(){
        return new BaseResultVO(1, "ok");
    }

    public static BaseResultVO create(int code, String msg){
        return new BaseResultVO(code, msg);
    }

    protected int code;

    protected String msg;

    public BaseResultVO() {

    }

    protected BaseResultVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
