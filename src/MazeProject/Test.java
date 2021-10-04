package MazeProject;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        MazeReader mr = new MazeReader("maze1.txt");
        System.out.println(mr);
        MazeSolver ms = new MazeSolver(mr.getMaze());
        System.out.println("Length of path: " + ms.DFS());
    }
}
