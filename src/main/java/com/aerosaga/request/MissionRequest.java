
package com.aerosaga.dto.request;

import jakarta.validation.constraints.NotBlank;

public class MissionRequest {

    @NotBlank(message = "Mission ID is required")
    private String missionId;

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }
}
