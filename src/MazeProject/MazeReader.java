package MazeProject;

import java.io.*;
import java.util.ArrayList;

// Change maze tostring later
public class MazeReader {
    private char[][] maze;
    private Square[][] sMaze;

    public char[][] getMaze() {
        return this.maze;
    }
    public MazeReader(String fileName) {
        ArrayList<ArrayList<Character>> tempMaze = new ArrayList<>();
        fileName = System.getProperty("user.dir") + "/" + fileName;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));) {
            String line = bufferedReader.readLine();
            while(line != null) {
                ArrayList<Character> chars = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    chars.add(c);
                }
                tempMaze.add(chars);
                line = bufferedReader.readLine();
            }

        } catch(Exception e) {
            System.err.println("Error encountered");
        }

        this.maze = new char[tempMaze.size()][tempMaze.get(0).size()];
        this.sMaze = new Square[tempMaze.size()][tempMaze.get(0).size()];
        for (int i = 0; i < tempMaze.size(); i++) {
            for (int j = 0; j < tempMaze.get(i).size(); j++) {
                char c = tempMaze.get(i).get(j);
                this.maze[i][j] = c;
                this.sMaze[i][j] = fromChar(c);
            }
        }
    }

    public static Square fromChar(char ch) throws IllegalArgumentException {
        switch (ch) {
            case '#':
                return Square.WALL;
            case '.':
                return Square.OPEN_SPACE;
            case 'o':
                return Square.START;
            case '*':
                return Square.EXIT;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringMaze = new StringBuilder();
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[i].length; j++) {
                stringMaze.append(this.maze[i][j]);
            }
            stringMaze.append("\n");
        }
        return stringMaze.toString();
    }
}
