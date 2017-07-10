/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.paqx.dne.service.model;

/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */

public class BootOrderSeqResponse {
    private String message;

    public BootOrderSeqResponse(){}

    public BootOrderSeqResponse(String message){
        this.message= message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("BootOrderSequenceInfo{");
        builder.append("message=").append(this.message);
        builder.append("}");

        return builder.toString();

    }


}
