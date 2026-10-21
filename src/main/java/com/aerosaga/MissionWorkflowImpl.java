
package com.aerosaga;

import io.temporal.workflow.Workflow;
import io.temporal.workflow.QueryMethod;
import io.temporal.activity.ActivityOptions;
import java.time.Duration;

public class MissionWorkflowImpl implements MissionWorkflow {

    private String missionStatus = "CREATED";

    private final MissionActivity activity =
        Workflow.newActivityStub(
            MissionActivity.class,
            ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(30))
                .build()
        );

    @Override
    public String getMissionStatus() {
        return missionStatus;
    }

    @Override
    public String startMission(String missionId) {

        missionStatus = "PICKUP_IN_PROGRESS";

        String pickupStatus = activity.pickupPackage(missionId);

        missionStatus = pickupStatus;

        missionStatus = "DELIVERY_IN_PROGRESS";

        String deliveryStatus = activity.deliverPackage(missionId);

        missionStatus = deliveryStatus;

        missionStatus = "COMPLETED";

        return "Mission " + missionId
            + ": " + pickupStatus
            + ", " + deliveryStatus;
    }
}
