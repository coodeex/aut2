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
public class RobotArm {
    Node node = new Node();
    Node tooltipNode = new Node();
    Box mast;
    Box yarm;
    Box xarm;
    Box zarm;
    Box tooltip;
    
    Geometry geomarm;
    Geometry geomTooltip;
    Geometry geox;
    Geometry geoy;
    Geometry geoz;
    
    public RobotArm(AssetManager assetManager,Node rootNode) {
        
        Material mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.Orange);
        
        
        mast = new Box(0.2f, 6f, 0.2f);
        geomarm = new Geometry("Box", mast);
        node.attachChild(geomarm);
        geomarm.setMaterial(mat);
        geomarm.setLocalTranslation(-8f,0f,-10f);
        
        yarm = new Box(0.2f, 6f, 0.2f);
        geoy = new Geometry("Box", yarm);
        node.attachChild(geoy);
        geoy.setMaterial(mat);
        geoy.setLocalTranslation(-7f,6f,0f);
        
        xarm = new Box(18f, 0.2f, 0.2f);
        geox = new Geometry("Box", xarm);
        node.attachChild(geox);
        geox.setMaterial(mat);
        geox.setLocalTranslation(6f,6f,0f);
        
        zarm = new Box(0.2f, 0.2f, 20f);
        geoz = new Geometry("Box", zarm);
        node.attachChild(geoz);
        geoz.setMaterial(mat);
        geoz.setLocalTranslation(-8f,6f,-8f);
        
        tooltip = new Box(0.14f, 0.4f, 0.14f);
        geomTooltip = new Geometry("Box", tooltip);
        tooltipNode.attachChild(geomTooltip);
        geomTooltip.setMaterial(mat);
        tooltipNode.setLocalTranslation(-7f, -0.4f, 0f);
    }
}