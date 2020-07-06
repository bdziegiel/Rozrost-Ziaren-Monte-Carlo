
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Board extends JPanel {

    private Map<Integer, int[]> patterns;
    private Integer Xcells, Ycells, X, Y;
    private Integer numOfCellsX, numOfCellsY;
    private Cell[][] currCells, prevCells;
    private int sasiedztwo, zarodki, promien, bc;
    boolean isFull = false;
    JPanel energyPanel;

    Board(Integer w, Integer h, Integer numOfCellsX, Integer numOfCellsY) {
        this.numOfCellsX = numOfCellsX;
        this.numOfCellsY = numOfCellsY;

        Xcells = w / numOfCellsX;
        Ycells = h / numOfCellsY;

        currCells = new Cell[numOfCellsX][numOfCellsY];
        prevCells =  new Cell[numOfCellsX][numOfCellsY];

        patterns = new HashMap<>();
        patterns.put(0, new int[] { 1, 1, 1, 1, 1, 1, 1, 1 }); // Moore
        patterns.put(1, new int[] { 0, 1, 0, 1, 0, 1, 0, 1 }); // von Neumann
        patterns.put(2, new int[] { 1, 1, 0, 1, 1, 1, 0, 1 }); // Heksagonalne lewe
        patterns.put(3, new int[] { 0, 1, 1, 1, 0, 1, 1, 1 }); //               prawe
        patterns.put(4, new int[] {}); // Heksagonalne losowe
        patterns.put(5, new int[] { 1, 1, 0, 0, 0, 1, 1, 1 }); // Pentagonalne lewe
        patterns.put(6, new int[] { 0, 1, 1, 1, 1, 1, 0, 0 }); // prawe
        patterns.put(7, new int[] { 1, 1, 1, 1, 0, 0, 0, 1 }); // górne
        patterns.put(8, new int[] { 0, 0, 0, 1, 1, 1, 1, 1 }); // dolne
        patterns.put(9, new int[] {}); // Pentagonalne losowe

        for (int i = 0; i < numOfCellsX; i++)
            for (int j = 0; j < numOfCellsY; j++) {
                currCells[i][j] = new Cell();
                prevCells[i][j] = new Cell();
            }
    }

    void start(){
        int x = iteration();
        if(x==0) isFull = true;
        setAsPrev();
    }

    void zarodkowanie(int zarodkowanie) {
        Random r = new Random();
        int x, y;

        switch (zarodkowanie) {
            case 0:  //jednorodne

                int xSize = X;
                int ySize = Y;
                int xWidth = numOfCellsX/xSize;
                int yHeight = numOfCellsY/ySize;
                for (int i=0; i<xSize; i++) {
                    for(int j=0; j<ySize; j++) {
                        int stepX = (i*2+1) * xWidth/2;
                        int stepY = (j*2+1) * yHeight/2;

                        do
                            currCells[stepX][stepY].set_random();
                        while (colorExists(currCells[stepX][stepY],stepX,stepY) );
                    }
                }
                break;
            case 1:  //z promieniem
                List<Point> list = new ArrayList<>(); //pomocnicza lista wszystkich punktow
                for (int i = 0; i < numOfCellsX; i++)
                    for (int j = 0; j < numOfCellsY; j++)
                        list.add(new Point(i, j));

                for (int i = 0; i < zarodki; i++) {
                    if (list.size() < 1) return;  //koniec miejsca
                    Point p = list.get(r. nextInt(list.size())); //szukam losowego punktu
                    do
                        currCells[p.x][p.y].set_random(); //losowy kolor
                    while (colorExists(currCells[p.x][p.y], p.x, p.y)); // szukam jeszcze nie użytego koloru
                    list.removeIf(v -> p.isInsideRadius(v, promien)); //punkt w obrębie promienia wiec usuwam
                }
                break;
            case 2:  //losowe
                for (int i = 0; i < zarodki; i++) {
                    x = r.nextInt(numOfCellsX);
                    y = r.nextInt(numOfCellsY);
                    currCells[x][y].set_random();
                    while (colorExists(currCells[x][y], x, y))
                        currCells[x][y].set_random(); //szukam nowego koloru
                }
                break;
        }
    }

    private int iteration() {
        int x = 0;
        Cell tmp;

        for (int i = 0; i <= numOfCellsX - 1; i++)
            for (int j = 0; j <= numOfCellsY - 1; j++) {
                if(currCells[i][j].state==1) continue;
                x++;
                tmp = findNeighbour(i, j); //szukam żywego sasiada
                currCells[i][j].state = tmp.state;
                currCells[i][j].repeatColor(tmp); //pobieram kolor od sąsiada
                currCells[i][j].sameColor++;
            }
        return x; //ilosc wolnego miejsca po iteracji
    }

    private Cell findNeighbour(int x, int y) {
        int[] s = {0, 0, 0, 0, 0, 0, 0, 0}; //sasiedzi
        int[] s_pattern; //wybrany wzor

        int left = x - 1;
        int right = x + 1;
        int top = y - 1;
        int bottom = y + 1;

        if (bc == 1) {
            if (left < 0) left = numOfCellsX - 1;
            if (right > numOfCellsX - 1) right = 0;
            if (top < 0) top = numOfCellsY - 1;
            else if (bottom > numOfCellsY - 1) bottom = 0;
        }

        // ustalam tablice sasiadow
        if (left >= 0 && top >= 0) s[0] = prevCells[left][top].state;
        if (top >= 0) s[1] = prevCells[x][top].state;
        if (top >= 0 && right <= numOfCellsX - 1) s[2] = prevCells[right][top].state;

        if (right <= numOfCellsX - 1)  s[3] = prevCells[right][y].state;
        if (bottom <= numOfCellsY - 1 && right <= numOfCellsX - 1) s[4] = prevCells[right][bottom].state;

        if (bottom <= numOfCellsY - 1)   s[5] = prevCells[x][bottom].state;

        if (left >= 0 && bottom <= numOfCellsY - 1) s[6] = prevCells[left][bottom].state;
        if (left >= 0) s[7] = prevCells[left][y].state;

        Cell newCells = new Cell(0);

        Random r = new Random();
        if (sasiedztwo == 4) s_pattern = patterns.get(r.nextInt(2) + 2);
        else if(sasiedztwo == 5) s_pattern = patterns.get(r.nextInt(4)+ 5);
        else s_pattern = patterns.get(sasiedztwo);

        if (s[0] == 1 && s_pattern[4] == 1) return currCells[left][top];
        else if (s[1] == 1 && s_pattern[5] == 1) return currCells[x][top];
        else if (s[2] == 1 && s_pattern[6] == 1) return currCells[right][top];
        else if (s[3] == 1 && s_pattern[7] == 1) return currCells[right][y];
        else if (s[4] == 1 && s_pattern[0] == 1) return currCells[right][bottom];
        else if (s[5] == 1 && s_pattern[1] == 1) return currCells[x][bottom];
        else if (s[6] == 1 && s_pattern[2] == 1) return currCells[left][bottom];
        else if (s[7] == 1 && s_pattern[3] == 1) return currCells[left][y];

        return newCells; //jesli nie ma sasiada, zwracam pustego
    }

    private boolean colorExists(Cell p, int x, int y) {
        for (int i = 0; i < numOfCellsX; i++) {
            for (int j = 0; j < numOfCellsY; j++) {
                if (x==i && y==j) continue;
                if (currCells[i][j].colorMatch(p)) return true;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < numOfCellsX; x++)
            for (int y = 0; y < numOfCellsY; y++) {
                g.setColor(new Color(this.currCells[x][y].R, this.currCells[x][y].G, this.currCells[x][y].B));
                g.fillRect(x * this.Xcells,y * this.Ycells, this.Xcells,this.Ycells);
            }
    }

    private void setAsPrev() {
        for (int i = 0; i < numOfCellsX; i++)
            for (int j = 0; j < numOfCellsY; j++) {
                prevCells[i][j].state = currCells[i][j].state;
                prevCells[i][j].repeatColor(currCells[i][j]);
            }
    }

    private Cell[] setNeighboursTab(int x, int y) {
        Cell[] s = new Cell[8];

        int left = x - 1;
        int right = x + 1;
        int top = y - 1;
        int bottom = y + 1;

        if (bc == 1) {
            if (left < 0) left = numOfCellsX - 1;
            if (right > numOfCellsX - 1) right = 0;
            if (top < 0) top = numOfCellsY - 1;
            else if (bottom > numOfCellsY - 1) bottom = 0;
        }

        if (left >= 0 && top >= 0)s[0] = prevCells[left][top];
        if (top >= 0) s[1] = prevCells[x][top];
        if (top >= 0 && right <= numOfCellsX - 1) s[2] = prevCells[right][top];
        if (right <= numOfCellsX - 1)  s[3] = prevCells[right][y];
        if (bottom <= numOfCellsY - 1 && right <= numOfCellsX - 1) s[4] = prevCells[right][bottom];
        if (bottom <= numOfCellsY - 1)   s[5] = prevCells[x][bottom];
        if (left >= 0 && bottom <= numOfCellsY - 1) s[6] = prevCells[left][bottom];
        if (left >= 0) s[7] = prevCells[left][y];

        return s;
    }

    private int countEnergy(int x, int y){
        Cell[] s = setNeighboursTab(x,y);
        int[]s_pattern;

        Random r = new Random();
        if (sasiedztwo == 4) s_pattern = patterns.get(r.nextInt(2)+2);
        else if(sasiedztwo == 5) s_pattern = patterns.get(r.nextInt(4)+5);
        else s_pattern = patterns.get(sasiedztwo);

        int energy=0, ii=0;
        for (Cell v : s) {
            if (s_pattern[ii]==0) continue;
            ++ii;
            if (!this.currCells[x][y].colorMatch(v)) ++energy;
        }
        return energy;
    }

    void monteCarlo(){
        List<Cell> cellList = new ArrayList<>(); //lista z której kasuje
        for (Cell[] cell : currCells) cellList.addAll(Arrays.asList(cell).subList(0, currCells[0].length));
        List<Cell> tmpList = new ArrayList<>(cellList);

        while(!cellList.isEmpty()){
            Random r = new Random();
            Cell tmpCell = cellList.get(r.nextInt(cellList.size()));

            int listIndex = tmpList.indexOf(tmpCell); //indeks potrzebny do znalezienia wspolrzednych

            int x = listIndex/currCells.length;
            int y = listIndex%currCells.length;
            int energy = countEnergy(x,y);

            if(energy>0) {
                Cell cell = new Cell();
                Cell[] sasiedzi = setNeighboursTab(x,y);
                Random z = new Random();

                    do {
                        cell.repeatColor(sasiedzi[z.nextInt(sasiedzi.length)]);
                    } while ((cell.R == 250 && cell.G == 250 && cell.B == 250)
                            || (cell.colorMatch(currCells[x][y])));

                    currCells[x][y].repeatColor(cell);
                    int newEnergy = countEnergy(x, y);
                 if (newEnergy > energy) currCells[x][y].repeatColor(prevCells[x][y]);
            }
            repaint();
            setAsPrev();
            cellList.remove(tmpCell);
        }

    }

    void showEnergy(){

        Cell[][] cells = currCells.clone();
        for(int i =0;i<cells.length; i++){
            for(int j=0;j<cells[0].length; j++){
                cells[i][j].energy=countEnergy(i,j);
            }
        }
        Energy energy = new Energy(cells,numOfCellsX,numOfCellsY, Xcells,Ycells);

        energyPanel.add(energy);
        energy.repaint();
    }


    void setSasiedztwo(int sasiedztwo){ this.sasiedztwo=sasiedztwo; }
    void setEnergyPanel(JPanel energyPanel){this.energyPanel=energyPanel;}

    void setIloscZarodkow(int zarodki){ this.zarodki = zarodki; }

    void setXY(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    void setPromien(int promien){ this.promien = promien;}

    void setBC(int bc){this.bc = bc;}

}
