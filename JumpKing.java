import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JPanel;
   

import javax.swing.*;   

import java.net.URL;             


import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;                           
/*
* JumpKing
*
* Autor: Luis Bauer
* Erstelldatum: 21.05.2022
*
*Quellen:
* 
*  -Implemetierung vom Hintergrundbild (verändert): https://stackoverflow.com/questions/12082660/background-image-for-simple-game
*  -
*  -
*  -
*  -
*  -               
*/
   
public class JumpKing extends JFrame implements  MouseListener, KeyListener {
  
  //Attribute 
  JPanel grafik;
  Rectangle ground, player;
  Random rand = new Random();
  JLabel L1;
  
  //Image background;

      
  private JFrame frame;
  private ImageIcon carIcon;
   
  public Image background;
  public Image bg;  
  
  
  int takt, punkte, highscore, timer;
  final int breite = 1440, hoehe = 1080;
  final int START_TEMPO = 1;
  int vxBall, vyBall, sprungmin=28; 
  int tempo = START_TEMPO;                                                                                               
  boolean gestartet;
  boolean verloren, keyhold, space, left, right;
  final Color HINWEIS = new Color(102,0,153);
  double faktor=1.2,height;
  
  

  
  public JumpKing() //Konstruktor
  { 
    
    
    
    
    
    
    Timer zaehler = new Timer(20,ae -> doTimerTick());   // Timer: Alle 20ms wird doTimerTick aufgerufen
    
    grafik = new JPanel() {      // Auf dieses Panel wird das Spiel gerendert
      protected void paintComponent(Graphics g){       // Wird von Swing aufgerufen um ein Neuzeichnen zu veranlassen
        
        
        super.paintComponent(g);    
        Image bg = Toolkit.getDefaultToolkit().getImage("D:\\Luis\\Desktop\\JumpKing\\1.png");      
        
        
        g.drawImage(bg, 0, 0, null);
        
        
        
        Image idle = Toolkit.getDefaultToolkit().getImage("D:\\Luis\\Desktop\\JumpKing\\idle.png");      
        
        
        g.drawImage(idle, player.x, player.y, null);
        
        
        
        
        
        
        JumpKing.this.paintPanel(g);    //  Aufruf der eigentlichen Zeichnenoperation
      }
    };
    
    add(grafik);
    
    
    
    
    
    setSize(1440, 1080);
    setTitle("JumpKing");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1455, 1118);
    
    /*
    carIcon = new ImageIcon(this.getClass().getResource(""));
    grafik
    */
    
    
    
    
    
    
    
    
    
    
    ground = new Rectangle(0,983, 2000, 1) ;   // Speichert die Positionsdaten des Grounds (ground =983, bzw.783!!!)
    player =  new Rectangle(100,200, 93, 103) ;   // Speichert die Positionsdaten des Spielers
    
    
    
    initGame();
    
    addMouseListener(this);
    addKeyListener(this);
    setResizable(true);
    //setExtendedState(JFrame.MAXIMIZED_BOTH); 
    //setUndecorated(true);
    
    setVisible(true);
    
    
    
    
    zaehler.start();  // Timer starten
  }
    
    // Anfangswerte beim Neustart
  private void initGame() {
    //ground.x=90; ground.y = hoehe-50 ;
    player.x=breite/2; player.y = hoehe/2 ;
    verloren = false;
    ///vxBall = 3 + rand.nextInt(7);    // Ball soll mit Zufallswerten nach oben fliegen
    //vyBall = -(3 + rand.nextInt(7));
    tempo = START_TEMPO;
    punkte = 0;
    takt = 0;
  }
    
    // Operation zum Neuzeichen der grafischen Benutzeroberfläche
  public void paintPanel(Graphics g){
    
    // Ball und Paddle zeichnen (wird immer gemacht)
    
    
    
    
    
    
    
    Color invis = new Color(255, 255, 255,70);
    
    g.setColor(Color.blue);
    g.fillRect(0,983, 2000, 1);
    
    
    
    
    
    g.setColor(invis);
    g.fillRect(player.x,player.y,93, 103);

    
    g.setColor(Color.RED);
    
    
    
    
    
    
    
    
    // Vor Programmstart
    if (!gestartet && !verloren ) {
      g.setColor(Color.RED);
      g.setFont(new Font("Arial",1,50));
      g.drawString("Herzlichen willkommen zu BounceBall!", 150, hoehe/2-350);
      g.setColor(Color.white);
      g.setFont(new Font("Arial",1,50));
      g.drawString("SPACE um zu starten!", 120, hoehe/2-100);
    }
    
    //Während des Spielens
    if (!verloren && gestartet) {
      //Aktueller Score
      g.setFont(new Font("Arial",1,50));                  
      g.setColor(Color.WHITE);
      g.drawString(String.valueOf(punkte),breite/2-25,100);
      
      //Highscore Farb- und Schriftart-gebung
      g.setColor(Color.MAGENTA);
      g.setFont(new Font("Arial",1,30)); 
      
      //Highscore Rechnung und Ausgabe 
      if (punkte>highscore) {
        highscore = punkte;
      } 
      g.drawString("Highscore: " + highscore + " Hits! Zeit: "+ takt/50 + " "+ " Tempo:"+tempo, 20, 40);
    }
    
    // Der Spieler hat verloren
    if (verloren) {
      //Ausgaben der Punkte und "Verloren" wenn das Spiel verloren ist
      g.setFont(new Font("Arial",1,40));
      g.setColor(Color.RED);
      g.drawString("Verloren!",breite/2-50,100);
      
      g.setColor(Color.MAGENTA);
      g.setFont(new Font("Arial",1,25));
      
      /* Ausgaben, welche im Abschlussbildschirm erfolgen und die jeweiligen Punktestände anzeigen, sowie auch
      *  Hinweise was geschieht wenn der Spieler nicht erneut spielen möchte */ 
      g.setColor(Color.WHITE);
      g.drawString("Diese Runde bewältigt: "+punkte+" Hits!", breite/2-100, 200); 
      g.setColor(Color.MAGENTA);
      g.drawString("Aktueller Highscore: "+highscore+" Hits!", breite/2-100, 300);
      
      g.setColor(HINWEIS);
      g.drawString("Um Ihren Highscore zurückzusetzten, drücken Sie Q", 30, 400);
      g.setColor(Color.WHITE);
      g.drawString("SPACE um erneut zu spielen!", 30, 450);
      g.setColor(Color.BLACK);
      g.drawString("Achtung: Beim Schließen des Programms geht jeglicher Spielfortschritt verloren!", 30, 500);
    } 
  }
    
    // Hier wird bei jedem Timer Tick das Szenario neu berechnet und ein Neuzeichnen veranlasst
  private void doTimerTick(){
    takt = takt +1;
    
    
    
    if (gestartet) {
      player.y=player.y+10;
    }
    
    
    if (gestartet) {
      if(player.y <= 0) {      
        
        //Spieler geht oben raus => neues Level laden (Screen)
        
      } else if (player.x <= 0 || player.x >= breite-player.width) {
          
          //Spieler prallt gegen Wand ab (waagrecht)
          vxBall = -vxBall; 
          System.out.println("Wand");
        } else if (player.intersects(ground)) {
            
            //System.out.println("Paddle"+player.y);
            player.y = ground.y - player.height;   // Refelxion an dem Paddle  (vy invertieren) und Punkte hochzählen
            vyBall = vyBall;
            punkte = punkte +1;
            
            //Spieler berüht Boden nichts machen iguess  
            
          } else if (player.y >= hoehe) {
              //Ball geht unten ins aus => voheriges Lvl laden
            } else if (left==true && space==true) {
                
              }
      
      
      
      //System.out.println(vxBall+vyBall);
      player.x += vxBall;  // Neue Ballposition berechen  
      player.y += vyBall;
      
      grafik.repaint();
    }
    
    
    
  }
    
  public void resetHighscore(){
    highscore=0;
    punkte=0;
    repaint();
  }
    
    //Mainmethode
  public static void main(String[] args){
   new JumpKing();
  }
    
    //Maus- und Key-Listener Maus und Tastendruckerkennung
  @Override
  public void mouseClicked(MouseEvent e){
    
  }
    
  @Override
  public void keyReleased(KeyEvent e){
    
    double g=1.5;
    if (e.getKeyCode()==KeyEvent.VK_SPACE) {   
      if(!gestartet) {
        initGame();
        gestartet = true;
        
        
      }
      
    }  else if (e.getKeyCode()==KeyEvent.VK_Q) {  // Highscore löschen unnötig
        resetHighscore(); 
      }
    
    if (e.getKeyCode()==KeyEvent.VK_SPACE && player.y >= 880 ) {
      
      //Spieler springt (nach oben, rechts,links)
      
     // System.out.println(timer);
      if (timer > 20) {
        timer=20;
      } // end of if
      
      if (timer<=5) {
        timer=5;
        g=5;
        
      } // end of if
      
      if (timer<=100) {
        
      } // end of if
      
      height=timer*20;
      if (left==true) {    //Spieler springt nach links
        
        
        player.x-=sprungmin+(height*faktor);
        
        
        
        
        
        
        System.out.println("Links");
        
      }else if (right==true) {        //Spieler springt nach Rechts
          player.x+=100; 
          System.out.println("Rechts");
          
        }
      
      player.y = player.y-timer*20;  
      
      timer=0;
      left=false;
      right=false;
      System.out.println("Reset");
      
      
      
    } // end of if
    
    
    
    
    System.out.println("Y:"+player.y);
    
    space=false;
  }
    
    
  @Override
  public void mousePressed(MouseEvent e){
  }
    
  @Override
  public void mouseReleased(MouseEvent e){
  }
    
  @Override
  public void mouseEntered(MouseEvent e){
  }
    
  @Override
  public void mouseExited(MouseEvent e){
  }
    
  @Override
  public void keyTyped(KeyEvent e){ 
  }
    
  @Override
  public void keyPressed(KeyEvent e){  
    
    
    
    
    if(e.getKeyCode() == KeyEvent.VK_SPACE && player.y >= 880){
      space=true;
      
      System.out.println("Y:"+player.y);
      //timer starten
      timer++;
      
    } 
    
    
    if(e.getKeyCode() == KeyEvent.VK_LEFT && player.y >= 880 ){
      
      
      
      
      left=true;
      
      
      if (space==false) {
        player.x = player.x-10;      // Spieler nach links bewegen
      } // end of if
      
      
      System.out.println("left");
      
    } else {
      
      if(e.getKeyCode() == KeyEvent.VK_RIGHT && player.y >= 880){
        
        if (space==false) {
          player.x = player.x+10;    // Spieler nach rechts bewegen
        } // end of if
        
        
        right=true;
      }
    }// end of if-else
    
    
    
  }  
}