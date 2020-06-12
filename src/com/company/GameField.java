package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 320;
    private final int DOTSIZE = 16;
    private final int ALLDOTS = 400;
    private Image snake;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALLDOTS];
    private int[] y = new int[ALLDOTS];
    private int snakes;
    private Timer timer;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.green);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        snakes = 3;
        for (int i = 0; i < snakes; i++) {
            x[i] = 48 - i* DOTSIZE;
            y[i] = 48;
        }
        timer = new Timer(250,this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)* DOTSIZE;
        appleY = new Random().nextInt(20)* DOTSIZE;
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            snakes++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = snakes; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("snake.png");
        snake = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < snakes; i++) {
                g.drawImage(snake,x[i],y[i],this);
            }
        } else{
            String str = "Game Over";
            //Font f = new Font("Arial",16,Font.BOLD);
            g.setColor(Color.black);
            // g.setFont(f);
            g.drawString(str,125,SIZE/2);
        }
    }

    public void move(){
        for (int i = snakes; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0] -= DOTSIZE;
        }
        if(rightDirection){
            x[0] += DOTSIZE;
        } if(upDirection){
            y[0] -= DOTSIZE;
        } if(downDirection){
            y[0] += DOTSIZE;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if(key == KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if(key == KeyEvent.VK_UP && !downDirection){
                rightDirection = false;
                upDirection = true;
                leftDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && !upDirection){
                rightDirection = false;
                downDirection = true;
                leftDirection = false;
            }
        }
    }


}

