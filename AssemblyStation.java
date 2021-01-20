/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
    
    

    public AssemblyStation(AssetManager assetManager, Node rootNode, float xOffset, float zOffset) {
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

}
