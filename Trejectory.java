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
public class Trejectory {

    ArrayList<Vector3f> points;
    int index;// ‘points’listan indeksi

    int size;// kuinka monta waypointtia ‘points’listassa on

    // alustaa yllämainitut points ja index muuttujat
    public void Trajectory() {
        index = 0;
        points = new ArrayList<Vector3f>();
    }

    // lisää pisteen listan hännille
    public void addPoint(Vector3f v) {
        points.add(v);
    }

    // nollaa indeksin ja asettaa size muuttujalle oikean arvon
    public void initTrajectory() {
        index = 0;
        size = points.size();
    }

    // palauttaa indexin kohdalla olevan pisteen tai null jos ei enää pisteitä
    public Vector3f nextPoint() {
        if (points.get(index) == null) {
            return points.get(index);
        }else {
            return null;}
        
    }//tänne kirjotin nyt näin
    //tähän yks rivi lisää näin
}
