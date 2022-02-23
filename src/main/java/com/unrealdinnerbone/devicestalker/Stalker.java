package com.unrealdinnerbone.devicestalker;

import com.unrealdinnerbone.unreallib.TaskScheduler;
import com.unrealdinnerbone.unreallib.discord.DiscordWebhook;
import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Stalker
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Stalker.class);
    private static final List<String> BLACKLIST = new ArrayList<>();
    private static final String WEBHOOK_URL = System.getenv("WEBHOOK_URL");

    static {
        BLACKLIST.add("rtl_433/LaCrosse-TX141THBv2/169");
        BLACKLIST.add("rtl_433/Solight-TE44/34");
    }

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("Stalker started!");

        TaskScheduler.scheduleRepeatingTask( 30, TimeUnit.MINUTES, () -> {
            try {
                String publisherId = UUID.randomUUID().toString();
                IMqttClient client = new MqttClient("tcp://10.0.0.167:1885",publisherId);
                MqttConnectOptions options = new MqttConnectOptions();
                options.setAutomaticReconnect(true);
                options.setCleanSession(true);
                options.setConnectionTimeout(10);
                client.connect(options);
                client.subscribe("rtl_433/#", (topic, msg) -> {
                    String payload = new String(msg.getPayload());
                    if(!BLACKLIST.contains(topic)) {
                        DiscordWebhook.of(WEBHOOK_URL)
                                .setUsername("Stalker")
                                .setContent(StringEscapeUtils.escapeJson(payload))
                                .execute();
                    }

                    LOGGER.info("Received {} message: {}", topic, payload);
                });


                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            client.disconnect();
                        }catch(MqttException e) {
                            LOGGER.error("Error while disconnecting", e);
                        }
                    }
                }, TimeUnit.MINUTES.toMillis(30));

            }catch(Exception e) {
                LOGGER.error("Error while connecting to MQTT", e);
            }


        });

//        CountDownLatch receivedSignal = new CountDownLatch(10);
//        receivedSignal.await(1, TimeUnit.MINUTES);
    }
}
