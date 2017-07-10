/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 */

package com.dell.cpsd.paqx.dne.service.task.handler.preprocess;

import com.dell.cpsd.paqx.dne.domain.IWorkflowTaskHandler;
import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.WorkflowService;
import com.dell.cpsd.paqx.dne.service.model.Status;
import com.dell.cpsd.paqx.dne.service.model.TaskResponse;
import com.dell.cpsd.paqx.dne.service.task.handler.BaseTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Task responsible for handling Boot Order Sequence
 *
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */
@Component
public class SetBootOrderAndDisablePXETaskHandler extends BaseTaskHandler implements IWorkflowTaskHandler {

    /*
     * The logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignDefaultHostNameTaskHandler.class);

    /*
     * The <code>NodeService</code> instance
     */
    private WorkflowService workflowService;

    public SetBootOrderAndDisablePXETaskHandler(NodeService nodeService){
        this.workflowService = workflowService;
    }

    @Override
    public boolean executeTask(Job job)
    {
        LOGGER.info("Execute BootOrderSequence Task");
        TaskResponse response = initializeResponse(job);
        try {
            Thread.sleep(20000);
        }
        catch(Exception e){}
        response.setWorkFlowTaskStatus(Status.SUCCEEDED);
        return true;
    }

}
