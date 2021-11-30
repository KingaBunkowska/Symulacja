package simulation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonParser {
    private static final Gson gson = new Gson();

    public static Map<String, Integer> readSimulationParams(String filename) {

        try {
            Type typeOfT = new TypeToken<Map<String, Integer>>(){}.getType();
            String jsonString = Files.lines(Paths.get(filename))
                    .collect(Collectors.joining("\n"));
            System.out.println(jsonString);
            return gson.fromJson(jsonString,typeOfT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    /*public static Map<String, Integer> readSimulationParams(String filename) {
        Type type = new TypeToken<Map<String, Integer>>() {
        }.getType();
        try {

            String jsonString = Files.readString( Paths.get(filename));
            return gson.fromJson(jsonString, type);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }*/

    public static void dumpStatisticsToJsonFile(String filename, SimulationStatistics statistics) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(statistics, writer);
            writer.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}