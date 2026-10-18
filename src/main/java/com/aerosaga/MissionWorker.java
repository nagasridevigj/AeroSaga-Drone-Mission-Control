
package com.aerosaga;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class MissionWorker {

    public static final String TASK_QUEUE =
            "AEROSAGA_TASK_QUEUE";

    public static void main(String[] args) {

        WorkflowServiceStubs service =
                WorkflowServiceStubs.newLocalServiceStubs();

        WorkflowClient client =
                WorkflowClient.newInstance(service);

        WorkerFactory factory =
                WorkerFactory.newInstance(client);

        Worker worker =
                factory.newWorker(TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(
                MissionWorkflowImpl.class);

        worker.registerActivitiesImplementations(
                new MissionActivityImpl());

        factory.start();

        System.out.println(
                "AeroSaga Worker started successfully!");
        System.out.println(
                "Task Queue: " + TASK_QUEUE);
    }
}

 