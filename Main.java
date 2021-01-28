package mygame;

import com.jme3.app.SimpleApplication;

import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.light.PointLight;
import com.jme3.math.Vector3f;
import java.util.ArrayList;

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

    boolean freeze = false; // debug tarkoituksiin – laita true siinä kohdassa koodia

    // mihin haluat robotin pysähtyvän
    boolean moving = false; // true kun robotti liikkuu
    boolean goingToLego = false; // true kun mennään hakemaan legoa bufferista
    Lego lego;
    int slotIndex = 0; // kokoonpanoaseman slot
    final int numColors = 4; // final on sama kuin C-kielen const
    int colorIndex = 0; // “colors” (kts alla) listan indeksi
    // listan perusteella tiedetään missä järjestyksessä lajitellaan värin mukaan
    ArrayList<String> colors = new ArrayList(numColors);

    @Override
    public void simpleInitApp() {

        flyCam.setMoveSpeed(10);

        RobotArm Arm = new RobotArm(assetManager, rootNode);

        rootNode.attachChild(Arm.node);
        Arm.node.attachChild(Arm.tooltipNode);

        Station = new AssemblyStation(assetManager, rootNode, 5, -11, Arm);
        rootNode.attachChild(Station.node);

        LegoBuffer pufferi = new LegoBuffer(assetManager, rootNode, 5, -29, 10, 6);

        System.out.println(pufferi.legos);

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

        //Station.initTestMove(new Vector3f(7, -1, -31)); //HUOM: Poista initTestMove() kutsu.
        colors.add("yellow");
        // lisää muut värit tehtävänannon mukaisessa järjestyksessä
        //???

    }

    @Override
    public void simpleUpdate(float tpf) {
        //Station.move(); tää rivi kommentoitu pois ku lisättiin part3 koodi:
        if (!freeze && moving) {
            moving = Station.move();
        }
        if (!moving && !freeze) {
            // moving=false tarkoittaa että saavuttiin reitin päähän, joten on 2 tapausta:
            // otetaan lego mukaan tai jätetään se
            if (goingToLego) { // otetaan lego mukaan
                // nyt ollaan bufferilla sen legon kohdalla mikä otetaan mukaan
                // v:hen laitetaan kokoonpanoaseman slot numero ”slotIndex” koordinaatit
                //Vector3f v =  ?  ?  ?
                slotIndex++;
                // suoritetaan APP kohteeseen v
                // ?  ?  ? 
                goingToLego = false;
                moving = true;
            } else { // jätetään lego tähän
                if (lego != null) { // käynnistyksen yhteydessä tätä koodia ei suoriteta
                    // lego on nyt toimitettu oikeaan paikkaan kokoonpanoasemalle
                    // otetaan paikka talteen ennen kuin irrotetaan noodi
                    Vector3f loc = lego.node.getWorldTranslation();
                    // irrota legon node tooltipin nodesta
                    // (tämä on pitkä rimpsu jossa käytetään monen olion nimeä
                    //???
                    lego.node.setLocalTranslation(loc);
                    // legon node ei ole nyt kiinni missään nodessa, joten se ei tule
                    // näkyviin ennen kuin korjaat asian
                    //?  ?  ?
                }
                // haetaan bufferista seuraava lego, jonka väri on: colors.get(colorIndex)
                // eli päivitä muuttujan ’lego’ arvo
                //???
                moving = true;
                if (lego == null) {
                    // bufferissa ei ole enempää tämänvärisiä legoja
                    colorIndex++;
                    if (colorIndex >= numColors) {
                        // kaikki legot on siirretty
                        freeze = true; // tämän jälkeen mitään ei tapahdu
                    } else {
                        // haetaan bufferista seuraava lego, jonka väri on:
                        // colors.get(colorIndex)
                        //?  ?  ?
                    }
                }
                if (!freeze) {
                    Station.initMoveToLego(lego);
                }
                goingToLego = true;
            }
        }

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
