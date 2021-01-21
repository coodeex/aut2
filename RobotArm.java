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
    
    private Vector3f targetLocation; // välietappi
    float step = 0.1f; // etäisyys akselia kohden mikä liikutaan yhden syklin aikana
    
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
    // target on välietappi johon kuuluu ajaa
    public void initMove(Vector3f target) {
        targetLocation = target;
    }
    // palauttaa tooltipin alapinnan keskipisteen koordinaatit maailma-koordinaateissa
    // käytä Geometry luokan getWorldTranslation()

    public Vector3f getToolTipLocation() {
        //?  ?  ?
    }
    // moves towards target location and returns false when it reached the location

    public boolean move() {
        Vector3f location = getToolTipLocation();
        // lasketaan etäisyys määränpäähän maailma-koordinaateissa
        float xDistance = targetLocation.getX() - location.getX();
        float zDistance =  targetLocation.getZ() - location.getZ();
        float  yDistance =  targetLocation.getY() - location.getY(); 
       
        // booleanit ilmaisee että onko kyseisen akselin suuntainen liike valmis        
        boolean  xReady = false;
        boolean yReady = false;
        boolean zReady = false;
        
        float x; // x-akselin suuntainen liike tämän syklin aikana
        float y; // y-akselin suuntainen liike tämän syklin aikana
        float z; // z-akselin suuntainen liike tämän syklin aikana
        
        // siirrytään stepin verran oikeaan suuntaan jos matkaa on yli stepin verran
        // muuten siirrytään targetLocationin x koordinaattiit
        if (xDistance > step) {
            x = step;
        } else if ((-1 * xDistance) > step) {
            x = -1 * step;
        } else {
            xReady = true;
            x = xDistance;
        }
        
        if (zDistance > step) {
            z = step;
        } else if ((-1 * zDistance) > step) {
            z = -1 * step;
        } else {
            zReady = true;
            z = zDistance;
        }

        if (yDistance > step) {
            y = step;
        } else if ((-1 * yDistance) > step) {
            y = -1 * step;
        } else {
            yReady = true;
            y = yDistance;
        }
        
        // siirretään mastossa kiinni oleva zArm, joka liikkuu siis z-suuntaan
        // 0.5f siitä syystä että robotti ulottuu paremmin (xArm liikuu zArmia pitkin)
        Vector3f v = new Vector3f(0, 0, 0.5f * z);
        geoz.setLocalTranslation(geoz.getLocalTranslation().add(v));
        
        // xArm on zArmin varassa minkä lisäksi se liikkuu sitä pitkin, joten nyt
        // käytetä 0.5f kerrointa kuten äsken
        Vector3f v1 = new Vector3f(0, 0, z);
        geox.setLocalTranslation(geox.getLocalTranslation().add(v1));
        
        // yArm liikkuu xArm Pitkin x suuntaan ja tekee myös y-suuntaisen liikkeen,
        // minkä lisäksi zArmin liike siirtää myös yArmia
        Vector3f v2 = new Vector3f(x, y, z);
        geoy.setLocalTranslation(geoy.getLocalTranslation().add(v2));
        
        // nodetoolTip paikaksi on määritelty yArm alapinta, mutta nodetoolTipin parent
        // noodi ei liiku, joten nodetoolTip pitää siirtää kuten yArm
        // samalla liikkuu nodetoolTippiin liitetty tooltipin geometria
        tooltipNode.setLocalTranslation(tooltipNode.getLocalTranslation().add(? ? ?));
        
        if ((yReady && xReady) && zReady) {
            return false; //i.e. not moving anymore
        } else {
            return true;
        }
    }
}
