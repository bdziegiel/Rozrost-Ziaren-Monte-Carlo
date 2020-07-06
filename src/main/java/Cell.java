
import java.util.Random;

class Cell {

    int state = 0;
    int sameColor = 0;
    int energy = 0;
    int R = 250;
    int G = 250;
    int B = 250;

    Cell(){}
    Cell(int x){ this.state = x;}

    void set_random() {
        this.state = 1;
        Random r = new Random();
        this.R = r.nextInt(250);
        this.G = r.nextInt(250);
        this.B = r.nextInt(250);
    }

    void repeatColor(Cell p) {
        this.R = p.R;
        this.G = p.G;
        this.B = p.B;
    }

    boolean colorMatch(Cell p) {
        if (this.R==250 && this.G==250 && this.B==250) return false;
        return (this.R == p.R && this.G == p.G && this.B == p.B);
    }
}
