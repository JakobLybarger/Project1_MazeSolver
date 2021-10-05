package MazeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class Tutorial_View extends JFrame {

    private char[][] maze;
    private boolean loaded = false;
    private static MazeReader mazeReader;
    private final List<Integer> path = new ArrayList<>();
    private int pathIndex;


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

        step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pathIndex += 2;
                if (pathIndex > path.size() - 2) {
                    pathIndex = path.size() - 2;
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
                    System.out.println(mazeReader);
                    loaded = true;
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
                if (loaded) {
                    Tutorial_Solver.dfs(maze, path);
                }
            }
        });
        topPanel.add(label);
        topPanel.add(textField);
        topPanel.add(load);
        topPanel.add(start);
        topPanel.add(step);

        add(BorderLayout.NORTH, topPanel);

//        Tutorial_Solver.dfs(maze, path);
        pathIndex = -1;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (loaded) {
            g.translate(70, 70);

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
                    g.fillRect(30 * col, 30 * row, 30, 30);
                    g.setColor(Color.BLACK);
                    g.drawRect(30 * col, 30 * row, 30, 30);
                }
            }

            for (int p = 0; p <= pathIndex; p += 2) {
                int pathX = path.get(p);
                int pathY = path.get(p+1);
                g.setColor(Color.YELLOW);
                g.fillRect(pathX * 30, pathY * 30, 30, 30);
            }
        }

    }

    @Override
    protected void processKeyEvent(KeyEvent ke) {
        if (ke.getID() != KeyEvent.KEY_PRESSED) {
            return;
        }

        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            pathIndex -= 2;
            if (pathIndex < 0) {
                pathIndex = 0;
            }
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            pathIndex += 2;
            if (pathIndex > path.size() - 2) {
                pathIndex = path.size() - 2;
            }
        }
        repaint();
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
