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
    TileButton[][] gameMatrix;
    JButton startBtn = new JButton();
    JPanel mainPanel, minesPanel;
    private JPanel topPanel;

    public MainFrame(){
        setTitle("Minesweeper");
        setSize(300, 350);
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
        System.out.println("New game");
        gameover = false;
        gameMatrix = new TileButton[gameSize][gameSize];

        int[][] matrix = new int[gameSize][gameSize];

        // set the mines randomly
        Random r = new Random();
        int leftMines = gameSize;

        while(leftMines>0){
            int a = r.nextInt(gameSize), b = r.nextInt(gameSize);
            if(matrix[a][b] == 0){
                matrix[a][b] = -1; // put a mine
                leftMines--;
            }
        }

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
        for(int i=1; i<gameSize-1; i++){
            // set the top row
            if(matrix[0][i-1] == -1) matrix[0][i]++;
            if(matrix[0][i+1] == -1) matrix[0][i]++;
            if(matrix[1][i] == -1) matrix[0][i]++;
            if(matrix[1][i+1] == -1) matrix[0][i]++;
            if(matrix[1][i-1] == -1) matrix[0][i]++;

            // set the bottom row
            if(matrix[gameSize-1][i-1] == -1) matrix[gameSize-1][i]++;
            if(matrix[gameSize-1][i+1] == -1) matrix[gameSize-1][i]++;
            if(matrix[gameSize-2][i+1] == -1) matrix[gameSize-1][i]++;
            if(matrix[gameSize-2][i] == -1) matrix[gameSize-1][i]++;
            if(matrix[gameSize-2][i-1] == -1) matrix[gameSize-1][i]++;

            //set the left column
            if(matrix[i-1][0] == -1) matrix[i][0]++;
            if(matrix[i+1][0] == -1) matrix[i][0]++;
            if(matrix[i][1] == -1) matrix[i][0]++;

            //set the right column
            if(matrix[i+1][gameSize-1] == -1) matrix[i][gameSize-1]++;
            if(matrix[i-1][gameSize-1] == -1) matrix[i][gameSize-1]++;
            if(matrix[i][gameSize-2] == -1) matrix[i][gameSize-1]++;
        }

        // topleft
        if(matrix[0][1] == -1) matrix[0][0]++;
        if(matrix[1][1] == -1) matrix[0][0]++;
        if(matrix[1][0] == -1) matrix[0][0]++;

        //bottomleft
        if(matrix[0][gameSize-2] == -1) matrix[0][gameSize-1]++;
        if(matrix[1][gameSize-2] == -1) matrix[0][gameSize-1]++;
        if(matrix[1][gameSize-1] == -1) matrix[0][gameSize-1]++;

        //TODO the other corners

        for(int i=0; i<gameSize; i++){
            for(int j=0; j<gameSize; j++){
                gameMatrix[i][j] = new TileButton(matrix[i][j], i, j);
            }
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

        startBtn.setPreferredSize(new Dimension(50, 50));

        try {
            Image img = ImageIO.read(getClass().getResource("resources/smiley.png"));
            img = img.getScaledInstance( (int)startBtn.getPreferredSize().getWidth()-20, (int)startBtn.getPreferredSize().getHeight()-20,  java.awt.Image.SCALE_SMOOTH ) ;
            startBtn.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startBtn.setBackground(Color.WHITE);

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(startBtn);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(2, 2));

        mainPanel.add(minesPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        add(mainPanel);

        validate();
        repaint();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
