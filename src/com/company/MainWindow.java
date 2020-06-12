package com.company;

import javax.swing.*;

class Main extends JFrame {
    public Main(){
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(335, 350);
        setLocation(400, 400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args){
        Main mw = new Main();
    }
}
