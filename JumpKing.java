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
*  -Hintergrundbilder (leicht verändert)          : https://github.com/cohancarpentier/jump-king-map 
*  -Implemetierung vom Hintergrundbild (verändert): https://stackoverflow.com/questions/12082660/background-image-for-simple-game
*  -Implemetierung von Sounds                     : https://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java
*  -
*  -
*  -
*  -               
*/
   
public class JumpKing extends JFrame implements  MouseListener, KeyListener {
  
  //Attribute 
  JPanel grafik;
  Rectangle ground, player, wall, ground1, ground2;  //Hier werden Wände und Böden deklariert
  
  public Image background, background1;              //Hier werden die Bilder deklariert (eig. unnötig) => besprechen!
   
  
  
  int takt, punkte, highscore, timer,height, g, j, k, i;
  final int breite = 1440, hoehe = 1080;                    //final heißt kann nicht mehr verändert werden
  final int START_TEMPO = 1;
  int vxBall, vyBall, sprungmin=28; 
  int tempo = START_TEMPO;                                                                                               
  boolean gestartet, faceright, faceleft, fall, isonground, absprung;
  boolean verloren, keyhold, space, left, right1, idle;
  final Color HINWEIS = new Color(102,0,153);
  double faktor=1.2;
  
  
  
  
  public JumpKing() //Konstruktor
  { 
     
    
    
    Timer zaehler = new Timer(10,ae -> doTimerTick());   // Timer: Alle 20ms wird doTimerTick() aufgerufen
    
    grafik = new JPanel() {      // Auf dieses Panel wird das Spiel gerendert
      protected void paintComponent(Graphics g){       // Wird von Swing aufgerufen um ein Neuzeichnen zu veranlassen
        
          
        Image background = Toolkit.getDefaultToolkit().getImage("pictures\\backgrounds\\1.png");     //Bild laden
        g.drawImage(background, 0, 0, null);                                                    //Bild ausgeben g.drawImage([name], [x], [y], [observer=null!])
        
        
        if (space==false && right1==false && left==false && fall==false && faceleft==false) {                 //Wenn Spieler nicht nach links/[rechts] schaut oder bewegt, sich nicht duckt/fällt => Spieler steht einfach da, schaut nach rechts (idle)

          Image idle = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idle.png");      
          g.drawImage(idle, player.x, player.y, null);

       
        }else if (space==false && right1==false && left==false && fall==false && faceright==false ) {         //Wenn Spieler nicht nach [links]/rechts schaut oder bewegt, sich nicht duckt/fällt => Spieler steht einfach da, schaut nach links (idlel)
          
          Image idlel = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idlel.png");      
          g.drawImage(idlel, player.x, player.y, null);

          
        }else if (space==false && right1==true && left==true && fall==false) {                                //Wenn Spieler nicht nach links/ UND rechts bewegt, sich nicht duckt/fällt 
          
          if (faceleft==false) {
            
          Image idle = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idle.png");      
          g.drawImage(idle, player.x, player.y, null);


          }else if (faceright==false) {
            
            Image idlel = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idlel.png");      
            g.drawImage(idlel, player.x, player.y, null);


          }

        }
        
        
        
        
        
        
        
        JumpKing.this.paintPanel(g);      //  Aufruf der eigentlichen Zeichnenoperation
      }
    };
    
    add(grafik);     //Ka 
    
    
    
    
    
    setSize(1440, 1080);      //eig richtige Größe, wird unten überschrieben
    setTitle("JumpKing");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1455, 1118);    //überschrieben weil, vermutlich zählt oben in GUI die weiße Zeile mit als size
    


    player =  new Rectangle(100,200, 93, 103) ;   // Speichert die Positionsdaten des Spielers
    
    //Böden   // Speichert die Positionsdaten der Böden 
    ground = new Rectangle(0,983, 2000, 1) ;   
    ground1 = new Rectangle(0,550, 385, 1) ;

    //Wände  // Speichert die Positionsdaten der Wände 
    wall   = new Rectangle(385,550, 1, 434);
    
    
    initGame();    //Aufruf von der Initialisierung

    //basic Sachen (Grundgerüst)
    addMouseListener(this);
    addKeyListener(this);
    setResizable(true);
    setVisible(true);
    
    
    
    
    zaehler.start();  // Timer starten für timer tick
  }
    
    
  private void initGame() {         // Anfangswerte beim Neustart
    

    player.x=breite/2; player.y = hoehe/2 ; //Spieler startet in der Mitte des Screens
    verloren = false; 
    
    
    left=false;
    right1=false;
    space=false;
    
    
    
    vxBall = 0 ;    // Geschwindigkeit (X-Achse) des Spielers 
    vyBall = 0 ;    // Geschwindigkeit (Y-Achse) des Spielers 
    
    punkte = 0;
    takt = 0;
  }
    
    // Operation zum Neuzeichen der grafischen Benutzeroberfl�che
  public void paintPanel(Graphics g){
    
    // Ball und Paddle zeichnen (wird immer gemacht)
    
    
    
    
    
    
    
    Color invis = new Color(255, 255, 255,140); //Unsichtbare Farbe (Zum Debuggen)
    

    //Erste Stage:

    //Dient nur zum Debuggen! Kann eig alles weg, trotzdem da lassen



    //Player
    g.setColor(invis);
    g.fillRect(player.x,player.y,93, 103);

    //Böden

    //Boden0
    g.setColor(Color.blue);
    g.fillRect(0,983, 2000, 1);
    

    //Boden1
    g.setColor(Color.blue);
    g.fillRect(0,550, 385, 1);

    
    
    //Wände

    //Wand0
    g.setColor(Color.blue);
    g.fillRect(385,550, 1, 434);

    

    //Für später wichtig: (von Hr. Herburger)

    ///////////////VON HIER********************

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

    /////////////BIS HIER**********************
    
    //Animationen:
    //Spieler hat einen x-Wert (4stellig) (player.x= ????)

    int animation=player.x %100;   //nun wird er auf seine letzten 2 Stellen gekürzt
    
    
    //Nun wird überprüft welchen Wert die 2 Stellen (0-99) haben 

    //Nach Rechts laufen: Zu jedem X Wert, ein neues Bild
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

    //Nach Links laufen: Zu jedem X Wert, ein neues Bild
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
        
     
 
    
    if (space==true) {     //Wenn space gedrückt wird => duckt sich der Player

      Image squat = Toolkit.getDefaultToolkit().getImage("pictures\\player\\squat.png");      
      g.drawImage(squat, player.x, player.y, null);

    }
    


    
    if(isonground==false ) { //player ist in Luft => Fallanimation (bzw. Springanimation)

      fall=true;
      if (faceright==true) {

      Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\fall.png");      
      g.drawImage(fall, player.x, player.y, null);
 
      }else if(faceleft==true){
        
      Image falll = Toolkit.getDefaultToolkit().getImage("pictures\\player\\falll.png");      
      g.drawImage(falll, player.x, player.y, null);

      }else{

        //für den Fall: Spiel gestartet & faceright/left sind beide false
        Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\fall.png");      
        g.drawImage(fall, player.x, player.y, null);

      }
      
    }else{
      fall=false;
    }
    
  }
  

  private void doTimerTick(){      // Hier wird bei jedem Timer Tick das Szenario neu berechnet und ein Neuzeichnen veranlasst

    

    takt = takt +1;       //Für die Zeitmessung wie lange man spielt 
    
    if (space==true) {    //Timer für die Zeit wie lange man leertaste hält
      timer++;
    }else {
      timer=0;
    } // end of if-else
    
    
    
    if (gestartet) {

       g++;
       player.y += g;   //Schwerkraft (lineare Steigung der Geschwindigkeit)


      if(player.y <= 0) {      
        //Spieler geht oben raus => neues Level laden (Screen)       
      } 


      if (player.intersects(ground1)) {
            
            g=0;
            
            player.y = ground1.y - player.height ;

            if (absprung==true) {
              
            } else {
              vxBall=0;
              vyBall=0;
            }

            isonground=true;
            absprung=false;

            
          }else if (player.intersects(ground)) {
            
           
            g=0;
            //System.out.println("Paddle"+player.y);
            player.y = ground.y - player.height ;   // basically nichts
            //vxBall=0;
            //vyBall=0;
            //punkte = punkte +1;
            
            if (absprung==true) {            //Absprung-Flag: Damit min. 1 dotimeertick() durchgelaufen wird schwierig zu erklären
              
            } else {
              vxBall=0;
              vyBall=0;
            }

            isonground=true;
            absprung=false;
           
          }else{
           isonground=false;
          }


          if (player.intersects(wall)) {        //Wand Kollision

            isonground=false;             
            vxBall= -vxBall;                    //Vx invertieren

            do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
              
            player.x = wall.x + 5;  

            }while (player.intersects(wall));

            System.out.println("Wall");
        
          }
      
       
      if (player.y >= hoehe) {
          //Ball geht unten ins aus => voheriges Lvl laden
          } 
      
      
      player.x += vxBall;  // Neue Ballposition berechen  
      player.y -= vyBall;
      
      grafik.repaint();   //Neuzeichnen
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
    
    
   
    if (e.getKeyCode()==KeyEvent.VK_RIGHT) {   //Wenn Rechts losgelassen wird nicht springen!
      right1=false;
      //space=false;
    }
    
    if (e.getKeyCode()==KeyEvent.VK_LEFT) {    //Wenn Links losgelassen wird nicht springen!
      left=false;
      //space=false;
      
    }
    
    
    
    if (e.getKeyCode()==KeyEvent.VK_SPACE) {   //Spiel starten, wenn Leertaste gedrückt wird UND Spiel nicht gestartet ist

      if(!gestartet) {
        initGame();
        gestartet = true;
        
      }
      
    }  
    
    if (e.getKeyCode()==KeyEvent.VK_SPACE && isonground==true ) {   //Player springt
      



      try                                                                      
      { 
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(new File("sounds\\jump.wav")));    //Sound abspielen   
      clip.start();
      }
      catch (Exception exc){
      exc.printStackTrace(System.out);
      }


      
     
      
      // System.out.println(timer);

      //Timer (wie lange leertaste gedrückt wurde) wird umgerechent (limitiert, minimiert)

      if (timer > 20) {
        timer=20;
      }else if (timer<=5) {
        timer=5;
        g=5;
        
      } // end of if


      //Hight wird noch nicht benutzt (für später)
      height=timer*20;
      
      
      if (left==true) {    //Spieler springt nach links

        vxBall= -timer/2;   //Berechnung der X-Geschwindigkeit minus, da nach links
        System.out.println("Links");
        
      }


      if (right1==true) {        //Spieler springt nach Rechts
        
        vxBall= timer/2;     //Berechnung der X-Geschwindigkeit plus, da nach links
        System.out.println("Rechts");
        
      }
      

      vyBall=timer*2;   //Berechnung der Y-Geschwindigkeit 

      timer=0;          //Werte werden resetet
      absprung=true;
      left=false;
      right1=false;
      isonground=false;
      
    } // end of if
 
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
    
    
    
    
    if(e.getKeyCode() == KeyEvent.VK_SPACE && isonground==true){          //Space wird gedrückt
      space=true;
    } 
    
    
    if(e.getKeyCode() == KeyEvent.VK_LEFT && isonground==true && right1==false ){   //Left_Arrow Key wird gedrückt

      left=true;
      
      if (space==false) {            //Spieler nach links bewegen ohne Sprung, wenn leertaste nicht gedrückt   

        player.x = player.x-10;      //Sollte Verbessert werden z.B. vxBall -= 10;

      } // end of if
      
      
      
    } 
    
    if(e.getKeyCode() == KeyEvent.VK_RIGHT && isonground==true && left==false ){   //Right_Arrow Key wird gedrückt 
      
      
      right1=true;
      System.out.println(player.x);
      
      if (space==false) {          //Spieler nach rechts bewegen ohne Sprung, wenn leertaste nicht gedrückt   

        player.x = player.x+10;    //Sollte Verbessert werden z.B. vxBall -= 10;

        
      } // end of if


    }
    
  }  

}