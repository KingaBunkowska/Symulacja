package gui;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton{
    private static final int DEFAULT_WIDTH = 2;
    private static final int DEFAULT_HEIGHT = 1;

    public CustomButton(
            String text,
            JPanel panel,
            GridBagConstraints constraints,
            int row,
            int col,
            boolean enabled
    ) {
        super(text);
        constraints.gridy = row;
        constraints.gridx = col;
        constraints.gridwidth = DEFAULT_WIDTH;
        constraints.gridheight = DEFAULT_HEIGHT;
        setEnabled(enabled);
        panel.add(this, constraints);
    }
}
