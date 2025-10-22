package org.sodogynyba;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter

public class GamePanel extends JPanel {

    private JButton startWaveButton;
    private JLabel moneyLabel;
    private JLabel healthLabel;
    private JLabel waveLabel;

    public GamePanel() {
        startWaveButton = new JButton("Start Wave");
        healthLabel = new JLabel("Health: 0");
        waveLabel = new JLabel("Wave: 0");
        moneyLabel = new JLabel("Money: 0");

        add(startWaveButton);

        add(healthLabel);

        add(waveLabel);

        add(moneyLabel);

    }
}
