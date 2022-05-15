package ee.kull.model;

import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

@Log4j2
public class Weather {

    public static String getWeather(String message, Model model) throws IOException {

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=39ae6171f4eca53d66f3235a5b18c21d");
        log.info("User send request");
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while ((in.hasNext())) {
            result += in.nextLine();
        }
        getJSON(result, model);

        return "City: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "℃" + "\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "Main: " + model.getMain();
    }


    private static void getJSON(String result, Model model) {

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));
        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getInt("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray array = object.getJSONArray("weather");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            model.setMain((String) obj.get("main"));
            log.info("Got JSON object");
        }
    }

}