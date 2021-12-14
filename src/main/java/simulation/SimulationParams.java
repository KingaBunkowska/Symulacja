package simulation;

import java.util.Map;

public class SimulationParams {
    private static final String CONFIG_PATH = "config.json";
    private static final Map<String ,Integer> paramsMap;

    static {
        paramsMap = JsonParser.readSimulationParams(CONFIG_PATH);
    }

    public static Integer getField (String fieldName){
        return paramsMap.computeIfAbsent(fieldName, k -> {
            throw new IllegalArgumentException("There is no field: "+k);
        });
    }

    public static class SimulationStatistics {
        private final int noOfAnimals;
        private final int noOfPlants;
        private final double meanLifeLength;
        private final double meanNumberOfChildren;
        private final double meanEnergy;
        private final int dayNumber;

        public SimulationStatistics(
                int noOfAnimals,
                int noOfPlants,
                double meanLifeLength,
                double meanNumberOfChildren,
                double meanEnergy,
                int dayNumber
        ) {
            this.noOfAnimals = noOfAnimals;
            this.noOfPlants = noOfPlants;
            this.meanLifeLength = meanLifeLength;
            this.meanNumberOfChildren = meanNumberOfChildren;
            this.meanEnergy = meanEnergy;
            this.dayNumber = dayNumber;
        }

        @Override
        public String toString() {
            return "Day: " + dayNumber +
                    "\nNumber of Animals: " + noOfAnimals +
                    "\nNumber of Plants: " + noOfPlants +
                    "\nMean Life Length: " + meanLifeLength +
                    "\nMean Children Number: " + meanNumberOfChildren +
                    "\nEnergy: " + meanEnergy + "\n";
        }
    }

    public static Map<String, Integer> getParamsMap() {
        return paramsMap;
    }

    public static void setField(String fieldName, int value){
        paramsMap.put(fieldName, value);
    }
}
