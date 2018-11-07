package com.app.golfapp.ui.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.LinkedList;
import java.util.List;

/**
 * XMLHelper contain some helper methods to aid the extracting of relevant data from the XML file.
 *
 * To contain pure XML stuff only.
 */
public class XMLHelper {

    /**
     * Returns all child nodes of the input node that are Element-nodes.
     *
     * @return a List<Element> containing all Element child nodes the input node.
     */
    public static List<Element> getChildElements(Node node){
        List<Element> ret = new LinkedList<Element>();
        NodeList allChilds = node.getChildNodes();
        for(int i=0; i < allChilds.getLength(); i++){
            if(allChilds.item(i).getNodeType() == Node.ELEMENT_NODE){
                ret.add( (Element) allChilds.item(i));
            }else{
                // Do nothing.
            }
        }
        return ret;
    }

    /**
     * Returns all Element child nodes of the Node node with the given tag String.
     *
     * @param node Node to work from.
     * @param tag  String , the tag string to retain.
     * @return List<Element> List of elements with the given tag.
     */
    public static List<Element> getChildElementsByTag(Node node, String tag){
        List<Element> ret = XMLHelper.getChildElements(node);
        List<Element> removeList = new LinkedList<Element>();
        for(Element e : ret){
            if(!e.getTagName().equals(tag)) {
                removeList.add(e);
            }else{
                // Do nothing.
            }
        }
        ret.removeAll(removeList);
        return ret;
    }
    /**
     * Returns the first Element child node of the Node node with the given tag String.
     *
     * @param node Node to work from.
     * @param tag  String , the tag string to retain.
     * @return Element , An element with the input tag or null if none is found.
     */
    public static Element getChildElementByTag(Node node, String tag){
        List<Element> ret = XMLHelper.getChildElements(node);
        for(Element e : ret){
            if(e.getTagName().equals(tag)){
                return e;
            }
        }
        return null;
    }

    /**
     * Tries to return the text-field from an element as it is described by a text-node. Returns null for zero length
     * String or if a text-node is not found.
     *
     * @param e - Element to retrieve text from.
     * @return String containing the text-node child's value or null if it does not exist or is of zero length.
     */
    public static String getElementText(Element e){
        NodeList nodes = e.getChildNodes();
        String retStr = null;
        for(int i = 0; i < nodes.getLength(); i++){
            if(nodes.item(i).getNodeType() == Node.TEXT_NODE){
                retStr =  nodes.item(i).getNodeValue().trim();
                break; // Assumption: Only one text node, and it contain what we want.
            }
        }

        // Return checks
        if(retStr != null){
            // Assume it is a string
            if(retStr.length() > 0){
                return retStr;
            }else{
                return null;
            }

        }
        return null;
    }
}
