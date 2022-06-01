import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class JumpKingV2 extends JFrame implements MouseListener, KeyListener {
  JPanel hintergrundScreen;
  Rectangle gameGroundLineRectangle;
  Rectangle playerRectangle;
  final int WINDOW_BREITE = 1440;
  final int WINDOW_HOEHE = 1080;

  final String WELCOME_TEXT = "Herzlichen willkommen zu JumpKing!";
  final String START_INSTRUCTIONS = "SPACE um zu starten!";
  
  final  Color INVIS = new Color(255, 255, 255,140); //Unsichtbare Farbe (Zum Debuggen)

  boolean spielGestartet;
  boolean spielVerloren;

  Timer timerForAnimation = new Timer();

  public JumpKingV2(){
    hintergrundScreen = new JPanel(){
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image bg = Toolkit.getDefaultToolkit().getImage("1.png");
        g.drawImage(bg, 0, 0, null);
        repaint();
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
      player(g);
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
    changePlayerImg();
  }

  public void displayVerloren (Graphics g){
    
  }
  
  public void changePlayerImg (){
    TimerTask timeTaskToChangeImg = new TimerTask(){
      int i = 0;
      public void run(){
        i ++;
        switch (i){
          case 1:
            System.out.println(i);
            //setze bild 1
            break;
          case 2:
            //setze bild 2
            System.out.println(i);
            break;
          case 3:
            //setze bild 3
            System.out.println(i);
            break;
          default:
            i = 0;
            System.out.println(i);
            break;
        }
      }
    };
    timerForAnimation.scheduleAtFixedRate(timeTaskToChangeImg, 0, 1000); //1000ms = 1sec
  }
  
  public void player(Graphics g) {
    g.setColor(INVIS);
    g.fillRect(playerRectangle.x,playerRectangle.y,93, 103);
  }
  
  public void playerMovement(int geschwindigkeit) {
    playerRectangle.x -= geschwindigkeit;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int tastendruck=e.getKeyCode();
    
    if (tastendruck==KeyEvent.VK_SPACE) {   //Spiel starten, wenn Leertaste gedrückt wird UND Spiel nicht gestartet ist
      if(!spielGestartet) {
        spielGestartet = true;
      }
    } 
    
    if (tastendruck==KeyEvent.VK_LEFT) {
      playerMovement(5);
    } // end of if
    
    if (tastendruck==KeyEvent.VK_RIGHT) {
      playerMovement(-5);
    } // end of if
     
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
