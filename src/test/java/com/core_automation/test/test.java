package com.core_automation.test;

import com.core_automation.config.ServerManager;
import com.core_automation.utils.ExtentManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class test {

    @Test
    public void main() throws InterruptedException {
        new ServerManager().startServer();

        Thread.sleep(5000);

        if (new ServerManager().getServer() != null) {
            new ServerManager().getServer().stop();
        }
    }
}
