import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class JumpKingV2 extends JFrame implements MouseListener, KeyListener {

    JPanel hintergrundScreen;
    Rectangle gameGroundLineRectangle;
    Rectangle playerRectangle;
    final int WINDOW_BREITE = 1440;
    final int WINDOW_HOEHE = 1080;

    final String WELCOME_TEXT = "Herzlichen willkommen zu JumpKing!";
    final String START_INSTRUCTIONS = "SPACE um zu starten!";

    boolean spielGestartet;
    boolean spielVerloren;

    public JumpKingV2(){
        hintergrundScreen = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image bg = Toolkit.getDefaultToolkit().getImage("1.png");
                g.drawImage(bg, 0, 0, null);
                JumpKingV2.this.checkGameStatus(g);
            }
        };

        add(hintergrundScreen);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_BREITE, WINDOW_HOEHE);

        gameGroundLineRectangle = new Rectangle(0,983, 2000, 1) ;   // Speichert die Positionsdaten des Grounds (ground =983, bzw.783!!!)
        playerRectangle =  new Rectangle(100,200, 93, 103) ;   // Speichert die Positionsdaten des Spielers

        addMouseListener(this);
        addKeyListener(this);
        setResizable(true);
        setVisible(true);// Fenster anzeigen
    }

    public static void main(String[] args){
        new JumpKingV2();
    }

    public void paintGameGrounds(Rectangle[] grounds){

    }

    public void checkGameStatus(Graphics g) {
        if(!spielGestartet && !spielVerloren) {
           displayStartScreen(g);
        }
        if(spielGestartet && !spielVerloren){
            displayRunningGame(g);
        }
        if(spielVerloren){
            displayVerloren(g);
        }
    }

    public void displayStartScreen (Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD,50));
        g.drawString(WELCOME_TEXT, 150, WINDOW_HOEHE/2-350);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD,50));
        g.drawString(START_INSTRUCTIONS, 120, WINDOW_HOEHE/2-100);
    }

    public void displayRunningGame (Graphics g){
    }

    public void displayVerloren (Graphics g){

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}