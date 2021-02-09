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

    boolean stackMode = true;

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
    LegoBuffer pufferi;
    int legoBufferX;
    int legoBufferZ;
    int assemblyStationX;
    int assemblyStationZ;
    
    /////
    /////
    
    boolean stackMode2 = false;

    public static float floorHeight2 = -15;
    public AssemblyStation Station2;

    boolean freeze2 = false; // debug tarkoituksiin – laita true siinä kohdassa koodia

    // mihin haluat robotin pysähtyvän
    boolean moving2 = false; // true kun robotti liikkuu
    boolean goingToLego2 = false; // true kun mennään hakemaan legoa bufferista
    Lego lego2;
    int slotIndex2 = 0; // kokoonpanoaseman slot
    final int numColors2 = 4; // final on sama kuin C-kielen const
    int colorIndex2 = 0; // “colors” (kts alla) listan indeksi
    // listan perusteella tiedetään missä järjestyksessä lajitellaan värin mukaan
    ArrayList<String> colors2 = new ArrayList(numColors2);
    LegoBuffer pufferi2;
    int legoBuffer2X;
    int legoBuffer2Z;
    int assemblyStation2X;
    int assemblyStation2Z;
    AmlRead AmlRead = new AmlRead();
    int[] coordinateArray;//xzxzxzxz
    

    @Override
    public void simpleInitApp() {

        flyCam.setMoveSpeed(20);

        RobotArm Arm = new RobotArm(assetManager, rootNode);

        rootNode.attachChild(Arm.node);
        Arm.node.attachChild(Arm.tooltipNode);
        
        coordinateArray= AmlRead.AmlRead();
        //System.out.println(": coordinateArrayyyyyyyyzzzzzz"+ coordinateArray);
        for(int c = 0;c<8;c++){
            System.out.println("coordinateArray["+c+"] "+ coordinateArray[c]);
        }
        
        pufferi = new LegoBuffer(assetManager, rootNode, coordinateArray[0], coordinateArray[1], 10, 6);

        Station = new AssemblyStation(assetManager, rootNode, coordinateArray[2], coordinateArray[3], Arm);
        rootNode.attachChild(Station.node);

        

        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White);
        lamp_light.setRadius(400f);
        lamp_light.setPosition(new Vector3f(2f, 8.0f, 10.0f));
        rootNode.addLight(lamp_light);

        //Station.initTestMove(new Vector3f(7, -1, -31)); //HUOM: Poista initTestMove() kutsu.
        colors.add("yellow");
        // lisää muut värit tehtävänannon mukaisessa järjestyksessä
        colors.add("blue");
        colors.add("pink");
        colors.add("green");
        
        
        ///////////////////////////
        ///////////////////////////
        
        
        RobotArm Arm2 = new RobotArm(assetManager, rootNode);

        rootNode.attachChild(Arm2.node);
        Arm2.node.attachChild(Arm2.tooltipNode);
        
        pufferi2 = new LegoBuffer(assetManager, rootNode, coordinateArray[4], coordinateArray[5], 10, 6);

        Station2 = new AssemblyStation(assetManager, rootNode, coordinateArray[6], coordinateArray[7], Arm2);
        rootNode.attachChild(Station2.node);

        /*PointLight lamp_light2 = new PointLight();
        lamp_light.setColor(ColorRGBA.White);
        lamp_light.setRadius(400f);
        lamp_light.setPosition(new Vector3f(2f, 8.0f, 10.0f));
        rootNode.addLight(lamp_light2);*/

        //Station.initTestMove(new Vector3f(7, -1, -31)); //HUOM: Poista initTestMove() kutsu.
        colors2.add("yellow");
        // lisää muut värit tehtävänannon mukaisessa järjestyksessä
        colors2.add("blue");
        colors2.add("pink");
        colors2.add("green");

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
                Vector3f v;
                if (stackMode) {
                    v = Station.stackSlotPosition(slotIndex, lego.color);
                } else {
                    v = Station.slotPosition(slotIndex);
                }
                slotIndex++;

                // suoritetaan APP kohteeseen v
                Station.initMoveToStation(lego, v);
                goingToLego = false;
                moving = true;
            } else { // jätetään lego tähän
                if (lego != null) { // käynnistyksen yhteydessä tätä koodia ei suoriteta
                    // lego on nyt toimitettu oikeaan paikkaan kokoonpanoasemalle
                    // otetaan paikka talteen ennen kuin irrotetaan noodi
                    Vector3f loc = lego.node.getWorldTranslation();
                    // irrota legon node tooltipin nodesta
                    // (tämä on pitkä rimpsu jossa käytetään monen olion nimeä

                    Station.assemblyArm.tooltipNode.detachChild(lego.node);

                    lego.node.setLocalTranslation(loc);
                    // legon node ei ole nyt kiinni missään nodessa, joten se ei tule
                    // näkyviin ennen kuin korjaat asian
                    rootNode.attachChild(lego.node);
                }
                // haetaan bufferista seuraava lego, jonka väri on: colors.get(colorIndex)
                // eli päivitä muuttujan ’lego’ arvo                    rootNode.attachChild(lego.node);
                lego = pufferi.giveLego(colors.get(colorIndex));
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
                        lego = pufferi.giveLego(colors.get(colorIndex));
                    }
                }
                if (!freeze) {
                    Station.initMoveToLego(lego);
                }
                goingToLego = true;
            }
        }
        
        ///////////////
        ///////////////

        //Station.move(); tää rivi kommentoitu pois ku lisättiin part3 koodi:
        if (!freeze2 && moving2) {
            moving2 = Station2.move();
        }
        if (!moving2 && !freeze2) {
            // moving=false tarkoittaa että saavuttiin reitin päähän, joten on 2 tapausta:
            // otetaan lego mukaan tai jätetään se
            if (goingToLego2) { // otetaan lego mukaan

                // nyt ollaan bufferilla sen legon kohdalla mikä otetaan mukaan
                // v:hen laitetaan kokoonpanoaseman slot numero ”slotIndex” koordinaatit
                Vector3f v;
                if (stackMode2) {
                    v = Station2.stackSlotPosition(slotIndex2, lego2.color);
                } else {
                    v = Station2.slotPosition(slotIndex2);
                }
                slotIndex2++;

                // suoritetaan APP kohteeseen v
                Station2.initMoveToStation(lego2, v);
                goingToLego2 = false;
                moving2 = true;
            } else { // jätetään lego tähän
                if (lego2 != null) { // käynnistyksen yhteydessä tätä koodia ei suoriteta
                    // lego on nyt toimitettu oikeaan paikkaan kokoonpanoasemalle
                    // otetaan paikka talteen ennen kuin irrotetaan noodi
                    Vector3f loc = lego2.node.getWorldTranslation();
                    // irrota legon node tooltipin nodesta
                    // (tämä on pitkä rimpsu jossa käytetään monen olion nimeä

                    Station2.assemblyArm.tooltipNode.detachChild(lego2.node);

                    lego2.node.setLocalTranslation(loc);
                    // legon node ei ole nyt kiinni missään nodessa, joten se ei tule
                    // näkyviin ennen kuin korjaat asian
                    rootNode.attachChild(lego2.node);
                }
                // haetaan bufferista seuraava lego, jonka väri on: colors.get(colorIndex)
                // eli päivitä muuttujan ’lego’ arvo                    rootNode.attachChild(lego.node);
                lego2 = pufferi2.giveLego(colors2.get(colorIndex2));
                //???
                moving2 = true;
                if (lego2 == null) {
                    // bufferissa ei ole enempää tämänvärisiä legoja
                    colorIndex2++;
                    if (colorIndex2 >= numColors2) {
                        // kaikki legot on siirretty
                        freeze2 = true; // tämän jälkeen mitään ei tapahdu
                    } else {
                        // haetaan bufferista seuraava lego, jonka väri on:
                        // colors.get(colorIndex)
                        //?  ?  ?
                        lego2 = pufferi2.giveLego(colors2.get(colorIndex2));
                    }
                }
                if (!freeze2) {
                    Station2.initMoveToLego(lego2);
                }
                goingToLego2 = true;
            }
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
