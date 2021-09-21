package MazeProject;

import java.io.*;
import java.util.ArrayList;

public class MazeReader {
    private char[][] maze;

    public MazeReader(String fileName) throws IOException {
        ArrayList<ArrayList<Character>> tempMaze = new ArrayList<>();
        fileName = System.getProperty("user.dir") + "/" + fileName;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        try {
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
        } finally {
            bufferedReader.close();
        }

        this.maze = new char[tempMaze.size()][tempMaze.get(0).size()];
        for (int i = 0; i < tempMaze.size(); i++) {
            for (int j = 0; j < tempMaze.get(i).size(); j++) {
                this.maze[i][j] = tempMaze.get(i).get(j);
            }
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
