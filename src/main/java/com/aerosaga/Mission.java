
package com.aerosaga;

public class Mission {
    private String missionId;
    private String droneId;
    private String pickupLocation;
    private String deliveryLocation;
    private String status;

    public Mission(String missionId, String droneId,
                   String pickupLocation, String deliveryLocation) {
        this.missionId = missionId;
        this.droneId = droneId;
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;
        this.status = "CREATED";
    }

    public String getMissionId() {
        return missionId;
    }

    public String getDroneId() {
        return droneId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }
  
    public String getStatus() {
        return status;
    }
}
 