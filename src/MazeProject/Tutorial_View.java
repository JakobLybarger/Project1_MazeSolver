//package MazeProject;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//public class Tutorial_View extends JFrame {
//
//    private char[][] maze;
//    private boolean loaded = false;
//    private boolean started = false;
//    private boolean running = false;
//    private static MazeReader mazeReader;
//    private static MazeSolver mazeSolver;
//    private List<Integer> path = new ArrayList<>();
//    private int pathIndex;
//    private boolean solutionFound;
//    Timer timer;
//
//
//    public Tutorial_View() {
//
//        setTitle("Simple Maze Solver");
//        setSize(640, 480);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JPanel topPanel = new JPanel();
//        JLabel label = new JLabel("File Name: ");
//        JTextField textField = new JTextField(20);
//        JButton load = new JButton("Load");
//        JButton start = new JButton("Start");
//        JButton step = new JButton("Step");
//        JButton animate = new JButton("Animate");
//
//        JPanel bottomPanel = new JPanel();
//        JLabel status = new JLabel("No maze");
//
//
//        step.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                pathIndex += 2;
//                System.out.println(pathIndex + " + " + path.size());
//
//                if (pathIndex > path.size() - 2) {
//                    pathIndex = path.size() - 2;
//                    if (solutionFound) {
//                        status.setText("Solution complete: exit reachable at "
//                                + path.get(path.size()-2) + "," + path.get(path.size()-1)
//                                + " in " + path.size() / 2 + " moves.");
//                    } else {
//                        status.setText("Solution complete: exit not reachable");
//                    }
//                    timer.stop();
//                }
//                repaint();
//            }
//        });
//        load.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                try {
//                    mazeReader = new MazeReader(textField.getText());
//                    maze = mazeReader.getMaze();
//                    mazeSolver = new MazeSolver(maze);
//                    status.setText("Maze Loaded");
//                    loaded = true;
//                    started = false;
//                    pathIndex = -1;
//                    path = new ArrayList<>();
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
//                if (loaded && !started) {
//                    started = true;
////                    solutionStatus = "Solution in progress";
//                    status.setText("Solution in progress");
//                    solutionFound = mazeSolver.GBFS(path);
//                }
//            }
//        });
//
//        timer = new Timer(500, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                step.doClick();
//            }
//        });
//        animate.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                running = !running;
//                if (running) {
//                    timer.start();
//                } else {
//                    timer.stop();
//                }
//            }
//        });
//
//
//
//        topPanel.add(label);
//        topPanel.add(textField);
//        topPanel.add(load);
//        topPanel.add(start);
//        topPanel.add(step);
//        topPanel.add(animate);
//
//        bottomPanel.add(status);
//
//        add(BorderLayout.NORTH, topPanel);
//        add(BorderLayout.SOUTH, bottomPanel);
//
//        pathIndex = -1;
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//
//
//        if (loaded) {
//            g.translate(0, 70);
//            int smallestDimension = Math.min(getBounds().getSize().height-30, getBounds().getSize().width);
//            int largestMazeDimension = Math.max(maze.length, maze[0].length);
//            int cellSize = smallestDimension / largestMazeDimension;
//
//            for (int row = 0; row < maze.length; row++) {
//                for (int col = 0; col < maze[0].length; col++) {
//                    Color color;
//                    switch(maze[row][col]) {
//                        case '#': color = Color.BLACK; break;
//                        case '.': color = Color.WHITE; break;
//                        case '*': color = Color.RED; break;
//                        case 'o': color = Color.GREEN; break;
//                        default:
//                            throw new IllegalStateException("Unexpected value: " + maze[row][col]);
//                    }
//                    g.setColor(color);
//                    g.fillRect(cellSize * col, cellSize * row, cellSize, cellSize);
//                    g.setColor(Color.BLACK);
//                    g.drawRect(cellSize * col, cellSize * row, cellSize, cellSize);
//                }
//            }
//
//            for (int p = 0; p <= pathIndex-2; p += 2) {
//                int pathX = path.get(p);
//                int pathY = path.get(p+1);
//                g.setColor(Color.YELLOW);
//                g.fillRect(pathX * cellSize, pathY * cellSize, cellSize, cellSize);
//                g.setColor(Color.BLACK);
//                g.drawRect(cellSize * pathX, cellSize * pathY, cellSize, cellSize);
//            }
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
