package com.dell.cpsd.paqx.dne.service.task.handler.addnode;

import java.util.Map;

/**
 * Task responsible for changing the idrac credentials.
 * 
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.cpsd.paqx.dne.domain.IWorkflowTaskHandler;
import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.model.ChangeIdracCredentialsResponse;
import com.dell.cpsd.paqx.dne.service.model.FirstAvailableDiscoveredNodeResponse;
import com.dell.cpsd.paqx.dne.service.model.NodeInfo;
import com.dell.cpsd.paqx.dne.service.model.Status;
import com.dell.cpsd.paqx.dne.service.model.TaskResponse;
import com.dell.cpsd.paqx.dne.service.task.handler.BaseTaskHandler;

public class ChangeIdracCredentialsTaskHandler  extends BaseTaskHandler implements IWorkflowTaskHandler
{

    /*
     * The logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeIdracCredentialsTaskHandler.class);

    /*
     * The <code>NodeService</code> instance
     */
    private NodeService nodeService;
    
    /**
     * ChangeIdracCredentialsTaskHandler constructor.
     * 
     * @param nodeService
     *            - The <code>NodeService</code> instance.
     * 
     * @since 1.0
     */
    public ChangeIdracCredentialsTaskHandler(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }
    
    @Override
    public boolean executeTask(Job job)
    {
        LOGGER.info("Execute ChangeIdracCredentialsTaskHandler task");

        TaskResponse response = initializeResponse(job);

        try
        {
            Map<String, TaskResponse> responseMap = job.getTaskResponseMap();
            FirstAvailableDiscoveredNodeResponse findNodeTask = (FirstAvailableDiscoveredNodeResponse)responseMap.get("findAvailableNodes");
            if (findNodeTask == null)
            {
                throw new IllegalStateException("No discovered node task found.");
            }
            
            NodeInfo nodeInfo = findNodeTask.getNodeInfo();
            if (nodeInfo == null)
            {
                throw new IllegalStateException("No discovered node info found.");
            }
            
            ChangeIdracCredentialsResponse responseMessage = this.nodeService.changeIdracCredentials(nodeInfo.getNodeId());
            
            if ("SUCCESS".equalsIgnoreCase(responseMessage.getMessage()))
            {
                response.setWorkFlowTaskStatus(Status.SUCCEEDED);
                return true;
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Error while changing the idrac credentials", e);
            response.addError(e.toString());
        }

        response.setWorkFlowTaskStatus(Status.FAILED);
        return false;
    }
    
    /**
     * Create the <code>ChangeIdracCredentialsResponse</code> instance and initialize it.
     * 
     * @param job
     *            - The <code>Job</code> this task is part of.
     */
    @Override
    public ChangeIdracCredentialsResponse initializeResponse(Job job)
    {
        ChangeIdracCredentialsResponse response = new ChangeIdracCredentialsResponse();
        response.setWorkFlowTaskName(job.getCurrentTask().getTaskName());
        response.setWorkFlowTaskStatus(Status.IN_PROGRESS);
        job.addTaskResponse(job.getStep(), response);

        return response;
    }

}
