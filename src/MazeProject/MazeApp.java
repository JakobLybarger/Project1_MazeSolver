package MazeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeApp {

    static MazeReader mazeReader;
    static MazeSolver mazeSolver;
    static JFrame frame = new JFrame("Maze Solver");


    public static void main(String[] args) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        
        // text field, load, start
        JPanel panel = new JPanel();
        JLabel label = new JLabel("File Name: ");
        JTextField textField = new JTextField(20);
        JButton load = new JButton("Load");
        JButton start = new JButton("Start");
        panel.add(label);
        panel.add(textField);
        panel.add(load);
        panel.add(start);

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    mazeReader = new MazeReader(textField.getText());
                    System.out.println(mazeReader);

                    temporarything();
                    SwingUtilities.updateComponentTreeUI(frame);
                } catch (Error e) {
                    System.err.println("Invalid file name try again");
                    System.err.println(e.getStackTrace());
                }

            }
        });

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    mazeSolver = new MazeSolver(mazeReader.getMaze());
//                    mazeSolver.GBFS();
                    System.out.println("Length of path: " + mazeSolver.GBFS());
                } catch (Error e) {
                    System.err.println("No file loaded");
                    System.err.println(e.getStackTrace());
                }
            }
        });

        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.setVisible(true);
    }

    static private void temporarything() {
        char[][] charMaze = mazeReader.getMaze();
        int rows = mazeReader.getMaze().length, cols = mazeReader.getMaze()[0].length;
        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new GridLayout(rows, cols));
        JButton[][] maze = new JButton[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new JButton();
                maze[i][j].setPreferredSize(new Dimension(30, 30));
                if (charMaze[i][j] == '.') {
                    maze[i][j].setBackground(Color.white);
                } else if (charMaze[i][j] == '#') {
                    maze[i][j].setBackground(Color.black);
                } else if (charMaze[i][j] == 'o') {
                    maze[i][j].setText("Start");
                    maze[i][j].setBackground(Color.green);
                } else {
                    maze[i][j].setText("Goal");
                    maze[i][j].setBackground(Color.red);
                }

                mazePanel.add(maze[i][j]);
            }
        }

        frame.getContentPane().add(BorderLayout.CENTER, mazePanel);
    }
}
