package com.aerosaga.controller;

import com.aerosaga.MissionWorkflow;
import com.aerosaga.MissionWorker;
import com.aerosaga.dto.request.MissionRequest;

import io.temporal.api.enums.v1.WorkflowIdReusePolicy;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowExecutionAlreadyStarted;

import io.temporal.client.WorkflowOptions;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import io.temporal.client.WorkflowNotFoundException;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final WorkflowClient workflowClient;

    public MissionController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    // Create a new mission
    @PostMapping
    public ResponseEntity<Map<String, String>> createMission(
            @Valid @RequestBody MissionRequest request) {

        String missionId = request.getMissionId().trim();
        String droneId = request.getDroneId().trim();
        String pickupLocation = request.getPickupLocation().trim();
        String deliveryLocation = request.getDeliveryLocation().trim();

        String workflowId = "MISSION-" + missionId;

        MissionWorkflow workflow = workflowClient.newWorkflowStub(
                MissionWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setWorkflowId(workflowId)
                        .setTaskQueue(MissionWorker.TASK_QUEUE)
                        .setWorkflowIdReusePolicy(
                                WorkflowIdReusePolicy.WORKFLOW_ID_REUSE_POLICY_REJECT_DUPLICATE
                        )
                        .build()
        );

        try {
            WorkflowClient.start(
                    workflow::startMission,
                    missionId,
                    droneId,
                    pickupLocation,
                    deliveryLocation
            );

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    Map.of(
                            "missionId", missionId,
                            "workflowId", workflowId,
                            "status", "STARTED"
                    )
            );

        } catch (WorkflowExecutionAlreadyStarted exception) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of(
                            "missionId", missionId,
                            "workflowId", workflowId,
                            "status", "ALREADY_EXISTS",
                            "message", "A mission with this ID already exists."
                    )
            );
        }
    }

    // Get mission status
   @GetMapping("/{missionId}/status")
public ResponseEntity<Map<String, String>> getMissionStatus(
        @PathVariable("missionId") String missionId) {

    String workflowId = "MISSION-" + missionId;

    try {
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

    } catch (WorkflowNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "missionId", missionId,
                        "message", "Mission not found."
                )
        );
    }
}

    // Get complete mission details
   @GetMapping("/{missionId}")
public ResponseEntity<Map<String, String>> getMissionDetails(
        @PathVariable("missionId") String missionId) {

    String workflowId = "MISSION-" + missionId;

    try {
        MissionWorkflow workflow = workflowClient.newWorkflowStub(
                MissionWorkflow.class, workflowId
        );

        return ResponseEntity.ok(workflow.getMissionDetails());

    } catch (WorkflowNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "missionId", missionId,
                        "message", "Mission not found."
                )
        );
    }
} 