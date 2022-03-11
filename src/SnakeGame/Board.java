package SnakeGame;
 
import java.awt.*;
import javax.swing.*;


import  java.awt.event.*;
public class Board extends JPanel implements ActionListener{

    private Image apple;
    private Image dot;
    private Image head;

    private final int DOT_SIZE=10;
    private final int ALLDOT=360000;
    private final int RANDOM_POSITION=50;
    

    private  int apple_x;
    private  int apple_y;

    private final int x[]=new int[ALLDOT];
    private final int y[]=new int[ALLDOT];

    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;

    private boolean inGame=true;

    private int dots=0;

    private Timer timer;


    //   JMenuBar menuBar=new JMenuBar();
    //  JMenu option=new JMenu("OPTION");
   

    Board(){
        
        addKeyListener(new TAdapter());
       setPreferredSize(new Dimension(600,600)); 
       setBackground(Color.BLACK);
       setFocusable(true);
       loadImages();
       initGame();
     
          //setJMenubar(menuBar);
    }

  

    public void  loadImages(){
        ImageIcon ic1=new ImageIcon(ClassLoader.getSystemResource("SnakeGame/Icon/apple.png"));
        apple=ic1.getImage();
       ImageIcon ic2=new ImageIcon(ClassLoader.getSystemResource("SnakeGame/Icon/dot.png"));
       dot=ic2.getImage();
       ImageIcon ic3=new ImageIcon(ClassLoader.getSystemResource("SnakeGame/Icon/head.png"));
      head=ic3.getImage();
    }

    public void initGame(){
     dots=3;

     for(int z=0;z<dots;z++){
         x[z]=50 - (z * DOT_SIZE);
         y[z]=50;
     }
     locateApple();
     timer=new Timer(300,this);
     timer.start();

    }

    public void locateApple(){
        int r=(int)(Math.random()*RANDOM_POSITION);
        apple_x=(r*DOT_SIZE);
         r=(int)(Math.random()*RANDOM_POSITION);
        apple_y=(r*DOT_SIZE);
    }

    public void checkApple(){
        if(x[0]==apple_x && y[0]==apple_y){
             dots++;
             locateApple();
        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
           
            for(int t=dots-1;t>=0;t--){
                if(t==0)  g.drawImage(head, x[t], y[t], this);
                else g.drawImage(dot, x[t], y[t], this);
                
            }
            Toolkit.getDefaultToolkit().sync();

           score(g);
        }
        else
            gameOver(g);
    }
    public void score(Graphics g){
        String s=Integer.toString(dots-1);
        String masai="Score:"+s;
        Font font=new Font("SAN SERIF",Font.BOLD,15);
        FontMetrics metrices=getFontMetrics(font);

        g.setColor(Color.green);
        g.setFont(font);
        g.drawString(masai, (600-metrices.stringWidth(masai)+5)/2, 20);

    }

    public void gameOver(Graphics g){
        String msg="Game Over";
        Font font=new Font("SAN SERIF",Font.BOLD,15);
        FontMetrics metrices=getFontMetrics(font);

        g.setColor(Color.red);
        g.setFont(font);
        g.drawString("Score:"+Integer.toString(dots-1), (600-metrices.stringWidth(msg))/2+5, 600/2-15);
        g.drawString(msg, (600-metrices.stringWidth(msg))/2, 600/2);
    }

    public void checkCollision(){
        if(y[0]>599 || x[0]>599 || x[0]<0 || y[0]<0)inGame=false;

        for(int t=dots;t>0;t--){
            if(t>4 && x[0]==x[t] && y[0]==y[t])
                inGame=false;
        }
        
     if(!inGame)timer.stop();
    }

    public void move(){
        if(leftDirection){
            x[0]-=DOT_SIZE;
        }

        if(rightDirection){
            x[0]+=DOT_SIZE;
        }
        if(upDirection){
            y[0]-=DOT_SIZE;
        }
        if(downDirection){
            y[0]+=DOT_SIZE;
        }

        for(int t=dots;t>0;t--){
            x[t]=x[t-1];
            y[t]=y[t-1];
        }
    }

    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

     private class TAdapter extends KeyAdapter{
         @Override
        public void keyPressed(KeyEvent e){
           int key=e.getKeyCode();
           if(key==KeyEvent.VK_LEFT && (!rightDirection)){
               leftDirection=true;
               upDirection=false;
               downDirection=false;
           }
           if(key==KeyEvent.VK_RIGHT && (!leftDirection)){
           rightDirection=true;
            upDirection=false;
            downDirection=false;
        }
        if(key==KeyEvent.VK_UP && (!downDirection)){
            leftDirection=false;
            upDirection=true;
           rightDirection=false;
        }
        if(key==KeyEvent.VK_DOWN && (!upDirection)){
            leftDirection=false;
            rightDirection=false;
            downDirection=true;
        }


        }

     }


}

