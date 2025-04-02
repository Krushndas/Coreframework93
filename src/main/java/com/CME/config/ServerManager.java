package com.CME.config;

import com.CME.base.BaseTest;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import static com.CME.utils.ExtentManager.logInfo;

public class ServerManager extends BaseTest {
    private static ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();

    public AppiumDriverLocalService getServer(){
        return server.get();
    }

    public void startServer(){
        logInfo("starting appium server");
        AppiumDriverLocalService server = WindowsGetAppiumService();
        server.start();
        if(!server.isRunning()){
            logInfo("Appium server not started. ABORT!!!");
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium server not started. ABORT!!!");
        }
        ServerManager.server.set(server);
        logInfo("Appium server started");
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    public AppiumDriverLocalService WindowsGetAppiumService() {

        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE));
    }
}
