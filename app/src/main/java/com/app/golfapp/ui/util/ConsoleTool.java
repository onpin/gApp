package com.app.golfapp.ui.util;

import com.app.golfapp.ui.mapaid.MapAid;

import org.w3c.dom.Element;
import org.w3c.dom.Node;


import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConsoleTool {

    static MapAid mapAid;
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        File xml = new File("resources/maps/bayview.kml");
        mapAid = new MapAid(xml);
        // Todo: Get text only. Not all children as text
        Element documentElement;
        if(mapAid.docRoot.getElementsByTagName("Document").item(0).getNodeType() == Node.ELEMENT_NODE){ // Should only have one match.
            documentElement = (Element) mapAid.docRoot.getElementsByTagName("Document").item(0);
            List<Element> folders = XMLHelper.getChildElementsByTag(documentElement, "Folder");
            for(Element e : folders){
                System.out.println(MapAid.getElementName(e));
            }
        }else{
            // Todo : Throw some exception.
        }

        // This can be removed. Only here for testing:
        Pattern regex = Pattern.compile("(?<=(H))0*[1-9][0-9]*");
        Matcher matcher = regex.matcher("509283405820934");//"H00000001532135TZ2"
        matcher.find();
        System.out.println(Integer.parseInt(matcher.group()));

        regex = Pattern.compile("[Hh]ole *[Oo]verlays");
        matcher = regex.matcher("dgfHole            overlaysfhsdhdfh sdfgsdfg");
        if (matcher.find()){
            System.out.println(matcher.group());
        }

        System.out.println(mapAid.docRoot.getElementsByTagName("gx:LatLonQuad").getLength());

    }


}
