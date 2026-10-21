
package com.aerosaga;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.QueryMethod;

@WorkflowInterface
public interface MissionWorkflow {

    @WorkflowMethod
    String startMission(String missionId);

    @QueryMethod
    String getMissionStatus();
}
