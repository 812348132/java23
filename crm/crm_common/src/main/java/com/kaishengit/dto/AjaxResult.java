package com.kaishengit.dto;

/**
 * Created by zjs on 2017/7/17.
 */
public class AjaxResult {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String state;
    private String message;
    private Object data;

    public static AjaxResult success(){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(SUCCESS);
        return ajaxResult;
    }

    public static AjaxResult success(Object data){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(SUCCESS);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static AjaxResult error() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.ERROR);
        return ajaxResult;
    }
    public static AjaxResult error(String message) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.ERROR);
        ajaxResult.setMessage(message);
        return ajaxResult;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static String getERROR() {
        return ERROR;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
