package lots.of.foxes.ai;

import java.util.ArrayList;
import java.util.List;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Moritz
 */
public class NotSoDumbAITurnHandler extends AbstractAITurnHandler {
    
    public NotSoDumbAITurnHandler(Board board, Player player) {
        super(board, player);
    }

    @Override
    public Line receiveTurn() {
        List<Box> closableBoxes = BoardUtil.getCloseableBoxes(board);
        if(closableBoxes.size() > 0) {
            List<Line> closingLines = BoardUtil.filterAvailableLines(BoardUtil.getLinesSurroundingBox(board, closableBoxes.get(0)));
            if(closingLines.size() > 0) {
                return closingLines.get(0);
            }

            
        }
                    
        List<Box> suboptimalBoxes = BoardUtil.filterBoxes(new ArrayList<>(board.getBoxes()), box -> box.getLineCount() < 3);
        if(suboptimalBoxes.size() > 0) {
            return BoardUtil.getRandomFromList(BoardUtil.filterAvailableLines(BoardUtil.getLinesSurroundingBox(board, BoardUtil.getRandomFromList(suboptimalBoxes))));
        }
            
        return BoardUtil.getRandomAvailableLine(board);
    }
    
}
