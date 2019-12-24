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

        while(this.current_flow > 0){ // river flows to other places as long as there is more water
            int x = current.getX();
            int y = current.getY();

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
                MapSpace temp_location = map.getLocation(temp);
                if(temp_location != null){
                    if(map.getLocation(temp).checkModifier(new RiverModifer())){
                        lowergrounds_Coordinate.remove(index); //a river will not flow back to a river, or that location is out of boundary, so remove those.
                    }
                }else{
                    lowergrounds_Coordinate.remove(index);
                }
            }

            if(debug_mode==true){System.out.println("The valid coordinates: " + lowergrounds_Coordinate);}
            Collections.shuffle(lowergrounds_Coordinate);

            Coordinate lowest = lowergrounds_Coordinate.get(0);

            for(Coordinate c: lowergrounds_Coordinate){
                MapSpace temp_location = map.getLocation(c);
                if(temp_location!=null && map.getLocation(c).getTerrainModifiers().contains(new OceanModifier())){ // if river run close to ocean
                    if(debug_mode==true){System.out.println("River reached ocean.");}
                    current_flow = 0; // all water will run into ocean and it will no longer run to other places.
                    flow.add(c); // so that the river knows where it ends
                    break;
                }
                if(temp_location!=null && map.getLocation(lowest) != null ){
                    if(temp_location.getAltitude() <= map.getLocation(lowest).getAltitude()){
                        lowest = c;// find the lowest mapspace around
                    }
                }
            }

            try{
                this.flow.add(lowest);
                map.getLocation(lowest).addModifier(new RiverModifer());
                this.current_flow = this.current_flow - 40;
                current = lowest;
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }

        }


        return true;
    }
}
