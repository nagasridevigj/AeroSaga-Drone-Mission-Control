package com.aerosaga;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class MissionClient {

    public static void main(String[] args) {

        WorkflowServiceStubs service =
                WorkflowServiceStubs.newLocalServiceStubs();

        try {
            WorkflowClient client =
                    WorkflowClient.newInstance(service);

            WorkflowOptions options =
                    WorkflowOptions.newBuilder()
                            .setTaskQueue(MissionWorker.TASK_QUEUE)
                            .setWorkflowId("MISSION-M002")
                            .build();

            MissionWorkflow workflow =
                    client.newWorkflowStub(
                            MissionWorkflow.class,
                            options
                    );

            String result = workflow.startMission(
                    "M002",
                    "DRONE-01",
                    "Chennai",
                    "Coimbatore"
            );

            System.out.println("Final Result: " + result);
            System.out.println("Mission Status: "
                    + workflow.getMissionStatus());

        } finally {
            service.shutdown();
        }
    }
}