package mygame;

import com.jme3.app.SimpleApplication;

import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.light.PointLight;
import com.jme3.math.Vector3f;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();

    }
    public static float floorHeight = -15;
   public AssemblyStation Station; 

    @Override
    public void simpleInitApp() {
        
        
        flyCam.setMoveSpeed(10);

        RobotArm Arm = new RobotArm(assetManager, rootNode);

        rootNode.attachChild(Arm.node);
        Arm.node.attachChild(Arm.tooltipNode);

        Station = new AssemblyStation(assetManager, rootNode, 5, -11, Arm);
        rootNode.attachChild(Station.node);
       
        
        LegoBuffer pufferi= new LegoBuffer(assetManager, rootNode, 5, -29, 10, 6);
        
        /*Lego lego1 = new Lego(assetManager, "yellow");
        rootNode.attachChild(lego1.node);
        lego1.node.setLocalTranslation(2f, 0, 0);

        Lego lego2 = new Lego(assetManager, "pink");
        rootNode.attachChild(lego2.node);
        lego2.node.setLocalTranslation(4f, 0, 0);

        Lego lego3 = new Lego(assetManager, "");
        rootNode.attachChild(lego3.node);
        lego3.node.setLocalTranslation(6f, 0, 0);

        Lego lego4 = new Lego(assetManager, "blue");
        rootNode.attachChild(lego4.node);
        lego4.node.setLocalTranslation(8f, 0, 0);*/

        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White);
        lamp_light.setRadius(400f);
        lamp_light.setPosition(new Vector3f(2f, 8.0f, 10.0f));
        rootNode.addLight(lamp_light);
        Station.initTestMove(new Vector3f(7, -1, -31));
    }

    @Override
    public void simpleUpdate(float tpf) {
       Station.move();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
