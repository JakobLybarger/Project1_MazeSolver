package MazeProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class MazeReader {
    private char[][] charMaze;
    private Square[][] maze;

    public MazeReader(String fileName) {
        readFile(fileName);
    }

    public Square[][] getMaze() {
        return this.maze;
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

    private void readFile(String fileName) {
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

        this.charMaze = new char[tempMaze.size()][tempMaze.get(0).size()];
        this.maze = new Square[tempMaze.size()][tempMaze.get(0).size()];
        for (int i = 0; i < tempMaze.size(); i++) {
            for (int j = 0; j < tempMaze.get(i).size(); j++) {
                char c = tempMaze.get(i).get(j);
                this.charMaze[i][j] = c;
                this.maze[i][j] = fromChar(c);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringMaze = new StringBuilder();
        for (int i = 0; i < this.charMaze.length; i++) {
            for (int j = 0; j < this.charMaze[i].length; j++) {
                stringMaze.append(this.charMaze[i][j]);
            }
            stringMaze.append("\n");
        }
        return stringMaze.toString();
    }
}
