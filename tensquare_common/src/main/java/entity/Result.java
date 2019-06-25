package entity;

public class Result {
    private boolean flag;//是否成功
    private Integer code;//返回码
    private String message;//返回信息
    private Object data;//返回数据

    //空参构造器
    public Result() {
    }

    //1.有参构造器  这个3个方法 适用于增删该
    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
    //2.有参构造器  这个4个方法 适用于查询
    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //set get 方法
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
