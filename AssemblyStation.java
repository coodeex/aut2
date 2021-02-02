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
    float legoSpacingX = 2; // legojen slottipaikkojen etäisyys 
    float legoSpacingZ = 2;
    float surfaceHeight;
    float x;
    float z;
    String currentLegoColor; //for stack mode
    int currentStack = -1; //for stack mode
    int currentColorIndex = 0;

    public AssemblyStation(AssetManager assetManager, Node rootNode, float xOffset, float zOffset, RobotArm Arm) {
        x = xOffset;
        z = zOffset;
        assemblyArm = Arm;
        float yExtent = 6;
        box = new Box(20, yExtent, 10);
        geom = new Geometry("Box", box);
        node.attachChild(geom);

        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.LightGray);
        geom.setMaterial(mat);
        geom.setLocalTranslation(xOffset, Main.floorHeight + yExtent, zOffset);
        surfaceHeight = Main.floorHeight + 2 * yExtent;
    }

    // APP kohteeseen lego.location
    // sama idea kuin edellisen harjoituksen
    public void initMoveToLego(Lego lego) {
        trajectory = new Trejectory();

        Vector3f v1 = assemblyArm.getToolTipLocation();
        v1.setY(maxHeight);
        trajectory.addPoint(v1);

        Vector3f v2 = new Vector3f();
        v2.setY(maxHeight);
        v2.setX(lego.location.getX());
        v2.setZ(lego.location.getZ());
        trajectory.addPoint(v2);

        trajectory.addPoint(lego.location);
        trajectory.initTrajectory();
    }

    // APP kohteeseen destination
    public void initMoveToStation(Lego lego, Vector3f destination) {
        assemblyArm.tooltipNode.attachChild(lego.node);
        lego.node.setLocalTranslation(0, -0.6f, 0);

        trajectory = new Trejectory();

        Vector3f v1 = lego.location;
        v1.setY(maxHeight);
        trajectory.addPoint(v1);

        Vector3f v2 = new Vector3f();
        v2.setY(maxHeight);
        v2.setX(destination.getX());
        v2.setZ(destination.getZ());
        trajectory.addPoint(v2);

        destination.setY(destination.getY() + 0.4f);
        trajectory.addPoint(destination);
        trajectory.initTrajectory();

// muuten lego ei lähde mukaan
        // nyt legon noden sijainti pitää määritellä nodeToolTip paikallisissa
        // koordinaateissa. lego.node.setLocalTranslation(0,0,0) laittaisi legon// keskipisteen tooltipin keskipisteeseen
        // vinkki: tooltipin yExtent = 0.4f ja legon yExtent = 0.2f???
        // sitten tehdään APP kohteeseen ”destination”
    }

    public void initTestMove(Vector3f destination) {

        // eka välietappi suoraan ylös max korkeuteen
        Vector3f v1 = assemblyArm.getToolTipLocation();
        v1.setY(maxHeight);
        trajectory.addPoint(v1);
        // toka välietappi max korkeuteen destination ylle

        Vector3f v2 = new Vector3f();
        v2.setY(maxHeight);
        v2.setX(destination.getX());
        v2.setZ(destination.getZ());

        trajectory.addPoint(v2);
        destination.setY(destination.getY() + 0.4f);
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
            //System.out.println("tähän asti päästään");

            Vector3f nextPoint = trajectory.nextPoint();
            //System.out.println("tänne ei päästä");
            if (nextPoint == null) {
                return false;
            } else {
                // debug printit tulee konsoliin näkyviin kun suljet ohjelman
                //System.out.println(nextPoint);
                // annetaan robotille seuraava välietappi ja alustetaan moving seuraavaa
                // move() kutsua silmälläpitäen

                assemblyArm.initMove(nextPoint);
                moving = true;
                return true;
            }
        }
    }

    // kokoonpanoasemalla on slotteja, joiden indeksi on kokonaisluku
    // tämä palauttaa slotin 3D koordinaatit
    public Vector3f slotPosition(int slot) {
        // vain osa asemasta on varattu tähän tarkoitukseen. Sen koko on 16x12
        int rowSize = (int) ((16) / legoSpacingX);

        int columnSize = (int) ((12) / legoSpacingZ);

        int rowIndex = slot % rowSize;

        float xOffset = (rowIndex - 1) * legoSpacingX;

        int columnIndex = slot / rowSize;

        float zOffset = (columnIndex + 2) * legoSpacingZ;

        float yOffset = 0.4f;

        // legonyExtent
        // ’x’ja ’z’onfloat muuttujia, joihin on tallennettu konstruktorin xOffset/zOffset
        // laske ’surfaceHeight’ konstruktorissa
        return new Vector3f(x + xOffset, surfaceHeight + yOffset, z + zOffset - 12);
        //return null;
    }

    public Vector3f stackSlotPosition(int slot, String legoColor) {
        if (!legoColor.equals(currentLegoColor)) {
            currentStack++;
            currentLegoColor = legoColor;
            currentColorIndex = 0;
        } else {
            currentColorIndex++;
        }

        //System.out.println("slot"+slot);
        // vain osa asemasta on varattu tähän tarkoitukseen. Sen koko on 16x12
        int rowSize = (int) ((8) / legoSpacingX);

        int rowIndex = currentStack % rowSize;

        float xOffset = (rowIndex - 1) * legoSpacingX;

        float yOffset = 0.4f;

        // legonyExtent
        // ’x’ja ’z’onfloat muuttujia, joihin on tallennettu konstruktorin xOffset/zOffset
        // laske ’surfaceHeight’ konstruktorissa
        return new Vector3f(x + xOffset, surfaceHeight + yOffset + currentColorIndex * 0.4f, z );
        //return null;
    }
}
