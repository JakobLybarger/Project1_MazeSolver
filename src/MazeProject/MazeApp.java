package MazeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MazeApp extends JFrame {

    private char[][] maze;
    private boolean loaded = false;
    private boolean started = false;
    private boolean running = false;
    private static MazeReader mazeReader;
    private static MazeSolver mazeSolver;
    private ArrayList<Integer> path = new ArrayList<>();
    private int pathIndex;
    private boolean solutionFound;
    Timer timer;

    public MazeApp() {
        setTitle("Levi, Jakob, and Sandeep's Maze Solver");
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
        JLabel status = new JLabel("No maze");
        JLabel algorithm = new JLabel("");


        step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pathIndex += 2;

                if (pathIndex > path.size() - 2) {
                    pathIndex = path.size() - 2;
                    if (solutionFound) {
                        status.setText("Solution complete: exit reachable at "
                                + path.get(path.size()-2) + "," + path.get(path.size()-1)
                                + " in " + path.size() / 2 + " moves.");
                    } else {
                        status.setText("Solution complete: exit not reachable");
                    }
                    timer.stop();
                    running = false;
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
                    status.setText("Maze Loaded");
                    algorithm.setText("");
                    loaded = true;
                    started = false;
                    pathIndex = -1;
                    path = new ArrayList<>();
                    repaint();
                } catch (Error e) {
                    System.err.println("Invalid file name try again");
                    System.err.println(e.getStackTrace());
                }

            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selectedAlgorithm;
                if (loaded && !started) {
                    started = true;
                    status.setText("Solution in progress");
                    ArrayList<Integer> bfsPath = new ArrayList<>();
                    ArrayList<Integer> dfsPath = new ArrayList<>();
                    ArrayList<Integer> gbfsPath = new ArrayList<>();
                    mazeSolver.BFS(bfsPath);
                    mazeSolver.DFS(dfsPath);
                    solutionFound = mazeSolver.GBFS(gbfsPath);

                    path = gbfsPath;
                    selectedAlgorithm = "   Greedy Best-First Search";
                    if (dfsPath.size() < path.size()) {
                        path = dfsPath;c
                        selectedAlgorithm = "   Depth-First Search";
                    }
                    if (bfsPath.size() < path.size()) {
                        path = bfsPath;
                        selectedAlgorithm = "   Breadth-First Search";
                    }
                    algorithm.setText(selectedAlgorithm);
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
        bottomPanel.add(algorithm);

        add(BorderLayout.NORTH, topPanel);
        add(BorderLayout.SOUTH, bottomPanel);

        pathIndex = -1;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (loaded) {
            g.translate(0, 70);

            int frameHeight = getBounds().getSize().height-95;
            int frameWidth = getBounds().getSize().width;
            int mazeRows = maze.length;
            int mazeCols = maze[0].length;
            int cellHeight = frameHeight / mazeRows;
            int cellWidth = frameWidth / mazeCols;

            // Can be used to keep aspect ratio
//            if (cellHeight < cellWidth) {
//                cellWidth = cellHeight;
//            } else {
//                cellHeight = cellWidth;
//            }

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
                    g.fillRect(cellWidth * col, cellHeight * row, cellWidth, cellHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(cellWidth * col, cellHeight * row, cellWidth, cellHeight);
                }
            }

            for (int p = 0; p <= pathIndex-2; p += 2) {
                int pathX = path.get(p);
                int pathY = path.get(p+1);
                g.setColor(Color.GRAY);
                g.fillRect(pathX * cellWidth, pathY * cellHeight, cellWidth, cellHeight);
                g.setColor(Color.BLACK);
                g.drawRect(cellWidth * pathX, cellHeight * pathY, cellWidth, cellHeight);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MazeApp mazeApp = new MazeApp();
                mazeApp.setVisible(true);
            }
        });
    }
}
