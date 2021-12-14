package gui;

import simulation.Animal;
import simulation.IWorldMap;
import simulation.Vector2D;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel{
    private IWorldMap map;

    MapPanel(IWorldMap map){
        this.map=map;
    }


    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        setupLayout(graphics);
        drawPlants(graphics);
        drawAnimals(graphics);
        drawBorders(graphics);
    }

    private void setupLayout(Graphics graphics){
        graphics.setColor(CustomColors.BACKGROUND_COLOR);
        graphics.fillRect(0, 0, getWidth(), getHeight()); //draw rectangle
        graphics.setColor(CustomColors.MAP_BACKGROUND_COLOR); //set map colour
        graphics.fillRect(
                0, 0,
                map.getWidth() * LayoutConstants.MAP_SCALE,
                map.getHeight() * LayoutConstants.MAP_SCALE
        ); //makes map
    }

    private Polygon createPlant() {
        int[] leafX = {
                0,
                LayoutConstants.MAP_SCALE / 4,
                LayoutConstants.MAP_SCALE,
                3 * LayoutConstants.MAP_SCALE / 4
        };
        int[] leafY = {
                LayoutConstants.MAP_SCALE,
                LayoutConstants.MAP_SCALE / 4,
                0,
                3 * LayoutConstants.MAP_SCALE / 4
        };
        return new Polygon(leafX, leafY, leafX.length);
    }

    private void drawPlants(Graphics graphics){
        graphics.setColor(CustomColors.PLANT_COLOR);
        Polygon leaf = createPlant();
        Vector2D prevPosition = new Vector2D(0,0);
        for (Vector2D position: map.getPlantsLocations().keySet()){
            Vector2D move = position.substract(prevPosition);
            leaf.translate(move.x() * LayoutConstants.MAP_SCALE, move.y() * LayoutConstants.MAP_SCALE);
            graphics.fillPolygon(leaf);
            prevPosition = position;
        }
    }

    private void drawAnimals(Graphics graphics){
        map.getAnimalLocations().forEach((positions, animals) -> {
            int maxEnergy = animals.stream().mapToInt(Animal::getEnergy).max().orElse(0);
            Color energyColor = CustomColors.ENERGY_COLOR[Math.min(maxEnergy/10, CustomColors.ENERGY_COLOR.length-1)];
            graphics.setColor(energyColor);
            graphics.fillOval(
                    positions.x() * LayoutConstants.MAP_SCALE,
                    positions.y() * LayoutConstants.MAP_SCALE,
                    LayoutConstants.MAP_SCALE,
                    LayoutConstants.MAP_SCALE
            );
            displayNoOfAnimals(graphics, energyColor, animals.size(), positions);
        });
    }

    private void displayNoOfAnimals(Graphics graphics, Color energyColor, int noOfAnimals, Vector2D position) {
        Color textColor = new Color(
                255 - energyColor.getRed(),
                255 - energyColor.getGreen(),
                255 - energyColor.getBlue()
        );
        String text = Integer.toString(noOfAnimals);
        int textWidth = (int) graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
        int textHeight = graphics.getFontMetrics().getMaxAscent();
        int x = position.x() * LayoutConstants.MAP_SCALE + LayoutConstants.MAP_SCALE / 2;
        int y = position.y() * LayoutConstants.MAP_SCALE + LayoutConstants.MAP_SCALE / 2;
        graphics.setColor(textColor);
        graphics.drawString(text, x - textWidth / 2, y + textHeight / 2);
    }

    private void drawBorders(Graphics graphics){
        graphics.setColor(CustomColors.BORDER_COLOR);
        graphics.drawRect(0,0, map.getWidth() * LayoutConstants.MAP_SCALE, map.getHeight() * LayoutConstants.MAP_SCALE);
    }

    public IWorldMap getMap() {
        return map;
    }
}
