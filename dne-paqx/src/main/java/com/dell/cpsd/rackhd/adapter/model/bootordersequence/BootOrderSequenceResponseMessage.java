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

/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Message(value = "com.dell.cpsd.rackhd.adapter.model.bootordersequence.BootOrderSequenceResponseMessage", version = "1.0")
@JsonPropertyOrder({"messageProperties", "bootOrderSequenceResponse"})
public class BootOrderSequenceResponseMessage implements HasMessageProperties<MessageProperties> {

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


    @JsonProperty("bootOrderSequenceResponse")
    @JsonPropertyDescription("AMQP properties.")
    private BootOrderSequenceResponse bootOrderSequenceResponse;


    /**
     * No args constructor for use in serialization
     *
     */
    public BootOrderSequenceResponseMessage()
    {

    }

    public BootOrderSequenceResponseMessage(MessageProperties messageProperties, BootOrderSequenceResponse bootOrderSequenceResponse)
    {
        super();
        this.messageProperties = messageProperties;
        this.bootOrderSequenceResponse = bootOrderSequenceResponse;
    }

    @JsonProperty("messageProperties")
    @Override
    public MessageProperties getMessageProperties()
    {
        return messageProperties;
    }

    @JsonProperty("messageProperties")
    @Override
    public void setMessageProperties(MessageProperties messageProperties)
    {
        this.messageProperties = messageProperties;
    }

    @JsonProperty("bootOrderSequenceResponse")
    public BootOrderSequenceResponse getBootOrderSequenceResponse()
    {
        return bootOrderSequenceResponse;
    }

    @JsonProperty("bootOrderSequenceResponse")
    public void setBootOrderSequenceResponse(BootOrderSequenceResponse bootOrderSequenceResponse)
    {
        this.bootOrderSequenceResponse = bootOrderSequenceResponse;
    }
}
