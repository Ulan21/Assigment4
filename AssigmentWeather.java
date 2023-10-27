import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AssigmentWeather {
    private static String fetchWeather() throws IOException {
        Scanner sc = new Scanner(System.in);
        String city = sc.nextLine();
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" +city +"&appid=d5559abc76adbb16c090019fcb628bc5";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        int connect = connection.getResponseCode();
        if (connect == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Error fetching weather data. Response code: " + connect);
        }
    }

    public static void printCurrentWeather(){
        try {
            JSONObject pogoda = new JSONObject(fetchWeather());
            System.out.println("температура: " + (pogoda.getJSONObject("main").getDouble("temp") - 273.15));
            System.out.println("ощущение: " + (pogoda.getJSONObject("main").getInt("feels_like") - 273.15));
            System.out.println("минимальный показатель температуры воздуха: " + (pogoda.getJSONObject("main").getInt("temp_min")-273.15));
            System.out.println("скорость ветра: " + pogoda.getJSONObject("wind").getInt("speed") + " км/ч");
        } catch (IOException e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        printCurrentWeather();
    }
}