package com.finnauxapp.ApiRequest;

public class DeviceBaseModel {
    public DeviceInfoChildModel getMobileDevice() {
        return MobileDevice;
    }

    public void setMobileDevice(DeviceInfoChildModel mobileDevice) {
        MobileDevice = mobileDevice;
    }

    public DeviceInfoChildModel MobileDevice;
}
