package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class River {
    private ArrayList<Coordinate> flow;
    private WorldMap map;
    private Coordinate spawn;
    private Coordinate current;
    private final int water_resource;
    private int current_flow;
    private boolean debug_mode = true;

    public River(WorldMap map,Coordinate spawn,int water_resource){
        this.spawn = spawn;
        this.map = map;
        flow = new ArrayList<>();
        flow.add(spawn);
        current = spawn;
        this.water_resource = water_resource;
        this.current_flow = water_resource;
        if(debug_mode==true)System.out.println("A river has been created.");
    }

    public boolean flow(){
        Random rand = new Random();

        int x = current.getX();
        int y = current.getY();

        while(this.current_flow > 0){ // river flows to other places as long as there is more water
            ArrayList<Coordinate> lowergrounds_Coordinate = new ArrayList<>();
            lowergrounds_Coordinate.add(new Coordinate(x-1,y));
            lowergrounds_Coordinate.add(new Coordinate(x-1,y-1));
            lowergrounds_Coordinate.add(new Coordinate(x-1,y+1));
            lowergrounds_Coordinate.add(new Coordinate(x+1,y));
            lowergrounds_Coordinate.add(new Coordinate(x+1,y-1));
            lowergrounds_Coordinate.add(new Coordinate(x+1,y+1));
            lowergrounds_Coordinate.add(new Coordinate(x,y-1));
            lowergrounds_Coordinate.add(new Coordinate(x,y+1));

            for(int index=0;index<lowergrounds_Coordinate.size();index++){
                Coordinate temp = lowergrounds_Coordinate.get(index);
                if(map.getLocation(temp).checkModifier(new RiverModifer()) || map.getLocation(temp) == null){
                    lowergrounds_Coordinate.remove(index); //a river will not flow back to a river, or that location is out of boundary, so remove those.
                }
            }

            Collections.shuffle(lowergrounds_Coordinate);
            Coordinate lowest = lowergrounds_Coordinate.get(0);

            for(Coordinate c: lowergrounds_Coordinate){
                if(map.getLocation(c).getTerrainModifiers().contains(new OceanModifier())){ // if river run close to ocean
                    if(debug_mode==true){System.out.println("River reached ocean.");}
                    current_flow = 0; // all water will run into ocean and it will no longer run to other places.
                    flow.add(c); // so that the river knows where it ends
                    break;
                }
                if(map.getLocation(c).getAltitude()<=map.getLocation(lowest).getAltitude()){
                    lowest = c;// find the lowest mapspace around
                }
            }

            this.flow.add(lowest);
            map.getLocation(lowest).addModifier(new RiverModifer());
            this.current_flow = this.current_flow - 40;
        }


        return true;
    }
}
