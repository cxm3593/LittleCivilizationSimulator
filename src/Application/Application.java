package Application;
import Model.ViewMode;
import Model.WorldMap;

public class Application {
    public static void main(String[] args) {
        WorldMap map = new WorldMap(160,90);
        map.drawMap();
        //System.out.println(map.toString());
        map.print(ViewMode.Altitude);
    }
}
