package thd.gameobjects.base;

import java.util.Objects;

/**
 * Manages positon of gameobjects in {@link GameView} with a resolution of 1280 x 720 pixels.
 *
 * @see GameView
 */
public class Position implements Comparable<Position> {

    private double x;
    private double y;

    /**
     * Creates a position on (0, 0).
     */
    public Position() {
        this(0, 0);
    }

    /**
     * Creates a position with the coordinates of the given position.
     *
     * @param otherPosition Another position.
     */
    public Position(Position otherPosition) {
        this(otherPosition.x, otherPosition.y);
    }

    /**
     * Creates a position on (x, y).
     *
     * @param x X-coordinate on the window.
     * @param y Y-coordinate on the window.
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x coordinate.
     *
     * @return x coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y coordinate.
     *
     * @return y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Updates this position to the coordinates of the given position.
     *
     * @param otherPosition Another position.
     */
    public void updateCoordinates(Position otherPosition) {
        x = otherPosition.x;
        y = otherPosition.y;
    }

    /**
     * Updates this position to the coordinates of the new position.
     *
     * @param x X-coordinate on the window.
     * @param y Y-coordinate on the window.
     */
    public void updateCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * One pixel to the right.
     */
    public void right() {
        x++;
    }

    /**
     * To the right by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void right(double pixel) {
        x += pixel;
    }

    /**
     * One pixel to the left.
     */
    public void left() {
        x--;
    }

    /**
     * To the left by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void left(double pixel) {
        x -= pixel;
    }

    /**
     * One pixel upwards.
     */
    public void up() {
        y--;
    }

    /**
     * Upwards by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void up(double pixel) {
        y -= pixel;
    }

    /**
     * One pixel downwards.
     */
    public void down() {
        y++;
    }

    /**
     * Downwards by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void down(double pixel) {
        y += pixel;
    }

    @Override
    public String toString() {
        return "Position (" + (int) Math.round(x) + ", " + (int) Math.round(y) + ")";
    }

    /**
     * Returns the distance between this position and another one.
     *
     * @param otherPosition The other Position.
     * @return The direct distance between this Position and the other Position.
     */
    public double distance(Position otherPosition) {
        return Math.sqrt(Math.pow(otherPosition.getX() - x, 2) + Math.pow(otherPosition.getY() - y, 2));
    }

    /**
     * Moves towards the given position with the given speed.
     *
     * @param otherPosition Another position.
     * @param speedInPixel  Speed of movement in a single frame.
     */
    public void moveToPosition(Position otherPosition, double speedInPixel) {
        double distance = distance(otherPosition);
        if (distance <= speedInPixel) {
            updateCoordinates(otherPosition);
        } else {
            right((otherPosition.x - x) / distance * speedInPixel);
            down((otherPosition.y - y) / distance * speedInPixel);
        }
    }

    /**
     * Checks if this position is similar to the other position.
     *
     * @param otherPosition Another position.
     * @return True if this position has the same x- and y-coordinates as the other position,
     * when both are rounded to <code>int</code>.
     */
    public boolean similarTo(Position otherPosition) {
        return Math.round(x) == Math.round(otherPosition.x)
                && Math.round(y) == Math.round(otherPosition.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position other = (Position) o;
        return (Double.compare(x, other.x) == 0
                && Double.compare(y, other.y) == 0);
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Position o) {
        return Double.compare(distance(new Position(0, 0)), o.distance(new Position(0, 0)));
    }
}
