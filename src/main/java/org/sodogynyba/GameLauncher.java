package org.sodogynyba;

import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.enemies.FastEnemy;
import org.sodogynyba.entities.enemies.TankEnemy;
import org.sodogynyba.entities.projectiles.Projectile;
import org.sodogynyba.entities.towers.RegularTower;
import org.sodogynyba.entities.towers.SlowTower;
import org.sodogynyba.entities.towers.Tower;
import org.sodogynyba.game.Game;
import org.sodogynyba.path.Path;

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

    private static Timer gameTimer;

    private static JFrame menuFrame;
    private static JFrame gameFrame;
    private static JPanel gameBoardPanel;
    private static JPanel infoPanel;

    private static JLabel gardenHealthLabel;
    private static JLabel budgetLabel;
    private static JLabel wavesLabel;

    private static JButton startWavesButton;
    private static JButton addTowerButton;
    private static String selectedTowerType = null;

    private static Game game;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameLauncher::showMenu);
    }

    private static void showMenu() {
        menuFrame = new JFrame("Sodo Gynyba");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);
        menuFrame.setSize(800, 600);
        menuFrame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Sodo Gynyba", SwingConstants.CENTER );
        title.setFont(new Font("Arial", Font.BOLD, 40));
        menuFrame.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setMaximumSize(new Dimension(200, 50));
        startButton.addActionListener(e -> {
            String[] pathOptions = {"1 Path" , "2 Paths"};
            String pathChoice = (String) JOptionPane.showInputDialog(
                    menuFrame,
                    "Enter number of paths:",
                    "Path Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    pathOptions,
                    pathOptions[0]
            );
            if(pathChoice == null) return;
            int pathType = pathChoice.equals("1 Path") ? 1 : 2;

            int numWaves = 0;
            while(numWaves <=0 || numWaves > 10){
                String input = JOptionPane.showInputDialog(
                        menuFrame,
                        "Enter number of Waves (1-10):",
                        "5"
                );
                if(input == null) return;
                try{
                    numWaves = Integer.parseInt(input);
                } catch(NumberFormatException ex){
                    numWaves = 0;
                }
            }
            menuFrame.dispose();
            launchGame(pathType, numWaves);
        });

        JButton quitButton = new JButton("Quit Game");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setPreferredSize(new Dimension(200, 50));
        quitButton.setMaximumSize(new Dimension(200, 50));
        quitButton.addActionListener(e -> {System.exit(0);});

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(quitButton);
        buttonPanel.add(Box.createVerticalGlue());

        menuFrame.add(buttonPanel, BorderLayout.CENTER);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }
    private static void launchGame(int pathType, int numWaves) {
        game = new Game(pathType, numWaves);
        game.startGame();

        gameFrame = new JFrame("Sodo Gynyba");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setSize(800, 600);
        gameFrame.setLayout(new BorderLayout());

        setupInfoPanel();
        setupGameBoardPanel();

        gameFrame.add(infoPanel, BorderLayout.NORTH);
        gameFrame.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)) {{
            setBackground(Color.GRAY);
            add(gameBoardPanel);
        }}, BorderLayout.CENTER);

        gameFrame.setVisible(true);

        gameTimer = new Timer(200, e -> updateGame());
        gameTimer.start();
    }
    public static void setupInfoPanel(){
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(800, 100));
        infoPanel.setBackground(Color.LIGHT_GRAY);

        gardenHealthLabel = new JLabel("Garden Health: " + game.getGarden().getHealth());
        budgetLabel = new JLabel("Budget: " + game.getBudget());
        wavesLabel = new JLabel("Waves: " + game.getCurrentWave());

        startWavesButton = new JButton("Start Wave");
        startWavesButton.addActionListener(e -> {
            game.startNextWave();
            startWavesButton.setEnabled(false);
            gameBoardPanel.repaint();
        });
        addTowerButton = new JButton("Add Tower");
        addTowerButton.addActionListener(e -> {
           String[] options = {"Regular Tower", "Slowing Tower"};
           String choice = (String) JOptionPane.showInputDialog(
                   gameFrame,
                   "Select tower type:",
                   "Tower Selection",
                   JOptionPane.PLAIN_MESSAGE,
                   null,
                   options,
                   options[0]
           );
           if(choice != null){
               selectedTowerType = choice;
           }
        });

        infoPanel.add(gardenHealthLabel);
        infoPanel.add(budgetLabel);
        infoPanel.add(wavesLabel);
        infoPanel.add(startWavesButton);
        infoPanel.add(addTowerButton);
    }
    private static void setupGameBoardPanel(){
        gameBoardPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                //draw background
                g.setColor(new Color(80, 139, 34));
                g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

                //draw path
                g.setColor(Color.LIGHT_GRAY);
                for(Path path : game.getPaths()) {
                    for (Point p : path.getWaypoints()) {
                        g.fillRect(p.x, p.y, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }

                //draw garden
                g.setColor(new Color(0, 128, 0));
                g.fillRect(0, (GRID_ROWS - 1) * BLOCK_SIZE, BOARD_WIDTH, BLOCK_SIZE);
                g.setColor(Color.WHITE);
                g.drawString("GARDEN", 5, (GRID_ROWS * BLOCK_SIZE ) - 4);

                //draw grid lines
                g.setColor(Color.DARK_GRAY);
                for(int i = 0; i <= GRID_COLS; i++) g.drawLine(i * BLOCK_SIZE, 0, i * BLOCK_SIZE, BOARD_HEIGHT);
                for(int i = 0; i <= GRID_ROWS; i++) g.drawLine(0, i * BLOCK_SIZE, BOARD_WIDTH, i * BLOCK_SIZE);

                //draw towers
                for(Tower tower : game.getTowers()){
                    Point p = tower.getPosition();
                    if(tower instanceof SlowTower){
                        g.setColor(new Color(102, 178, 255));
                    }
                    else{
                        g.setColor(Color.BLUE);
                    }
                    g.fillRect(p.x, p.y, BLOCK_SIZE, BLOCK_SIZE);
                }

                //draw enemies
                for(Enemy enemy : game.getActiveEnemies()){
                    if(!enemy.isAlive()) continue;
                    if(enemy instanceof FastEnemy){
                        g.setColor(new Color(255, 102, 102));
                    }
                    else if(enemy instanceof TankEnemy){
                        g.setColor(new Color(128, 0, 0));
                    }
                    else{
                        g.setColor(Color.RED);
                    }
                    Point p = enemy.getPositionCopy();
                    g.fillRect(p.x, p.y, BLOCK_SIZE, BLOCK_SIZE);
                }

                //draw projectiles
                g.setColor(Color.ORANGE);
                for(Projectile projectile : game.getProjectiles()){
                    if(projectile.isActive()){
                        Point p = projectile.getPositionCopy();
                        g.fillOval(p.x, p.y, 6, 6);
                    }
                }
            }
        };
        gameBoardPanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        gameBoardPanel.setBackground(Color.GREEN);
        gameBoardPanel.setLayout(null);

        gameBoardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / BLOCK_SIZE;
                int row = e.getY() / BLOCK_SIZE;
                Point clickPoint = new Point(col * BLOCK_SIZE, row * BLOCK_SIZE);
                Tower tower;
                if(selectedTowerType.equals("Regular Tower")){
                    tower = new RegularTower(clickPoint);
                }
                else{
                    tower = new SlowTower(clickPoint);
                }
                if(game.placeTower(tower)){
                    selectedTowerType = null;
                }
                gameBoardPanel.repaint();
            }
        });
    }
    private static void updateGame(){
        game.update();
        gameBoardPanel.repaint();

        gardenHealthLabel.setText("Garden Health: " + game.getGarden().getHealth());
        budgetLabel.setText("Budget: " + game.getBudget());
        wavesLabel.setText("Waves: " + game.getCurrentWave());

        startWavesButton.setEnabled(!game.isWaveActive());

        if(game.isGameOver()){
            JOptionPane.showMessageDialog(gameFrame, "Game Over");
            gameTimer.stop();
        }else if (game.isVictory()){
            JOptionPane.showMessageDialog(gameFrame, "Victory!");
            gameTimer.stop();
        }
    }
}
