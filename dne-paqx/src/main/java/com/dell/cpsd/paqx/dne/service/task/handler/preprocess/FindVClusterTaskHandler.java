/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 * </p>
 */

package com.dell.cpsd.paqx.dne.service.task.handler.preprocess;

import com.dell.cpsd.paqx.dne.domain.IWorkflowTaskHandler;
import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.model.IdracInfo;
import com.dell.cpsd.paqx.dne.service.model.Status;
import com.dell.cpsd.paqx.dne.service.model.TaskResponse;
import com.dell.cpsd.paqx.dne.service.model.VClusterTaskResponse;
import com.dell.cpsd.paqx.dne.service.model.VirtualizationCluster;
import com.dell.cpsd.paqx.dne.service.task.handler.BaseTaskHandler;
import com.dell.cpsd.virtualization.capabilities.api.ClusterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FindVClusterTaskHandler extends BaseTaskHandler implements IWorkflowTaskHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindVClusterTaskHandler.class);

    private NodeService nodeService;

    public FindVClusterTaskHandler(NodeService nodeService){
        this.nodeService = nodeService;
    }

    @Override
    public boolean executeTask(Job job)
    {
        LOGGER.info("Execute FindVCluster task");
        TaskResponse response = initializeResponse(job);
        try {
            List<VirtualizationCluster> clusters = nodeService.listClusters();
            List<ClusterInfo> clusterInfo = clusters.stream().map(c -> new ClusterInfo(c.getName(), c.getNumberOfHosts()))
                    .collect(Collectors.toList());

//            response.setClusterInfo(clusterInfo);
            buildResponseResult(clusterInfo);
            response.setWorkFlowTaskStatus(Status.SUCCEEDED);
            return true;
        }
        catch(Exception e){
            LOGGER.info("", e);
            response.setWorkFlowTaskStatus(Status.FAILED);
            response.addError(e.toString());
        }
        return false;
    }

    /*
     * This method add all the node information to the response object
     */
    private Map<String, Object> buildResponseResult(ClusterInfo clusterInfo)
    {
        Map<String, Object> result = new HashMap<>();

        if (clusterInfo == null)
        {
            return result;
        }

        if (clusterInfo.getName() != null)
        {
            result.put("name", clusterInfo.getName());
        }
        
        if (clusterInfo.getNumberOfHosts() != null)
        {
            result.put("numberOfHosts", clusterInfo.getNumberOfHosts());
        }
        
        return result;
    }
    
    @Override
    public VClusterTaskResponse initializeResponse(Job job){
        VClusterTaskResponse response = new VClusterTaskResponse();
        response.setWorkFlowTaskName(job.getCurrentTask().getTaskName());
        response.setWorkFlowTaskStatus(Status.IN_PROGRESS);
        job.addTaskResponse(job.getStep(), response);

        return response;
    }

}
