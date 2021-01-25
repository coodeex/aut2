/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 *
 * @author matia
 */
// sdsahdasdh
public class Trejectory {

    ArrayList<Vector3f> points;
    int index;// ‘points’listan indeksi

    int size = 0;// kuinka monta waypointtia ‘points’listassa on

    // alustaa yllämainitut points ja index muuttujat
    public void Trejectory() {
        index = 0;
        points = new ArrayList<Vector3f>();
    }

    // lisää pisteen listan hännille
    public void addPoint(Vector3f v) {
        
        //System.out.println(points);
        //System.out.println(v);
        points.add(v);
         //System.out.println("jälkeen"+points);
    }

    // nollaa indeksin ja asettaa size muuttujalle oikean arvon
    public void initTrajectory() {
        index = 0;
        size = points.size();//palauttaa int nron kuinka pitkä arrayList points on
    }

    // palauttaa indexin kohdalla olevan pisteen tai null jos ei enää pisteitä
    public Vector3f nextPoint() {
        
        if (index<size) {
            index++;
            System.out.println("eka piste :"+points.get(index-1));
            return points.get(index - 1);
        } else {
            return null;
        }

    }
}
