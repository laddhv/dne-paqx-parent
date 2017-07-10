package com.dell.cpsd.paqx.dne.service.model;

public class BootOrderStatus {

    private String status;
    private String message;

    public BootOrderStatus(){}

    public BootOrderStatus(String status, String message){
        this.status=status;
        this.message= message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("BootOrderStatus{");
        builder.append("status=").append(this.status);
        builder.append("message=").append(this.message);
        builder.append("}");

        return builder.toString();

    }
}
