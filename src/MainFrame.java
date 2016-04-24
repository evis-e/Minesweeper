import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

/**
 * Created by pc on 4/20/2016.
 */
public class MainFrame extends JFrame {

    public static final int gameSize = 10;
    public static boolean gameover = false;
    static TileButton[][] gameMatrix;
    static JButton startBtn = new JButton();
    JPanel mainPanel, minesPanel;
    private JPanel topPanel;
    private static final int startBtnSize = 60, startBtnIconGap = 10 ;

    public MainFrame(){
        setTitle("Minesweeper");
        setSize(400, 460);
        setResizable(false);
        setLocationRelativeTo(null);  //position it in the middle of the screen

        newGame();

        setVisible(true);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
    }

    private void newGame() {
        //System.out.println("New game");
        gameover = false;
        gameMatrix = new TileButton[gameSize][gameSize];

        int[][] matrix = new int[gameSize][gameSize];

        // set the mines randomly
        Random r = new Random();
        int leftMines = gameSize;

        while(leftMines>0){
            int a = r.nextInt(10), b = r.nextInt(10);
            if(matrix[a][b] == 0){  // if empty
                matrix[a][b] = -1; // put a mine
                leftMines--;
            }
        }
//        for(int i=0; i<gameSize; i++){
//            for(int j=0; j<gameSize; j++){
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//        }

        // set the numbers around the mines
        for(int i=1; i<gameSize-1; i++){
            for(int j=1; j<gameSize-1; j++){
                if(matrix[i][j] == -1) continue;

                if(matrix[i-1][j] == -1) matrix[i][j]++;
                if(matrix[i][j-1] == -1) matrix[i][j]++;
                if(matrix[i+1][j] == -1) matrix[i][j]++;
                if(matrix[i][j+1] == -1) matrix[i][j]++;
                if(matrix[i+1][j+1] == -1) matrix[i][j]++;
                if(matrix[i-1][j-1] == -1) matrix[i][j]++;
                if(matrix[i-1][j+1] == -1) matrix[i][j]++;
                if(matrix[i+1][j-1] == -1) matrix[i][j]++;
            }
        }
        for(int k=1; k<gameSize-1; k++){
            // set the top row
            if(matrix[0][k] != -1) {
                if (matrix[0][k - 1] == -1) matrix[0][k]++;
                if (matrix[0][k + 1] == -1) matrix[0][k]++;
                if (matrix[1][k] == -1) matrix[0][k]++;
                if (matrix[1][k + 1] == -1) matrix[0][k]++;
                if (matrix[1][k - 1] == -1) matrix[0][k]++;
            }

            // set the bottom row
            if(matrix[gameSize-1][k] != -1) {
                if (matrix[gameSize - 1][k + 1] == -1) matrix[gameSize - 1][k]++;
                if (matrix[gameSize - 1][k - 1] == -1) matrix[gameSize - 1][k]++;
                if (matrix[gameSize - 2][k] == -1) matrix[gameSize - 1][k]++;
                if (matrix[gameSize - 2][k + 1] == -1) matrix[gameSize - 1][k]++;
                if (matrix[gameSize - 2][k - 1] == -1) matrix[gameSize - 1][k]++;
            }

            //set the left column
            if(matrix[k][0] != -1) {
                if (matrix[k - 1][0] == -1) matrix[k][0]++;
                if (matrix[k + 1][0] == -1) matrix[k][0]++;
                if (matrix[k][1] == -1) matrix[k][0]++;
                if (matrix[k - 1][1] == -1) matrix[k][0]++;
                if (matrix[k + 1][1] == -1) matrix[k][0]++;
            }
            //set the right column
            if(matrix[k][gameSize-1] != -1) {
                if (matrix[k + 1][gameSize - 1] == -1) matrix[k][gameSize - 1]++;
                if (matrix[k - 1][gameSize - 1] == -1) matrix[k][gameSize - 1]++;
                if (matrix[k][gameSize - 2] == -1) matrix[k][gameSize - 1]++;
                if (matrix[k + 1][gameSize - 2] == -1) matrix[k][gameSize - 1]++;
                if (matrix[k - 1][gameSize - 2] == -1) matrix[k][gameSize - 1]++;
            }
        }

        // topleft
        if(matrix[0][0] != -1) {
            if (matrix[0][1] == -1) matrix[0][0]++;
            if (matrix[1][1] == -1) matrix[0][0]++;
            if (matrix[1][0] == -1) matrix[0][0]++;
        }

        //topright
        if(matrix[0][gameSize-1] != -1) {
            if (matrix[0][gameSize - 2] == -1) matrix[0][gameSize - 1]++;
            if (matrix[1][gameSize - 2] == -1) matrix[0][gameSize - 1]++;
            if (matrix[1][gameSize - 1] == -1) matrix[0][gameSize - 1]++;
        }

        //bottomleft
        if(matrix[gameSize-1][0] != -1) {
            if (matrix[gameSize - 2][0] == -1) matrix[gameSize - 1][0]++;
            if (matrix[gameSize - 2][1] == -1) matrix[gameSize - 1][0]++;
            if (matrix[gameSize - 1][1] == -1) matrix[gameSize - 1][0]++;
        }

        //bottomright
        if(matrix[gameSize-1][gameSize-1] != -1) {
            if (matrix[gameSize - 2][gameSize - 1] == -1) matrix[gameSize - 1][gameSize - 1]++;
            if (matrix[gameSize - 2][gameSize - 2] == -1) matrix[gameSize - 1][gameSize - 1]++;
            if (matrix[gameSize - 1][gameSize - 2] == -1) matrix[gameSize - 1][gameSize - 1]++;
        }

        for(int i=0; i<gameSize; i++){
            for(int j=0; j<gameSize; j++){
                System.out.print(matrix[i][j] + " ");
                gameMatrix[i][j] = new TileButton(matrix[i][j], i, j);
                gameMatrix[i][j].setFocusPainted(false);
            }
            System.out.println();
        }

        try{
            //TODO check if it is the first time the game runs
        remove(mainPanel);}
        catch (Exception e){
        }

        minesPanel = new JPanel();
        minesPanel.setLayout(new GridLayout(10, 10, 2, 2));

        // add the stuff to the panel
        for(int i=0; i<gameSize; i++)
            for(int j=0; j<gameSize; j++)
                minesPanel.add(gameMatrix[i][j]);

        startBtn.setPreferredSize(new Dimension(startBtnSize, startBtnSize));

        try {
            Image img = ImageIO.read(getClass().getResource("resources/smiley.png"));
            img = img.getScaledInstance( (int)startBtn.getPreferredSize().getWidth()-startBtnIconGap,
                    (int)startBtn.getPreferredSize().getHeight()-startBtnIconGap,  java.awt.Image.SCALE_SMOOTH ) ;
            startBtn.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startBtn.setFocusPainted(false);
        startBtn.setBackground(Color.WHITE);

        // we put the startBtn in the center of the topPanel
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(startBtn);

        // mainPanel contains all other panels
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(2, 2));

        // arrange the panels inside the mainPanel
        mainPanel.add(minesPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        //add the mainPanel to the frame
        add(mainPanel);

        // make sure the components are drawn in the screen on game start
        validate();
        repaint();
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    public static void gameOver(int x, int y) {
        gameover = true;

        // open all the mines
        for(int i=0; i<gameSize; i++){
            for(int j=0; j<gameSize; j++){
                if(gameMatrix[i][j].type == -1){
                    gameMatrix[i][j].setOpen(Color.WHITE);
                }
            }
        }

        // set the clicked mine to red
        gameMatrix[x][y].setOpen(Color.RED);

        // change the icon of the startBtn

        try {
            Image img = ImageIO.read(MainFrame.class.getResource("resources/dead.png"));
            img = img.getScaledInstance( (int)startBtn.getPreferredSize().getWidth()-startBtnIconGap,
                    (int)startBtn.getPreferredSize().getHeight()-startBtnIconGap,  java.awt.Image.SCALE_SMOOTH ) ;
            startBtn.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openAdjacent(int x, int y) {
        if(x<0 || x>=gameSize || y<0 || y>=gameSize) return;
        if(gameMatrix[x][y].isOpen()) return;

        gameMatrix[x][y].open(Color.WHITE);

        if(gameMatrix[x][y].type != 0) return;

        // try to open all adjacent tiles
        openAdjacent(x-1, y);
        openAdjacent(x-1, y-1);
        openAdjacent(x-1, y+1);
        openAdjacent(x+1, y);
        openAdjacent(x+1, y+1);
        openAdjacent(x+1, y-1);
        openAdjacent(x, y+1);
        openAdjacent(x, y-1);
    }

    public static boolean isVictory() {
        for(int i=0; i<gameSize; i++){
            for(int j=0; j<gameSize; j++){
                if(gameMatrix[i][j].type != -1 && !gameMatrix[i][j].isOpen()){
                    return false;
                }
            }
        }
        return true;
    }

    public static void sunglassesOn() {
        gameover = true;
        try {
            Image img = ImageIO.read(MainFrame.class.getResource("resources/sunglasses.png"));
            img = img.getScaledInstance( (int)startBtn.getPreferredSize().getWidth()-startBtnIconGap,
                    (int)startBtn.getPreferredSize().getHeight()-startBtnIconGap,  java.awt.Image.SCALE_SMOOTH ) ;
            startBtn.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
