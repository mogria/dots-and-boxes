package lots.of.foxes.model;


/**
 * Represents a single box on the Board
 * 
 * @author Moritz
 */
public class Box extends GridElement { 
    /**
     * the number of lines used around this box
     */
    private int lineCount = 0;
    
    /**
     * the player which used the last line.
     */
    private Player owner = null;
    
    public Box(int boxId) {
        super(boxId);
    }

    /**
     * Get the value of lineCount
     *
     * @return the value of lineCount
     */
    public int getLineCount() {
        return lineCount;
    }

    /**
     * Get the value of owner
     *
     * @return the value of owner
     */
    public Player getOwner() {
        return owner;
    }
    
    /**
     * Increases the number of lines in use around this Box.
     * if the fourth line is being taken the player passed is declared owner
     * of the Box.
     * 
     * @param player the player adding the line
     * @return true of the player owns the box now
     */
    public boolean addLine(Player player) {
        lineCount++;
        if(lineCount >= 4) {
            owner = player;
            player.increaseBoxCount();
            return true;
        }
        return false;    
    }
}
