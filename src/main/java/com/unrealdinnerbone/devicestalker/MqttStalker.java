package com.unrealdinnerbone.devicestalker;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MqttStalker implements IStalker{

    private static final String WEBHOOK_URL = System.getenv("WEBHOOK_URL");
    private static final List<String> BLACKLIST = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttStalker.class);

    @Override
    public void run() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            IMqttClient client = new MqttClient("tcp://10.0.0.115:1885","StalkerClient-Dev");
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(0);
            client.connect(options);
//                    TaskScheduler.scheduleRepeatingTask(1, TimeUnit.SECONDS, task -> {
//                        try {
//                            client.publish("rtl_433/Badger-ORION/69", "{\"time\":\"2023-04-12 19:29:46\",\"model\":\"Badger-ORION\",\"id\":{AMOUNT},\"flags_1\":0,\"volume_gal\":{AMOUNT},\"flags_2\":5,\"mic\":\"CRC\"}"
//                                    .replace("{AMOUNT}", String.valueOf(atomicBoolean.getAndIncrement()))
//                                    .getBytes(), 0, false);
//                        } catch (MqttException e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
            List<String> blacklist = new ArrayList<>();
//            blacklist.add("rtl_433/Acurite-6045M/77");
            blacklist.add("rtl_433/Badger-ORION/937670");
//            blacklist.add("rtl_433/LaCrosse-TX141THBv2/234");
//            blacklist.add("rtl_433/LaCrosse-TX141THBv2/122");
            client.subscribe("rtl_433/#", (topic, msg) -> {
                String payload = new String(msg.getPayload());
                LOGGER.info("Received {} message: {}", topic, payload);
            });


        }catch(Exception e) {
            LOGGER.error("Error while connecting to MQTT", e);
        }
    }
}
