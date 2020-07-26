package lottery.common.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO {

    public static ResultVO ok(){
        return new ResultVO(1, "ok");
    }

    public static ResultVO of(Object data){
        return new ResultVO(data);
    }

    public static ResultVO error(int code, String msg){
        return new ResultVO(code, msg);
    }

    public ResultVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultVO(Object data) {
        this.code = 1;
        this.msg = "ok";
        this.data = data;
    }

    private int code;

    private String msg;

    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
