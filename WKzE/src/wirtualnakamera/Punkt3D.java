/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera;

/**
 *
 * @author rafal
 */
public class Punkt3D extends Punkt2D {

    public double x;
    public double y;
    public double z;
    double w;

    public Punkt3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }

    public Punkt3D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Punkt3D(double[][] wektorWsp) {
        this.x = wektorWsp[0][0];
        this.y = wektorWsp[1][0];
        this.z = wektorWsp[2][0];
        this.w = wektorWsp[3][0];
        
    }

    public double[][] wektorWsp() {
        double t[][] = {
            {this.x},
            {this.y},
            {this.z},
            {this.w}
        };
        return t;
    }

    void normalizuj() {
        this.x = this.x / this.w;
        this.y = this.y / this.w;
        this.z = this.z / this.w;
        this.w = this.w / this.w;
    }

    void przemnozPrzezMacierzINormalizuj(Macierz mac) {
        double[][] wsp = Macierz.multiply(mac.macierz, this.wektorWsp());
        this.noweWspolrzedne(wsp[0][0], wsp[1][0], wsp[2][0], wsp[3][0]);
        this.normalizuj();
    }
    
    public void noweWspolrzedne(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public String toString() {
        return "Punkt3D{" + "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + '}' + "\n";
    }
}
