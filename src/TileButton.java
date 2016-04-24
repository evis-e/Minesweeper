import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by pc on 4/20/2016.
 */
public class TileButton extends JButton {
    int type; // 0-8 means the number, -1 means a mine
    int x, y;
    boolean flag = false;
    private boolean open = false;

    public boolean isOpen(){
        return open;
    }
    public TileButton(int type, int x, int y){
        this.x = x;
        this.y = y;
        this.type = type;
        setBackground(Color.DARK_GRAY);
        addActionListener(e -> {
            // if game is over, do nothing
            if(!MainFrame.gameover && !open && !flag) {
                setOpen(Color.WHITE);
                if (type == -1) {
                    MainFrame.gameOver(x, y);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if(!MainFrame.gameover) {
                        if (flag) removeFlag();
                        else putFlag();
                    }
                }
            }
        });
    }

    private void removeFlag() {
        setIcon(null);
        flag = false;
    }

    private void putFlag() {
        flag = true;
        try {
            Image img = ImageIO.read(getClass().getResource("resources/flag.png"));
            img = img.getScaledInstance(this.getWidth() - 15, this.getHeight() - 15, java.awt.Image.SCALE_SMOOTH);

            setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open(Color c){
        if(flag) return;

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
    }
    public void setOpen(Color c){
        if(type == 0){
            MainFrame.openAdjacent(x, y);
        }else{
            open(c);
        }
        if(MainFrame.isVictory()){
            MainFrame.sunglassesOn();
        }
//        setEnabled(false);
    }
}
