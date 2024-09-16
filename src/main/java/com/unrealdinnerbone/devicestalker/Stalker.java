package com.unrealdinnerbone.devicestalker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Stalker
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Stalker.class);

    private static final String URL = System.getenv().getOrDefault("WEBHOOK_URL", "");


    public static void main(String[] args) throws IOException {
        LOGGER.info("Stalker started!");
//        Path of = Path.of("test.png");
//        PathHelper.downloadFile("http://10.0.0.167:8078/?kiosk&screenshot", of);
        new MqttStalker().run();
    }


}
