
package com.aerosaga;

import io.temporal.workflow.Workflow;
import io.temporal.activity.ActivityOptions;
import java.time.Duration;

public class MissionWorkflowImpl implements MissionWorkflow {

    private final MissionActivity activity =
        Workflow.newActivityStub(
            MissionActivity.class,
            ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(30))
                .build()
        );

    @Override
    public String startMission(String missionId) {

        String pickupStatus =
            activity.pickupPackage(missionId);

        String deliveryStatus =
            activity.deliverPackage(missionId);

        return "Mission " + missionId
            + ": " + pickupStatus
            + ", " + deliveryStatus;
    }
}
