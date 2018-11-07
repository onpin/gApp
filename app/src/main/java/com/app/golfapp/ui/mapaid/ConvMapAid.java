package com.app.golfapp.ui.mapaid;

import java.io.File;

/**
 * WORK IN PROGRESS!
 *
 * Extension of the MapAid. This class provide some helper methods to retrieve data from the map KML in a more computer
 * readable format.
 * For example you can expect methods like double[] getPlacemarkerPosition to exist.
 */
public class ConvMapAid extends MapAid {
    public ConvMapAid(File xml) {

        super(xml);
    }

    public ConvMapAid(String xml) {

        super(xml);
    }
}
