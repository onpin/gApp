package com.app.golfapp.ui.mapaid;



import com.app.golfapp.ui.util.XMLHelper;
import org.w3c.dom.*; // E-tree stuff. Apparently use enough of it for the IDE to import as * instead of class wise.
import java.io.File;            // Dealing with files
import java.io.StringReader;    // Reads a string as a char-stream.
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern; // Regex stuff.
import org.xml.sax.InputSource; //
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Class to Aid parsing of the mappin KML file. Only provides shortcuts to relevant data of the kml. This class by
 * itself does not do any smart conversions between the XML w3c.dom formats to more computer readable ones. For such
 * functionality the class could be subclassed.
 */
public class MapAid{
    /* Helper strings */
    public static final String VERSION="0.5";
    // Convenience strings
    public static final String NEWLINE="\n";

    // Todo : Evaluate if the search strings should be public, private, protected or none of the mentioned.
    // Tag search strings : KML reference : https://developers.google.com/kml/documentation/kmlreference
    public static final String SEARCH_DOC          = "Document";
    public static final String SEARCH_DESCRIPTION  = "description";
    public static final String SEARCH_NAME         = "name";
    public static final String SEARCH_FOLDER       = "Folder";
    public static final String SEARCH_PLACEMARK    = "Placemark";
    public static final String SEARCH_POINT        = "Point";
    public static final String SEARCH_COORD        = "coordinates";
    public static final String SEARCH_OVERLAY      = "GroundOverlay";
    public static final String SEARCH_LATLONQUAD   = "gx:LatLonQuad";
    public static final String SEARCH_LATLONBOX    = "LatLonBox";
    public static final String SEARCH_HREF         = "href";
    public static final String SEARCH_OPEN         = "open"; // not for searching really but is a tag-string we use.

    // Name searches Simple
    public static final String NAME_VENDOR = "Vendor";

    // Name searches Advanced
    // Regex comment: For performance keep them simple. But note that many of the are only used once.
    // Todo : At some point: Update Regexes for arbitrary number 0*[1-9][0-9]*. For now only accept formats that strictly follow the format we currently use.
    public static final Pattern REGEX_HOMEBASE              = Pattern.compile("HB[0-9]");

    public static final Pattern REGEX_HOLE                  = Pattern.compile("H[0-9][0-9]");
    public static final Pattern REGEX_HOLE_NBR              = Pattern.compile("(?<=(H))0*[1-9][0-9]*"); // Possibly unnecessarily advanced. Matches any positive integer with any number of leading zeroes.
    public static final Pattern REGEX_HOLE_TEE              = Pattern.compile("H[0-9][0-9]T[1-9]");
    public static final Pattern REGEX_HOLE_HAZARD           = Pattern.compile("H[0-9][0-9]H[1-9]");
    public static final Pattern REGEX_GREEN_FRONT           = Pattern.compile("H[0-9][0-9]F");
    public static final Pattern REGEX_GREEN_CENTER          = Pattern.compile("H[0-9][0-9]C$"); // End anchor to avoid matching with corner markers.
    public static final Pattern REGEX_GREEN_BACK            = Pattern.compile("H[0-9][0-9]B");

    public static final Pattern REGEX_CLUBHOUSE             = Pattern.compile("[Cc]lubhouse]"); // Not a simple search since the clubhouse marker is not consistent between maps as of 20180614
    public static final Pattern REGEX_FOLDER_HOLEOVERLAYS   = Pattern.compile("[Hh]ole *[Oo]verlays"); // Not a simple search since regex is kind of fun and I think this is something people can misspell.
    // Description parsing
    public static final String DESC_DELIM           ="=";


    // Vendor folder description
    public static final String DESC_VENDOR_TZ       = "timezone";
    public static final String DESC_VENDOR_VENDOR   = "vendor";

    // Hole description (optional)
    public static final String DESC_HOLE_LABEL      = "label";
    // Coordinate parsing
    public static final String COORD_DELIM          = " ";
    public static final String COORD_LATLON_DELIM   = ",";

    // Other Strings
    public static final String STR_FRONT9 = "Front 9";  // Todo: This is only a default string, only works in English. Should possibly be read from a file.
    public static final String STR_BACK9 = "Back 9";

    /* Variables */
    // Many of the variables here *could* be of type final if I had initialized them in directly in the constructor
    // instead of in separate init-methods called from the constructor.
    // Todo : Make private after initial testing.
    public Document docRoot;


    protected Element                           folderVendor; // Vendor description contain some data we need.
    protected Element                           folderCourse; //
    protected Element                           folderHoleOverlays; // (optional) Subfolders are subcourses.
    protected List<Element>                     subCourses; // Not in maps pre version 1
    protected List<Element>                     overlays;
    protected List<Element>                     homebasePlacemarks;
    protected SortedMap<Integer,List<Element>>  holePlacemarks; // One list per hole.
    protected Element                           facilityPlacemark ;
    protected List<Element>                     otherPlacemarks; // Placemarks not contained in the other lists.


    /* ----- Constructors ----- */
    /**
     * init from a file.
     * @param xml
     */
    public MapAid(File xml){
        this.initFromFile(xml);
        this.initPlacemarkLists();
        this.initMainFolders();
        this.initSubCourses();
       // this.initOverlays();

       // this.verifyHoleData();
    }

    /**
     * Init from a string containing the xml. Not a file name
     * @param xml
     */
    public MapAid(String xml){
        this.initFromXMLString(xml);
        this.initPlacemarkLists();
    }

    /* ----- Methods ----- */
    /* Init Methods */
    /**
     * Initialize document root from file
     */
    private void initFromFile(File f){
        // TODO Implement
        //throw new Exception("Not implemented yet.  ");
        DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuild = dBF.newDocumentBuilder();
            this.docRoot = dBuild.parse(f);
        }catch (Exception E) { // Todo : Catch Parser--- Exception instead?
            E.printStackTrace();
        }
    }

    /**
     * Initialize document root from String.
     */
    private void initFromXMLString(String xml){
        // ToDo : Actually test this function.
        DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder dBuild = dBF.newDocumentBuilder(); // According to doc this is an abstract method.
            this.docRoot = dBuild.parse( new InputSource( new StringReader( xml ) ) );
        } catch (Exception E) {
            E.printStackTrace();
        }

    }

    /**
     * Document root must be initialized before this.
     * Initialize main folder variables
     */
    private void initMainFolders(){
        // Not using docRoot.getElementsByTagName() , since we are working from the document element and not root.
        Element doc = (Element) docRoot.getElementsByTagName(SEARCH_DOC).item(0); // Should only be one result.
        NodeList folders = doc.getElementsByTagName( SEARCH_FOLDER); // All descendant folders in document.
        for(int i=0; i < folders.getLength(); i++){
            Element e = (Element) folders.item(i); // All nodes with a tag are elements
            String name = getElementName(e);

            // Simple searches first
            if(NAME_VENDOR.equals(name)){
                this.folderVendor = e;
                continue;
            }

            // REGEX searches
            Matcher m;
            m = REGEX_FOLDER_HOLEOVERLAYS.matcher(name);
            if(m.find()){
                this.folderHoleOverlays = e;
                continue;
            }

            // Other
            if(e.getParentNode() == doc){ // Case e is vendor-folder already taken care of.
                this.folderCourse = e;
            }
        }

        // Todo: Check if all folders have been initialized. Otherwise throw some exception.
    }
    /**
     * Document root must be initialized before this.
     * Initialize ground overlays.
     */
    private void initOverlays(){
        this.overlays = new LinkedList<Element>();
        NodeList overlays = this.docRoot.getElementsByTagName(SEARCH_OVERLAY);
        for(int i=0; i < overlays.getLength(); i++){
            this.overlays.add( (Element) overlays.item(i));
        }
    }

    /**
     * Folders and overlays must have been initialized.
     * Initalizes subcourses. Either by making them or getting them from subfolders of the hole overlays-folder.
     */
    private void initSubCourses(){
        this.subCourses = new LinkedList<Element>();
        List<Element> subCourseFolders = XMLHelper.getChildElementsByTag(this.folderHoleOverlays, SEARCH_FOLDER);
        int listLength = subCourseFolders.size();
        List<Element> holeOverlays = XMLHelper.getChildElementsByTag(this.folderHoleOverlays, SEARCH_OVERLAY);
        if(listLength == 0){
            // Not arranged in subcourses. Trying to do it automatically.
            listLength = holeOverlays.size(); // Reuse of variable we don't need anymore.
            if(listLength == 18){ // Most common case. Divide into Front and Back nine.
                // Todo implement
                // Manually make folders elements.
                Element front9 = makeFolder(STR_FRONT9);
                Element back9  = makeFolder(STR_BACK9);
                this.folderHoleOverlays.appendChild(front9);
                this.folderHoleOverlays.appendChild(back9);
                this.subCourses.add(front9);
                this.subCourses.add(back9);

                // Moving overlays from overlay folder to subfolders.
                for(Element o : holeOverlays){
                    this.folderHoleOverlays.removeChild(o);

                    int nbr = getHoleNbr( getElementName(o) );
                    if(nbr < 10){
                        front9.appendChild(o);
                    }else{
                        back9.appendChild(o);
                    }
                }
            }else{
                // Todo: Implement
                // Automatically build Subcourses in steps of 9 and insert overlays in appropiate subcourse by nbr.
            }
        }else{
            if(holeOverlays.size() == 0){
                for(Element f : subCourseFolders){
                    this.subCourses.add(f);
                    holeOverlays = XMLHelper.getChildElementsByTag(f, SEARCH_OVERLAY);
                    if(holeOverlays.size() == 0){
                        // Todo: Subcourse should not be empty; Throw Exception.
                    }
                    // Else Trust the rest is in order for now.
                }
            }else{
                // Todo : Should not happen: Throw Exception.
            }
        }
    }
    /**
     * Document root must be initialized before this.
     * Initializes placemark lists and maps
     */
    private void initPlacemarkLists(){
        this.otherPlacemarks        = new LinkedList<Element>();

        List<Element> allPlacemarks        = new LinkedList<Element>();
        NodeList nodeList = docRoot.getElementsByTagName(SEARCH_PLACEMARK);

        for(int i=0; i< nodeList.getLength(); i++){
            if(nodeList.item(i).getNodeType() != Node.ELEMENT_NODE){
                continue; // Should not happen.
            }else{
                allPlacemarks.add( (Element) nodeList.item(i));
            }
        }

        // All placemarks gotten. Assigning special variables and removing these subsets from the allPlacemarks list.

        this.homebasePlacemarks = getElementsByName(allPlacemarks, REGEX_HOMEBASE);
        allPlacemarks.removeAll(this.homebasePlacemarks);

        List<Element> allHolePlacemarks = getElementsByName(allPlacemarks, REGEX_HOLE);
        allPlacemarks.removeAll(allHolePlacemarks);

        this.facilityPlacemark = getElementByName(allPlacemarks, REGEX_CLUBHOUSE);
        allPlacemarks.remove(this.facilityPlacemark);

        this.otherPlacemarks = allPlacemarks; // What is left of allPlacemarks


        // Putting hole placemarks at the appropiate places.
        this.holePlacemarks = new TreeMap<Integer, List<Element>>();
        for(Element e : allHolePlacemarks){
            int holeNbr = getHoleNbr( getElementName(e) ); // Todo : What if it returns -1 (should not happen due to way the REGEX for adding stuff to allHolePlacemarks works)
            // If key exist, add value. If it does not exist, add key and then the associated value.
            if(holePlacemarks.containsKey(holeNbr)){
                holePlacemarks.get(holeNbr).add(e);
            }else{
                holePlacemarks.put(holeNbr, new LinkedList<Element>());
                holePlacemarks.get(holeNbr).add(e);
            }
        }


    }

    /**
     * Test to verify all data is in order.
     * placemarks, overlays and folders need to be initialized before this method.
     */
    private void verifyHoleData(){
        // Verifiying hole data:
        Set<Integer> holes = this.holePlacemarks.keySet();
        for(int i : holes){
            String hole;
            if(i < 10){  hole = "H0"+Integer.toString(i);}
            else{ hole = "H"+Integer.toString(i); }

            // Build REGEXes for placemarks that must exist.
            List<Pattern> mustExistList = Arrays.asList(
                                                 // Compare regexes to the REGEX_XXX-variables.
                                                    Pattern.compile(hole + "TZ1"),
                                                    Pattern.compile(hole + "TZ2"),
                                                    Pattern.compile(hole + "F"),
                                                    Pattern.compile(hole + "C$"),
                                                    Pattern.compile(hole + "B")

                                            );
            for(Pattern p : mustExistList){
                if( getElementByName( this.holePlacemarks.get(i), p) == null ){ // null if not found.
                    // Todo: Placemark not found. Throw appropiate exception.
                }// else {// Do nothing}
            }
            // An overlay must exist as well.

            if( getElementByName( this.overlays, Pattern.compile(hole + "$") ) == null ){ // End anchor to not match a green zoom.
                // Todo: Overlay not found. Throw appropiate exception.
            } // else {// Do nothing}
            // Todo: Deal With Green Zooms
        }
    }

    /* Internal Methods */
    /**
     *  Makes an element with a folder tag and the appropiate subelements with the name folderName.
     *
     * @param folderName The new folder.
     */
    protected Element makeFolder(String folderName){
        Element newFolder = docRoot.createElement(SEARCH_FOLDER);

        Element open = docRoot.createElement(SEARCH_OPEN); // Todo: Is this added to the document at creation? Probably not according to the examples I have read.
        Text openTxt = docRoot.createTextNode("0"); // Closed, probably the only string I feel I can define incode.
        open.appendChild(openTxt);

        Element name = docRoot.createElement(SEARCH_NAME);
        Text nameTxt = docRoot.createTextNode(folderName);
        name.appendChild(nameTxt);

        newFolder.appendChild(name);
        newFolder.appendChild(open);

        return newFolder;
    }


    /* public */

    /**
     *
     * @return
     */
    public String getFacilityName(){
        // Todo Implement
        return  getElementName(this.facilityPlacemark);
    }

    /**
     *
     * @return
     */
    public String getFacilityPositionStr(){
        // Todo Return default position instead of null
        return getPlacemarkCoordinate(this.facilityPlacemark);
    }


    /* public static */
    /**
     *  Returns the hole number as an integer from a string containing the hole name. For example H04 or H14TZ2
     *
     * The the momment
     *
     * @param holeName String containing the hole name.
     * @return hole number or -1 if no name was found.
     */
    public static int getHoleNbr(String holeName){
        Matcher m = REGEX_HOLE_NBR.matcher(holeName);
        if(m.find()){
            return Integer.parseInt(m.group()); // Regex should only deliver numbers to parseInt.
        }
        return -1;
    }
    /**
     * Returns the text field of an element subelement called name or null if it does not exist.
     * @param element
     * @return name of kml-element.
     */
    public static String getElementName(Element element){
        List<Element> childs = XMLHelper.getChildElementsByTag(element, SEARCH_NAME);
        if( childs.size() > 0){
            Element name = childs.get(0); // Should only be one result.
            return XMLHelper.getElementText(name);
        }
        return null;

    }
    /**
     * Returns the text field of an element subelement called coordinate or null if it does not exist.
     * @param element
     * @return coordinate
     */
    public static String getPlacemarkCoordinate(Element element){
        // Todo Verify element is a placemark
        Element point = XMLHelper.getChildElementByTag(element, SEARCH_POINT);
        Element coord = XMLHelper.getChildElementByTag(point  , SEARCH_COORD);
        return XMLHelper.getElementText(coord);
    }

    /**
     *
     * @param element an Element with the tag GroundOverlay
     * @return
     */
    public static String getOverlayCoordinates(Element element){
        // Todo Verify element in a GroundOverlay
        // Todo Make option to find LatLonBox instead of LatLonQuad
        Element coordContainer = XMLHelper.getChildElementByTag(element, SEARCH_LATLONQUAD);
        Element coord = XMLHelper.getChildElementByTag(element, SEARCH_COORD);
        return XMLHelper.getElementText(coord);
    }
    /**
     * Returns the text field of an element subelement called description or null if it does not exist.
     * @param element
     * @return
     */
    public static String getElementDescription(Element element){
        Element coord = XMLHelper.getChildElementByTag(element, SEARCH_DESCRIPTION);
        return XMLHelper.getElementText(element);
    }
    /**
     * Returns the text field of an element subelement called href or null if it does not exist.
     * @param element
     * @return
     */
    public static String getElementLink(Element element){
        Element coord = XMLHelper.getChildElementsByTag(element, SEARCH_HREF).get(0);
        return XMLHelper.getElementText(element);
    }

    /**
     * Iterates over a list and returns the first element with a name a equaling input String name or null of none is
     * found.
     *
     * @param elementList a list with elements to search from.
     * @param name The name to search for.
     * @return The first element with the input name. null if none is found.
     */
    public static Element getElementByName(List<Element> elementList, String name){
        for(Element e : elementList){
            String eName = getElementName(e);
            if(name.equals(eName)){
                return e;
            }else{
                continue;
            }

        }
        return null;
    }


    /**
     *
     * @param elementList
     * @param regex
     * @return
     */
    public static Element getElementByName(List<Element> elementList, Pattern regex){
        for(Element e : elementList){
            String eName = getElementName(e);
            Matcher m = regex.matcher(eName);
            if(m.find()){
                return e;
            }else{
                continue;
            }

        }
        return null;
    }

    /**
     * Iterates over a list and returns a list of all elements with a name corresponding to name. An empty list will
     * be returned if none is found.
     * @param elementList a list with elements to search from.
     * @param name The name to search for.
     * @return an empty list or all elements with a name corresponding to the input name.
     */
    public static List<Element> getElementsByName(List<Element> elementList, String name){
        List<Element> retList = new LinkedList<Element>();
        for(Element e : elementList){
            String eName = getElementName(e);
            if(name.equals(eName)){
                retList.add(e);
            }else{
                continue;
            }
        }
        return retList;
    }
    /**
     * Iterates over a list and returns a list of all elements with a name matching the input regex Pattern. An empty
     * list will be returned if none is found.
     * @param elementList a list with elements to search from.
     * @param regex The regex Pattern to search for.
     * @return an empty list or all elements with a name corresponding to the input Pattern.
     */
    public static List<Element> getElementsByName(List<Element> elementList, Pattern regex){
        List<Element> retList = new LinkedList<Element>();
        for(Element e : elementList){
            String eName = getElementName(e);
            Matcher m = regex.matcher(eName);
            if(m.find()){
                retList.add(e);
            }else{
                continue;
            }
        }
        return retList;
    }



    /**
     *
     * @return A List of all placemarkers.
     */
    public List<Element> getAllHoleMarkers(){
        List<Element> ret = new ArrayList<Element>( this.holePlacemarks.values().size() );
        for(int i : this.holePlacemarks.keySet()){
            ret.addAll(this.holePlacemarks.get(i));;
        }
        return ret;
    }

    /**
     *
     * @param holeNbr
     * @return
     */
    public List<Element> getHoleTriggerZones(int holeNbr){
        // Todo : Simplify when deciding to upate hole-regex to regex = Pattern.compile("H"+Integer.toString(holeNbr)+"TZ[1-9]");

        Pattern regex;
        if(holeNbr < 10){
            regex = Pattern.compile("H0"+Integer.toString(holeNbr)+"TZ[1-9]");
        }else{ // holeNbr
            regex = Pattern.compile("H"+Integer.toString(holeNbr)+"TZ[1-9]");
        }

        return getElementsByName(this.holePlacemarks.get(holeNbr), regex);
    }
    /**
     *
     * @param holeNbr
     * @return
     */
    public List<Element> getHoleTeeBoxes(int holeNbr){
        // Todo : Simplify when deciding to upate hole-regex to regex = Pattern.compile("H"+Integer.toString(holeNbr)+"TZ[1-9]");

        Pattern regex;
        if(holeNbr < 10){
            regex = Pattern.compile("H0"+Integer.toString(holeNbr)+"T[1-9]");
        }else{ // holeNbr
            regex = Pattern.compile("H"+Integer.toString(holeNbr)+"T[1-9]");
        }

        return getElementsByName(this.holePlacemarks.get(holeNbr), regex);
    }
    /**
     *
     * @param holeNbr
     * @return
     */
    public List<Element> getHoleHazards(int holeNbr){
        // Todo : Simplify when deciding to upate hole-regex to regex = Pattern.compile("H"+Integer.toString(holeNbr)+"TZ[1-9]");

        Pattern regex;
        if(holeNbr < 10){
            regex = Pattern.compile("H0"+Integer.toString(holeNbr)+"H[1-9]");
        }else{ // holeNbr
            regex = Pattern.compile("H"+Integer.toString(holeNbr)+"H[1-9]");
        }

        return getElementsByName(this.holePlacemarks.get(holeNbr), regex);
    }
    /**
     *
     * @param holeNbr
     * @return
     */
    public Element getHoleFront(int holeNbr){
        // Todo : Simplify when deciding to upate hole-regex to regex = Pattern.compile("H"+Integer.toString(holeNbr)+"TZ[1-9]");

        Pattern regex;
        if(holeNbr < 10){
            regex = Pattern.compile("H0"+Integer.toString(holeNbr)+"F");
        }else{ // holeNbr
            regex = Pattern.compile("H"+Integer.toString(holeNbr)+"F");
        }

        return getElementsByName(this.holePlacemarks.get(holeNbr), regex).get(0); // Should only be one.
    }
    /**
     *
     * @param holeNbr
     * @return
     */
    public Element getHoleCenter(int holeNbr){
        // Todo : Simplify when deciding to upate hole-regex to regex = Pattern.compile("H"+Integer.toString(holeNbr)+"TZ[1-9]");

        Pattern regex;
        if(holeNbr < 10){
            regex = Pattern.compile("H0"+Integer.toString(holeNbr)+"C");
        }else{ // holeNbr
            regex = Pattern.compile("H"+Integer.toString(holeNbr)+"C");
        }

        return getElementsByName(this.holePlacemarks.get(holeNbr), regex).get(0); // Should only be one.
    }
    /**
     *
     * @param holeNbr
     * @return
     */
    public Element getHoleFlag(int holeNbr){ // Actual hole position.
        // Todo: Have a separate marker for this:
        return getHoleCenter(holeNbr);
    }
    /**
     *
     * @param holeNbr
     * @return
     */
    public Element getHoleBack(int holeNbr){
        // Todo : Simplify when deciding to upate hole-regex to regex = Pattern.compile("H"+Integer.toString(holeNbr)+"TZ[1-9]");

        Pattern regex;
        if(holeNbr < 10){
            regex = Pattern.compile("H0"+Integer.toString(holeNbr)+"B");
        }else{ // holeNbr
            regex = Pattern.compile("H"+Integer.toString(holeNbr)+"B");
        }

        return getElementsByName(this.holePlacemarks.get(holeNbr), regex).get(0); // Should only be one.
    }

    /**
     *
     * @param holeNbr
     * @return
     */
    public List<Element> getHolePlacemarks(int holeNbr){
        return this.holePlacemarks.get(holeNbr);

    }

    /**
     *
     * @return Existing hole numbers
     */
    public Set<Integer> getExistingHoleNbrs(){
        return this.holePlacemarks.keySet();
    }

    /**
     *
     * @return
     */
    public Element getVendorFolder() {
        return this.folderVendor;
    }

    /**
     *
     * @return
     */
    public Element getCourseFolder() {
        return this.folderCourse;
    }

    /**
     *
     * @return
     */
    public List<Element> getOtherPlacemarks(){
        return this.otherPlacemarks;
    }
    /*

    private static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    private static Document convertStringToDocument(String xmlStr) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
     */
}
