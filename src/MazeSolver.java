/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     *
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells
        Stack<MazeCell> s = new Stack<>();
        ArrayList<MazeCell> m = new ArrayList<>();
        // current cell starts as the end cell and is added to queue
        MazeCell current = maze.getEndCell();
        s.push(current);
        // while the current cell isnt the starting cell, find the parent of that cell and it to the stack
        while (current != maze.getStartCell()) {
            current = current.getParent();
            s.push(current);
        }
        // for each cell in the stack add it into an arraylist so that it goes from start to finish
        int n = s.size();
        for (int i = 0; i < n; i++) {
            m.add(s.pop());
        }
        return m;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     *
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Stack<MazeCell> toExplore = new Stack<>();
        // start at the beginning of the maze, update the cell to explored and add it to the stack
        MazeCell current = maze.getStartCell();
        toExplore.push(current);
        current.setExplored(true);
        // while their are still cells left to explore keep exploring
        while (!toExplore.empty()) {
            // set the current mazecell to whatever is at the top of the stack
            current = toExplore.pop();
            // essentially a base case, return the solution
            if (current.equals(maze.getEndCell())) {
                return getSolution();
            }
            // find all the neighbors of the cell and add them to the stack and set them to explored as well as setting
            // the current cell as their parent
            ArrayList<MazeCell> neighbors = getNeighbors(current);
            for (int i = 0; i < neighbors.size(); i++) {
                MazeCell neighbor = neighbors.get(i);
                neighbor.setParent(current);
                neighbor.setExplored(true);
                toExplore.push(neighbor);
            }
        }
        return null;
    }

    public ArrayList<MazeCell> getNeighbors(MazeCell m) {
        // goes through from the order of n,e,s,w and adds all the valid cells to an arraylist and returns it
        ArrayList<MazeCell> holder = new ArrayList<>();
        if (maze.isValidCell(m.getRow() - 1, m.getCol())) {
            holder.add(maze.getCell(m.getRow() - 1, m.getCol()));
        }
        if (maze.isValidCell(m.getRow(), m.getCol() + 1)) {
            holder.add(maze.getCell(m.getRow(), m.getCol() + 1));
        }
        if (maze.isValidCell(m.getRow() + 1, m.getCol())) {
            holder.add(maze.getCell(m.getRow() + 1, m.getCol()));
        }
        if (maze.isValidCell(m.getRow(), m.getCol() - 1)) {
            holder.add(maze.getCell(m.getRow(), m.getCol() - 1));
        }
        return holder;
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     *
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // very similar to DFS but using a queue instead of a stack
        Queue<MazeCell> toExplore = new LinkedList<>();
        MazeCell current = maze.getStartCell();
        toExplore.add(current);
        current.setExplored(true);
        while (!toExplore.isEmpty()) {
            current = toExplore.remove();
            if (current.equals(maze.getEndCell())) {
                return getSolution();
            }
            ArrayList<MazeCell> neighbors = getNeighbors(current);
            for (int i = 0; i < neighbors.size(); i++) {
                MazeCell neighbor = neighbors.get(i);
                neighbor.setParent(current);
                neighbor.setExplored(true);
                toExplore.add(neighbor);
            }
        }
        return null;
    }
    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
