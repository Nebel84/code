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
import javax.sql.rowset.CachedRowSet;
import javax.swing.*;   

import java.net.URL;             


import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;        

import java.io.*;
import javax.sound.sampled.*;
/*
* JumpKing
*
* Autor: Luis Bauer
* Erstelldatum: 21.05.2022
*
*Quellen:
* 
*  -Implemetierung vom Hintergrundbild (ver�ndert): https://stackoverflow.com/questions/12082660/background-image-for-simple-game
*  -
*  -
*  -
*  -
*  -               
*/
   
public class JumpKing extends JFrame implements  MouseListener, KeyListener {
  
  //Attribute 
  JPanel grafik;
  Rectangle ground, player, wall, ground1, ground2;
  Random rand = new Random();
  JLabel L1;
  
  //Image background;

      
  
   
  public Image background;
  public Image bg;  
  
  
  int takt, punkte, highscore, timer,height, g, j, k;
  final int breite = 1440, hoehe = 1080;
  final int START_TEMPO = 1;
  int vxBall, vyBall, sprungmin=28; 
  int tempo = START_TEMPO;                                                                                               
  boolean gestartet, faceright, faceleft, fall,ignoreground, isonground, ignoreground1;
  boolean verloren, keyhold, space, left, right1, idle;
  final Color HINWEIS = new Color(102,0,153);
  double faktor=1.2;
  
  
  
  
  public JumpKing() //Konstruktor
  { 
     
    
    
    Timer zaehler = new Timer(10,ae -> doTimerTick());   // Timer: Alle 20ms wird doTimerTick aufgerufen
    
    grafik = new JPanel() {      // Auf dieses Panel wird das Spiel gerendert
      protected void paintComponent(Graphics g){       // Wird von Swing aufgerufen um ein Neuzeichnen zu veranlassen
        
        
        super.paintComponent(g);    
        Image bg = Toolkit.getDefaultToolkit().getImage("pictures\\backgrounds\\1.png");      
        
        
        g.drawImage(bg, 0, 0, null);
        
        
        if (space==false && right1==false && left==false && fall==false && faceleft==false) {

          
          
          Image idle = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idle.png");      
          
          
          g.drawImage(idle, player.x, player.y, null);

          

        }else if (space==false && right1==false && left==false && fall==false && faceright==false ) {
          
          Image idlel = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idlel.png");      
          
          
          g.drawImage(idlel, player.x, player.y, null);

        }else if (space==false && right1==true && left==true && fall==false) {
          
          if (faceleft==false) {
            
          Image idle = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idle.png");      
          
          
            g.drawImage(idle, player.x, player.y, null);

          }else if (faceright==false) {
            
            Image idlel = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idlel.png");      
          
          
            g.drawImage(idlel, player.x, player.y, null);


          }

        }
        
        
        
        
        
        
        
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
    ground1 = new Rectangle(0,550, 385, 1) ;

    player =  new Rectangle(100,200, 93, 103) ;   // Speichert die Positionsdaten des Spielers
    wall   = new Rectangle(385,550, 1, 434);
    
    
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
    
    
    left=false;
    right1=false;
    space=false;
    
    
    
    vxBall = 0 ;    // Ball 
    vyBall = 0 ;
    tempo = START_TEMPO;
    punkte = 0;
    takt = 0;
  }
    
    // Operation zum Neuzeichen der grafischen Benutzeroberfl�che
  public void paintPanel(Graphics g){
    
    // Ball und Paddle zeichnen (wird immer gemacht)
    
    
    
    
    
    
    
    Color invis = new Color(255, 255, 255,140);
    

    //Erste Stage:

    //ground
    g.setColor(Color.blue);
    g.fillRect(0,983, 2000, 1);
    
    //player
    g.setColor(invis);
    g.fillRect(player.x,player.y,93, 103);
    
    //wall
    g.setColor(Color.blue);
    g.fillRect(385,550, 1, 434);

    //Ground1

    g.setColor(Color.blue);
    g.fillRect(0,550, 385, 1);



    
    
    
    
    
    
    
    
    
    // Vor Programmstart
    if (!gestartet && !verloren ) {
      g.setColor(Color.RED);
      g.setFont(new Font("Arial",1,50));
      g.drawString("Herzlichen willkommen zu JumpKing!", 150, hoehe/2-350);
      g.setColor(Color.white);
      g.setFont(new Font("Arial",1,50));
      g.drawString("SPACE um zu starten!", 120, hoehe/2-100);
    }
    
    //W�hrend des Spielens
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
      
      /* Ausgaben, welche im Abschlussbildschirm erfolgen und die jeweiligen Punktest�nde anzeigen, sowie auch
      *  Hinweise was geschieht wenn der Spieler nicht erneut spielen m�chte */ 
      g.setColor(Color.WHITE);
      g.drawString("Diese Runde bew�ltigt: "+punkte+" Hits!", breite/2-100, 200); 
      g.setColor(Color.MAGENTA);
      g.drawString("Aktueller Highscore: "+highscore+" Hits!", breite/2-100, 300);
      
      g.setColor(HINWEIS);
      g.drawString("Um Ihren Highscore zur�ckzusetzten, dr�cken Sie Q", 30, 400);
      g.setColor(Color.WHITE);
      g.drawString("SPACE um erneut zu spielen!", 30, 450);
      g.setColor(Color.BLACK);
      g.drawString("Achtung: Beim Schlie�en des Programms geht jeglicher Spielfortschritt verloren!", 30, 500);
    }   
    
    
    int animation=player.x %100;
    
    

    if (right1==true && space==false && left==false && isonground==true) {
      
      //System.out.println("ani:"+animation);
      faceright=true;
      faceleft=false;
      switch (animation){
        case 0:
          
        Image run1 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1.png");      
        
        
        g.drawImage(run1, player.x, player.y, null);
          //setze bild 1
          break;

        case 10:


        Image run2 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2.png");      
        
        
        g.drawImage(run2, player.x, player.y, null);
          //setze bild 2
          
          break;

        case 20:

        Image run3 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3.png");      
        
        
        g.drawImage(run3, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 30:

        run1 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1.png");      
        
        
        g.drawImage(run1, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 40:

        run2 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2.png");      
        
        
        g.drawImage(run2, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 50:


        run3 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3.png");      
        
        
        g.drawImage(run3, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 60:

        run1 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1.png");      
        
        
        g.drawImage(run1, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 70:


        run2 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2.png");      
        
        
        g.drawImage(run2, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 80:


        run3 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3.png");      
        
        
        g.drawImage(run3, player.x, player.y, null);
          //setze bild 3
          
          break;
        
        case 90:

        run1 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1.png");      
        
        
        g.drawImage(run1, player.x, player.y, null);
          //setze bild 3
          
          break;


        default:
          
          break;
      }
    
    
          
  
      
    }   


    if (left==true && space==false && right1==false && isonground==true) {
      
      //System.out.println("aniLeft:"+animation);
      faceleft=true;
      faceright=false;

      switch (animation){
        case 0:
          
        Image run1l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1l.png");      
        
        
        g.drawImage(run1l, player.x, player.y, null);
          //setze bild 1
          break;

        case 10:


        Image run2l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2l.png");      
        
        
        g.drawImage(run2l, player.x, player.y, null);
          //setze bild 2
          
          break;

        case 20:

        Image run3l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3l.png");      
        
        
        g.drawImage(run3l, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 30:

        run1l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1l.png");      
        
        
        g.drawImage(run1l, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 40:

        run2l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2l.png");      
        
        
        g.drawImage(run2l, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 50:


        run3l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3l.png");      
        
        
        g.drawImage(run3l, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 60:

        run1l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1l.png");      
        
        
        g.drawImage(run1l, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 70:


        run2l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2l.png");      
        
        
        g.drawImage(run2l, player.x, player.y, null);
          //setze bild 3
          
          break;

        case 80:


        run3l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3l.png");      
        
        
        g.drawImage(run3l, player.x, player.y, null);
          //setze bild 3
          
          break;
        
        case 90:

        run1l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1l.png");      
        
        
        g.drawImage(run1l, player.x, player.y, null);
          //setze bild 3
          
          break;


        default:
          
          break;
      }
    
    
          
  
      
    }   
        
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      //System.out.println("rechts"+faceright);
      //System.out.println("links"+faceleft);
      
      
      
      
      
      
    
    
    if (space==true) {
      Image squat = Toolkit.getDefaultToolkit().getImage("pictures\\player\\squat.png");      
      
      
      g.drawImage(squat, player.x, player.y, null);
      
      
      
    }
    
    
    if(isonground==false ) { //player ist in Luft (muss verbessert werden) zb if player is not on ground 


      


       


        
      
      




      fall=true;

      if (faceright==true) {
      Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\fall.png");      
      
      
      g.drawImage(fall, player.x, player.y, null);
 
      }else if(faceleft==true){
        
      Image falll = Toolkit.getDefaultToolkit().getImage("pictures\\player\\falll.png");      
      
      
      g.drawImage(falll, player.x, player.y, null);

      }else{
        //für den Fall: spiel gestartet & faceright/left sind beide false
        Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\fall.png");      
      
      
        g.drawImage(fall, player.x, player.y, null);

      }
      
    }else{

      fall=false;
    }
    
  }
  

int i=0;
  
    // Hier wird bei jedem Timer Tick das Szenario neu berechnet und ein Neuzeichnen veranlasst


  private void doTimerTick(){
    takt = takt +1;
    
    if (space==true) {
      timer++;
    }else {
      timer=0;
    } // end of if-else
    
    
    
    
    
    
    
    
    
    
    
    
    if (gestartet) {
       g++;
       player.y += g;
       if (vyBall > 0) {
         vyBall-=1;
       }

       
      
      if(player.y <= 0) {      
        
        //Spieler geht oben raus => neues Level laden (Screen)
        
      } 
      
          if (player.intersects(ground)) {
            
            g=0;
            //System.out.println("Paddle"+player.y);
            player.y = ground.y - player.height;   // basically nichts
            //vxBall=0;
            //vyBall=0;
            punkte = punkte +1;
            
            if (ignoreground==false) {


              vxBall=0;
              //System.out.println("NOOOO");
            }else if (ignoreground==true) {
              i++;
            }

            if (i>2) {
              ignoreground=false;
              
              i=0;
            }


            if (isonground==false) {
              i++;
            }

            if (i>2) {
              isonground=true;
              i=0;
            }
              
            

           
            //Spieler ber�ht Boden nichts machen iguess  
            
          }else{

           


          }
          
          if (player.intersects(wall)) {
            isonground=false;
            vxBall= -vxBall;

            do {
              
            player.x = wall.x + 5;

            }while (player.intersects(wall));

            System.out.println("Wall");
        
          }
          
          if (player.intersects(ground1)) {
            
            g=0;
            
            
            
            System.out.println(ignoreground);
           
            if (ignoreground==false) {

              
              vxBall=0;
              //System.out.println("NOOOO");
            }else if (ignoreground==true) {
              k++;
            }

            if (k>2) {
              ignoreground=false;
              
              k=0;
            }


            if (isonground==false) {
              j++;
            }
            
            if (j>2) {
              isonground=true;
              j=0;
            }
            
            
          

           
            player.y = ground1.y - player.height;
            

            
          }else{

           

        

          }
          
          
          
          
       
      if (player.y >= hoehe) {
              //Ball geht unten ins aus => voheriges Lvl laden
            } 
      
      //System.out.println("Vyball: "+vyBall);
      player.x += vxBall;  // Neue Ballposition berechen  
      player.y -= vyBall;
      //System.out.println("Vxball: "+vxBall);
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
    
    
    //System.out.println(right1);
    //System.out.println(left);
    
    
    
    if (e.getKeyCode()==KeyEvent.VK_RIGHT) {   
      right1=false;
    }
    
    if (e.getKeyCode()==KeyEvent.VK_LEFT) {   
      left=false;
      
      
    }
    
    
    
    if (e.getKeyCode()==KeyEvent.VK_SPACE) {   
      if(!gestartet) {
        initGame();
        gestartet = true;
        
        
      }
      
      
      
    }  
    
    if (e.getKeyCode()==KeyEvent.VK_SPACE && isonground==true ) {
      



      try
      {
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(new File("sounds\\jump.wav")));
      clip.start();
      }
      catch (Exception exc){
      exc.printStackTrace(System.out);
      }


      
      //Spieler springt (nach oben, rechts,links)
      
      System.out.println(timer);
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
        //
        
        //player.x-=sprungmin+(height*faktor);
        vxBall= -timer/2;
        ignoreground=true;
        ignoreground1=true;
        System.out.println("Links");
        
      }
      //          100
      if (right1==true) {        //Spieler springt nach Rechts
        //player.x+=sprungmin+(height*faktor); 
        vxBall= timer/2;
        ignoreground=true;
        ignoreground1=true;
        System.out.println("Rechts");
        
      }
      
      
      
      //player.y = player.y-timer*20;  
      vyBall=timer*2+5;
      

      timer=0;
      left=false;
      right1=false;
      
      
      //System.out.println("Reset");  
      
      
      
      
      isonground=false;
      
    } // end of if
    
    
    
    
    //System.out.println("Y:"+player.y);
    
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
    
    
    
    
    if(e.getKeyCode() == KeyEvent.VK_SPACE && isonground==true){
      space=true;
      
      //System.out.println("Time"+timer);
      //timer starten
      //timer++;
      
    } 
    
    
    if(e.getKeyCode() == KeyEvent.VK_LEFT && isonground==true && right1==false ){
      
      
      
      
      left=true;
      
      
      if (space==false) {
        player.x = player.x-10;      // Spieler nach links bewegen
        ///left=false;
        
      } // end of if
      
      
      //System.out.println("left");
      
    } 
    
    if(e.getKeyCode() == KeyEvent.VK_RIGHT && isonground==true && left==false ){
      
      
      right1=true;
      System.out.println(player.x);
      
      if (space==false) {
        player.x = player.x+10;    // Spieler nach rechts bewegen
        //right=false;
        
      } else{

         //System.out.println("right");
      }
      
      
     
    }
    
    
    
  }  
}