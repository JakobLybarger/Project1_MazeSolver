package MazeProject;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        MazeReader mr = new MazeReader(fileName);
        System.out.println(mr);
    }
}
