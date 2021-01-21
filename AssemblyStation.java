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

/**
 *
 * @author matia
 */
public class AssemblyStation {

    Node node = new Node();
    Geometry geom;
    Box box;
    
    float maxHeight = 4; // max korkeus reitin välietapeille
    boolean moving = false; // true jos matkalla seuraavaan välietappiin
    Trejectory trajectory;
    RobotArm assemblyArm;

    public AssemblyStation(AssetManager assetManager, Node rootNode, float xOffset, float zOffset, RobotArm Arm) {
        assemblyArm = Arm;
        float yExtent = 6;   
        box = new Box(20, yExtent, 10);
        geom = new Geometry("Box", box);
        node.attachChild(geom);
       
        Material mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.LightGray);
        geom.setMaterial(mat);
        geom.setLocalTranslation(xOffset, Main.floorHeight + yExtent, zOffset);
    }
    // tehdään APP eli reitinsuunnittelu destination koordinaatteihin
    public void initTestMove(Vector3f destination) {
        trajectory = new Trejectory();
        // eka välietappi suoraan ylös max korkeuteen
        Vector3f v1 = assemblyArm.getToolTipLocation();
        v1.setY(maxHeight);
        trajectory.addPoint(v1);
        // toka välietappi max korkeuteen destination ylle
        
        v1.setZ(destination.getZ());
        v1.setX(destination.getX());
        trajectory.addPoint(v1);
        
        trajectory.addPoint(destination);
        trajectory.initTrajectory();
    }
    // käskyttää robottia ajamaan reitin, joka on määritelty trajectory-attribuuttiin
    // palauttaa false jos saavutettiin trajectory viimeinen (väli)etappi, eli
    // initTestMove() saama destination. Muuten palauttaa true.
    // tätä tulee kutsua syklisesti kunnes se palauttaa false

    public boolean move() {
        if (moving) {
            moving = assemblyArm.move();
            return true;
        } else {
            // tänne tullaan jos edellinen välietappi saavutettiin
            Vector3f nextPoint = trajectory.nextPoint();
            if (nextPoint == null) {
                return false;
            } else {
                // debug printit tulee konsoliin näkyviin kun suljet ohjelman
                System.out.println(nextPoint.toString());
                // annetaan robotille seuraava välietappi ja alustetaan moving seuraavaa
                // move() kutsua silmälläpitäen
                //???
                return true;
            }
        }
    }
}
