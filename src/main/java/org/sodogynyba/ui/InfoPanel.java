package org.sodogynyba.ui;

import org.sodogynyba.game.Game;
import org.sodogynyba.utils.GameConfig;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private static final int PANEL_HEIGHT = 100;
    private static final int FLOW_HGAP = 20;
    private static final int FLOW_VGAP = 20;

    private final Game game;
    private final JLabel gardenHealthLabel;
    private final JLabel budgetLabel;
    private final JLabel wavesLabel;

    public final JButton startWavesButton;
    public final JButton addTowerButton;

    public InfoPanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(GameConfig.BOARD_WIDTH, PANEL_HEIGHT));
        setBackground(Color.LIGHT_GRAY);

        gardenHealthLabel = new JLabel();
        budgetLabel = new JLabel();
        wavesLabel = new JLabel();

        startWavesButton = new JButton("Start Wave");
        addTowerButton = new JButton("Add Tower");

        setLayout(new FlowLayout(FlowLayout.CENTER, FLOW_HGAP, FLOW_VGAP));
        add(gardenHealthLabel);
        add(budgetLabel);
        add(wavesLabel);
        add(startWavesButton);
        add(addTowerButton);

        updateLabels();
    }

    public void updateLabels() {
        gardenHealthLabel.setText("Garden Health: " + game.getGarden().getHealth());
        budgetLabel.setText("Budget: " + game.getBudget());

        int displayWave = game.getCurrentWave();
        if (displayWave > game.getTotalWaves()) {
            displayWave = game.getTotalWaves();
        }
        wavesLabel.setText("Waves: " + displayWave);
    }
}
