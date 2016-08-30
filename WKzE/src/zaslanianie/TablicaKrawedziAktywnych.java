/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zaslanianie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rafal
 */
public class TablicaKrawedziAktywnych {

    TablicaKrawedzi tablicaKrawedzi;
    public ArrayList<AktywnaKrawedz> lista;

    public TablicaKrawedziAktywnych(TablicaKrawedzi tk, int wysokoscEkranu) {
        this.lista = new ArrayList<AktywnaKrawedz>();
        this.tablicaKrawedzi = tk;
    }

    public List<AktywnaKrawedz> NastepneAktywneKrawedzie(int y) {  //if null?
//        ArrayList<AktywnaKrawedz> listaUsuwanych = new ArrayList<AktywnaKrawedz>();
        ArrayList<AktywnaKrawedz> nowaLista = new ArrayList<AktywnaKrawedz>();

      /*  for (AktywnaKrawedz ak : lista) {
            if (ak.y_gornego > y) {
                listaUsuwanych.add(ak);
            } 
        }*/
            
        for (AktywnaKrawedz ak : lista) {       //zachowanie krawedzi
            if (ak.y_gornego > y) {            //ktore pozostaja aktywne
                nowaLista.add(ak);
            }
        }

      //  lista.removeAll(listaUsuwanych);
        lista = nowaLista;
        
        for (AktywnaKrawedz ak : lista) {
            if (ak.pionowa == false) {
                ak.x_przeciecia += ak.c;
            }
        }

        ArrayList<TablicaKrawedzi.Krawedz> tab = tablicaKrawedzi.lista.get(y);  //if null?
        if (tab != null) {
            for (TablicaKrawedzi.Krawedz kr : tab) {
                lista.add(new AktywnaKrawedz(kr));
            }
        }
        //if (tab != null) tab.clear();     //po co to?
        Collections.sort(lista);


        return lista;
    }

    /*
     * public TablicaKrawedziAktywnych(TablicaKrawedzi tk, int wysokoscEkranu) {
     * listalista = new ArrayList<ArrayList<TablicaKrawedzi.Krawedz>>(); for
     * (int i = 0; i < wysokoscEkranu ; i++) { listalista.set(i, null); }
     *
     * for (int i = 0; i < wysokoscEkranu ; i++) {
     * ArrayList<TablicaKrawedzi.Krawedz> l = tk.lista.get(i); if (l == null) {
     * continue; } for () } for (ArrayList<TablicaKrawedzi.Krawedz> kra :
     * tk.lista) { for (TablicaKrawedzi.Krawedz kr : kra) {
     *
     * }
     * }
     * }
     *
     */
    public class AktywnaKrawedz implements Comparable {

        @Override
        public String toString() {
            return "AktywnaKrawedz{" + "x_dolnego=" + x_dolnego + ", y_dolnego=" + y_dolnego + ", x_gornego=" + x_gornego + ", y_gornego=" + y_gornego + ", x_przeciecia=" + x_przeciecia + '}' + "\n";
        }
/*
        @Override
        public String toString() {
            return "AktywnaKrawedz{" + "x_dolnego=" + x_dolnego + ", y_dolnego=" + y_dolnego + ", x_gornego=" + x_gornego + ", y_gornego=" + y_gornego + ", c=" + c + ", nr_wielokata1=" + nr_wielokata1 + ", nr_wielokata2=" + nr_wielokata2 + ", pionowa=" + pionowa + ", x_przeciecia=" + x_przeciecia + '}';
        }
*/
        
        int x_dolnego;
        int y_dolnego;
        int x_gornego;
        int y_gornego;
        float c;
        int nr_wielokata1;
        int nr_wielokata2;
        boolean pionowa;
        float x_przeciecia;

        public AktywnaKrawedz(TablicaKrawedzi.Krawedz kr) {
            this.x_dolnego = kr.x_dolnego;
            this.y_dolnego = kr.y_dolnego;
            this.x_gornego = kr.x_gornego;
            this.y_gornego = kr.y_gornego;
            this.c = kr.c;
            this.nr_wielokata1 = kr.nr_wielokata1;
            this.nr_wielokata2 = kr.nr_wielokata2;
            this.pionowa = kr.pionowa;
            this.x_przeciecia = (float) this.x_dolnego;
        }

        @Override
        public int compareTo(Object o) {
            AktywnaKrawedz ak = (AktywnaKrawedz) o;
            if (this.x_przeciecia < ak.x_przeciecia) {
                return -1;
            } else if (this.x_przeciecia == ak.x_przeciecia) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
