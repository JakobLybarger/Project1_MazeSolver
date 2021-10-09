package MazeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.math.*;
//
//public class Tutorial_View extends JFrame {
//    private static MazeReader mazeReader;
//    private final List<Integer> path = new ArrayList<>();
//    private int pathIndex;
//    private boolean loaded;
//
//    public Tutorial_View() {
//        setTitle("Simple Maze Solver");
//        setSize(800, 800);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JPanel panel = new JPanel();
//        JLabel label = new JLabel("File Name: ");
//        JTextField textField = new JTextField(20);
//        JButton load = new JButton("Load");
//        JButton start = new JButton("Start");
//        panel.add(label);
//        panel.add(textField);
//        panel.add(load);
//        panel.add(start);
//
//        load.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                try {
//                    mazeReader = new MazeReader(textField.getText());
//                    System.out.println(mazeReader);
//                    loaded = true;
////                    temporarything();
//                    repaint();
//                } catch (Error e) {
//                    System.err.println("Invalid file name try again");
//                    System.err.println(e.getStackTrace());
//                }
//
//            }
//        });
//
//        start.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                try {
//                    Tutorial_Solver.dfs(mazeReader.getMaze(), path);
//
//                } catch (Error e) {
//                    System.err.println("No file loaded");
//                    System.err.println(e.getStackTrace());
//                }
//            }
//        });
//
//        add(BorderLayout.NORTH, panel);
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        if (loaded) {
//            System.out.println("here");
//            char[][] charMaze = mazeReader.getMaze();
//            int rows = mazeReader.getMaze().length, cols = mazeReader.getMaze()[0].length;
//            JPanel mazePanel = new JPanel();
//            mazePanel.setLayout(new GridLayout(rows, cols));
//            JButton[][] maze = new JButton[rows][cols];
//            for (int i = 0; i < rows; i++) {
//                for (int j = 0; j < cols; j++) {
//                    maze[i][j] = new JButton();
//                    maze[i][j].setPreferredSize(new Dimension(30, 30));
//                    switch (charMaze[i][j]) {
//                        case '.': maze[i][j].setBackground(Color.WHITE); break;
//                        case '#': maze[i][j].setBackground(Color.BLACK); break;
//                        case 'o': maze[i][j].setBackground(Color.GREEN); break;
//                        case '*': maze[i][j].setBackground(Color.RED); break;
//                        default:
//                            throw new IllegalStateException("Unexpected Value: " + maze[i][j]);
//                    }
//                    mazePanel.add(maze[i][j]);
//                    System.out.println("Adding");
//                }
//            }
//
//            add(BorderLayout.CENTER, mazePanel);
//        }
//
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                Tutorial_View view = new Tutorial_View();
//                view.setVisible(true);
//            }
//        });
//    }
//}

public class Tutorial_View extends JFrame {

    private char[][] maze;
    private boolean loaded = false;
    private boolean started = false;
    private boolean running = false;
    private static MazeReader mazeReader;
    private static MazeSolver mazeSolver;
    private List<Integer> path = new ArrayList<>();
    private int pathIndex;
    private String solutionStatus = "No maze";
    private boolean solutionFound;
    Timer timer;


    public Tutorial_View() {

        setTitle("Simple Maze Solver");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("File Name: ");
        JTextField textField = new JTextField(20);
        JButton load = new JButton("Load");
        JButton start = new JButton("Start");
        JButton step = new JButton("Step");
        JButton animate = new JButton("Animate");

        JPanel bottomPanel = new JPanel();
        JLabel status = new JLabel(solutionStatus);


        step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pathIndex += 2;
                System.out.println(pathIndex + " + " + path.size());

//                System.out.println(solutionFound);
                if (pathIndex > path.size() - 2) {
                    System.out.println("In here");
                    pathIndex = path.size() - 2;
                    if (solutionFound) {
                        solutionStatus = "Solution complete: exit reachable at "
                                + path.get(path.size()-2) + "," + path.get(path.size()-1)
                                + " in " + path.size() / 2 + " moves.";
                        status.setText(solutionStatus);
                    } else {
                        solutionStatus = "Solution complete: exit not reachable";
                        status.setText(solutionStatus);
                    }
                    timer.stop();
                }
                repaint();
            }
        });
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    mazeReader = new MazeReader(textField.getText());
                    maze = mazeReader.getMaze();
                    mazeSolver = new MazeSolver(maze);
                    solutionStatus = "Maze Loaded";
                    status.setText(solutionStatus);
                    System.out.println(mazeReader);
                    loaded = true;
                    started = false;
                    pathIndex = -1;
                    path = new ArrayList<>();
                    repaint();
//                    temporarything();
                } catch (Error e) {
                    System.err.println("Invalid file name try again");
                    System.err.println(e.getStackTrace());
                }

            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (loaded && !started) {
                    started = true;
                    solutionStatus = "Solution in progress";
                    status.setText(solutionStatus);
                    solutionFound = mazeSolver.GBFS(path);
                }
            }
        });

        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                step.doClick();
            }
        });
        animate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                running = !running;
                if (running) {
                    timer.start();
                } else {
                    timer.stop();
                }
            }
        });



        topPanel.add(label);
        topPanel.add(textField);
        topPanel.add(load);
        topPanel.add(start);
        topPanel.add(step);
        topPanel.add(animate);

        bottomPanel.add(status);

        add(BorderLayout.NORTH, topPanel);
        add(BorderLayout.SOUTH, bottomPanel);

//        Tutorial_Solver.dfs(maze, path);
        pathIndex = -1;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        if (loaded) {
            g.translate(0, 70);
            int smallestDimension = Math.min(getBounds().getSize().height-30, getBounds().getSize().width);
            int largestMazeDimension = Math.max(maze.length, maze[0].length);
            int cellSize = smallestDimension / largestMazeDimension;

            for (int row = 0; row < maze.length; row++) {
                for (int col = 0; col < maze[0].length; col++) {
                    Color color;
                    switch(maze[row][col]) {
                        case '#': color = Color.BLACK; break;
                        case '.': color = Color.WHITE; break;
                        case '*': color = Color.RED; break;
                        case 'o': color = Color.GREEN; break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + maze[row][col]);
                    }
                    g.setColor(color);
                    g.fillRect(cellSize * col, cellSize * row, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(cellSize * col, cellSize * row, cellSize, cellSize);
                }
            }

            for (int p = 0; p <= pathIndex-2; p += 2) {
                int pathX = path.get(p);
                int pathY = path.get(p+1);
                g.setColor(Color.YELLOW);
                g.fillRect(pathX * cellSize, pathY * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(cellSize * pathX, cellSize * pathY, cellSize, cellSize);
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Tutorial_View view = new Tutorial_View();
                view.setVisible(true);
            }
        });
    }
}
