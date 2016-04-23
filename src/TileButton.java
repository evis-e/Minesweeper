import sun.applet.Main;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by pc on 4/20/2016.
 */
public class TileButton extends JButton {
    int type;
    int x, y;
//    boolean flag = false;
    private boolean open = false;

    public TileButton(int type, int x, int y){
        this.x = x;
        this.y = y;
        this.type = type;
        addActionListener(e -> {
            // if game is over, do nothing
            if(!MainFrame.gameover || open) {
                setOpen();
                if (type == -1) {
                    MainFrame.gameover = true;
                }
            }
        });
    }

    public void setOpen(){
        open = true;
        try {
            Image img = null;
            if(type == -1)
            img = ImageIO.read(getClass().getResource("resources/mine.png"));
            else
            img = ImageIO.read(getClass().getResource("resources/"+type+".png"));

            img = img.getScaledInstance( this.getWidth(), this.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;
            setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(Color.WHITE);
        //TODO dfs for the other buttons around this one
//        setEnabled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
