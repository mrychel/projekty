/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera;

/**
 *
 * @author rafal
 */
public class Punkt2D {
    public int x;
    public int y;
    public double macierzysteZ;

    public Punkt2D(int x, int y, double macierzysteZ) {
        
        this.x = x;
        this.y = y;
        this.macierzysteZ = macierzysteZ;
    }

    public Punkt2D() {
    }

    @Override
    public String toString() {
        return "Punkt2D{" + "x=" + x + ", y=" + y + '}';
    }
    
    
}
