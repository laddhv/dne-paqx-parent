package com.dell.cpsd.paqx.dne.service.model;

import java.util.List;

public class BootOrderStatus {

    private String status;
    private List<String> errors;

    public BootOrderStatus(){}

    public BootOrderStatus(String status, List<String> errors){
        this.status=status;
        this.errors= errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }


    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("BootOrderStatus{");
        builder.append("status=").append(this.status);
        builder.append("errors=").append(this.errors);
        builder.append("}");

        return builder.toString();

    }
}
