package com.dell.cpsd.paqx.dne.service.task.handler.preprocess;

import com.dell.cpsd.paqx.dne.domain.IWorkflowTaskHandler;
import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.service.WorkflowService;
import com.dell.cpsd.paqx.dne.service.model.Status;
import com.dell.cpsd.paqx.dne.service.model.TaskResponse;
import com.dell.cpsd.paqx.dne.service.task.handler.BaseTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AssignDefaultCredentialsTaskHandler extends BaseTaskHandler implements IWorkflowTaskHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignDefaultCredentialsTaskHandler.class);

    private WorkflowService workflowService;

    public AssignDefaultCredentialsTaskHandler(WorkflowService workflowService){
        this.workflowService = workflowService;
    }

    @Override
    public boolean executeTask(Job job) {
        LOGGER.info("Execute assign Default Credentials task");
        TaskResponse response = initializeResponse(job);
        try {
            Thread.sleep(50000);
        }
        catch(Exception e){}
        response.setWorkFlowTaskStatus(Status.SUCCEEDED);
        LOGGER.info("Execute assign Default Credentials task - complete");
        return true;
    }


}
