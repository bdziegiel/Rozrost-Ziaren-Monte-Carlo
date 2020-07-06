import javax.swing.*;
import java.awt.*;

public class Energy extends JPanel {

    private Cell[][] cells;
    private Integer x,y, xCells, yCells;
    private Color[] colors;

    Energy(Cell[][]cells, Integer numOfCellsX,
           Integer numOfCellsY, Integer Xcells, Integer Ycells){

        this.cells = cells;
        x=numOfCellsX;
        y=numOfCellsY;
        xCells = Xcells;
        yCells = Ycells;

        colors = new Color[8];
        colors[0]=Color.cyan;

        for(int i=1;i<8;i++){
            colors[i]=colors[i-1].darker();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++) {
            g.setColor(this.colors[this.cells[i][j].energy]);
            g.fillRect(i * this.xCells, j * this.yCells, this.xCells, this.yCells);
        }
    }

}
