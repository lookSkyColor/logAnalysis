package com.example.demo.util;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by wangzhenyu on 2018/4/2.
 * 统一的返回结果
 */
public class ResultVO<T> {

    @ApiModelProperty(value = "操作状态 0:成功")
    private int code;

    @ApiModelProperty(value = "操作提示信息")
    private String msg;

    @ApiModelProperty(value = "操作返回实体")
    private T result;

    public ResultVO() {
    }

    ;

    public ResultVO(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public ResultVO(T result) {
        this(ResultCodeEnum.SUCCESS, result);
    }

    public ResultVO(ResultCodeEnum resultCodeEnum, T result) {
        this.result = result;
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
    }

    public ResultVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResultVO<T> createSuccess(T result) {
        return new ResultVO(result);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return StringUtils.isBlank(msg) ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /**
     * 构造器
     *
     * @param <T> liudeqing  2018/11/17 12:09:28
     */
    public static class ResultVoBuilder<T> {

        private int code;

        private String msg;

        private T result;

        public ResultVoBuilder code(int code) {
            this.code = code;
            return this;
        }

        public ResultVoBuilder errorCode() {
            this.code = ResultCodeEnum.ERROR.getCode();
            return this;
        }

        public ResultVoBuilder successCode() {
            this.code = ResultCodeEnum.SUCCESS.getCode();
            return this;
        }

        public ResultVoBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResultVoBuilder result(T result) {
            this.result = result;
            return this;
        }

        public ResultVO builder() {
            return new ResultVO(code, StringUtils.isBlank(msg) ? "" : msg, result);
        }

    }

}
