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
        Stack<Node> frontier = new Stack<>();
        frontier.push(new Node(startX, startY));

        while (!frontier.isEmpty()) {
            Node curNode = frontier.pop();
            visited[curNode.y][curNode.x] = true;

            if (this.maze[curNode.y][curNode.x] == Square.EXIT) {
                path.add(curNode.x);
                path.add(curNode.y);
                return true;
            } else if (!(curNode.y == startY && curNode.x == startX)) {
                path.add(curNode.x);
                path.add(curNode.y);
            }

            // Refactor nr and nc to something else
            for (int[] direction : directions) {
                int nextY = curNode.y + direction[0], nextX = curNode.x + direction[1];
                if (nextY < 0 || nextY >= this.rows || nextX < 0 || nextX >= this.cols ||
                        this.maze[nextY][nextX] == Square.WALL || visited[nextY][nextX]) {
                    continue;
                }
                frontier.push(new Node(nextX, nextY));
            }
        }
        return false;
    }

    // BFS
    boolean BFS(List<Integer> path) {
        boolean[][] visited = new boolean[this.maze.length][this.maze[0].length];
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        Queue<Node> frontier = new LinkedList<>();
        frontier.add(new Node(startX, startY));

        while (!frontier.isEmpty()) {
            Node curNode = frontier.poll();
            visited[curNode.y][curNode.x] = true;

            if (this.maze[curNode.y][curNode.x] == Square.EXIT) {
                path.add(curNode.x);
                path.add(curNode.y);
                return true;
            } else if (!(curNode.y == startY && curNode.x == startX)) {
                path.add(curNode.x);
                path.add(curNode.x);
            }

            // Refactor nr and nc to something else
            for (int[] direction : directions) {
                int nextY = curNode.y + direction[0], nextX = curNode.x + direction[1];
                if (nextY < 0 || nextY >= this.rows || nextX < 0 || nextX >= this.cols ||
                        this.maze[nextY][nextX] == Square.WALL || visited[nextY][nextX]) {
                    continue;
                }
                frontier.add(new Node(nextX, nextY));
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

            for (int[] direction : directions) {
                int nextY = curNode.y + direction[0], nextX = curNode.x + direction[1];
                if (nextY < 0 || nextY >= this.rows || nextX < 0 || nextX >= this.cols ||
                        this.maze[nextY][nextX] == Square.WALL || visited[nextY][nextX]) {
                    continue;
                }

                GBFSNode nextNode = new GBFSNode(nextX, nextY, curNode.distanceFromStart + 1, calcDistanceToClosestGoal(nextX, nextY));
                if (!frontier.contains(nextNode)) {
                    frontier.add(nextNode);
                }
            }
        }

        return false;
    }

    private int calcDistanceToClosestGoal(int x, int y) {
        int smallest = 2147483647;
        for (Node goal : this.goals) {
            int currentDistance = calcManhattanDistance(x, y, goal.x, goal.y);
            if (currentDistance < smallest) {
                smallest = currentDistance;
            }
        }
        return smallest;
    }

    private int calcManhattanDistance(int curX, int curY, int endX, int endY) {
        return Math.abs(curX - endX) + Math.abs(curY - endY);
    }

    private static class Node {
        int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private static class GBFSNode implements Comparable<GBFSNode> {
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
                return x == ((GBFSNode) otherNode).x && y == ((GBFSNode) otherNode).y;
            }
            return false;
        }
    }
}
