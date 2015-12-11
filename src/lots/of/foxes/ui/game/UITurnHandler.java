package lots.of.foxes.ui.game;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lots.of.foxes.AbstractTurnHandler;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Adrian
 */
public class UITurnHandler extends AbstractTurnHandler {

    private BoardUI boardUI;
    private GameInfo gameInfo;
    
    private Thread parentThread;
    private Thread uiThread;
    
    private final Object turnLock = new Object();


    public UITurnHandler(JFrame frame, Board board, Player player, int lineheight, int boxwidth, Thread parentThread) {
        super(board, player);
        this.parentThread = parentThread;
        boardUI = new BoardUI(board, 10, 50, turnLock);
        gameInfo = new GameInfo();
        frame.add(boardUI);
        frame.add(gameInfo);
        uiThread = new Thread(boardUI);
        uiThread.start();
    }
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public void sendTurn(Line line) {
        boardUI.repaint();
    }

    @Override
    public Line receiveTurn() {
        try {
            synchronized(turnLock) {
                turnLock.wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(UITurnHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return boardUI.GetlastClickedLine();
    }

}
