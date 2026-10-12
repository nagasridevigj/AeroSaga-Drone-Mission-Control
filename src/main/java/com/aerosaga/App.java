
package com.aerosaga;

public class App {
    public static void main(String[] args) {
        Mission mission = new Mission(
            "M001", "D001", "Chennai", "Bengaluru"
        );

        System.out.println("AeroSaga Mission Created");
        System.out.println("Mission ID: " + mission.getMissionId());
        System.out.println("Drone ID: " + mission.getDroneId());
        System.out.println("Pickup: " + mission.getPickupLocation());
        System.out.println("Delivery: " + mission.getDeliveryLocation());
        System.out.println("Status: " + mission.getStatus());
    }
}
