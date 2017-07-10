/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 */

package com.dell.cpsd.paqx.dne.service.task.handler.addnode;

import com.dell.cpsd.paqx.dne.domain.IWorkflowTaskHandler;
import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.model.*;
import com.dell.cpsd.paqx.dne.service.task.handler.BaseTaskHandler;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Task responsible for finding discovered nodes.
 * 
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */

public class FindDiscoveredNodesTaskHandler extends BaseTaskHandler implements IWorkflowTaskHandler
{
    /*
     * The logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FindDiscoveredNodesTaskHandler.class);

    /*
     * The <code>NodeService</code> instance
     */
    private NodeService         nodeService;

    /**
     * FindDiscoveredNodesTaskHandler constructor.
     * 
     * @param nodeService
     *            - The <code>NodeService</code> instance.
     * 
     * @since 1.0
     */
    public FindDiscoveredNodesTaskHandler(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }

    /**
     * Perform the task of finding discovered nodes.
     * 
     * @param job
     *            - The <code>Job</code> this task is part of.
     * 
     * @since 1.0
     */
    @Override
    public boolean executeTask(Job job)
    {
        LOGGER.info("Execute Find Discovered Nodes");
        TaskResponse response = initializeResponse(job);

        try
        {
            List<DiscoveredNode> discoveredNodesResponse = nodeService.listDiscoveredNodes();

            if (discoveredNodesResponse != null)
            {
                final List<NodeInfo> nodeInfoList = discoveredNodesResponse.stream()
                        .map(n -> new NodeInfo(n.getConvergedUuid(), n.getNodeId(), NodeStatus.valueOf(n.getNodeStatus().toString())))
                        .collect(Collectors.toList());

                if (!CollectionUtils.isEmpty(nodeInfoList))
                {
                    NodeInfo nodeInfo = (NodeInfo) nodeInfoList.get(0);
                    LOGGER.info("Found first available node : " + nodeInfo);
                    LOGGER.info("Building task response based on the node info.");
                    response.setResults(buildResponseResult(nodeInfo));
                    // response.setNodeInfo(nodeInfo);
                    response.setWorkFlowTaskStatus(Status.SUCCEEDED);
                    return true;
                }
            }

            LOGGER.warn("No discovered nodes found");
        }
        catch (Exception e)
        {
            LOGGER.error("Error finding discovered nodes", e);
            response.addError(e.toString());
        }

        response.setWorkFlowTaskStatus(Status.FAILED);
        return false;
    }

    /*
     * This method add all the node information to the response object
     */
    private Map<String, Object> buildResponseResult(NodeInfo nodeInfo)
    {
        Map<String, Object> result = new HashMap<>();

        if (nodeInfo == null)
        {
            return result;
        }

        if (nodeInfo.getSymphonyUuid() != null)
        {
            result.put("symphonyUUID", nodeInfo.getSymphonyUuid());
        }

        if (nodeInfo.getNodeId() != null)
        {
            result.put("nodeID", nodeInfo.getNodeId());
        }

        if (nodeInfo.getNodeStatus() != null)
        {
            result.put("nodeStatus", nodeInfo.getNodeStatus());
        }

        if (nodeInfo.getIdentity() != null)
        {
            result.put("identity", nodeInfo.getIdentity());
        }

        if (nodeInfo.getDefinition() != null)
        {
            result.put("definition", nodeInfo.getDefinition());
        }

        if (nodeInfo.getParentGroups() != null)
        {
            result.put("parentGroups", nodeInfo.getParentGroups());
        }

        if (nodeInfo.getEndpoints() != null)
        {
            result.put("endpoints", nodeInfo.getEndpoints());
        }

        return result;
    }

    /**
     * Create the <code>FirstAvailableDiscoveredNodeResponse</code> instance and initialize it.
     * 
     * @param job
     *            - The <code>Job</code> this task is part of.
     */
    @Override
    public FirstAvailableDiscoveredNodeResponse initializeResponse(Job job)
    {
        FirstAvailableDiscoveredNodeResponse response = new FirstAvailableDiscoveredNodeResponse();
        response.setWorkFlowTaskName(job.getCurrentTask().getTaskName());
        response.setWorkFlowTaskStatus(Status.IN_PROGRESS);
        job.addTaskResponse(job.getStep(), response);

        return response;
    }

}
