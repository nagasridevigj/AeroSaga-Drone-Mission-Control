package com.aerosaga;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.QueryMethod;

import java.util.Map;

@WorkflowInterface
public interface MissionWorkflow {

    @WorkflowMethod
    String startMission(
            String missionId,
            String droneId,
            String pickupLocation,
            String deliveryLocation
    );

    @QueryMethod
    String getMissionStatus();

    @QueryMethod
    Map<String, String> getMissionDetails();
}