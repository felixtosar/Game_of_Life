package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife extends JFrame {
    public JLabel generationLabel;
    public JLabel aliveLabel;
    private final String GENERATION_TEXT = "Generation #";
    private final String ALIVE_TEXT = "Alive: ";
    private final JLabel[] labels;
    private JButton resetButton;
    private JToggleButton toggleButton;
    public static final int SIZE_OF_UNIVERSE = 20;

    public GameOfLife() {

        Universe currentGeneration = new Universe(SIZE_OF_UNIVERSE); // model
        Controller controller = new Controller(currentGeneration, this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setTitle("Game of Life");
        setLocationRelativeTo(null);

        generationLabel = new JLabel(GENERATION_TEXT + 0);
        generationLabel.setName("GenerationLabel");
        aliveLabel = new JLabel(ALIVE_TEXT + 0);
        aliveLabel.setName("AliveLabel");
        JPanel matrix = new JPanel();
        matrix.setBackground(Color.BLACK);

        resetButton = new JButton("reset");
        resetButton.setName("ResetButton");
        resetButton.setActionCommand("RESET_BUTTON");
        toggleButton = new JToggleButton("pause", true);
        toggleButton.setName("PlayToggleButton");
        toggleButton.setActionCommand("PAUSE_RESUME");
        resetButton.addActionListener(controller);
        toggleButton.addItemListener(controller);

        labels = new JLabel[400];
        for(int i = 0; i < 400; i++) {
            labels[i] = new JLabel();
            labels[i].setOpaque(true);
            matrix.add(labels[i]);
        }
        matrix.setLayout(new GridLayout(20, 20, 1,1));

        add(generationLabel);
        add(aliveLabel);
        add(resetButton);
        add(toggleButton);
        add(matrix);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setVisible(true);
        controller.run();
    }

    public void setGenerationLabel(int generation) {
        generationLabel.setText(GENERATION_TEXT + generation);
    }

    public void setAliveLabel(int alive) {
        aliveLabel.setText(ALIVE_TEXT + alive);
    }

    public void updateStatus(boolean[] status) {
        for (int i = 0; i < status.length; i++) {
            if(status[i]) {
                labels[i].setBackground(Color.BLACK);
            } else {
                labels[i].setBackground(Color.GRAY);
            }
        }
    }
}