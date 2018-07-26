// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Bengi Sevil (bsevil18)
package towerofhanoi;

import java.util.EmptyStackException;
import java.util.Observable;

/**
 * HanoiSolver class represents the Tower of Hanoi game.
 * 
 * @author Bengi Sevil
 * @version 06.14.2018
 *
 */
public class HanoiSolver extends Observable {
    private Tower left;
    private Tower right;
    private Tower middle;
    private int numDiscs;


    /**
     * HanoiSolver default constructor. Creates a new object of
     * type HanoiSolver and initializes the private fields left,
     * right and middle to the corresponding fields in the Position.
     * numDiscs is initialized to the parameter numDiscs.
     * 
     * @param numDiscs
     *            The total number of discs.
     * 
     */
    public HanoiSolver(int numDiscs) {
        left = new Tower(Position.LEFT);
        right = new Tower(Position.RIGHT);
        middle = new Tower(Position.MIDDLE);
        this.numDiscs = numDiscs;
    }


    /**
     * Returns the numDiscs field.
     * 
     * @return numDiscs
     *         The number of discs.
     */
    public int discs() {
        return numDiscs;
    }


    /**
     * Returns the tower at the given position.
     * 
     * @param pos
     *            The position of the tower.
     * @return tower
     *         Left, right, or middle.
     */
    public Tower getTower(Position pos) {
        switch (pos) {
            case LEFT:
                return left;
            case RIGHT:
                return right;
            case MIDDLE:
                return middle;
            default:
                return left;
        }

    }


    /**
     * Return left , middle, and right�s toString() appended.
     * For example: if the left, middle, right tower each have a
     * single disc with width of 10, 20, and 30 respectively,
     * the output of toString() is �[10][20][30]".
     * 
     * @return The number of discs in all 3 towers in
     *         the [left disc widths][middle disc widths][right disc widths]
     *         format.
     */
    public String toString() {
        String str = "[";

        LinkedStack<Disc> tempL = new LinkedStack<Disc>();
        LinkedStack<Disc> tempM = new LinkedStack<Disc>();
        LinkedStack<Disc> tempR = new LinkedStack<Disc>();

        if (left.size() == 1) {
            str += "" + left.peek().getWidth();
            str += "]";
        }
        else {
            while (!left.isEmpty()) {
                if (left.size() == 1) {
                    str += "" + left.peek().getWidth();
                    break;
                }
                str += "" + left.peek().getWidth() + ", ";
                tempL.push(left.pop());
            }
            str += "]";
            while (!tempL.isEmpty()) {
                left.push(tempL.pop());
            }
        }
        if (middle.size() == 1) {
            str += "[";
            str += "" + middle.peek().getWidth();
            str += "]";
        }
        else {
            str += "[";
            while (!middle.isEmpty()) {
                if (middle.size() == 1) {
                    str += "" + middle.peek().getWidth();
                    break;
                }
                str += "" + middle.peek().getWidth() + ", ";

                tempM.push(middle.pop());
            }
            str += "]";
            while (!tempM.isEmpty()) {
                middle.push(tempM.pop());
            }
        }
        if (right.size() == 1) {
            str += "[";
            str += "" + right.peek().getWidth();
            str += "]";
        }

        else {
            str += "[";
            while (!right.isEmpty()) {
                if (right.size() == 1) {
                    str += "" + right.peek().getWidth();
                    break;
                }
                str += "" + right.peek().getWidth() + ", ";
                tempR.push(right.pop());
            }
            str += "]";
            while (!tempR.isEmpty()) {
                right.push(tempR.pop());
            }
        }
        return str;
    }


    /**
     * This method executes the specified move. Pop the Disc
     * from the "source" Tower, and push it onto the "destination"
     * Tower.
     * 
     * @param source
     * @param destination
     * @throws EmptyStackException
     *             If the tower is empty.
     */
    private void move(Tower source, Tower destination) {
        if (source.isEmpty()) {
            throw new EmptyStackException();
        }
        Disc temp = source.pop();
        destination.push(temp);
        this.setChanged();
        super.notifyObservers(destination.position());
    }


    /**
     * The algorithm to move currentDiscs from startPole to endPole
     * using tempPole as a spare according to the rule of the Towers
     * of Hanoi problem.
     * 
     * @param currentDiscs
     *            The discs on the current pole.
     * @param startPole
     *            Left pole
     * @param tempPole
     *            Middle pole
     * @param endPole
     *            Right pole
     */
    private void solveTowers(
        int currentDiscs,
        Tower startPole,
        Tower tempPole,
        Tower endPole) {

        if (currentDiscs > 0) {
            solveTowers(currentDiscs - 1, startPole, endPole, tempPole);
            move(startPole, endPole);
            solveTowers(currentDiscs - 1, tempPole, startPole, endPole);
        }

    }


    /**
     * This method makes the initial call to the solveTowers
     * method with the parameters numDiscs, left, middle and right
     * for the startPole, tempPole and endPole respectively.
     * 
     */
    public void solve() {
        solveTowers(numDiscs, left, middle, right);
    }

}
