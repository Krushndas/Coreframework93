package com.CME.test;

import com.CME.config.ServerManager;
import org.testng.annotations.Test;

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
