package com.unrealdinnerbone.devicestalker;

import com.unrealdinnerbone.unreallib.TaskScheduler;
import com.unrealdinnerbone.unreallib.discord.DiscordWebhook;
import com.unrealdinnerbone.unreallib.discord.EmbedObject;
import com.unrealdinnerbone.unreallib.json.JsonUtil;
import com.unrealdinnerbone.unreallib.web.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Stalker
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Stalker.class);

    private static final List<DeckType> types = new ArrayList<>();


    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    private static final String URL = System.getenv().getOrDefault("WEBHOOK_URL", "");

    static {
//        types.add(new DeckType("Modmuss", DeckType.Location.UK, DeckType.Level._512, 1635022040));
        types.add(new DeckType("Gaz", DeckType.Location.UK, DeckType.Level._512, 1644261865));
        types.add(new DeckType("UnReal", DeckType.Location.US, DeckType.Level._512, 1644261972));
        types.add(new DeckType("Jake", DeckType.Location.UK, DeckType.Level._512, 1659379850));
//        types.add(new DeckType("Max", DeckType.Location.EU, DeckType.Level._512, 1639598215));
    }

    public static void main(String[] args) {
        runTask();
        LOGGER.info("Stalker started!");

//        ZonedDateTime monday = getZonedDataTimeFor(DayOfWeek.MONDAY);
//        ZonedDateTime thursday = getZonedDataTimeFor(DayOfWeek.THURSDAY);

//        checkForDecks();
//        LOGGER.info("Next Monday Is: {}", formatter.format(monday));
//        LOGGER.info("Next Thursday Is: {}", formatter.format(thursday));
//        TaskScheduler.scheduleTask(monday.toInstant(), task -> handleTask());
//        TaskScheduler.scheduleTask(thursday.toInstant(), task -> handleTask());
    }

    private static final Map<String, AtomicReference<Double>> values = new HashMap<>();

    private static void handleTask() {
        Instant nextTime = Instant.now().plus(7, ChronoUnit.DAYS);
        LOGGER.info("Scheduling next task at {}", formatter.format(nextTime));
        TaskScheduler.scheduleTask(nextTime, (theTask) -> handleTask());
        runTask();
    }
    private static void runTask() {
        LOGGER.info("Starting Deck Bot!");

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                checkForDecks();
            }
        };
        TaskScheduler.scheduleRepeatingTask(30, TimeUnit.SECONDS, timerTask);
//        scheduleTask(6, TimeUnit.HOURS, task -> {
//            LOGGER.info("Cancelling Watch Task");
//            timerTask.cancel();
//        });
    }

    private static void checkForDecks() {
        List<EmbedObject> embedObjects = new ArrayList<>();
        for (DeckType type : types) {
            try {
                HttpResponse<String> response = HttpUtils.get(type.getAPIUrl());
                DeckInfo deckInfo = JsonUtil.DEFAULT.parse(DeckInfo.class, response.body());
                if (!values.containsKey(type.name)) {
                    values.put(type.name, new AtomicReference<>(0.0));
                }
                if (values.get(type.name).get() != deckInfo.personalInfo().elapsedTimePercentage()) {
                    LOGGER.info("New value for {}: {}", type.name, deckInfo.personalInfo().elapsedTimePercentage());
                    embedObjects.add(EmbedObject.builder()
                            .author(new EmbedObject.Author(type.name, type.getInfoURL(), "https://unreal.codes/kevStonk.png"))
                            .color(Color.CYAN)
                            .field(new EmbedObject.Field("New %", String.valueOf(deckInfo.personalInfo().elapsedTimePercentage()), true))
                            .field(new EmbedObject.Field("Old %", String.valueOf(values.get(type.name).get()), true))
                            .field(new EmbedObject.Field("Jump %", String.valueOf(deckInfo.personalInfo().elapsedTimePercentage() - values.get(type.name).get()), true))
                            .field(EmbedObject.Field.of("Order Time", getTimeStamp(type.timestamp()), true))
                            .field(EmbedObject.Field.of("Last Processed Order", getTimeStamp(deckInfo.personalInfo().latestOrderSeconds()), true))
                            .build());
                    values.get(type.name).set(deckInfo.personalInfo().elapsedTimePercentage());
                } else {
                    LOGGER.debug("No change for {}", type.name);
                }
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Error getting info for {}", type.name, e);
            }
        }

        DiscordWebhook discordWebhook = DiscordWebhook.of(URL)
                .setUsername("Deck Updates")
                .setAvatarUrl("https://pbs.twimg.com/profile_images/1448687317414080515/jKt70qEv_400x400.png");

        for (EmbedObject embedObject : embedObjects) {
            discordWebhook.addEmbed(embedObject);
        }

        try {
            if(!embedObjects.isEmpty()) {
                discordWebhook.execute();
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Error Sending Webhook", e);
        }
    }

    private static String getTimeStampR(long seconds) {
        return "<t:" + seconds +":R:>";
    }
    private static String getTimeStamp(long seconds) {
        return "<t:" + seconds +">";
    }

    private static ZonedDateTime getZonedDataTimeFor(DayOfWeek dayOfWeek) {
        return Instant.now()
                .atZone(ZoneId.of("America/Chicago"))
                .with(TemporalAdjusters.next(dayOfWeek))
                .with(LocalTime.NOON);
    }

    public static TimerTask scheduleTask(int time, TimeUnit timeUnit, TaskScheduler.Task task) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                task.run(this);
            }
        };
        TaskScheduler.getTimer().schedule(timerTask, timeUnit.toMillis(time));
        return timerTask;
    }





    public record DeckType(String name, Location location, Level level, long timestamp) {


        public String getAPIUrl() {
            return "https://getmydeck.ingenhaag.dev/api/v2/regions/" + location.toString() +  "/versions/" +  level.toString() + "/infos/" + timestamp;
        }

        public String getInfoURL() {
            return "https://getmydeck.ingenhaag.dev/s/" + location.toString() +  "/" +  level.toString() + "/" + timestamp;
        }
        public enum Location {
            UK,
            EU,
            US
            ;

            @Override
            public String toString() {
                return name();
            }
        }

        public enum Level {
            _64,
            _256,
            _512;


            @Override
            public String toString() {
                return name().replace("_", "");
            }
        }
    }

}
