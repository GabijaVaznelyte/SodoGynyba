package org.sodogynyba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameLauncher {
    private static final int BLOCK_SIZE = 16;
    private static final int GRID_ROWS = 20;
    private static final int GRID_COLS = 20;
    private static final int BOARD_WIDTH = GRID_COLS * BLOCK_SIZE;
    private static final int BOARD_HEIGHT = GRID_ROWS * BLOCK_SIZE;

    private static JFrame window;
    private static JPanel gameBoardPanel;
    private static JPanel infoPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        window = new JFrame("Sodo Gynyba");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(800, 600);
        window.setLayout(new BorderLayout());

        // Info panel (budget, wave info, etc.) at top
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(800, 100));
        infoPanel.setBackground(Color.LIGHT_GRAY);

        // Labels for budget and wave
        infoPanel.add(new JLabel("Budget: 100")); // placeholder
        infoPanel.add(new JLabel("Wave: 1"));

        // Buttons
        JButton buyTowerButton = new JButton("Buy tower");
        buyTowerButton.addActionListener(e -> {
            // Placeholder for tower purchase logic
            System.out.println("Buy Tower button clicked!");
        });

        JButton startWaveButton = new JButton("Start Wave");
        startWaveButton.addActionListener(e -> {
            // Placeholder for starting next wave
            System.out.println("Start Wave button clicked!");
        });

        infoPanel.add(buyTowerButton);
        infoPanel.add(startWaveButton);

        // Game board panel (center) where towers and enemies will appear
        gameBoardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.DARK_GRAY);
                for(int i = 0; i <= GRID_COLS; i++){
                    g.drawLine(i * BLOCK_SIZE, 0 , i * BLOCK_SIZE, BOARD_HEIGHT);
                }
                for(int i = 0; i <= GRID_ROWS; i++){
                    g.drawLine(0, i * BLOCK_SIZE, BOARD_WIDTH, i * BLOCK_SIZE);
                }
            }
        };
        gameBoardPanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        gameBoardPanel.setBackground(Color.GREEN);
        gameBoardPanel.setLayout(null); // free placement within grid

        // Mouse listener for placing towers
        gameBoardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / BLOCK_SIZE;
                int row = e.getY() / BLOCK_SIZE;
                int x = col * BLOCK_SIZE;
                int y = row * BLOCK_SIZE;
                System.out.println("Clicked at: row=" + row + ", col=" + col);
                addTower(x, y);
            }
        });

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerWrapper.setBackground(Color.GRAY);
        centerWrapper.add(gameBoardPanel);

        // Add panels to window
        window.add(infoPanel, BorderLayout.NORTH);
        window.add(centerWrapper, BorderLayout.CENTER);

        window.setVisible(true);
    }
    public static void addTower(int x, int y) {
        JButton towerButton = new JButton("T");
        towerButton.setBounds(x, y, BLOCK_SIZE, BLOCK_SIZE);
        gameBoardPanel.add(towerButton);
        gameBoardPanel.repaint();
    }
}
