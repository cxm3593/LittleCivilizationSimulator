package Application;
import Model.ViewMode;
import Model.WorldMap;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        Boolean debug_mode = true;
        //System.out.println("\033[31m RED");

        WorldMap map = new WorldMap(50,50);
        map.drawMap();
        //System.out.println(map.toString());
        map.print(ViewMode.Altitude);
    }
}
