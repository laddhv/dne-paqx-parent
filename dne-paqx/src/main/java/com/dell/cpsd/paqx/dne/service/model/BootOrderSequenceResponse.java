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

public class BootOrderSequenceResponse extends TaskResponse {

    private BootOrderStatus bootOrderStatus;

    public BootOrderStatus getBootOrderStatus() {
        return bootOrderStatus;
    }

    public void setBootOrderStatus(BootOrderStatus bootOrderStatus) {
        this.bootOrderStatus = bootOrderStatus;
    }


}
