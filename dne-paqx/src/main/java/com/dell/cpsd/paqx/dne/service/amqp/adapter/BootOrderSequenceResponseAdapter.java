/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.paqx.dne.service.amqp.adapter;

import com.dell.converged.capabilities.compute.discovered.nodes.api.ConfigureBootDeviceIdracResponseMessage;
import com.dell.cpsd.service.common.client.callback.IServiceCallback;
import com.dell.cpsd.service.common.client.callback.ServiceResponse;
import com.dell.cpsd.service.common.client.rpc.ServiceCallbackAdapter;
import com.dell.cpsd.service.common.client.rpc.ServiceCallbackRegistry;

/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */

public class BootOrderSequenceResponseAdapter implements ServiceCallbackAdapter<ConfigureBootDeviceIdracResponseMessage, ServiceResponse<ConfigureBootDeviceIdracResponseMessage>> {
    /**
     * The ServiceCallbackRegistry instance.
     */
    private ServiceCallbackRegistry serviceCallbackRegistry;

    /**
     * BootOrderSequenceResponseAdapter constructor.
     *
     * @param serviceCallbackRegistry - The <code>ServiceCallbackRegistry</code> instance.
     *
     * @since 1.0
     */
    public BootOrderSequenceResponseAdapter(ServiceCallbackRegistry serviceCallbackRegistry)
    {
        this.serviceCallbackRegistry = serviceCallbackRegistry;
    }

    /**
     * Transform a <code>BootOrderSequenceResponseMessage</code> to a
     * <code>ServiceResponse<BootOrderSequenceResponseMessage></code> instance.
     *
     * @param bootOrderSequenceResponse - The <code>BootOrderSequenceResponseMessage</code> to transform.
     *
     * @since 1.0
     */
    @Override
    public ServiceResponse<ConfigureBootDeviceIdracResponseMessage> transform(ConfigureBootDeviceIdracResponseMessage bootOrderSequenceResponse)
    {
        return new ServiceResponse<>(bootOrderSequenceResponse.getMessageProperties().getCorrelationId(), bootOrderSequenceResponse, null);
    }

    /**
     * Consume a <code>ServiceResponse<BootOrderSequenceResponseMessage></code> instance.
     *
     * @param callback - The <code>IServiceCallback</code> used to handle the message.
     * @param bootOrderSequenceResponse - The <code>ServiceResponse<BootOrderSequenceResponseMessage></code> to be consumed.
     *
     * @since 1.0
     */
    @Override
    public void consume(IServiceCallback callback, ServiceResponse<ConfigureBootDeviceIdracResponseMessage> bootOrderSequenceResponse)
    {
        callback.handleServiceResponse(bootOrderSequenceResponse);
    }

    /**
     * Remove the service callback for a <code>BootOrderSequenceResponseMessage</code> instance.
     *
     * @param bootOrderSequenceResponse - The <code>BootOrderSequenceResponseMessage</code> instance.
     *
     * @return The removed <code>IServiceCallback</code> instance.
     *
     * @since 1.0
     */
    @Override
    public IServiceCallback take(ConfigureBootDeviceIdracResponseMessage bootOrderSequenceResponse)
    {
        return serviceCallbackRegistry.removeServiceCallback(bootOrderSequenceResponse.getMessageProperties().getCorrelationId());
    }

    /**
     * Get the <code>BootOrderSequenceResponseMessage</code> class instance.
     *
     * @return The <code>Class<BootOrderSequenceResponseMessage></code> instance.
     *
     * @since 1.0
     */
    @Override
    public Class<ConfigureBootDeviceIdracResponseMessage> getSourceClass()
    {
        return ConfigureBootDeviceIdracResponseMessage.class;
    }
}
