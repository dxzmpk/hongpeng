package com.dxzmpk.libnetwork;



public class HttpResponseList {

    private int status;
    private String message;
    private long pageTotal;
    private String data;

    public HttpResponseList() {
    }

    public HttpResponseList(int status, String message, long pageTotal, String data) {
        this.status = status;
        this.message = message;
        this.pageTotal = pageTotal;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(long pageTotal) {
        this.pageTotal = pageTotal;
    }
}
