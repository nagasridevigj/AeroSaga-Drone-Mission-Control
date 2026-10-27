
package com.aerosaga.controller;

import com.aerosaga.MissionWorkflow;
import com.aerosaga.MissionWorker;
import com.aerosaga.dto.request.MissionRequest;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final WorkflowClient workflowClient;

    public MissionController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createMission(
            @Valid @RequestBody MissionRequest request) {

        String missionId = request.getMissionId().trim();
        String workflowId = "MISSION-" + missionId;

        MissionWorkflow workflow = workflowClient.newWorkflowStub(
                MissionWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setWorkflowId(workflowId)
                        .setTaskQueue(MissionWorker.TASK_QUEUE)
                        .build()
        );

        WorkflowClient.start(workflow::startMission, missionId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                Map.of(
                        "missionId", missionId,
                        "workflowId", workflowId,
                        "status", "STARTED"
                )
        );
    }

    @GetMapping("/{missionId}/status")
    public ResponseEntity<Map<String, String>> getMissionStatus(
            @PathVariable String missionId) {

        String workflowId = "MISSION-" + missionId;

        MissionWorkflow workflow = workflowClient.newWorkflowStub(
                MissionWorkflow.class, workflowId
        );

        String status = workflow.getMissionStatus();

        return ResponseEntity.ok(
                Map.of(
                        "missionId", missionId,
                        "status", status
                )
        );
    }
}

