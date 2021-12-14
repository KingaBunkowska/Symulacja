package simulation;

/*public record SimulationStatistics (
        int dayNumber,
        double meanLifeLength,
        double meanNumberOfChildren,
        double meanEnergy,
        int noOfAnimals,
        int noOfPlants
        )*/

public class SimulationStatistics {
    private final int dayNumber;
    private final double meanLifeLength;
    private final double meanNumberOfChildren;
    private final double meanEnergy;
    private final int noOfAnimals;
    private final int noOfPlants;



    public SimulationStatistics(
            int dayNumber,
            double meanLifeLength,
            double meanNumberOfChildren,
            double meanEnergy,
            int noOfAnimals,
            int noOfPlants


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
                "\nMean Life Length: " + formatNumber(meanLifeLength) +
                "\nMean Children Number: " + formatNumber(meanNumberOfChildren) +
                "\nMean Energy: " + formatNumber(meanEnergy) + "\n";
    }

    private String formatNumber(double number) {
        return String.format("%.2f", number);
    }
}