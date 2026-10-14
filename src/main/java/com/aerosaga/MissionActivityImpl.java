
package com.aerosaga;

public class MissionActivityImpl implements MissionActivity {

    @Override
    public String pickupPackage(String missionId) {
        System.out.println(
            "Package picked up for mission: " + missionId
        );
        return "PICKUP_COMPLETED";
    }

    @Override
    public String deliverPackage(String missionId) {
        System.out.println(
            "Package delivered for mission: " + missionId
        );
        return "DELIVERY_COMPLETED";
    }
}
