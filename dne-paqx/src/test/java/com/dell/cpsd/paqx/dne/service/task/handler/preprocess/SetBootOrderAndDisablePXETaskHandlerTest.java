/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 */
package com.dell.cpsd.paqx.dne.service.task.handler.preprocess;

import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.repository.InMemoryJobRepository;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.WorkflowService;
import com.dell.cpsd.paqx.dne.service.WorkflowServiceImpl;
import com.dell.cpsd.paqx.dne.service.model.*;
import com.dell.cpsd.paqx.dne.service.workflow.preprocess.PreProcessService;
import com.dell.cpsd.paqx.dne.service.workflow.preprocess.PreProcessTaskConfig;
import com.dell.cpsd.service.common.client.exception.ServiceExecutionException;
import com.dell.cpsd.service.common.client.exception.ServiceTimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The tests for SetBootOrderAndDisablePXETaskHandler.
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */

@RunWith(MockitoJUnitRunner.class)
public class SetBootOrderAndDisablePXETaskHandlerTest {
    /*
     * The <code>NodeService</code> instance.
     * @since 1.0
     */
    @Mock
    private NodeService nodeService = null;

    /*
     * The job running the add node to system definition task handler.
     */
    private Job job         = null;

    /**
     * The test setup.
     *
     * @since 1.0
     */
    @Before
    public void setUp()
    {
        PreProcessTaskConfig preprocessConfig = new PreProcessTaskConfig();
        WorkflowService workflowService = new WorkflowServiceImpl(new InMemoryJobRepository(), preprocessConfig.preProcessWorkflowSteps());

        PreProcessService preprocessService = new PreProcessService();
        preprocessService.setWorkflowService(workflowService);

        this.job = preprocessService.createWorkflow("preProcessWorkflow", "startPreProcessWorkflow", "submitted");

        NodeExpansionRequest nodeExpansionRequest = new NodeExpansionRequest("idracIpAddress", "idracGatewayIpAddress", "idracSubnetMask",
                "managementIpAddress", "esxiKernelIpAddress1", "esxiKernelIpAddress2", "scaleIOSVMDataIpAddress1",
                "scaleIOSVMDataIpAddress2", "scaleIOSVMManagementIpAddress");
        this.job.setInputParams(nodeExpansionRequest);

        FirstAvailableDiscoveredNodeResponse response = new FirstAvailableDiscoveredNodeResponse();
        NodeInfo nodeInfo = new NodeInfo("symphonyUuid", "nodeId", NodeStatus.DISCOVERED);
        response.setNodeInfo(nodeInfo);
        this.job.addTaskResponse("findAvailableNodes", response);
        this.job.changeToNextStep("setBootOrderAndDisablePXE");
    }

    /**
     * Test successful execution of SetBootOrderAndDisablePXETaskHandler.executeTask() method
     *
     * @throws ServiceExecutionException
     * @throws ServiceTimeoutException
     *
     * @since 1.0
     */
    @Test
    public void testExecuteTask_successful_case() throws ServiceTimeoutException, ServiceExecutionException
    {
        BootOrderStatus bootOrderStatus = new BootOrderStatus();
        ArgumentCaptor<BootOrderSequenceRequest> requestCaptor = ArgumentCaptor.forClass(BootOrderSequenceRequest.class);
        when(this.nodeService.bootOrderStatus(requestCaptor.capture())).thenReturn(bootOrderStatus);

        SetBootOrderAndDisablePXETaskHandler instance = new SetBootOrderAndDisablePXETaskHandler(this.nodeService);
        boolean expectedResult = true;
        boolean actualResult = instance.executeTask(this.job);

        assertEquals(expectedResult, actualResult);
        verify(this.nodeService, times(1)).bootOrderStatus(requestCaptor.capture());
    }

    /**
     * Test error execution of SetBootOrderAndDisablePXETaskHandler.executeTask() method - test error case where no findAvailableNodes task response was
     * set.
     *
     * @throws ServiceExecutionException
     * @throws ServiceTimeoutException
     *
     * @since 1.0
     */
    @Test
    public void testExecuteTask_no_find_nodes_response() throws ServiceTimeoutException, ServiceExecutionException
    {
        Map<String, TaskResponse> taskResponse = this.job.getTaskResponseMap();
        taskResponse.remove("findAvailableNodes");

        ArgumentCaptor<BootOrderSequenceRequest> requestCaptor = ArgumentCaptor.forClass(BootOrderSequenceRequest.class);

        SetBootOrderAndDisablePXETaskHandler instance = new SetBootOrderAndDisablePXETaskHandler(this.nodeService);
        boolean expectedResult = false;
        boolean actualResult = instance.executeTask(this.job);

        assertEquals(expectedResult, actualResult);
        verify(this.nodeService, times(0)).bootOrderStatus(requestCaptor.capture());
    }

    /**
     * Test error execution of SetBootOrderAndDisablePXETaskHandler.executeTask() method - test error case where the NodeService bootOrderStatus
     * experiences an error.
     *
     * @throws ServiceExecutionException
     * @throws ServiceTimeoutException
     *
     * @since 1.0
     */
    @Test
    public void testExecuteTask_network_settings_error() throws ServiceTimeoutException, ServiceExecutionException
    {
        ArgumentCaptor<BootOrderSequenceRequest> requestCaptor = ArgumentCaptor.forClass(BootOrderSequenceRequest.class);
        when(this.nodeService.bootOrderStatus(requestCaptor.capture())).thenReturn(null);

        SetBootOrderAndDisablePXETaskHandler instance = new SetBootOrderAndDisablePXETaskHandler(this.nodeService);
        boolean expectedResult = false;
        boolean actualResult = instance.executeTask(this.job);

        assertEquals(expectedResult, actualResult);
        verify(this.nodeService, times(1)).bootOrderStatus(requestCaptor.capture());
    }

}
