package com.aerosaga;

import io.temporal.workflow.Workflow;
import io.temporal.activity.ActivityOptions;

import java.time.Duration;
import java.util.Map;

public class MissionWorkflowImpl implements MissionWorkflow {

    private String missionId;
    private String droneId;
    private String pickupLocation;
    private String deliveryLocation;

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
    public Map<String, String> getMissionDetails() {

        return Map.of(
            "missionId", missionId,
            "droneId", droneId,
            "pickupLocation", pickupLocation,
            "deliveryLocation", deliveryLocation,
            "status", missionStatus
        );
    }

    @Override
    public String startMission(
            String missionId,
            String droneId,
            String pickupLocation,
            String deliveryLocation) {

        this.missionId = missionId;
        this.droneId = droneId;
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;

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