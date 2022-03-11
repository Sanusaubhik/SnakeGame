package SnakeGame;

import javax.swing.*;
public class Snake extends JFrame{
Snake(){

add(new Board());
pack();

setLocationRelativeTo(null);
setTitle("Snake Game");
//setIconImage(new ImageIcon().getImage());
setResizable(false);
}



public static void main(String args[]){
    new Snake().setVisible(true);
}




}