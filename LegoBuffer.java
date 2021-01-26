/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;

/**
 *
 * @author matia
 */
public class LegoBuffer {

    private Box box;
    private Geometry geom;
    private float surfaceHeight;
    ArrayList<Lego> legos = new ArrayList(500);
    float x;
    float z;
    private float legoSpacingX = 2;
    private float legoSpacingZ = 2;
    int rowSize;
    int columnSize;
    // luo varastotason koordinaatteihin x=xOffset, z=zOffset (y-korkeus siten että se
    // on solun lattialla. Legoja on riveissa ja sarakkeissa, joihin mahtuu ”rowSize”
    // * ”columnSize” legoja

    public LegoBuffer(AssetManager assetManager, Node rootNode, float xOffset, float zOffset, int rowSize, int columnSize) {
            // tallennetaan parametrien arvot olion yllämääriteltyihin muuttujiin, jotta
         // ne olisivat käytössä sen jälkeen kun tämä konstruktori metodi on suoritettu
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        x = xOffset;
        z = zOffset;    // buffer on box jonka yExtent on:
        float yExtent = 7;
            // luo ColorRGBA.LightGray värinen box jonka koko on (16f, yExtent, 8f)
            // luo sille geometria ja liitä se rootNodeen???
            // tason pinnan y koordinaatti lasketaan Main.floorHeight + yExtentavulla
        surfaceHeight = 9999;
            // laitetaan varastotaso siten että pohja on tuotantosolun lattian korkeudella
        geom.setLocalTranslation(x, 9999, z);
        String colorLego = "red";
            // punainenlego tulee vain jos koodissasi on bugi
            // laitetaan tasolle rowSize*columnSize legoa. Vuorotellen eri värisiä. Muista
            // Lego konstruktori hyväksyy nämä: ”yellow”, ”blue”, ”pink”, ”green”
        for (int i = 0; i < (rowSize * columnSize / 4); i++) {
            for (int j = 0; j < 4; j++) {
                // asetetaan colorLego arvo, vuorotellen eri väri

                Lego lego;
                // luodaan lego, lisätään se legos ArrayListiin,liitetään rootNodeen

            }
        }
        for (int i = 0; i < (rowSize * columnSize); i++) {
            legos.get(i).node.setLocalTranslation(getLegoCenterLocation(i));
        }
    }

        // legon x koordinaatti suhteessa LegoBuffer keskipisteeseen. ”legos” listan eka
        // lego on paikassa rowIndex=0 columnIndex=0. Listan toka lego on paikassa
        // rowIndex=0 columnIndex=1jne
    private float xCoord(int index) {
        int rowIndex = index % rowSize;
        return (rowIndex -rowSize/2) * legoSpacingX;
        }
        // legon zkoordinaatti suhteessa LegoBuffer keskipisteeseen. ”legos” listan eka
        // lego on paikassa rowIndex=0 columnIndex=0. Listan toka lego on paikassa
        // rowIndex=0 columnIndex=1 jne
    private float zCoord(int index) {
        return 32;
    }
        // palauttaa legos listan “index” kohdan legon (keskipisteen) koordinaatit
        // palauta maailma-koordinaatit eli huomioi x, z ja surfaceHeight
        // 0.2f on y-etäisyys pöydän pinnasta legon keskipisteeseen
        // käytä xCoord() ja yCoord() metodeja
    private Vector3f getLegoCenterLocation(int index) {
        return new Vector3f(x+xCoord(index), surfaceHeight + 0.2f, z+zCoord(index));
    }

    public float getSurfaceHeight() {
        return surfaceHeight;
    }

}
