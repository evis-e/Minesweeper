import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by pc on 4/20/2016.
 */
public class TileButton extends JButton {
    int type; // 0-8 means the number, -1 means a mine
    int x, y;
//    boolean flag = false;
    private boolean open = false;

    public TileButton(int type, int x, int y){
        this.x = x;
        this.y = y;
        this.type = type;
        setBackground(Color.DARK_GRAY);
        addActionListener(e -> {
            // if game is over, do nothing
            if(!MainFrame.gameover && !open) {
                setOpen(Color.WHITE);
                if (type == -1) {
                    MainFrame.gameOver(x, y);
                }
            }
        });
    }

    public void setOpen(Color c){
        open = true;
        // set the icon of the button
        try {
            Image img = null;
            if (type == -1){
                img = ImageIO.read(getClass().getResource("resources/mine.png"));
                img = img.getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH);
            }else {
                img = ImageIO.read(getClass().getResource("resources/" + type + ".png"));
                img = img.getScaledInstance(this.getWidth() - 5, this.getHeight() - 5, java.awt.Image.SCALE_SMOOTH);
            }
            setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(c);
        //TODO dfs for the other buttons around this one
//        setEnabled(false);
    }
}
