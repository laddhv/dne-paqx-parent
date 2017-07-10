/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.rackhd.adapter.model.bootordersequence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BootOrderSequenceResponse {

    @JsonProperty("message")
    @NotNull
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public BootOrderSequenceResponse()
    {

    }

    public BootOrderSequenceResponse(String message)
    {
        super();
        this.message = message;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.getMessage()).toHashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof BootOrderSequenceResponse)
        {
            return false;
        }
        BootOrderSequenceResponse rhs = ((BootOrderSequenceResponse) other);
        return new EqualsBuilder().append(this.getMessage(), rhs.getMessage()).isEquals();
    }
}
