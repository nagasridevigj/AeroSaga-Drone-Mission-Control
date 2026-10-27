
package com.aerosaga.config;

import com.aerosaga.MissionActivityImpl;
import com.aerosaga.MissionWorkflowImpl;
import com.aerosaga.MissionWorker;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs.newLocalServiceStubs();
    }

    @Bean
    public WorkflowClient workflowClient(
            WorkflowServiceStubs serviceStubs) {
        return WorkflowClient.newInstance(serviceStubs);
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient client) {
        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(MissionWorker.TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(
                MissionWorkflowImpl.class
        );

        worker.registerActivitiesImplementations(
                new MissionActivityImpl()
        );

        factory.start();
        return factory;
    }
}
