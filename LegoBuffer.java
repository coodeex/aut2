/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
        box = new Box(16f, yExtent, 8f);
        geom = new Geometry("Box", box);

        Material mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.LightGray);
        rootNode.attachChild(geom);
        
        geom.setMaterial(mat);
        
        
        // tason pinnan y koordinaatti lasketaan Main.floorHeight + yExtentavulla
        surfaceHeight = Main.floorHeight + 2*yExtent;
        // laitetaan varastotaso siten että pohja on tuotantosolun lattian korkeudella
        System.out.println("työtaso"+surfaceHeight);
        geom.setLocalTranslation(x,Main.floorHeight+yExtent , z);
        String colorLego = "red";
        // punainenlego tulee vain jos koodissasi on bugi
        // laitetaan tasolle rowSize*columnSize legoa. Vuorotellen eri värisiä. Muista
        // Lego konstruktori hyväksyy nämä: ”yellow”, ”blue”, ”pink”, ”green”

        for (int i = 0; i < (rowSize * columnSize / 4); i++) {
            for (int j = 0; j < 4; j++) {
                // asetetaan colorLego arvo, vuorotellen eri väri
                if(j==0){
                colorLego= "yellow";
                }   
                else if(j==1){
                colorLego= "blue";
                }
                else if(j==2){
                colorLego= "pink";
                }
                else if(j==3){
                colorLego= "green";
                }
                Lego lego = new Lego(assetManager,colorLego );
                rootNode.attachChild(lego.node);
               

                // luodaan lego, lisätään se legos ArrayListiin,liitetään rootNodeen
                legos.add(lego);
                
            }
        }
        for (int i = 0; i < (rowSize * columnSize); i++) {
             System.out.println("legon paikka :"+getLegoCenterLocation(i));
            legos.get(i).node.setLocalTranslation(getLegoCenterLocation(i));
        }
    }

    // legon x koordinaatti suhteessa LegoBuffer keskipisteeseen. ”legos” listan eka
    // lego on paikassa rowIndex=0 columnIndex=0. Listan toka lego on paikassa
    // rowIndex=0 columnIndex=1jne
    private float xCoord(int index) {
        int rowIndex = index % rowSize;
        return (rowIndex - rowSize / 2) * legoSpacingX;
    }
    // legon zkoordinaatti suhteessa LegoBuffer keskipisteeseen. ”legos” listan eka
    // lego on paikassa rowIndex=0 columnIndex=0. Listan toka lego on paikassa
    // rowIndex=0 columnIndex=1 jne

    private float zCoord(int index) {
        int columnIndex = index / rowSize;
        return (columnIndex - columnSize / 2) * legoSpacingZ;
    }
    // palauttaa legos listan “index” kohdan legon (keskipisteen) koordinaatit
    // palauta maailma-koordinaatit eli huomioi x, z ja surfaceHeight
    // 0.2f on y-etäisyys pöydän pinnasta legon keskipisteeseen
    // käytä xCoord() ja yCoord() metodeja

    private Vector3f getLegoCenterLocation(int index) {
       
        return new Vector3f(x + xCoord(index), surfaceHeight + 0.2f, z + zCoord(index));
    }

    public float getSurfaceHeight() {
        return surfaceHeight;
    }

}
