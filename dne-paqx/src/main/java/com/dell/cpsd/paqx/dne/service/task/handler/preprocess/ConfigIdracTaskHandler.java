/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 */

package com.dell.cpsd.paqx.dne.service.task.handler.preprocess;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.cpsd.paqx.dne.domain.IWorkflowTaskHandler;
import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.model.FirstAvailableDiscoveredNodeResponse;
import com.dell.cpsd.paqx.dne.service.model.IdracInfo;
import com.dell.cpsd.paqx.dne.service.model.IdracNetworkSettingsRequest;
import com.dell.cpsd.paqx.dne.service.model.IdracNetworkSettingsResponseInfo;
import com.dell.cpsd.paqx.dne.service.model.NodeInfo;
import com.dell.cpsd.paqx.dne.service.model.Status;
import com.dell.cpsd.paqx.dne.service.model.TaskResponse;
import com.dell.cpsd.paqx.dne.service.task.handler.BaseTaskHandler;

/**
 * Task responsible for configuring the iDRAC network settings.
 * 
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */

public class ConfigIdracTaskHandler extends BaseTaskHandler implements IWorkflowTaskHandler
{
    /*
     * The logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigIdracTaskHandler.class);

    /*
     * The <code>NodeService</code> instance
     */
    private NodeService         nodeService;

    /**
     * ConfigIdracTaskHandler constructor.
     * 
     * @param nodeService
     *            - The <code>NodeService</code> instance.
     * 
     * @since 1.0
     */
    public ConfigIdracTaskHandler(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }

    /**
     * Perform the task of configuring the iDRAC network settings.
     * 
     * @param job
     *            - The <code>Job</code> this task is part of.
     * 
     * @since 1.0
     */
    @Override
    public boolean executeTask(Job job)
    {
        LOGGER.info("Execute ConfigIdracTaskHandler task");

        TaskResponse response = initializeResponse(job);

        try
        {
            Map<String, TaskResponse> responseMap = job.getTaskResponseMap();
            TaskResponse findNodeTask = (FirstAvailableDiscoveredNodeResponse) responseMap.get("findAvailableNodes");
            if (findNodeTask == null)
            {
                throw new IllegalStateException("No discovered node task found.");
            }

            if (findNodeTask.getResults().get("nodeID") == null)
            {
                throw new IllegalStateException("No discovered node info found.");
            }

            String nodeId = findNodeTask.getResults().get("nodeID").toString();

            String ipAddress = job.getInputParams().getIdracIpAddress();
            String gatewayIpAddress = job.getInputParams().getIdracGatewayIpAddress();
            String subnetMask = job.getInputParams().getIdracSubnetMask();

            LOGGER.info("NodeId:" + nodeId);
            LOGGER.info("Idrac input request parameters: " + job.getInputParams().toString());

            IdracNetworkSettingsRequest idracNetworkSettingsRequest = new IdracNetworkSettingsRequest();
            idracNetworkSettingsRequest.setNodeId(nodeId);
            idracNetworkSettingsRequest.setIdracIpAddress(ipAddress);
            idracNetworkSettingsRequest.setIdracGatewayIpAddress(gatewayIpAddress);
            idracNetworkSettingsRequest.setIdracSubnetMask(subnetMask);

            IdracInfo idracInfo = nodeService.idracNetworkSettings(idracNetworkSettingsRequest);
            if (idracInfo != null)
            {
                response.setResults(buildResponseResult(idracInfo));
//                response.setIdracInfo(idracInfo);
                response.setWorkFlowTaskStatus(Status.SUCCEEDED);
                return true;
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Error configuring idrac network settings", e);
            response.addError(e.toString());
        }

        response.setWorkFlowTaskStatus(Status.FAILED);
        return false;
    }

    /*
     * This method add all the node information to the response object
     */
    private Map<String, Object> buildResponseResult(IdracInfo idracInfo)
    {
        Map<String, Object> result = new HashMap<>();

        if (idracInfo == null)
        {
            return result;
        }

        if (idracInfo.getNodeId() != null)
        {
            result.put("nodeID", idracInfo.getNodeId());
        }
        
        if (idracInfo.getMessage() != null)
        {
            result.put("message", idracInfo.getMessage());
        }
        
        if (idracInfo.getIdracIpAddress() != null)
        {
            result.put("idracIpAddress", idracInfo.getIdracIpAddress());
        }
        
        if (idracInfo.getIdracGatewayIpAddress() != null)
        {
            result.put("idracGatewayIpAddress", idracInfo.getIdracGatewayIpAddress());
        }

        if (idracInfo.getIdracSubnetMask() != null)
        {
            result.put("idracSubnetMask", idracInfo.getIdracSubnetMask());
        }
        
        return result;
    }
    
    /**
     * Create the <code>IdracNetworkSettingsResponseInfo</code> instance and initialize it.
     * 
     * @param job
     *            - The <code>Job</code> this task is part of.
     */
    @Override
    public IdracNetworkSettingsResponseInfo initializeResponse(Job job)
    {
        IdracNetworkSettingsResponseInfo response = new IdracNetworkSettingsResponseInfo();
        response.setWorkFlowTaskName(job.getCurrentTask().getTaskName());
        response.setWorkFlowTaskStatus(Status.IN_PROGRESS);
        job.addTaskResponse(job.getStep(), response);
        return response;
    }
}
