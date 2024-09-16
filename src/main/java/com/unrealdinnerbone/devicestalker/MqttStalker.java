package com.unrealdinnerbone.devicestalker;

import com.unrealdinnerbone.unreallib.discord.DiscordWebhook;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MqttStalker implements IStalker{

    private static final String WEBHOOK_URL = System.getenv("WEBHOOK_URL");
    private static final List<String> BLACKLIST = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttStalker.class);

    @Override
    public void run() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("mqtt");
            options.setPassword("mqtt!".toCharArray());
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
//            blacklist.add("rtl_433/Badger-ORION/937670");
//            blacklist.add("rtl_433/LaCrosse-TX141THBv2/234");
//            blacklist.add("rtl_433/LaCrosse-TX141THBv2/122");
            client.subscribe("homeassistant/status", (topic, msg) -> {
                for (Map.Entry<String, String> stringStringEntry : Messages.MESSAGES.entrySet()) {
                    String key = "the_" + stringStringEntry.getKey();
                    String value = stringStringEntry.getValue();
                    client.publish("homeassistant/sensor/" + key + "/config", value.replace( "\"unique_id\": \"", " \"unique_id\": \"the_").getBytes(), 0, false);
                }
            });

            for (Map.Entry<String, String> stringStringEntry : Messages.MESSAGES.entrySet()) {
                String key = "the_" + stringStringEntry.getKey();
                String value = stringStringEntry.getValue();
                client.publish("homeassistant/sensor/" + key + "/config", value.replace( "\"unique_id\": \"", " \"unique_id\": \"the_").getBytes(), 0, false);
            }
            //rtl_433/+/events


        }catch(Exception e) {
            LOGGER.error("Error while connecting to MQTT", e);
        }
    }
}
