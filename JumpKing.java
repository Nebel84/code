import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;
/*
 * JumpKing
 *
 * Autoren: Luis Bauer, Tilo Heinz, Benjamin Langlotz, Lucas Sanner;
 * Erstelldatum: 21.05.2022
 *
 *Quellen:
 *  -Hintergrundbilder (leicht verÃ¤ndert)          : https://github.com/cohancarpentier/jump-king-map
 *  -Implemetierung vom Hintergrundbild (verÃ¤ndert): https://stackoverflow.com/questions/12082660/background-image-for-simple-game
 *  -Implemetierung von Sounds                     : https://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java
 *  -
 *  -
 *  -
 *  -
 */


/* Programmierkonvention:
 * -Eigene Ideen > Ideen (Code) aus dem Internet kopieren
 * -Keine Deklaration von Images bei Attributen
 * -Quellen angeben!
 * -
 *
 *
 *
 * */

/*
 *Physics:
 *  -Wenn Spieler gegen Wand spring vxBall invertieren und /2 rechnen (100% richtig)
 *  -
 *  -
 *  -
 *  -
 *  -
 *  -
 */


public class JumpKing extends JFrame implements MouseListener, KeyListener {

    //Attribute
    JPanel grafik;

    //Hier werden WÃ¤nde und BÃ¶den deklariert
    Rectangle[][] wall = new Rectangle[101][101];  //1.Stage

    Rectangle player;

    Rectangle[][] ground = new Rectangle[101][101]; //1. Stage

    boolean[] coinBoolean = new boolean[100];

    Rectangle[] coinRectangle = new Rectangle[100]; //1. Stage


    int takt, punkte, timer, height, k, kontostand;
    int stage = 0;
    final int breite = 1455, hoehe = 1118;                    //final heißt kann nicht mehr verändert werden (muss nd unbedingt sein)
    final int START_TEMPO = 1;
    int vxPlayer, vyPlayer;
    int tempo = START_TEMPO;
    boolean gestartet, faceright, faceleft, fall, isonground, absprung, bevor, bump, dead;
    boolean verloren, space, left, right;
    double  gravity;
    boolean debugger = false;
    int auswahlMenue = 1;
    boolean menu = true;
    boolean shop = false;
    boolean shop1 = false;
    boolean shop2 = false;
    int auswahl = 1;
    final Image jumpBar0 = Toolkit.getDefaultToolkit().getImage("pictures\\Ladebalken\\Ladebalken 0.png");


    public JumpKing() //Konstruktor
    {


        Timer zaehler = new Timer(15, ae -> doTimerTick());   // Timer: Alle 10ms wird doTimerTick() aufgerufen


        grafik = new JPanel() {      // Auf dieses Panel wird das Spiel gerendert
            protected void paintComponent(Graphics g) {       // Wird von Swing aufgerufen um ein Neuzeichnen zu veranlassen

                switch (stage) {
                    case 0:
                        // Hier wird das Menü gemacht
                        switch (auswahlMenue) {             //Hier sind die Auswahlmöglichkeiten für das Menü
                            case 0:
                                auswahlMenue = 3;


                            case 1:
                                Image menu1 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen1.png");
                                g.drawImage(menu1, 0, 0, null);
                                repaint();
                                break;
                            case 2:
                                Image menu2 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen2.png");
                                g.drawImage(menu2, 0, 0, null);
                                repaint();
                                break;

                            case 3:
                                Image menu3 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen3.png");
                                g.drawImage(menu3, 0, 0, null);
                                repaint();
                                break;


                            default:

                        } // end of switch
                        switch (auswahlMenue) {
                            case 4:
                                auswahlMenue = 1;


                            case 1:
                                Image menu1 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen1.png");
                                g.drawImage(menu1, 0, 0, null);
                                repaint();
                                break;
                            case 2:
                                Image menu2 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen2.png");
                                g.drawImage(menu2, 0, 0, null);
                                repaint();
                                break;

                            case 3:
                                Image menu3 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen3.png");
                                g.drawImage(menu3, 0, 0, null);
                                repaint();
                                break;

                            default:

                        } // end of switch

                        break;
                    case 1:

                        Image background1 = Toolkit.getDefaultToolkit().getImage("pictures\\backgrounds\\1.png");     //Bild laden
                        g.drawImage(background1, 0, 0, null);                                                    //Bild ausgeben g.drawImage([name], [x], [y], [observer=null!])


                        if (coinBoolean[0] == false) {
                            Image coin = Toolkit.getDefaultToolkit().getImage("pictures\\coin\\coin.png");
                            g.drawImage(coin, 300, 300, null);
                        }

                        break;
                    case 2:

                        Image background2 = Toolkit.getDefaultToolkit().getImage("pictures\\backgrounds\\2.png");     //Bild laden
                        g.drawImage(background2, 0, 0, null);

                        if (coinBoolean[1] == false) {
                            Image coin = Toolkit.getDefaultToolkit().getImage("pictures\\coin\\coin.png");
                            g.drawImage(coin, 870, 500, null);
                        }

                        break;

                    case 3:

                        Image background3 = Toolkit.getDefaultToolkit().getImage("pictures\\backgrounds\\3.png");     //Bild laden
                        g.drawImage(background3, 0, 0, null);

                        if (coinBoolean[2] == false) {
                            Image coin = Toolkit.getDefaultToolkit().getImage("pictures\\coin\\coin.png");
                            g.drawImage(coin, 930, 500, null);
                        }

                        break;

                    case 4:

                        Image background4 = Toolkit.getDefaultToolkit().getImage("pictures\\backgrounds\\4.png");     //Bild laden
                        g.drawImage(background4, 0, 0, null);

                        if (coinBoolean[3] == false) {
                            Image coin = Toolkit.getDefaultToolkit().getImage("pictures\\coin\\coin.png");
                            g.drawImage(coin, 480, 150, null);
                        }

                        break;

                    case 5:

                        Image background5 = Toolkit.getDefaultToolkit().getImage("pictures\\backgrounds\\5.png");     //Bild laden
                        g.drawImage(background5, 0, 0, null);

                        if (!coinBoolean[4]) {
                            Image coin = Toolkit.getDefaultToolkit().getImage("pictures\\coin\\coin.png");
                            g.drawImage(coin, 490, 100, null);
                        }

                        break;

                    case 6:

                        Image background6 = Toolkit.getDefaultToolkit().getImage("pictures\\backgrounds\\6.png");     //Bild laden
                        g.drawImage(background6, 0, 0, null);

                        if (!coinBoolean[5]) {
                            Image coin = Toolkit.getDefaultToolkit().getImage("pictures\\coin\\crown.png");
                            g.drawImage(coin, 680, 400, null);
                        }

                        break;

                    default:
                        break;
                }

                if (!space && !right && !left && !fall && !faceleft && !dead) {                 //Wenn Spieler nicht nach links/[rechts] schaut oder bewegt, sich nicht duckt/fällt => Spieler steht einfach da, schaut nach rechts (idle)

                    Image idle = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idle.png");
                    g.drawImage(idle, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);

                } else if (!space && !right && !left && !fall && !faceright && !dead) {         //Wenn Spieler nicht nach [links]/rechts schaut oder bewegt, sich nicht duckt/fällt => Spieler steht einfach da, schaut nach links (idlel)
                    Image idlel = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idlel.png");
                    g.drawImage(idlel, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);

                } else if (space == false && right == true && left == true && fall == false && dead == false) {                                //Wenn Spieler nicht nach links/ UND rechts bewegt, sich nicht duckt/fällt

                    if (faceleft == false) {

                        Image idle = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idle.png");
                        g.drawImage(idle, player.x, player.y, null);
                        g.drawImage(jumpBar0, player.x - 20, player.y, null);

                    } else if (faceright == false) {

                        Image idlel = Toolkit.getDefaultToolkit().getImage("pictures\\player\\idlel.png");
                        g.drawImage(idlel, player.x, player.y, null);
                        g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    }
                }
                animation(g);
                JumpKing.this.paintPanel(g);      //  Aufruf der eigentlichen Zeichnenoperation
            }
        };

        add(grafik);     //Ka


        setSize(1440, 1080);      //eig richtige Größe, wird unten berschrieben
        setTitle("JumpKing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1454, 1118);    //überschrieben weil, vermutlich zählt oben in GUI die weiße Zeile mit als size

        Image image = Toolkit.getDefaultToolkit().getImage("pictures\\icon\\icon.png");
        setIconImage(image);


        player = new Rectangle(100, 200, 73, 103);   // Speichert die Positionsdaten des Spielers


        //COINS\\\

        coinRectangle[0] = new Rectangle(300, 300, 64, 64);         //Position der Münzen
        coinRectangle[1] = new Rectangle(870, 500, 64, 64);
        coinRectangle[2] = new Rectangle(930, 500, 64, 64);
        coinRectangle[3] = new Rectangle(480, 150, 64, 64);
        coinRectangle[4] = new Rectangle(60, 100, 64, 64);
        coinRectangle[5] = new Rectangle(680, 400, 64, 64);


        //COINS\\\

        ///***STAGE 1***\\\
        //Böden   // Speichert die Positionsdaten der Böden
        ground[1][0] = new Rectangle(0, 983, 2000, 1);
        ground[1][1] = new Rectangle(0, 550, 385, 1);
        ground[1][2] = new Rectangle(1050, 550, 385, 1);
        ground[1][3] = new Rectangle(550, 115, 340, 1);
        ground[1][4] = new Rectangle(550, 260, 340, 1);

        //Wände  // Speichert die Positionsdaten der Wände
        wall[1][0] = new Rectangle(385, 550, 1, 434);
        wall[1][1] = new Rectangle(0, 0, 1, 550);
        wall[1][2] = new Rectangle(1050, 550, 1, 434);
        wall[1][3] = new Rectangle(1440, 0, 1, 550);
        wall[1][4] = new Rectangle(550, 115, 1, 145);
        wall[1][5] = new Rectangle(890, 115, 1, 145);


        ///***STAGE 2***\\\
        ground[2][0] = new Rectangle(882, 885, 298, 1);
        ground[2][1] = new Rectangle(882, 985, 298, 1);
        ground[2][2] = new Rectangle(765, 595, 220, 1);
        ground[2][3] = new Rectangle(765, 695, 220, 1);
        ground[2][4] = new Rectangle(1225, 595, 190, 1);
        ground[2][5] = new Rectangle(1225, 690, 190, 1);
        ground[2][6] = new Rectangle(355, 310, 220, 1);
        ground[2][7] = new Rectangle(355, 500, 220, 1);
        ground[2][8] = new Rectangle(20, 235, 220, 1);
        ground[2][9] = new Rectangle(20, 505, 220, 1);

        for (int i = 10; i < 75; i++) {

            ground[2][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        //Wände  // Speichert die Positionsdaten der WÃ¤nde
        wall[2][0] = new Rectangle(882, 885, 1, 100);
        wall[2][1] = new Rectangle(1180, 885, 1, 100);
        wall[2][2] = new Rectangle(765, 595, 1, 100);
        wall[2][3] = new Rectangle(985, 595, 1, 100);
        wall[2][4] = new Rectangle(1225, 595, 1, 95);
        wall[2][5] = new Rectangle(1415, 595, 1, 95);
        wall[2][6] = new Rectangle(355, 310, 1, 190);
        wall[2][7] = new Rectangle(575, 310, 1, 190);
        wall[2][8] = new Rectangle(20, 235, 1, 270);
        wall[2][9] = new Rectangle(240, 235, 1, 270);
        wall[2][99] = new Rectangle(1415, 0, 1, 1118);
        wall[2][100] = new Rectangle(20, 0, 1, 1118);

        for (int i = 10; i < 75; i++) {

            wall[2][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        ///***STAGE 3***\\\
        ground[3][0] = new Rectangle(624, 912, 144, 1);
        ground[3][1] = new Rectangle(624, 960, 144, 1);
        ground[3][2] = new Rectangle(960, 912, 171, 1);
        ground[3][3] = new Rectangle(960, 960, 171, 1);
        ground[3][4] = new Rectangle(1272, 768, 144, 1);
        ground[3][5] = new Rectangle(1272, 816, 144, 1);
        ground[3][6] = new Rectangle(476, 354, 175, 1);
        ground[3][7] = new Rectangle(476, 494, 175, 1);
        ground[3][8] = new Rectangle(24, 288, 169, 1);
        ground[3][9] = new Rectangle(24, 337, 169, 1);
        ground[3][10] = new Rectangle(572, 668, 438, 1);
        ground[3][11] = new Rectangle(572, 770, 438, 1);

        ground[3][12] = new Rectangle(862, 620, 149, 1);
        ground[3][13] = new Rectangle(0, 0, 0, 0);


        ground[3][14] = new Rectangle(408, 0, 216, 1);
        ground[3][15] = new Rectangle(408, 50, 216, 1);

        for (int i = 16; i < 75; i++) {

            ground[3][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        //Wände  // Speichert die Positionsdaten der Wände
        wall[3][0] = new Rectangle(624, 912, 1, 48);
        wall[3][1] = new Rectangle(768, 912, 1, 48);
        wall[3][2] = new Rectangle(960, 912, 1, 48);
        wall[3][3] = new Rectangle(1131, 912, 1, 48);
        wall[3][4] = new Rectangle(1272, 768, 1, 48);
        wall[3][5] = new Rectangle(1416, 768, 1, 48);
        wall[3][6] = new Rectangle(476, 354, 1, 140);
        wall[3][7] = new Rectangle(651, 354, 1, 140);
        wall[3][8] = new Rectangle(24, 288, 1, 49);
        wall[3][9] = new Rectangle(193, 288, 1, 49);
        wall[3][10] = new Rectangle(572, 668, 1, 102);
        wall[3][11] = new Rectangle(1010, 668, 1, 102);


        wall[3][12] = new Rectangle(862, 620, 1, 56);
        wall[3][13] = new Rectangle(1011, 620, 1, 56);

        wall[3][14] = new Rectangle(408, 0, 1, 50);
        wall[3][15] = new Rectangle(624, 0, 1, 50); //216 x 50

        wall[3][99] = new Rectangle(1415, 0, 1, 1118);
        wall[3][100] = new Rectangle(20, 0, 1, 1118);

        for (int i = 16; i < 75; i++) {

            wall[3][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        ///***STAGE 4***\\\
        ground[4][0] = new Rectangle(403, 955, 223, 1);
        ground[4][1] = new Rectangle(403, 1079, 223, 1);

        ground[4][2] = new Rectangle(408, 648, 217, 1);
        ground[4][3] = new Rectangle(408, 696, 217, 1);

        ground[4][4] = new Rectangle(25, 648, 168, 1); //168
        ground[4][5] = new Rectangle(25, 695, 168, 1);

        ground[4][6] = new Rectangle(888, 480, 144, 1);
        ground[4][7] = new Rectangle(888, 696, 144, 1);

        ground[4][8] = new Rectangle(984, 216, 50, 1);//50 x 264
        ground[4][9] = new Rectangle(0, 0, 0, 0);

        ground[4][10] = new Rectangle(1034, 216, 168, 1);
        ground[4][11] = new Rectangle(1034, 266, 168, 1);

        ground[4][12] = new Rectangle(1296, 384, 121, 1);
        ground[4][13] = new Rectangle(1296, 434, 121, 1);

        ground[4][14] = new Rectangle(408, 264, 120, 1);
        ground[4][15] = new Rectangle(408, 504, 120, 1);


        ground[4][16] = new Rectangle(0, 0, 0, 0);
        ground[4][17] = new Rectangle(0, 0, 0, 0);
        ground[4][18] = new Rectangle(0, 0, 0, 0);

        ground[4][19] = new Rectangle(984, 47, 55, 1);

        for (int i = 20; i < 75; i++) {

            ground[4][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        //Wände  // Speichert die Positionsdaten der Wände
        wall[4][0] = new Rectangle(403, 955, 1, 125);
        wall[4][1] = new Rectangle(626, 955, 1, 125);

        wall[4][2] = new Rectangle(408, 648, 1, 48);
        wall[4][3] = new Rectangle(626, 648, 1, 48);

        wall[4][4] = new Rectangle(25, 648, 1, 47);
        wall[4][5] = new Rectangle(193, 648, 1, 47);


        wall[4][6] = new Rectangle(888, 480, 1, 216);
        wall[4][7] = new Rectangle(1032, 480, 1, 216);

        wall[4][8] = new Rectangle(984, 216, 1, 264);
        wall[4][9] = new Rectangle(1034, 216, 1, 264);


        wall[4][10] = new Rectangle(0, 0, 0, 0);
        wall[4][11] = new Rectangle(1202, 216, 1, 50);


        wall[4][12] = new Rectangle(1296, 384, 1, 50);
        wall[4][13] = new Rectangle(0, 0, 0, 0);

        wall[4][14] = new Rectangle(408, 264, 1, 240);
        wall[4][15] = new Rectangle(528, 264, 1, 240);

        wall[4][16] = new Rectangle(408, 0, 1, 265);
        wall[4][17] = new Rectangle(456, 0, 1, 265);

        wall[4][18] = new Rectangle(984, 0, 1, 47);
        wall[4][19] = new Rectangle(1039, 0, 1, 47);


        wall[4][99] = new Rectangle(1415, 0, 1, 1118);
        wall[4][100] = new Rectangle(20, 0, 1, 1118);

        for (int i = 18; i < 75; i++) {

            wall[4][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        ///***STAGE 5***\\\
        ground[5][0] = new Rectangle(335, 935, 121, 1);
        ground[5][1] = new Rectangle(335, 996, 121, 1);

        ground[5][2] = new Rectangle(983, 935, 121, 1);
        ground[5][3] = new Rectangle(983, 996, 121, 1);

        ground[5][4] = new Rectangle(985, 720, 120, 1); //168
        ground[5][5] = new Rectangle(985, 765, 120, 1);

        ground[5][6] = new Rectangle(1320, 480, 95, 1);
        ground[5][7] = new Rectangle(1320, 550, 95, 1);

        ground[5][8] = new Rectangle(865, 265, 95, 1);//50 x 264
        ground[5][9] = new Rectangle(865, 307, 95, 1);

        ground[5][10] = new Rectangle(673, 216, 95, 1);
        ground[5][11] = new Rectangle(673, 266, 95, 1);

        ground[5][12] = new Rectangle(480, 170, 97, 1);
        ground[5][13] = new Rectangle(480, 203, 97, 1);

        ground[5][14] = new Rectangle(120, 264, 96, 1);
        ground[5][15] = new Rectangle(120, 311, 96, 1);

        ground[5][16] = new Rectangle(25, 720, 95, 1);
        ground[5][17] = new Rectangle(25, 768, 95, 1);

        ground[5][18] = new Rectangle(455, 0, 530, 1);
        ground[5][19] = new Rectangle(455, 48, 530, 1);

        ground[5][20] = new Rectangle(0, 0, 0, 0);
        ground[5][21] = new Rectangle(0, 0, 0, 0);

        ground[5][22] = new Rectangle(0, 0, 0, 0);
        ground[5][23] = new Rectangle(0, 0, 0, 0);


        for (int i = 24; i < 75; i++) {

            ground[5][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        //Wände  // Speichert die Positionsdaten der Wände
        wall[5][0] = new Rectangle(335, 935, 1, 61);
        wall[5][1] = new Rectangle(456, 935, 1, 61);

        wall[5][2] = new Rectangle(983, 935, 1, 61);
        wall[5][3] = new Rectangle(1104, 935, 1, 61);

        wall[5][4] = new Rectangle(985, 720, 1, 45);
        wall[5][5] = new Rectangle(1105, 720, 1, 45);

        wall[5][6] = new Rectangle(1320, 480, 1, 70);
        wall[5][7] = new Rectangle(1415, 480, 1, 70);

        wall[5][8] = new Rectangle(865, 265, 1, 42);
        wall[5][9] = new Rectangle(960, 265, 1, 42);

        wall[5][10] = new Rectangle(673, 216, 1, 50);
        wall[5][11] = new Rectangle(768, 216, 1, 50);

        wall[5][12] = new Rectangle(480, 170, 1, 33);
        wall[5][13] = new Rectangle(577, 170, 1, 33);

        wall[5][14] = new Rectangle(120, 264, 1, 47);
        wall[5][15] = new Rectangle(216, 264, 1, 47);

        wall[5][16] = new Rectangle(25, 720, 1, 48);
        wall[5][17] = new Rectangle(120, 720, 1, 48);

        wall[5][18] = new Rectangle(455, 0, 1, 48);
        wall[5][19] = new Rectangle(985, 0, 1, 48);

        wall[5][20] = new Rectangle(0, 0, 0, 0);
        wall[5][21] = new Rectangle(456, 996, 1, 84);

        wall[5][22] = new Rectangle(983, 996, 1, 84);
        wall[5][23] = new Rectangle(1039, 996, 1, 84);


        wall[5][100] = new Rectangle(25, 0, 1, 1080);
        wall[5][99] = new Rectangle(1415, 0, 1, 1080);

        for (int i = 24; i < 75; i++) {

            wall[5][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        ///***STAGE 6***\\\
        ground[6][0] = new Rectangle(457, 984, 527, 1); //527 x 96
        ground[6][1] = new Rectangle(457, 1079, 527, 1);


        for (int i = 2; i < 75; i++) {

            ground[6][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        //Wände  // Speichert die Positionsdaten der Wände
        wall[6][0] = new Rectangle(457, 984, 1, 96);
        wall[6][1] = new Rectangle(984, 984, 1, 96);


        wall[6][100] = new Rectangle(25, 0, 1, 1080);
        wall[6][99] = new Rectangle(1415, 0, 1, 1080);

        for (int i = 2; i < 75; i++) {

            wall[6][i] = new Rectangle(0, 0, 0, 0); //Initialisierung damit kein Luafzeitfehler
        }


        initGame();    //Aufruf von der Initialisierung

        //basic Sachen (GrundgerÃ¼st)
        addMouseListener(this);
        addKeyListener(this);
        setResizable(false);
        setVisible(true);


        zaehler.start();  // Timer starten fÃ¼r timer tick
    }


    private void initGame() {         // Anfangswerte beim Neustart


        player.x = breite / 2;
        player.y = hoehe / 2; //Spieler startet in der Mitte des Screens
        verloren = false;


        left = false;
        right = false;
        space = false;

        //stage=1;

        vxPlayer = 0;    // Geschwindigkeit (X-Achse) des Spielers
        vyPlayer = 0;    // Geschwindigkeit (Y-Achse) des Spielers

        punkte = 0;
        takt = 0;
    }

    // Operation zum Neuzeichen der grafischen Benutzeroberfl�che
    public void paintPanel(Graphics g) {
        //Dient nur zum Debuggen! Kann eig alles weg, trotzdem da lassen

        //Player
        if (debugger == true) {

            Color invis = new Color(255, 255, 255, 140); //Unsichtbare Farbe (Zum Debuggen)
            g.setColor(invis);

        } else {
            Color invis = new Color(255, 255, 255, 0); //Unsichtbare Farbe (Zum Debuggen)
            g.setColor(invis);
        }


        g.fillRect(player.x, player.y, 73, 103);


        if (debugger == true) {


            if (stage == 1) {

                //Böden

                //Boden0
                g.setColor(Color.blue);
                g.fillRect(0, 983, 2000, 1);


                //Boden1
                g.setColor(Color.blue);
                g.fillRect(0, 550, 385, 1);


                //Boden2
                g.setColor(Color.blue);
                g.fillRect(1050, 550, 385, 1);


                //Boden3
                g.setColor(Color.blue);
                g.fillRect(550, 115, 340, 1);


                //Boden4
                g.setColor(Color.blue);
                g.fillRect(550, 260, 340, 1);


                //Wände

                //Wand0
                g.setColor(Color.blue);
                g.fillRect(385, 550, 1, 434);


                //Wand1
                g.setColor(Color.blue);
                g.fillRect(0, 0, 1, 550);


                //Wand2
                g.setColor(Color.blue);
                g.fillRect(1050, 550, 1, 434);


                //Wand3
                g.setColor(Color.blue);
                g.fillRect(1440, 0, 1, 550);


                //Wand4
                g.setColor(Color.blue);
                g.fillRect(550, 115, 1, 145);


                //Wand5
                g.setColor(Color.blue);
                g.fillRect(890, 115, 1, 145);

            } else if (stage == 2) {

                //Böden


                //Boden0
                g.setColor(Color.blue);
                g.fillRect(882, 885, 298, 1);
                //Boden1
                g.setColor(Color.blue);
                g.fillRect(882, 985, 298, 1);


                //Boden2
                g.setColor(Color.blue);
                g.fillRect(765, 595, 220, 1);
                //Boden3
                g.setColor(Color.blue);
                g.fillRect(765, 695, 220, 1);

                //Boden4
                g.setColor(Color.blue);
                g.fillRect(1225, 595, 190, 1);
                //Boden5
                g.setColor(Color.blue);
                g.fillRect(1225, 690, 190, 1);

                //Boden6
                g.setColor(Color.blue);
                g.fillRect(355, 310, 220, 1);
                //Boden7
                g.setColor(Color.blue);
                g.fillRect(355, 500, 220, 1);

                //Boden8
                g.setColor(Color.blue);
                g.fillRect(20, 235, 220, 1);
                //Boden9
                g.setColor(Color.blue);
                g.fillRect(20, 505, 220, 1);


                //Wände


                //Wand0
                g.setColor(Color.blue);
                g.fillRect(882, 885, 1, 100);
                //Wand1
                g.setColor(Color.blue);
                g.fillRect(1180, 885, 1, 100);


                //Wand2
                g.setColor(Color.blue);
                g.fillRect(765, 595, 1, 100);
                //Wand3
                g.setColor(Color.blue);
                g.fillRect(985, 595, 1, 100);

                //Wand4
                g.setColor(Color.blue);
                g.fillRect(1225, 595, 1, 95);

                //Wand5
                g.setColor(Color.blue);
                g.fillRect(1415, 595, 1, 95);

                //Wand6
                g.setColor(Color.blue);
                g.fillRect(355, 310, 1, 190);


                //Wand7
                g.setColor(Color.blue);
                g.fillRect(575, 310, 1, 190);

                //Wand78
                g.setColor(Color.green);
                g.fillRect(20, 235, 1, 270);


                //Wand9
                g.setColor(Color.blue);
                g.fillRect(240, 235, 1, 270);

                //Wand11
                g.setColor(Color.red);
                g.fillRect(20, 0, 1, 1118);

                //Wand10
                g.setColor(Color.green);
                g.fillRect(1415, 0, 1, 1118);


            } else if (stage == 3) {

                //Boden0
                g.setColor(Color.blue);
                g.fillRect(624, 912, 144, 1);
                //Boden1
                g.setColor(Color.blue);
                g.fillRect(624, 960, 144, 1);


                //Boden2
                g.setColor(Color.blue);
                g.fillRect(960, 912, 171, 1);
                //Boden3
                g.setColor(Color.blue);
                g.fillRect(960, 960, 171, 1);


                //Boden4
                g.setColor(Color.blue);
                g.fillRect(1272, 768, 144, 1);
                //Boden5
                g.setColor(Color.blue);
                g.fillRect(1272, 816, 144, 1);


                //Boden6
                g.setColor(Color.blue);
                g.fillRect(476, 354, 175, 1);
                //Boden7
                g.setColor(Color.blue);
                g.fillRect(476, 494, 175, 1);


                //Boden8
                g.setColor(Color.blue);
                g.fillRect(24, 288, 169, 1);
                //Boden9
                g.setColor(Color.blue);
                g.fillRect(24, 337, 169, 1);


                //Boden10
                g.setColor(Color.blue);
                g.fillRect(572, 668, 438, 1);  //770
                //Boden11
                g.setColor(Color.blue);
                g.fillRect(572, 770, 438, 1);


                //Boden12
                g.setColor(Color.blue);
                g.fillRect(862, 620, 149, 1);
                //Boden13
                g.setColor(Color.blue);
                g.fillRect(0, 0, 0, 0);


                //Boden14
                g.setColor(Color.blue);
                g.fillRect(408, 0, 216, 1);
                //Boden15
                g.setColor(Color.blue);
                g.fillRect(408, 50, 216, 1);


                //Wand0
                g.setColor(Color.blue);
                g.fillRect(624, 912, 1, 48);
                //Wand1
                g.setColor(Color.blue);
                g.fillRect(768, 912, 1, 48);


                //Wand2
                g.setColor(Color.blue);
                g.fillRect(960, 912, 1, 48);
                //Wand3
                g.setColor(Color.blue);
                g.fillRect(1131, 912, 1, 48);


                //Wand4
                g.setColor(Color.blue);
                g.fillRect(1272, 768, 1, 48);
                //Wand5
                g.setColor(Color.blue);
                g.fillRect(1416, 768, 1, 48);


                //Wand6
                g.setColor(Color.blue);
                g.fillRect(476, 354, 1, 140);
                //Wand7
                g.setColor(Color.blue);
                g.fillRect(651, 354, 1, 140);


                //Wand8
                g.setColor(Color.blue);
                g.fillRect(24, 288, 1, 49); //49
                //Wand9
                g.setColor(Color.blue);
                g.fillRect(193, 288, 1, 49);


                //Wand10
                g.setColor(Color.blue);
                g.fillRect(572, 668, 1, 102);
                //Wand11
                g.setColor(Color.blue);
                g.fillRect(1010, 668, 1, 102);


                //Wand12
                g.setColor(Color.blue);
                g.fillRect(862, 620, 1, 56);
                //Wand13
                g.setColor(Color.blue);
                g.fillRect(1011, 620, 1, 56);

                //Wand14
                g.setColor(Color.blue);
                g.fillRect(408, 0, 1, 50);
                //Wand15
                g.setColor(Color.blue);
                g.fillRect(624, 0, 1, 50);


            } else if (stage == 4) {

                //Boden0
                g.setColor(Color.blue);
                g.fillRect(403, 955, 223, 1);
                //Boden1
                g.setColor(Color.blue);
                g.fillRect(403, 1079, 223, 1);


                //Boden2
                g.setColor(Color.blue);
                g.fillRect(408, 648, 217, 1);
                //Boden3
                g.setColor(Color.blue);
                g.fillRect(408, 696, 217, 1);


                //Boden4
                g.setColor(Color.blue);
                g.fillRect(25, 648, 168, 1);
                //Boden5
                g.setColor(Color.blue);
                g.fillRect(25, 695, 168, 1);


                //Boden6
                g.setColor(Color.blue);
                g.fillRect(888, 480, 144, 1);
                //Boden7
                g.setColor(Color.blue);
                g.fillRect(888, 696, 144, 1);


                //Boden8
                g.setColor(Color.blue);
                g.fillRect(984, 216, 50, 1);
                //Boden 9 gibt es nicht


                //Boden10
                g.setColor(Color.blue);
                g.fillRect(1034, 216, 168, 1);
                //Boden11
                g.setColor(Color.blue);
                g.fillRect(1034, 266, 168, 1);


                //Boden12
                g.setColor(Color.blue);
                g.fillRect(1296, 384, 121, 1); //121 x 50
                //Boden13
                g.setColor(Color.blue);
                g.fillRect(1296, 434, 121, 1);


                //Boden14
                g.setColor(Color.blue);
                g.fillRect(408, 264, 120, 1); //120 x 240
                //Boden15
                g.setColor(Color.blue);
                g.fillRect(408, 504, 120, 1);


                //Boden16
                g.setColor(Color.blue);
                g.fillRect(0, 0, 0, 0); //48 x 264
                //Boden17
                g.setColor(Color.blue);
                g.fillRect(0, 0, 0, 0);


                //Boden19
                g.setColor(Color.blue);
                g.fillRect(984, 47, 55, 1);


                //Wand0
                g.setColor(Color.blue);
                g.fillRect(403, 955, 1, 125);
                //Wand1
                g.setColor(Color.blue);
                g.fillRect(626, 955, 1, 125);


                //Wand2
                g.setColor(Color.blue);
                g.fillRect(408, 648, 1, 48);
                //Wand3
                g.setColor(Color.blue);
                g.fillRect(626, 648, 1, 48);


                //Wand4
                g.setColor(Color.blue);
                g.fillRect(25, 648, 1, 47);
                //Wand5
                g.setColor(Color.blue);
                g.fillRect(193, 648, 1, 47);


                //Wand6
                g.setColor(Color.blue);
                g.fillRect(888, 480, 1, 216);
                //Wand7
                g.setColor(Color.blue);
                g.fillRect(1032, 480, 1, 216);


                //Wand8
                g.setColor(Color.blue);
                g.fillRect(984, 216, 1, 264);
                //Wand9
                g.setColor(Color.blue);
                g.fillRect(1034, 216, 1, 264);


                //Wand10 gibt es nicht
                //Wand11
                g.setColor(Color.blue);
                g.fillRect(1202, 216, 1, 50);


                //Wand12
                g.setColor(Color.blue);
                g.fillRect(1296, 384, 1, 50);
                //Wand13
                g.setColor(Color.blue);
                g.fillRect(1417, 384, 1, 50);


                //Wand14
                g.setColor(Color.blue);
                g.fillRect(408, 264, 1, 240);
                //Wand15
                g.setColor(Color.blue);
                g.fillRect(528, 264, 1, 240);


                //Wand16
                g.setColor(Color.blue);
                g.fillRect(408, 0, 1, 265);
                //Wand17
                g.setColor(Color.blue);
                g.fillRect(456, 0, 1, 265);


                //Wand18
                g.setColor(Color.blue);
                g.fillRect(984, 0, 1, 47);  //55 x 47
                //Wand19
                g.setColor(Color.blue);
                g.fillRect(1039, 0, 1, 47);


            } else if (stage == 5) {


                //Boden0
                g.setColor(Color.blue);
                g.fillRect(335, 935, 121, 1);//121 x 61
                //Boden1
                g.setColor(Color.blue);
                g.fillRect(335, 996, 121, 1);

                //Boden2
                g.setColor(Color.blue);
                g.fillRect(983, 935, 121, 1);//121 x 61  983
                //Boden3
                g.setColor(Color.blue);
                g.fillRect(983, 996, 121, 1);

                //Boden4
                g.setColor(Color.blue);
                g.fillRect(985, 720, 120, 1); //120 x 45
                //Boden5
                g.setColor(Color.blue);
                g.fillRect(985, 765, 120, 1);

                //Boden6
                g.setColor(Color.blue);
                g.fillRect(1320, 480, 95, 1); //95 x 70
                //Boden7
                g.setColor(Color.blue);
                g.fillRect(1320, 550, 95, 1);

                //Boden8
                g.setColor(Color.blue);
                g.fillRect(865, 265, 95, 1); //95 x 42
                //Boden9
                g.setColor(Color.blue);
                g.fillRect(865, 307, 95, 1);

                //Boden10
                g.setColor(Color.blue);
                g.fillRect(673, 216, 95, 1); //95 x 50
                //Boden11
                g.setColor(Color.blue);
                g.fillRect(673, 266, 95, 1);

                //Boden12
                g.setColor(Color.blue);
                g.fillRect(480, 170, 97, 1); //97 x 33
                //Boden13
                g.setColor(Color.blue);
                g.fillRect(480, 203, 97, 1);

                //Boden14
                g.setColor(Color.blue);
                g.fillRect(120, 264, 96, 1); //96 x 47
                //Boden15
                g.setColor(Color.blue);
                g.fillRect(120, 311, 96, 1);

                //Boden16
                g.setColor(Color.blue);
                g.fillRect(25, 720, 95, 1); //95 x 48
                //Boden17
                g.setColor(Color.blue);
                g.fillRect(25, 768, 95, 1);

                //Boden18
                g.setColor(Color.red);
                g.fillRect(455, 0, 530, 1); //530 x 48
                //Boden19
                g.setColor(Color.red);
                g.fillRect(455, 48, 530, 1);


                //Boden20
                g.setColor(Color.red);
                g.fillRect(455, 0, 530, 1); //530 x 48
                //Boden21
                g.setColor(Color.red);
                g.fillRect(455, 48, 530, 1);


                //Wand0
                g.setColor(Color.blue);
                g.fillRect(335, 935, 1, 61);
                //Wand1
                g.setColor(Color.blue);
                g.fillRect(456, 935, 1, 61);

                //Wand2
                g.setColor(Color.blue);
                g.fillRect(983, 935, 1, 61);
                //Wand3
                g.setColor(Color.blue);
                g.fillRect(1104, 935, 1, 61);

                //Wand4
                g.setColor(Color.blue);
                g.fillRect(985, 720, 1, 45);
                //Wand5
                g.setColor(Color.blue);
                g.fillRect(1105, 720, 1, 45);

                //Wand6
                g.setColor(Color.blue);
                g.fillRect(1320, 480, 1, 70);  //95 x 70
                //Wand7
                g.setColor(Color.blue);
                g.fillRect(1415, 480, 1, 70);

                //Wand8
                g.setColor(Color.blue);
                g.fillRect(865, 265, 1, 42);  //95 x 42
                //Wand8
                g.setColor(Color.blue);
                g.fillRect(960, 265, 1, 42);

                //Wand10
                g.setColor(Color.blue);
                g.fillRect(673, 216, 1, 50);  //95 x 50
                //Wand11
                g.setColor(Color.blue);
                g.fillRect(768, 216, 1, 50);

                //Wand12
                g.setColor(Color.blue);
                g.fillRect(480, 170, 1, 33);  //97 x 33
                //Wand13
                g.setColor(Color.blue);
                g.fillRect(577, 170, 1, 33);

                //Wand14
                g.setColor(Color.blue);
                g.fillRect(120, 264, 1, 47); //96 x 47
                //Wand15
                g.setColor(Color.blue);
                g.fillRect(216, 264, 1, 47);

                //Wand16
                g.setColor(Color.blue);
                g.fillRect(25, 720, 1, 48); //95 x 48
                //Wand17
                g.setColor(Color.blue);
                g.fillRect(120, 720, 1, 48);

                //Wand18
                g.setColor(Color.red);
                g.fillRect(455, 0, 1, 48); //530 x 48
                //Wand19
                g.setColor(Color.red);
                g.fillRect(985, 0, 1, 48);

                //Wand20
                g.setColor(Color.ORANGE);
                g.fillRect(400, 996, 1, 84);
                //Wand21
                g.setColor(Color.ORANGE);
                g.fillRect(456, 996, 1, 84);

                //Wand22
                g.setColor(Color.ORANGE);
                g.fillRect(983, 996, 1, 84);
                //Wand23
                g.setColor(Color.ORANGE);
                g.fillRect(1039, 996, 1, 84);


                //Wand99
                g.setColor(Color.blue);
                g.fillRect(25, 0, 1, 1080); //95 x 48
                //Wand100
                g.setColor(Color.blue);
                g.fillRect(1415, 0, 1, 1080);

            } else if (stage == 6) {

                //Wand0
                g.setColor(Color.blue);
                g.fillRect(457, 984, 1, 96);
                //Wand1
                g.setColor(Color.red);
                g.fillRect(984, 984, 1, 96);

                //Boden0
                g.setColor(Color.red);
                g.fillRect(457, 984, 527, 1);
                //Boden1
                g.setColor(Color.red);
                g.fillRect(457, 1079, 527, 1);

            }
        }

        ///////////////VON HIER********************
        if (shop && !gestartet) {      //Hier wird die Shopauswahl gemacht


            switch (auswahl) {
                case 0:

                    auswahl = 2;
                case 1:
                    Image shopWilli = Toolkit.getDefaultToolkit().getImage("Shop\\ShopWilli.png");
                    g.drawImage(shopWilli, 0, 0, null);
                    repaint();
                    break;
                case 2:
                    Image shopPinguin = Toolkit.getDefaultToolkit().getImage("Shop\\Shop.png");
                    g.drawImage(shopPinguin, 0, 0, null);
                    repaint();
                    break;
                default:

            } // end of switch
            switch (auswahl) {        // Auswahl der Shop Imgs

                case 3:

                    auswahl = 1;
                case 1:
                    Image shopWilli = Toolkit.getDefaultToolkit().getImage("Shop\\ShopWilli.png");
                    g.drawImage(shopWilli, 0, 0, null);
                    repaint();
                    break;
                case 2:
                    Image shopPinguin = Toolkit.getDefaultToolkit().getImage("Shop\\Shop.png");
                    g.drawImage(shopPinguin, 0, 0, null);
                    repaint();

                    break;
                default:

            } // end of switch


        } // end of if


        if (menu && !gestartet) {

            switch (auswahlMenue) {     //Auswahl der Menü Imgs
                case 0:
                    auswahlMenue = 3;


                case 1:
                    Image menu1 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen1.png");
                    g.drawImage(menu1, 0, 0, null);
                    repaint();
                    break;
                case 2:
                    Image menu2 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen2.png");
                    g.drawImage(menu2, 0, 0, null);
                    repaint();
                    break;

                case 3:
                    Image menu3 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen3.png");
                    g.drawImage(menu3, 0, 0, null);
                    repaint();
                    break;

                default:

            } // end of switch
            switch (auswahlMenue) {
                case 4:
                    auswahlMenue = 1;


                case 1:
                    Image menu1 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen1.png");
                    g.drawImage(menu1, 0, 0, null);
                    repaint();
                    break;
                case 2:
                    Image menu2 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen2.png");
                    g.drawImage(menu2, 0, 0, null);
                    repaint();
                    break;

                case 3:
                    Image menu3 = Toolkit.getDefaultToolkit().getImage("pictures\\menu\\startscreen3.png");
                    g.drawImage(menu3, 0, 0, null);
                    repaint();
                    break;

                default:

            } // end of switch
        } // end of if

        //Während des Spielens
        if (!verloren && gestartet) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Kontostand: " + kontostand, 600, 40);
        }
        //Animationen:
        //Spieler hat einen x-Wert (4stellig) (player.x= ????)

        int animation = player.x % 100;   //nun wird er auf seine letzten 2 Stellen gekürzt

        animation = animation / 10;       //So kommt eine 10er Zahl heraus
        animation = animation * 10;

        //Nun wird überprüft welchen Wert die 2 Stellen (0-99) haben

        //Nach Rechts laufen: Zu jedem X Wert, ein neues Bild

        if (right == true && space == false && left == false && isonground == true) {

            faceright = true;
            faceleft = false;

            switch (animation) {
                case 0:

                    Image run1 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run1, player.x, player.y, null);
                    //setze bild 1
                    break;

                case 10:


                    Image run2 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run2, player.x, player.y, null);
                    //setze bild 2

                    break;

                case 20:

                    Image run3 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run3, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 30:

                    run1 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run1, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 40:

                    run2 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run2, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 50:


                    run3 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run3, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 60:

                    run1 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run1, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 70:


                    run2 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run2, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 80:


                    run3 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run3, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 90:

                    run1 = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run1, player.x, player.y, null);
                    //setze bild 3

                    break;


                default:

                    break;
            }


        }

        //Nach Links laufen: Zu jedem X Wert, ein neues Bild
        if (left && !space && !right && isonground) {

            faceleft = true;
            faceright = false;

            switch (animation) {
                case 0:

                    Image run1l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run1l, player.x, player.y, null);
                    //setze bild 1
                    break;

                case 10:


                    Image run2l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run2l, player.x, player.y, null);
                    //setze bild 2

                    break;

                case 20:

                    Image run3l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run3l, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 30:

                    run1l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run1l, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 40:

                    run2l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run2l, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 50:


                    run3l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run3l, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 60:

                    run1l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run1l, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 70:


                    run2l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run2l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run2l, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 80:


                    run3l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run3l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run3l, player.x, player.y, null);
                    //setze bild 3

                    break;

                case 90:

                    run1l = Toolkit.getDefaultToolkit().getImage("pictures\\player\\run1l.png");
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                    g.drawImage(run1l, player.x, player.y, null);
                    //setze bild 3

                    break;

                default:

                    break;
            }


        }

        if (!fall && gravity > 42 && !right && !left) {
            dead = true;
            Image dead = Toolkit.getDefaultToolkit().getImage("pictures\\player\\deadr.png");
            g.drawImage(dead, player.x, player.y, null);
            g.drawImage(jumpBar0, player.x - 20, player.y, null);
        } else {
            dead = false;
        }


        if (space) {     //Wenn space gedrückt wird => duckt sich der Player
            vxPlayer = 0;
            Image squat = Toolkit.getDefaultToolkit().getImage("pictures\\player\\squat.png");
            g.drawImage(jumpBar0, player.x - 20, player.y, null);
            g.drawImage(squat, player.x, player.y, null);

        }

        if (!isonground) { //player ist in Luft => Fallanimation (bzw. Springanimation)

            right = false;
            left = false;
            fall = true;


            if (gravity > vyPlayer && !bump) {
                //Fallen

                if (faceright) {

                    Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\fall.png");
                    g.drawImage(fall, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);

                } else if (faceleft) {

                    Image falll = Toolkit.getDefaultToolkit().getImage("pictures\\player\\falll.png");
                    g.drawImage(falll, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);

                } else {

                    //für den Fall: Spiel gestartet & faceright/left sind beide false
                    Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\fall.png");
                    g.drawImage(fall, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);

                }

            } else if (gravity < vyPlayer && !bump) {
                //Springen

                if (faceright) {

                    Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\jump.png");
                    g.drawImage(fall, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);

                } else if (faceleft) {

                    Image falll = Toolkit.getDefaultToolkit().getImage("pictures\\player\\jumpl.png");
                    g.drawImage(falll, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);

                } else {

                    //für den Fall: Spiel gestartet & faceright/left sind beide false
                    Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\jump.png");
                    g.drawImage(fall, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);

                }

            }


            if (bump) {

                if (faceright) {
                    Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\bump.png");
                    g.drawImage(fall, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                } else if (faceleft == true) {
                    Image fall = Toolkit.getDefaultToolkit().getImage("pictures\\player\\bumpl.png");
                    g.drawImage(fall, player.x, player.y, null);
                    g.drawImage(jumpBar0, player.x - 20, player.y, null);
                }

            }

        } else {
            fall = false;
            isonground = true;
        }

    }


    private void doTimerTick() {      // Hier wird bei jedem Timer Tick das Szenario neu berechnet und ein Neuzeichnen veranlasst


        takt = takt + 1;       //Für die Zeitmessung wie lange man spielt

        if (space) {    //Timer für die Zeit wie lange man leertaste hält
            timer++;
        } else {
            timer = 0;
        } // end of if-else


        if (gestartet) {

            if (stage <= 5) {


                gravity++;
                player.y += gravity;   //Schwerkraft (lineare Steigung der Geschwindigkeit) vl mit vyball--

            } else if (stage == 6) {

                if (player.y < 770) {


                    gravity = gravity + 0.6;

                    player.y += gravity;   //Schwerkraft (lineare Steigung der Geschwindigkeit) vl mit vyball-


                } else {

                    gravity++;
                    player.y += gravity;   //Schwerkraft (lineare Steigung der Geschwindigkeit) vl mit vyball

                }
            }
        }

        if (player.y >= 1118) {
            //Ball geht unten raus => voheriges Lvl laden
            stage = stage - 1;
            player.y -= 1115;
        } else if (player.y <= 0) {
            //Ball geht oben  raus => nächstes Lvl laden
            stage = stage + 1;
            player.y += 1115;
        }


        switch (stage) {
            case 1:

                collision1();

                if (player.intersects(coinRectangle[0]) && coinBoolean[0] == false) {               //Collision with coin
                    kontostand++;
                    coinBoolean[0] = true;


                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\coin.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                }

                break;

            case 2:

                collision2();

                if (player.intersects(coinRectangle[1]) && coinBoolean[1] == false) {
                    kontostand++;
                    coinBoolean[1] = true;


                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\coin.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                }

                break;

            case 3:
                collision3();

                if (player.intersects(coinRectangle[2]) && coinBoolean[2] == false) {
                    kontostand++;
                    coinBoolean[2] = true;

                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\coin.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                }
                break;
            case 4:
                collision4();

                if (player.intersects(coinRectangle[3]) && coinBoolean[3] == false) {
                    kontostand++;
                    coinBoolean[3] = true;


                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\coin.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                }
                break;
            case 5:
                collision5();

                if (player.intersects(coinRectangle[4]) && coinBoolean[4] == false) {
                    kontostand++;
                    coinBoolean[4] = true;


                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\coin.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                }
                break;
            case 6:
                collision6();

                if (player.intersects(coinRectangle[5]) && coinBoolean[5] == false) { //TODO
                    coinBoolean[5] = true;

                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\endmusic.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                }
                break;


            default:
                break;
        }
    }

    public void animation(Graphics g) {
        if (timer != 0 && timer < 10) {
            Image jumpBar1 = Toolkit.getDefaultToolkit().getImage("pictures\\Ladebalken\\Ladebalken 1.png");
            g.drawImage(jumpBar1, player.x - 20, player.y, null);
        }
        if (timer >= 10 && timer < 20) {
            Image jumpBar2 = Toolkit.getDefaultToolkit().getImage("pictures\\Ladebalken\\Ladebalken 2.png");
            g.drawImage(jumpBar2, player.x - 20, player.y, null);
        }
        if (timer >= 20 && timer < 30) {
            Image jumpBar3 = Toolkit.getDefaultToolkit().getImage("pictures\\Ladebalken\\Ladebalken 3.png");
            g.drawImage(jumpBar3, player.x - 20, player.y, null);
        }
        if (timer >= 30 && timer < 40) {
            Image jumpBar4 = Toolkit.getDefaultToolkit().getImage("pictures\\Ladebalken\\Ladebalken 4.png");
            g.drawImage(jumpBar4, player.x - 20, player.y, null);
        }
        if (timer >= 40 && timer < 50) {
            Image jumpBar1 = Toolkit.getDefaultToolkit().getImage("pictures\\Ladebalken\\Ladebalken 5.png");
            g.drawImage(jumpBar1, player.x - 20, player.y, null);
        }
        if (timer >= 60) {
            Image jumpBar6 = Toolkit.getDefaultToolkit().getImage("pictures\\Ladebalken\\Ladebalken 6.png");
            g.drawImage(jumpBar6, player.x - 20, player.y, null);
        }
    }


    public void collision1() {

        if (gestartet) {

            if (player.intersects(ground[1][0])) {

                if ((gravity > 1 && !absprung)) {
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }

                    vxPlayer = 0;
                    vyPlayer = 0;
                }

                gravity = 0;
                bump = false;
                player.y = ground[1][0].y - player.height;
                absprung = false;
                isonground = true;
            } else {
                isonground = false;
            }


            if (player.intersects(ground[1][1]) && player.intersects(wall[1][0]) && !right && !left) {     //siehe png Datei

                if ((wall[1][0].x + wall[1][0].width - player.x) > (player.y + player.height - wall[1][0].y)) {

                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }

                        vxPlayer = 0;
                        vyPlayer = 0;
                    }

                    gravity = 0;
                    bump = false;
                    player.y = ground[1][1].y - player.height;
                    isonground = true;
                    absprung = false;

                } else if ((wall[1][0].x + wall[1][0].width - player.x) < (player.y + player.height - wall[1][0].y)) {

                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    player.x = wall[1][0].x;

                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x += 5;

                    } while (player.intersects(wall[1][0]));

                    if (!isonground) {
                        bump = true;
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    }
                }

            } else if (player.intersects(ground[1][1])) {

                if (gravity > 1 && !absprung) {
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }

                    vxPlayer = 0;
                    vyPlayer = 0;
                }
                gravity = 0;
                bump = false;
                player.y = ground[1][1].y - player.height;
                isonground = true;
                absprung = false;

            } else if (player.intersects(wall[1][0]) && !right && !left) {


                player.x = wall[1][0].x;

                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt

                    player.x += 5;

                } while (player.intersects(wall[1][0]));

                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }

            if (player.intersects(ground[1][2]) && player.intersects(wall[1][2]) && !right && !left) {     //siehe png Datei

                if ((player.x + player.width - wall[1][2].x) > (player.y + player.height - wall[1][2].y)) {

                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }

                        vxPlayer = 0;
                        vyPlayer = 0;
                    }
                    gravity = 0;
                    bump = false;
                    player.y = ground[1][2].y - player.height;
                    isonground = true;
                    absprung = false;

                } else if ((player.x + player.width - wall[1][2].x) < (player.y + player.height - wall[1][2].y)) {

                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    player.x = wall[1][2].x;

                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x -= 5;
                    } while (player.intersects(wall[1][2]));

                    if (!isonground) {
                        bump = true;
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    }
                }

            } else if (player.intersects(ground[1][2])) {

                if (gravity > 1 && !absprung) {
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }

                    vxPlayer = 0;
                    vyPlayer = 0;
                }

                gravity = 0;
                bump = false;
                player.y = ground[1][2].y - player.height;
                isonground = true;
                absprung = false;

            } else if (player.intersects(wall[1][2]) && !right && !left) {

                player.x = wall[1][2].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x -= 5;
                } while (player.intersects(wall[1][2]));

                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }

            if (player.intersects(ground[1][4]) && player.intersects(wall[1][5]) && !right && !left) {     //siehe png Datei

                if ((wall[1][5].x + wall[1][5].width - player.x) > (player.y + player.height - wall[1][5].y)) {


                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }

                        vxPlayer = 0;
                        vyPlayer = 0;
                    }
                    gravity = 0;
                    bump = false;
                    player.y = ground[1][4].y - player.height;
                    isonground = true;
                    absprung = false;

                } else if ((wall[1][5].x + wall[1][5].width - player.x) < (player.y + player.height - wall[1][5].y)) {

                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    player.x = wall[1][5].x;
                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x += 5;
                    } while (player.intersects(wall[1][5]));

                    if (!isonground) {
                        bump = true;
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    }
                }
            } else if (player.intersects(ground[1][4])) {

                if (gravity > 1 && !absprung) {
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                    vxPlayer = 0;
                    vyPlayer = 0;
                }

                gravity = 0;
                bump = false;
                player.y = ground[1][4].y - player.height;
                isonground = true;
                absprung = false;
            }

            if (player.intersects(ground[1][3]) && player.intersects(wall[1][5]) && !right && !left) {     //siehe png Datei

                if ((wall[1][5].x + wall[1][5].width - player.x) > (player.y + player.height - wall[1][5].y)) {

                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }

                        vxPlayer = 0;
                        vyPlayer = 0;
                    }

                    gravity = 0;
                    bump = false;
                    player.y = ground[1][3].y - player.height;
                    isonground = true;
                    absprung = false;

                } else if ((wall[1][5].x + wall[1][5].width - player.x) < (player.y + player.height - wall[1][5].y)) {

                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    player.x = wall[1][5].x;

                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x += 5;

                    } while (player.intersects(wall[1][5]));


                    if (!isonground) {
                        bump = true;
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    }
                }
            } else if (player.intersects(wall[1][5]) && !right && !left) {


                player.x = wall[1][5].x;

                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x += 5;
                } while (player.intersects(wall[1][5]));


                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
            if (player.intersects(ground[1][3]) && player.intersects(wall[1][4]) && !right && !left) {     //siehe png Datei

                if ((player.x + player.width - wall[1][4].x) > (player.y + player.height - wall[1][4].y)) {

                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }


                        vxPlayer = 0;
                        vyPlayer = 0;
                    }
                    gravity = 0;
                    bump = false;
                    player.y = ground[1][3].y - player.height;
                    isonground = true;
                    absprung = false;

                } else if ((player.x + player.width - wall[1][4].x) < (player.y + player.height - wall[1][4].y)) {

                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    player.x = wall[1][4].x;
                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x -= 5;
                    } while (player.intersects(wall[1][4]));


                    if (!isonground) {
                        bump = true;
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    }
                }
            } else if (player.intersects(ground[1][3])) {


                if (gravity > 1 && !absprung) {
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                    k++;

                    vxPlayer = 0;
                    vyPlayer = 0;
                }

                gravity = 0;
                bump = false;
                player.y = ground[1][3].y - player.height;
                isonground = true;
                absprung = false;

            } else if (player.intersects(wall[1][4]) && !right && !left) {

                player.x = wall[1][4].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x -= 5;

                } while (player.intersects(wall[1][4]));


                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }

            }


            if (player.intersects(ground[1][4]) && player.intersects(wall[1][4]) && !right && !left) {     //siehe png Datei

                if ((player.x + player.width - wall[1][4].x) > (player.y + player.height - wall[1][4].y)) {

                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }

                        vxPlayer = 0;
                        vyPlayer = 0;
                    }
                    gravity = 0;
                    bump = false;
                    player.y = ground[1][4].y - player.height;
                    isonground = true;
                    absprung = false;

                } else if ((player.x + player.width - wall[1][4].x) < (player.y + player.height - wall[1][4].y)) {

                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    player.x = wall[1][4].x;

                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x -= 5;
                    } while (player.intersects(wall[1][4]));


                    if (!isonground) {
                        bump = true;
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    }
                }
            }

            if (player.intersects(wall[1][0]) && isonground) {

                vxPlayer = 0;
                player.x = wall[1][0].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x += 5;
                } while (player.intersects(wall[1][0]));

            } else if (player.intersects(wall[1][2]) && isonground) {

                vxPlayer = 0;
                player.x = wall[1][2].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x -= 5;
                } while (player.intersects(wall[1][2]));
            }

            if (player.intersects(wall[1][1]) && isonground) {
                vxPlayer = 0;
                player.x = wall[1][1].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x += 5;
                } while (player.intersects(wall[1][1]));

            } else if (player.intersects(wall[1][3]) && isonground) {
                vxPlayer = 0;
                player.x = wall[1][3].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x -= 5;
                } while (player.intersects(wall[1][3]));
            }

            if (player.intersects(wall[1][1]) && !isonground) {

                player.x = wall[1][1].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x += 5;

                } while (player.intersects(wall[1][1]));


                vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                bump = true;
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                    clip.start();
                } catch (Exception exc) {
                    exc.printStackTrace(System.out);
                }
            }

            if (player.intersects(wall[1][3]) && !isonground) {

                player.x = wall[1][3].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x -= 5;
                } while (player.intersects(wall[1][3]));


                vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                bump = true;
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                    clip.start();
                } catch (Exception exc) {
                    exc.printStackTrace(System.out);
                }
            }

            player.x += vxPlayer;  // Neue Ballposition berechen
            player.y -= vyPlayer;

            grafik.repaint();   //Neuzeichnen
        }
    }


    public void collisionRectangle() {


        isonground = false;

        for (int i = 0; i < 20; i = i + 2) {


            if (player.intersects(ground[stage][i]) && player.intersects(wall[stage][i]) && !right && !left) {     //siehe png Datei

                if ((player.x + player.width - wall[stage][i].x) > (player.y + player.height - wall[stage][i].y)) {

                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }

                        vxPlayer = 0;
                        vyPlayer = 0;
                    }
                    gravity = 0;
                    bump = false;
                    player.y = ground[stage][i].y - player.height;
                    isonground = true;
                    absprung = false;

                } else if ((player.x + player.width - wall[stage][i].x) < (player.y + player.height - wall[stage][i].y)) {

                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    player.x = wall[stage][i].x;

                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x -= 5;
                    } while (player.intersects(wall[stage][i]));

                    if (!isonground) {
                        bump = true;
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    }
                }
            } else if (player.intersects(ground[stage][i + 1]) && player.intersects(wall[stage][i]) && !right && !left) {     //siehe png Datei

                if (gravity > 1 && !absprung) {
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }

                    vyPlayer = 0;
                }
                gravity = 0;
                player.y = ground[stage][i + 1].y;
                isonground = true;
                absprung = false;

            } else if (player.intersects(ground[stage][i + 1]) && player.intersects(wall[stage][i + 1]) && !right && !left) {     //siehe png Datei

                if ((wall[stage][i + 1].x - player.x) > (wall[stage][i + 1].y - player.y)) {

                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }

                        vyPlayer = 0;
                    }
                    gravity = 0;

                    player.y = ground[stage][i + 1].y;


                    isonground = true;
                    absprung = false;

                } else if ((wall[stage][i + 1].x - player.x) > (wall[stage][i + 1].y - player.y)) {

                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    player.x = wall[stage][i + 1].x;

                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x += 5;
                    } while (player.intersects(wall[stage][i + 1]));


                    if (!isonground) {
                        bump = true;
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    }
                }

            } else if (player.intersects(ground[stage][i]) && player.intersects(wall[stage][i + 1]) && !right && !left) {     //siehe png Datei

                if ((wall[stage][i + 1].x + wall[stage][i + 1].width - player.x) > (player.y + player.height - wall[stage][i + 1].y)) {

                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                        vxPlayer = 0;
                        vyPlayer = 0;
                    }

                    gravity = 0;
                    bump = false;
                    player.y = ground[stage][i].y - player.height;
                    isonground = true;
                    absprung = false;

                } else if ((wall[stage][i + 1].x + wall[stage][i + 1].width - player.x) < (player.y + player.height - wall[stage][i + 1].y)) {

                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    player.x = wall[stage][i + 1].x;

                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x += 5;
                    } while (player.intersects(wall[stage][i + 1]));

                    if (!isonground) {
                        bump = true;
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    }
                }
            } else if (player.intersects(ground[stage][i]) && player.intersects(wall[stage][i + 2])) {

                absprung = true;
                vxPlayer = 0;
                player.x = wall[stage][i + 2].x - player.width;

            } else if (player.intersects(ground[stage][i]) && player.intersects(wall[stage][i + 3])) {

                absprung = true;
                vxPlayer = 0;
                player.x = wall[stage][i + 3].x + 1;

            } else {

                if (player.intersects(wall[stage][i]) && !right && !left) {

                    player.x = wall[stage][i].x;

                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x -= 5;
                    } while (player.intersects(wall[stage][i]));

                    if (!isonground) {
                        bump = true;
                        vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    } else {
                        vxPlayer = 0;
                    }
                }

                if (player.intersects(ground[stage][i + 1])) {

                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }

                        vyPlayer = 0;
                    }

                    gravity = 0;
                    bump = false;
                    player.y = ground[stage][i + 1].y;
                    isonground = true;
                    absprung = false;
                }

                if (player.intersects(wall[stage][i + 1]) && !right && !left) {

                    player.x = wall[stage][i + 1].x;

                    do
                    {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                        player.x += 5;
                    } while (player.intersects(wall[stage][i + 1]));
                    if (!isonground) {
                        bump = true;
                        vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                    } else {
                        vxPlayer = 0;
                    }
                }
                if (player.intersects(ground[stage][i])) {


                    if (gravity > 1 && !absprung) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("sounds\\land.wav")));    //Sound abspielen
                            clip.start();
                        } catch (Exception exc) {
                            exc.printStackTrace(System.out);
                        }
                        k++;
                        vxPlayer = 0;
                        vyPlayer = 0;
                    }
                    gravity = 0;
                    bump = false;
                    player.y = ground[stage][i].y - player.height;
                    isonground = true;
                    absprung = false;
                }
            }
            if (wall[stage][i + 2] == null) {
                i = 12;

                break;
            } else if (wall[stage][i + 1] == null) {
                i = 12;

                break;
            }

            if (isonground && player.intersects(ground[stage][i + 2])) {

                gravity = 0;
                bump = false;
                player.y = ground[stage][i].y - player.height;


                vxPlayer = 0;                    //Vx invertieren
                player.x = wall[stage][i + 2].x - player.width;


                isonground = true;
                absprung = false;
            }
        }
    }


    public void collision2() {

        collisionRectangle();

        if (player.intersects(wall[2][100])) {

            if (isonground) {
                vxPlayer = 0;
                player.x = wall[2][100].x + 1;
                right = false;
                left = false;

            } else {

                player.x = wall[2][100].x + 1;
                right = false;
                left = false;

                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        } else if (player.intersects(wall[2][99])) {


            if (isonground) {
                vxPlayer = 0;
                player.x = wall[2][99].x - 1 - player.width;
                right = false;
                left = false;

            } else {


                player.x = wall[2][99].x - 1 - player.width;
                right = false;
                left = false;

                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        }

        player.x += vxPlayer;  // Neue Ballposition berechen
        player.y -= vyPlayer;

        grafik.repaint();   //Neuzeichnen
    }


    public void collision3() {

        collisionRectangle();

        if (player.intersects(wall[3][100])) {

            if (isonground) {
                vxPlayer = 0;
                player.x = wall[3][100].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x += 5;
                } while (player.intersects(wall[3][100]));
            } else {

                player.x = wall[3][100].x;

                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x += 5;
                } while (player.intersects(wall[3][100]));


                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        } else if (player.intersects(wall[3][99])) {

            if (isonground) {
                vxPlayer = 0;
                player.x = wall[3][99].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x -= 5;
                } while (player.intersects(wall[3][99]));
            } else {

                player.x = wall[3][99].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x -= 5;

                } while (player.intersects(wall[3][99]));
                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        }
        player.x += vxPlayer;  // Neue Ballposition berechen
        player.y -= vyPlayer;

        grafik.repaint();   //Neuzeichnen
    }

    public void collision4() {

        collisionRectangle();

        if (player.intersects(wall[3][100])) {

            if (isonground) {
                vxPlayer = 0;
                player.x = wall[3][100].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x += 5;
                } while (player.intersects(wall[3][100]));
            } else {


                player.x = wall[3][100].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x += 5;

                } while (player.intersects(wall[3][100]));
                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        } else if (player.intersects(wall[3][99])) {

            if (isonground) {
                vxPlayer = 0;
                player.x = wall[3][99].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x -= 5;
                } while (player.intersects(wall[3][99]));
            } else {

                player.x = wall[3][99].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x -= 5;
                } while (player.intersects(wall[3][99]));

                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        }

        player.x += vxPlayer;  // Neue Ballposition berechen
        player.y -= vyPlayer;
        grafik.repaint();   //Neuzeichnen
    }

    public void collision5() {

        collisionRectangle();

        if (player.intersects(wall[5][100])) {

            if (isonground) {
                vxPlayer = 0;
                player.x = wall[5][100].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berÃ¼hrt
                    player.x += 5;
                } while (player.intersects(wall[5][100]));
            } else {

                player.x = wall[5][100].x;

                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x += 5;
                } while (player.intersects(wall[5][100]));

                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        } else if (player.intersects(wall[5][99])) {

            if (isonground) {
                vxPlayer = 0;
                player.x = wall[5][99].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x -= 5;
                } while (player.intersects(wall[5][99]));
            } else {


                player.x = wall[5][99].x;

                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt

                    player.x -= 5;

                } while (player.intersects(wall[5][99]));
                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        }

        if (player.intersects(wall[stage][11]) && !right && !left) {


            player.x = wall[stage][11].x;

            do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                player.x += 5;
            } while (player.intersects(wall[stage][11]));


            if (!isonground) {
                bump = true;
                vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                    clip.start();
                } catch (Exception exc) {
                    exc.printStackTrace(System.out);
                }
            } else {
                vxPlayer = 0;
            }
        }

        player.x += vxPlayer;  // Neue Ballposition berechen
        player.y -= vyPlayer;

        grafik.repaint();   //Neuzeichnen
    }


    public void collision6() {

        collisionRectangle();

        if (player.intersects(wall[6][100])) {

            if (isonground) {
                vxPlayer = 0;
                player.x = wall[6][100].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x += 5;
                } while (player.intersects(wall[6][100]));
            } else {

                player.x = wall[6][100].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x += 5;
                } while (player.intersects(wall[6][100]));


                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        } else if (player.intersects(wall[6][99])) {

            if (isonground) {
                vxPlayer = 0;
                player.x = wall[6][99].x;
                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt

                    player.x -= 5;

                } while (player.intersects(wall[6][99]));
            } else {


                player.x = wall[6][99].x;

                do {                                //Spieler nach rechts verschieben bis er nichtmehr die Wand berührt
                    player.x -= 5;
                } while (player.intersects(wall[6][99]));

                if (!isonground) {
                    bump = true;
                    vxPlayer = -vxPlayer / 2;                    //Vx invertieren
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File("sounds\\wall.wav")));    //Sound abspielen
                        clip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                } else {
                    vxPlayer = 0;
                }
            }
        }

        player.x += vxPlayer;  // Neue Ballposition berechen
        player.y -= vyPlayer;

        grafik.repaint();   //Neuzeichnen

    }


    //Mainmethode
    public static void main(String[] args) {
        new JumpKing();
    }

    //Maus- und Key-Listener Maus und Tastendruckerkennung
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {   //Wenn Rechts losgelassen wird nicht springen!
            right = false;

            if (isonground) {
                vxPlayer = 0;
            }
            //space=false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Wenn Links losgelassen wird nicht springen!
            left = false;
            if (isonground) {
                vxPlayer = 0;
            }

            //space=false;

        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !shop) {   //Spiel starten, wenn Leertaste gedrückt wird UND Spiel nicht gestartet ist

            if (auswahlMenue == 1) {
                initGame();

                gestartet = true;
                stage = 1;
                menu = false;
            } else if (auswahlMenue == 2) {
                menu = false;
                shop = true;

            } else if (auswahlMenue == 3) {
                System.exit(0);
            } // end of if
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {   //Spiel starten, wenn Leertaste gedrückt wird UND Spiel nicht gestartet ist

            if (!gestartet && !menu && !shop) {
                initGame();
                gestartet = true;

            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE && isonground) {   //Player springt

            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File("sounds\\jump.wav")));    //Sound abspielen
                clip.start();
            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }

            //Timer (wie lange leertaste gedrückt wurde) wird umgerechent (limitiert, minimiert)

            if (timer > 40) {
                timer = 40;
            } else if (timer < 40 && timer >= 20) {
                timer = 35;
            } else if (timer <= 20) {
                timer = 20;
            }


            if (timer == 20) {
                vyPlayer = 8;
            } else {

                vyPlayer = timer - 8;   //Berechnung der Y-Geschwindigkeit
            }


            //Hight wird noch nicht benutzt (für später)
            height = timer * 20;


            if (left) {    //Spieler springt nach links


                if (timer == 20) {
                    vxPlayer = (-timer / 2) - 7;
                } else {
                    vxPlayer = -timer / 4;   //Berechnung der X-Geschwindigkeit minus, da nach links //Gut für max Sprünge
                }
                faceleft = true;
                faceright = false;
            }

            if (right) {        //Spieler springt nach Rechts

                if (timer == 20) {
                    vxPlayer = (timer / 2) + 7;
                } else {
                    vxPlayer = timer / 4;   //Berechnung der X-Geschwindigkeit minus, da nach links //Gut für max Sprünge
                }
                faceleft = false;
                faceright = true;
            }

            k = 0;
            timer = 0;          //Werte werden resetet
            absprung = true;
            left = false;
            right = false;
            isonground = false;

        } // end of if

        space = false;
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_M) {       // Abfrage Tastenkombi um ins Menü aus dem Spiel zu kommen
            menu = true;
            stage = 0;
            grafik.repaint();
            gestartet = false;
            verloren = true;
            shop = false;
            shop1 = false;
            shop2 = false;
        } // end of if


        if (e.getKeyCode() == KeyEvent.VK_DOWN && gestartet && !menu && !shop) {          //Eine Stage nach oben (Cheat)
            stage++;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN && menu && !gestartet && !shop) {
            auswahlMenue++;

        } // end of if
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            auswahlMenue = auswahlMenue - 1;

        } // end of if
        if (e.getKeyCode() == KeyEvent.VK_UP && shop && shop1 || shop2) {
            auswahl = auswahl - 1;
        } // end of if
        if (e.getKeyCode() == KeyEvent.VK_DOWN && shop) {
            auswahl++;
        } // end of if

        if (e.getKeyCode() == KeyEvent.VK_SPACE && isonground) {          //Space wird gedrückt
            space = true;
        }


        if (e.getKeyCode() == KeyEvent.VK_LEFT && isonground && !right) {   //Left_Arrow Key wird gedrückt

            left = true;

            if (!space) {            //Spieler nach links bewegen ohne Sprung, wenn leertaste nicht gedrückt

                vxPlayer = -5;
            } // end of if
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && isonground && !left) {   //Right_Arrow Key wird gedrückt

            right = true;

            if (!space) {          //Spieler nach rechts bewegen ohne Sprung, wenn leertaste nicht gedrÃ¼ckt

                vxPlayer = 5;

            } // end of if
        }
    }
}