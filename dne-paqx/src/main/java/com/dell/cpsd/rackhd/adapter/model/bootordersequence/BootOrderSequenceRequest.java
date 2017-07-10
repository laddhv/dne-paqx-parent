/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.rackhd.adapter.model.bootordersequence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"nodeId", "ipAddress"})
public class BootOrderSequenceRequest {

    @JsonProperty("nodeId")
    @NotNull
    private String nodeId;

    @JsonProperty("ipAddress")
    @NotNull
    private String ipAddress;

    public BootOrderSequenceRequest()
    {

    }

    public BootOrderSequenceRequest(String nodeId, String ipAddress)
    {
        super();
        this.nodeId = nodeId;
        this.ipAddress = ipAddress;
    }

    @JsonProperty("nodeId")
    public String getNodeId()
    {
        return nodeId;
    }

    @JsonProperty("nodeId")
    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    @JsonProperty("ipAddress")
    public String getIpAddress()
    {
        return ipAddress;
    }

    @JsonProperty("ipAddress")
    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(nodeId).append(ipAddress).toHashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof BootOrderSequenceRequest)
        {
            return false;
        }
        BootOrderSequenceRequest rhs = ((BootOrderSequenceRequest) other);
        return new EqualsBuilder().append(nodeId, rhs.nodeId).append(ipAddress, rhs.ipAddress)
                .isEquals();
    }
}
