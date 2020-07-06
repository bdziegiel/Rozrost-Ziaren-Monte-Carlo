import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

class Controller extends JFrame {

    private Integer boardHeight, boardWidth = 0;
    private int zarodkowanie, iteracje;
    private Board b;
    private Form f;

    Controller(Form f){
        this.f = f;
    }

    void initController(){
        f.getStartButton().addActionListener(buttonListener);
        f.getResetButton().addActionListener(buttonListener);
        f.getSetBoardButton().addActionListener(buttonListener);
        f.getMCbutton().addActionListener(buttonListener);
        f.getEnergiaButton().addActionListener(buttonListener);
        f.getJednorodne().addItemListener(itemListener);
        f.getLosowe().addItemListener(itemListener);
        f.getzPromieniem().addItemListener(itemListener);
    }

    private ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(f.getJednorodne().isSelected()) {
                f.getXnum().setEnabled(true);
                f.getYnum().setEnabled(true);
                f.getZarodki().setEnabled(false);
                f.getPromien().setEnabled(false);

                zarodkowanie = 0;
            }
            if(f.getLosowe().isSelected()) {
                f.getXnum().setEnabled(false);
                f.getYnum().setEnabled(false);
                f.getZarodki().setEnabled(true);
                f.getPromien().setEnabled(false);

                zarodkowanie = 2;
            }
            if(f.getzPromieniem().isSelected()) {
                f.getXnum().setEnabled(false);
                f.getYnum().setEnabled(false);
                f.getZarodki().setEnabled(true);
                f.getPromien().setEnabled(true);
                zarodkowanie= 1;
            }
        }
    };

    private ActionListener buttonListener = e -> {
        if(e.getActionCommand().equals("Ustaw planszę")) setBoard();
        if(e.getActionCommand().equals("Start")) start();
        if(e.getActionCommand().equals("Reset")) reset();
        if(e.getActionCommand().equals("Monte Carlo")) {
            iteracje = Integer.parseInt(f.getIteracje().getValue().toString());
            mcStart();
        }
        if(e.getActionCommand().equals("Wizualizacja Energii")){
            b.setEnergyPanel(f.getEnergyPanel());
            b.showEnergy();
            f.getMyFrame().setVisible(true);
        }
    };

    private void mcStart(){
        final Timer myTimer1 = new Timer();
        TimerTask task1 = new TimerTask() {

            @Override
            public void run() {
                System.out.println("it"+iteracje);
                b.monteCarlo();
                b.repaint();
                iteracje--;
                if(iteracje==0) myTimer1.cancel();
            }
        };

        myTimer1.schedule(task1, 1, 1);
    }

    private void setBoard(){
        if(f.getS100().isSelected()) setGridSize(100,100);
        else if(f.getS900().isSelected()) setGridSize(900,900);
        else if(f.getS300().isSelected()) setGridSize(300,300);
        else setGridSize(300,100);
        f.getS().setEnabled(false);
        f.getS100().setEnabled(false);
        f.getS300().setEnabled(false);
        f.getS900().setEnabled(false);
        f.getSetBoardButton().setEnabled(false);

        Integer w = f.getGraphicPanel().getWidth();
        Integer h = f.getGraphicPanel().getHeight();

        b = new Board(w, h, boardWidth, boardHeight);
        f.getGraphicPanel().add(b, BorderLayout.CENTER); //wyswietla pusta plansze
        f.getMyFrame().setVisible(true);
    }

    private void start(){
        final Timer myTimer = new Timer();
        if(!f.getSetBoardButton().isSelected()) setBoard();

        b.setSasiedztwo(f.getWyborSasiedztwa().getSelectedIndex());
        b.setIloscZarodkow(Integer.parseInt(f.getZarodki().getValue().toString()));
        b.setPromien(Integer.parseInt(f.getPromien().getValue().toString()));
        b.setXY(Integer.parseInt(f.getXnum().getValue().toString()),Integer.parseInt(f.getYnum().getValue().toString()) );
        if(f.getPeriod().isSelected())b.setBC(1); else b.setBC(0);
        b.zarodkowanie(zarodkowanie);

        TimerTask task = new TimerTask() {

            @Override
            public void run() { //wykonuje się dopóki nie zapełni się cała plansza
                b.start();
                b.repaint();
                if(b.isFull) myTimer.cancel();
            }
        };

        myTimer.schedule(task, 1, 100);
        f.getMCbutton().setEnabled(true);
        f.getEnergiaButton().setEnabled(true);
    }

    private void reset(){
        f.getGraphicPanel().removeAll();
        f.getEnergyPanel().removeAll();
        f.getGraphicPanel().repaint();
        f.getEnergyPanel().repaint();
        f.getS().setEnabled(true);
        f.getS100().setEnabled(true);
        f.getS300().setEnabled(true);
        f.getS900().setEnabled(true);
        f.getSetBoardButton().setEnabled(true);

    }
    private void setGridSize(Integer width, Integer height){
        this.boardWidth = width;
        this.boardHeight = height;
    }
}