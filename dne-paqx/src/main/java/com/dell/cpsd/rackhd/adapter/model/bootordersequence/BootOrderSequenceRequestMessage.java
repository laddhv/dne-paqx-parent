/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.rackhd.adapter.model.bootordersequence;

import com.dell.cpsd.common.rabbitmq.annotation.Message;
import com.dell.cpsd.common.rabbitmq.message.HasMessageProperties;
import com.dell.cpsd.rackhd.adapter.rabbitmq.MessageProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Message(value = "com.dell.cpsd.rackhd.adapter.model.bootordersequence.BootOrderSequenceRequestMessage", version = "1.0")
@JsonPropertyOrder({"messageProperties", "bootOrderSequenceRequest"})
public class BootOrderSequenceRequestMessage implements HasMessageProperties<MessageProperties> {

    /**
     * AMQP properties
     * <p>
     * AMQP properties.
     * (Required)
     *
     */
    @JsonProperty("messageProperties")
    @JsonPropertyDescription("AMQP properties.")
    private MessageProperties messageProperties;


    /**
     * Boot order sequence request object
     * <p>
     * Boot order sequence request object.
     * (Required)
     *
     */
    @JsonProperty("bootOrderSequence")
    @JsonPropertyDescription("AMQP properties.")
    private BootOrderSequenceRequest bootOrderSequenceRequest;


    /**
     * No args constructor for use in serialization
     *
     */
    public BootOrderSequenceRequestMessage()
    {

    }


    @JsonProperty("messageProperties")
    @Override
    public MessageProperties getMessageProperties() {
        return messageProperties;
    }


    @JsonProperty("messageProperties")
    @Override
    public void setMessageProperties(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }


    @JsonProperty("bootOrderSequence")
    public BootOrderSequenceRequest getBootOrderSequenceRequest() {
        return bootOrderSequenceRequest;
    }

    @JsonProperty("bootOrderSequence")
    public void setBootOrderSequenceRequest(BootOrderSequenceRequest bootOrderSequenceRequest) {
        this.bootOrderSequenceRequest = bootOrderSequenceRequest;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageProperties).append(bootOrderSequenceRequest).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BootOrderSequenceRequestMessage) == false) {
            return false;
        }
        BootOrderSequenceRequestMessage rhs = ((BootOrderSequenceRequestMessage) other);
        return new EqualsBuilder().append(messageProperties, rhs.messageProperties).append(bootOrderSequenceRequest, rhs.bootOrderSequenceRequest).isEquals();
    }
}
