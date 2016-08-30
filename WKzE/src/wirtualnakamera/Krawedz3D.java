/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera;

import java.util.ArrayList;

/**
 *
 * @author rafal
 */
public class Krawedz3D {
    Punkt3D punkt1;
    Punkt3D punkt2;
    int nr_sciany1;
    int nr_sciany2;

    public Krawedz3D(Punkt3D punkt1, Punkt3D punkt2, int nr_sciany1, int nr_sciany2) {
        this.punkt1 = punkt1;
        this.punkt2 = punkt2;
        this.nr_sciany1 = nr_sciany1;
        this.nr_sciany2 = nr_sciany2;
    }

    public Krawedz3D(Punkt3D punkt1, Punkt3D punkt2) {
        this.punkt1 = punkt1;
        this.punkt2 = punkt2;

    }

    @Override
    public String toString() {
        return "Krawedz3D{" + "punkt1=" + punkt1 + ", punkt2=" + punkt2 + ", nr_sciany1=" + nr_sciany1 + ", nr_sciany2=" + nr_sciany2 + '}' + "\n";
    }
    


    
    
    
}
