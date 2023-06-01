package cn.nf.chatgptserver.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class Result extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";

    public Result()
    {
    }

    public Result(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    public Result(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }

    @Override
    public Result put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    public static Result success()
    {
        return Result.success("Succeed.");
    }

    public static Result success(Object data)
    {
        return Result.success("Succeed.", data);
    }

    public static Result success(String msg, Object data)
    {
        return new Result(HttpStatus.OK.value(), msg, data);
    }

    public static Result error()
    {
        return Result.error("Failed.");
    }

    public static Result error(String msg)
    {
        return Result.error(msg, null);
    }

    public static Result error(String msg, Object data)
    {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    public static Result error(int code, String msg)
    {
        return new Result(code, msg, null);
    }

    public Object getData(){
        return this.get(DATA_TAG);
    }

    public boolean isSuccess(){
        int code=(int)this.get(CODE_TAG);
        return code==HttpStatus.OK.value();
    }
}
