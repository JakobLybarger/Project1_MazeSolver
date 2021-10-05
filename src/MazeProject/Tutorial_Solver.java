package MazeProject;

import java.util.List;
import java.util.Stack;

public class Tutorial_Solver {

    public static boolean dfs(char[][] maze, List<Integer> path) {
        int x=0, y=0, rows=maze.length, cols=maze[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'o') {
                    y = i;
                    x = j;
                }
            }
        }

        boolean[][] visited = new boolean[maze.length][maze[0].length];
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        Stack<int[]> frontier = new Stack<>();
        frontier.push(new int[]{y, x, 0});

        while (!frontier.isEmpty()) {
            int[] coord = frontier.pop();
            visited[coord[0]][coord[1]] = true;


            if (maze[coord[0]][coord[1]] == '*') {
                return true;
            } else if (!(coord[0] == y && coord[1] == x)) {
                path.add(coord[1]);
                path.add(coord[0]);
            }

            for (int i = 0; i < directions.length; i++) {
                int nr = coord[0] + directions[i][0], nc = coord[1] + directions[i][1];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols ||
                        maze[nr][nc] == '#' || visited[nr][nc]) {
                    continue;
                }
                frontier.push(new int[]{nr, nc, coord[2]+1});
            }
        }

        return false;
    }
}
