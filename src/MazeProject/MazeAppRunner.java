package MazeProject;

import javax.swing.*;
import java.io.IOException;

public class MazeAppRunner extends JFrame {
    public MazeAppRunner() {
        setTitle("Maze app");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MazeApp());
    }
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MazeAppRunner mazeAppRunner = new MazeAppRunner();
                mazeAppRunner.setVisible(true);
            }
        });
    }
}
