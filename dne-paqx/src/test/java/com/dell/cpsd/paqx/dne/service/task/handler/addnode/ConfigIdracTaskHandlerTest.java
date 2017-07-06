package com.dell.cpsd.paqx.dne.service.task.handler.addnode;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.repository.InMemoryJobRepository;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.WorkflowService;
import com.dell.cpsd.paqx.dne.service.WorkflowServiceImpl;
import com.dell.cpsd.paqx.dne.service.model.FirstAvailableDiscoveredNodeResponse;
import com.dell.cpsd.paqx.dne.service.model.IdracInfo;
import com.dell.cpsd.paqx.dne.service.model.IdracNetworkSettingsRequest;
import com.dell.cpsd.paqx.dne.service.model.NodeExpansionRequest;
import com.dell.cpsd.paqx.dne.service.model.NodeInfo;
import com.dell.cpsd.paqx.dne.service.model.NodeStatus;
import com.dell.cpsd.paqx.dne.service.task.handler.preprocess.ConfigIdracTaskHandler;
import com.dell.cpsd.paqx.dne.service.workflow.preprocess.PreProcessService;
import com.dell.cpsd.paqx.dne.service.workflow.preprocess.PreProcessTaskConfig;
import com.dell.cpsd.service.common.client.exception.ServiceExecutionException;
import com.dell.cpsd.service.common.client.exception.ServiceTimeoutException;

@RunWith(MockitoJUnitRunner.class)
public class ConfigIdracTaskHandlerTest {
	
    /**
     * The <code>NodeService</code> instance
     * 
     * @since   1.0
     */
	@Mock
	private NodeService nodeService = null;
	
	/*
	 * The job running the notify node discovery service node allocation complete task handler
	 */
	private Job job = null;
	
	
	/**
	 * Test pre-requisite setUp
	 */
	@Before
    public void setUp() {
		PreProcessTaskConfig preProcessTaskConfig = new PreProcessTaskConfig();
		WorkflowService workflowService = new WorkflowServiceImpl(new InMemoryJobRepository(), preProcessTaskConfig.preProcessWorkflowSteps());
		
		PreProcessService preProcessService = new PreProcessService();
		preProcessService.setWorkflowService(workflowService);
		
		this.job = preProcessService.createWorkflow("preProcess", "startPreProcessWorkflow", "submitted");
		
		this.job.setInputParams(new NodeExpansionRequest("idracIpAddress", "idracGatewayIpAddress", "idracSubnetMask", 
                "managementIpAddress", "esxiKernelIpAddress1", "esxiKernelIpAddress2", 
                "scaleIOSVMDataIpAddress1", "scaleIOSVMDataIpAddress2", "scaleIOSVMManagementIpAddress"));
		
		FirstAvailableDiscoveredNodeResponse response = new FirstAvailableDiscoveredNodeResponse();
        response.setNodeInfo(new NodeInfo("symphonyUuid", "nodeId", NodeStatus.DISCOVERED));
        this.job.addTaskResponse("findAvailableNodes", response);
         
        this.job.changeToNextStep("configIdrac");
	}
	
	/**
	 * Successful execution of ConfigIdracTaskHandler.executeTask(job)
	 * 
	 * @throws ServiceTimeoutException
	 * @throws ServiceExecutionException
	 */
	@Test
	public void testExecuteTask_successful_case() throws ServiceTimeoutException, ServiceExecutionException {
		
		IdracInfo idracInfo = new IdracInfo();		
		ArgumentCaptor<IdracNetworkSettingsRequest> captor = ArgumentCaptor.forClass(IdracNetworkSettingsRequest.class);
		
		ConfigIdracTaskHandler configIdracTaskHandler = new ConfigIdracTaskHandler(this.nodeService);
		
		when(this.nodeService.idracNetworkSettings(any(IdracNetworkSettingsRequest.class))).thenReturn(idracInfo);
		boolean expectedResult = true;
		boolean actualResult = configIdracTaskHandler.executeTask(job);
		
		assertEquals(expectedResult, actualResult);
		verify(this.nodeService, times(1)).idracNetworkSettings(captor.capture());		
	}
	
	
	  /**
     * Test error execution of ConfigIdracTaskHandler.executeTask() method
     *  - test error case where no discovered node instance is present.
     *  
	 * @throws ServiceExecutionException 
	 * @throws ServiceTimeoutException 
     *  
     *  @since   1.0
     */
    @Test
    public void testExecuteTask_no_discovered_node() throws ServiceTimeoutException, ServiceExecutionException {      
        ArgumentCaptor<IdracNetworkSettingsRequest> captor = ArgumentCaptor.forClass(IdracNetworkSettingsRequest.class);
                
        ConfigIdracTaskHandler configIdracTaskHandler = new ConfigIdracTaskHandler(this.nodeService);
        boolean expectedResult = false;
        when(this.nodeService.idracNetworkSettings(any(IdracNetworkSettingsRequest.class))).thenReturn(null);
        boolean actualResult = configIdracTaskHandler.executeTask(this.job);
        
        assertEquals(expectedResult, actualResult);
        verify(this.nodeService, times(1)).idracNetworkSettings(captor.capture());
    }

}
