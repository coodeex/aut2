package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;

public class Lego {

    Node node = new Node();
    Geometry geom;
    Box box;
    String color;
    Vector3f location ;

    public Lego(AssetManager assetManager, String color) {
        box = new Box(0.8f, 0.2f, 0.4f);
        ColorRGBA c;
        geom = new Geometry("Box", box);
        node.attachChild(geom);

        if (color.equals("green")) {
            c = ColorRGBA.Green;
        } else if (color.equals("pink")) {
            c = ColorRGBA.Pink;
        } else if (color.equals("blue")) {
            c = ColorRGBA.Blue;
        } else if (color.equals("yellow")) {
            c = ColorRGBA.Yellow;
        } else if (color.equals("red")) {
            c = ColorRGBA.Red;
        } else {
            c = ColorRGBA.DarkGray;
        }

        Material mat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");

        mat.setBoolean(
                "UseMaterialColors", true);

        mat.setColor(
                "Diffuse", c);
        geom.setMaterial(mat);

        for (int i = 0; i < 4; i++) {
            for (int x = 0; x < 2; x++) {
                Cylinder cyl3 = new Cylinder(20, 20, 0.1f, 0.1f, true);
            Geometry g3 = new Geometry("C", cyl3);
            g3.rotate(FastMath.HALF_PI, 0, 0);
            g3.setLocalTranslation((0.6f-(0.4f*i)), 0.2f, (0.2f-(0.4f*x)));
            g3.setMaterial(mat);
            node.attachChild(g3);
            }
        }

    }
}
