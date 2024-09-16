package com.unrealdinnerbone.devicestalker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages {

    public static Map<String, String> MESSAGES = new HashMap<>();

    static {
        MESSAGES.put("weathy_temp_f",  """
        {
            "name": "weathy_temp_f",
            "unique_id": "weathy_temp_f",
            "state_topic": "rtl_433/Fineoffset-WS90/19606",
            "unit_of_measurement": "F",
            "force_update": true,
            "value_template": "{{ value_json.temperature_F }}",
            "device": {
              "name": "Weather Station",
              "identifiers": "Acurite-Fineoffset-WS90-19606"
            }
        }""");

        MESSAGES.put("weathy_humidity",  """
        {
            "name": "weathy_humidity",
            "unique_id": "weathy_humidity",
            "state_topic": "rtl_433/Fineoffset-WS90/19606",
            "unit_of_measurement": "%",
            "force_update": true,
            "value_template": "{{ value_json.humidity }}",
            "device": {
              "name": "Weather Station",
              "identifiers": "Acurite-Fineoffset-WS90-19606"
            }
        }""");

        MESSAGES.put("weathy_wind_dir_deg",  """
        {
            "name": "weathy_wind_dir_deg",
            "unique_id": "weathy_wind_dir_deg",
            "state_topic": "rtl_433/Fineoffset-WS90/19606",
            "unit_of_measurement": "°",
            "force_update": true,
            "value_template": "{{ value_json.wind_dir_deg }}",
            "device": {
              "name": "Weather Station",
              "identifiers": "Acurite-Fineoffset-WS90-19606"
            }
        }""");

        MESSAGES.put("weathy_wind_avg_m_s",  """
        {
            "name": "weathy_wind_avg_m_s",
            "unique_id": "weathy_wind_avg_m_s",
            "state_topic": "rtl_433/Fineoffset-WS90/19606",
            "unit_of_measurement": "m/s",
            "force_update": true,
            "value_template": "{{ value_json.wind_avg_m_s }}",
            "device": {
              "name": "Weather Station",
              "identifiers": "Acurite-Fineoffset-WS90-19606"
            }
        }""");



        MESSAGES.put("weathy_light_lux",  """
        {
            "name": "weathy_light_lux",
            "unique_id": "weathy_light_lux",
            "state_topic": "rtl_433/Fineoffset-WS90/19606",
            "unit_of_measurement": "lx",
            "force_update": true,
            "value_template": "{{ value_json.light_lux }}",
            "device": {
              "name": "Weather Station",
              "identifiers": "Acurite-Fineoffset-WS90-19606"
            }
        }""");

        MESSAGES.put("weathy_rain_in",  """
        {
            "name": "weathy_rain_in",
            "unique_id": "weathy_rain_in",
            "state_topic": "rtl_433/Fineoffset-WS90/19606",
            "unit_of_measurement": "lx",
            "force_update": true,
            "value_template": "{{ value_json.rain_in }}",
            "device": {
              "name": "Weather Station",
              "identifiers": "Acurite-Fineoffset-WS90-19606"
            }
        }""");

        MESSAGES.put("weathy_supercap_V",  """
        {
            "name": "weathy_supercap_V",
            "unique_id": "weathy_supercap_V",
            "state_topic": "rtl_433/Fineoffset-WS90/19606",
            "unit_of_measurement": "V",
            "force_update": true,
            "value_template": "{{ value_json.supercap_V }}",
            "device": {
              "name": "Weather Station",
              "identifiers": "Acurite-Fineoffset-WS90-19606"
            }
        }""");

        MESSAGES.put("weathy_uvi",  """
        {
            "name": "weathy_uvi",
            "unique_id": "weathy_uvi",
            "state_topic": "rtl_433/Fineoffset-WS90/19606",
            "force_update": true,
            "value_template": "{{ value_json.uvi }}",
            "device": {
              "name": "Weather Station",
              "identifiers": "Acurite-Fineoffset-WS90-19606"
            }
        }""");

        MESSAGES.put("water_meter_consumption",  """
        {
            "name": "water_meter_consumption",
            "unique_id": "water_meter_consumption",
            "state_topic": "rtl_433/Badger-ORION/937670",
            "state_class": "total_increasing",
            "device_class": "water",
            "unit_of_measurement": "gal",
            "force_update": true,
            "value_template": "{{ value_json.volume_gal }}",
            "device": {
              "name": "Water Meter",
              "identifiers": "Badger-ORION-937670"
            }
        }""");

        MESSAGES.put("porch_temperature",  """
        {
            "name": "porch_temperature",
            "unique_id": "porch_temperature",
            "state_topic": "rtl_433/LaCrosse-TX141THBv2/122",
            "unit_of_measurement": "F",
            "force_update": true,
            "value_template": "{{ value_json.temperature_F }}",
            "device": {
              "name": "Porch Sensor",
              "identifiers": "LaCrosse-TX141THBv2-122"
            }
        }""");

        MESSAGES.put("porch_humidity",  """
        {
            "name": "porch_humidity",
            "unique_id": "porch_humidity",
            "state_topic": "rtl_433/LaCrosse-TX141THBv2/122",
            "unit_of_measurement": "%",
            "force_update": true,
            "value_template": "{{ value_json.humidity }}",
            "device": {
              "name": "Porch Sensor",
              "identifiers": "LaCrosse-TX141THBv2-122"
            }
        }""");

        MESSAGES.put("garage_freezer",  """
        {
            "name": "Garage Freezer",
            "unique_id": "garage_freezer",
            "state_topic": "rtl_433/Acurite-515/209",
            "unit_of_measurement": "F",
            "force_update": true,
            "value_template": "{{ value_json.temperature_F }}",
            "device": {
              "name": "Garage Freezer",
              "identifiers": "Acurite-515-209"
            }
        }""");

        MESSAGES.put("garage_fridge",  """
        {
            "name": "Garage Fridge",
            "unique_id": "garage_fridge",
            "state_topic": "rtl_433/Acurite-515/76",
            "unit_of_measurement": "F",
            "force_update": true,
            "value_template": "{{ value_json.temperature_F }}",
            "device": {
              "name": "Garage Fridge",
              "identifiers": "Acurite-515-76"
            }
        }""");

        MESSAGES.put("backyard_temperature",  """
        {
            "name": "backyard_temperature",
            "unique_id": "backyard_temperature",
            "state_topic": "rtl_433/Acurite-6045M/77",
            "unit_of_measurement": "F",
            "force_update": true,
            "value_template": "{{ value_json.temperature_F }}",
            "device": {
              "name": "Backyard Sensor",
              "identifiers": "Acurite-6045M-77"
            }
        }""");

        MESSAGES.put("backyard_humidity",  """
        {
            "name": "backyard_humidity",
            "unique_id": "backyard_humidity",
            "state_topic": "rtl_433/Acurite-6045M/77",
            "unit_of_measurement": "%",
            "force_update": true,
            "value_template": "{{ value_json.humidity }}",
            "device": {
              "name": "Backyard Sensor",
              "identifiers": "Acurite-6045M-77"
            }
        }""");

        MESSAGES.put("backyard_strike_count",  """
        {
            "name": "backyard_strike_count",
            "unique_id": "backyard_strike_count",
            "state_class": "measurement",
            "state_topic": "rtl_433/Acurite-6045M/77",
            "force_update": true,
            "value_template": "{{ value_json.strike_count }}",
            "device": {
              "name": "Backyard Sensor",
              "identifiers": "Acurite-6045M-77"
            }
        }""");

        MESSAGES.put("backyard_storm_dist",  """
        {
            "name": "backyard_storm_dist",
            "unique_id": "backyard_storm_dist",
            "state_class": "measurement",
            "state_topic": "rtl_433/Acurite-6045M/77",
            "force_update": true,
            "value_template": "{% if value_json.storm_dist != 31 %} {{ value_json.storm_dist}} {% else %} -1 {% endif %}",
            "device": {
              "name": "Backyard Sensor",
              "identifiers": "Acurite-6045M-77"
            }
        }""");

    }

}
