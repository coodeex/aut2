/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
//import com.jme3.scene.Node;
import java.io.File;
import java.util.jar.Attributes.Name;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author juhan
 */
public class AmlRead {

    public int[] AmlRead() {
        int intArray[];
        intArray = new int[20];
        


    // TODO Auto-generated method stub
    try {
      File inputFile = new File(getClass().getResource("tuotantosolu.aml").getPath());
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      // System.out.println("Root element :" +
      // doc.getDocumentElement().getNodeName());
      NodeList InternalElements = doc.getElementsByTagName("InternalElement");
      // System.out.println(InternalElements.getLength());
      // InstanceHierarchy.getElementsByTagName("InstanceHierarchy");
      int i=0;

      for (int temp = 0; temp < InternalElements.getLength(); temp++) {
        Node nNode = InternalElements.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          String RefBaseSystemUnitPath = eElement.getAttribute("RefBaseSystemUnitPath");
          if ("SystemUnitClassLib/Cell".equals(RefBaseSystemUnitPath)) {
            //System.out.println(eElement.getAttribute("Name") + ":");
            NodeList CellInternalElements = nNode.getChildNodes();
            // System.out.println("CellInternalElements pituus: " +
            // CellInternalElements.getLength());

            for (int temp1 = 0; temp1 < CellInternalElements.getLength(); temp1++) {
              Node nNode1 = CellInternalElements.item(temp1);
              if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement1 = (Element) nNode1;
                String Name = eElement1.getAttribute("Name");// esim legobuffer
                // System.out.print(Name + ": ");
                NodeList Coordinates = eElement1.getElementsByTagName("Attribute");

                for (int temp2 = 0; temp2 < Coordinates.getLength(); temp2++) {
                  Node nNode2 = Coordinates.item(temp2);
                  if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement2 = (Element) nNode2;
                    String Name2 = eElement2.getAttribute("Name");// esim X
                    NodeList Value = eElement2.getElementsByTagName("Value");
                    Element valueElement = (Element) Value.item(0);
                    // System.out.print(Name2 + ": " + valueElement.getTextContent() + "; ");
                    //System.out.print(" "+i+" "+valueElement.getTextContent());
                    
                    //Integer.parseInt(valueElement.getTextContent());
                    intArray[i]= Integer.parseInt(valueElement.getTextContent());
                    i++;
                    //System.out.print("iiiiiii"+intArray[i]);
                  }
                }
                //System.out.println();

              }
            }

            // Node nNode1 = CellInternalElements.item(temp);
            // if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
            // Element eElement1 = (Element) nNode1;
            // // String Name = eElement1.getAttribute("Name");// esim legobuffer
            // System.out.println("elementi nimi: " + eElement1.getAttribute("Name"));
            // }

          }
        }
      }
    } catch (

    Exception e) {
      e.printStackTrace();
    }
  



        
        

        
        return intArray;

        /*aasdffa=[666,666,666];
        return aasdffa;*/
    }
}
