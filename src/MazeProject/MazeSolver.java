package MazeProject;

import java.util.*;

public class MazeSolver {

    Square[][] maze;
    Node[] goals;
    int startX, startY, rows, cols;

    public MazeSolver(Square[][] maze) {
        this.maze = maze.clone();
        this.rows = maze.length;
        this.cols = maze[0].length;

        List<Node> endGoals = new ArrayList<>();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == Square.START) {
                    this.startY = i;
                    this.startX = j;
                }

                if (maze[i][j] == Square.EXIT) {
                    endGoals.add(new Node(j, i));
                }
            }
        }

        this.goals = new Node[endGoals.size()];
        for (int i = 0; i < endGoals.size(); i++) {
            this.goals[i] = endGoals.get(i);
        }
    }

    boolean DFS(List<Integer> path) {
        boolean[][] visited = new boolean[this.maze.length][this.maze[0].length];
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        Stack<int[]> frontier = new Stack<>();
        frontier.push(new int[]{startY, startX, 0});

        while (!frontier.isEmpty()) {
            int[] coord = frontier.pop();
            visited[coord[0]][coord[1]] = true;

            if (this.maze[coord[0]][coord[1]] == Square.EXIT) {
                path.add(coord[1]);
                path.add(coord[0]);
                return true;
            } else if (!(coord[0] == startY && coord[1] == startX)) {
                path.add(coord[1]);
                path.add(coord[0]);
            }

            // Refactor nr and nc to something else
            for (int i = 0; i < directions.length; i++) {
                int nr = coord[0] + directions[i][0], nc = coord[1] + directions[i][1];
                if (nr < 0 || nr >= this.rows || nc < 0 || nc >= this.cols ||
                        this.maze[nr][nc] == Square.WALL || visited[nr][nc]) {
                    continue;
                }
                frontier.push(new int[]{nr, nc, coord[2]+1});
            }
        }
        return false;
    }

    // BFS
    boolean BFS(List<Integer> path) {
        boolean[][] visited = new boolean[this.maze.length][this.maze[0].length];
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        Queue<int[]> frontier = new LinkedList<>();
        frontier.add(new int[]{startY, startX, 0});

        while (!frontier.isEmpty()) {
            int[] coord = frontier.poll();
            visited[coord[0]][coord[1]] = true;

            if (this.maze[coord[0]][coord[1]] == Square.EXIT) {
                path.add(coord[1]);
                path.add(coord[0]);
                return true;
            } else if (!(coord[0] == startY && coord[1] == startX)) {
                path.add(coord[1]);
                path.add(coord[0]);
            }

            // Refactor nr and nc to something else
            for (int i = 0; i < directions.length; i++) {
                int nr = coord[0] + directions[i][0], nc = coord[1] + directions[i][1];
                if (nr < 0 || nr >= this.rows || nc < 0 || nc >= this.cols ||
                        this.maze[nr][nc] == Square.WALL || visited[nr][nc]) {
                    continue;
                }
                frontier.add(new int[]{nr, nc, coord[2]+1});
            }
        }
        return false;
    }

    // Best FS
    boolean GBFS(List<Integer> path) {
        boolean[][] visited = new boolean[this.maze.length][this.maze[0].length];
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        PriorityQueue<GBFSNode> frontier = new PriorityQueue<>();
        GBFSNode node = new GBFSNode(this.startX, this.startY, 0, calcDistanceToClosestGoal(this.startX, this.startY));
        frontier.add(node);

        while(!frontier.isEmpty()) {
            GBFSNode curNode = frontier.poll();
            if (this.maze[curNode.y][curNode.x] == Square.EXIT) {
                path.add(curNode.x);
                path.add(curNode.y);
                return true;
            } else if (!(curNode.y == startY && curNode.x == startX)) {
                path.add(curNode.x);
                path.add(curNode.y);
            }

            visited[curNode.y][curNode.x] = true;

            for (int i = 0; i < directions.length; i++) {
                int nextY = curNode.y + directions[i][0], nextX = curNode.x + directions[i][1];
                if (nextY < 0 || nextY >= this.rows || nextX < 0 || nextX >= this.cols ||
                        this.maze[nextY][nextX] == Square.WALL || visited[nextY][nextX]) {
                    continue;
                }

                GBFSNode nextNode = new GBFSNode(nextX, nextY, curNode.distanceFromStart+1, calcDistanceToClosestGoal(nextX, nextY));
                if (!frontier.contains(nextNode)) {
                    frontier.add(nextNode);
                }
            }
        }

        return false;
    }

    private int calcDistanceToClosestGoal(int x, int y) {
        int smallest = 2147483647;
        for (int i = 0; i < this.goals.length; i++) {
            int currentDistance = calcManhattanDistance(x, y, this.goals[i].x, this.goals[i].y);
            if (currentDistance < smallest) {
                smallest = currentDistance;
            }
        }
        return smallest;
    }

    private int calcManhattanDistance(int curX, int curY, int endX, int endY) {
        return Math.abs(curX - endX) + Math.abs(curY - endY);
    }

    private class Node {
        int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private class GBFSNode implements Comparable<GBFSNode> {
        int x, y, distanceFromStart, estimatedDistanceFromGoal;


        public GBFSNode(int x, int y, int distanceFromStart, int estimatedDistanceFromGoal) {
            this.x = x;
            this.y = y;
            this.distanceFromStart = distanceFromStart;
            this.estimatedDistanceFromGoal = estimatedDistanceFromGoal;
        }

        @Override
        public int compareTo(GBFSNode otherNode) {
            return estimatedDistanceFromGoal - otherNode.estimatedDistanceFromGoal;
        }

        @Override
        public boolean equals(Object otherNode) {
            if (otherNode instanceof GBFSNode) {
                if (x == ((GBFSNode) otherNode).x && y == ((GBFSNode) otherNode).y) {
                    return true;
                }
            }
            return false;
        }
    }
}
