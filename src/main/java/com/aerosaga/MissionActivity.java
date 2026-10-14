
package com.aerosaga;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MissionActivity {

    @ActivityMethod
    String pickupPackage(String missionId);

    @ActivityMethod
    String deliverPackage(String missionId);
}
