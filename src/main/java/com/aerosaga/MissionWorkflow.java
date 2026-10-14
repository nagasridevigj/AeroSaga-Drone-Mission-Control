
package com.aerosaga;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface MissionWorkflow {

    @WorkflowMethod
    String startMission(String missionId);
}
