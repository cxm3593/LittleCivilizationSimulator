package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WorldMap {
    private Boolean debug_mode = true; // print out debug information in this class?

    private MapSpace map[][];
    private int row;
    private int column;
    public final int MaxAltitudeStep = 500;
    public final int MaxOceanStep = 450;
    private ArrayList<Coordinate> peaks;
    private ArrayList<River> Rivers;


    public WorldMap(int row, int column){
        this.row = row;
        this.column = column;
        map = new MapSpace[row][column];
        peaks = new ArrayList<>();
        Rivers = new ArrayList<>();
    }

    /**
     * Draw a map
     */
    public void drawMap(){
        if(debug_mode==true)System.out.println("Drawing the map...");
        this.initialize();
        this.drawTerrain(1); //draw mountains
        this.drawOcean(1); //draw oceans
        this.drawRiver(); //draw rivers
    }

    /**
     * initialize the map with initialized mapspaces
     */
    public void initialize(){
        System.out.println("Initializing a map with basic MapSpace...");
        for(int i=0; i < (this.row); i++){
            for(int j = 0; j<(this.column); j++){
                map[i][j] = new MapSpace();
            }
        }
    }



    public void drawOcean(int on){
        Random rand = new Random();
        for(int i=0; i<on; i++){
            int x = rand.nextInt(this.row);
            int y = rand.nextInt(this.column);
            if(map[x][y].getAltitude() != 0){
                i=i-1;
            }else{
                drawOceanRec(x,y,(- rand.nextInt(5000)));
            }
        }
    }

    /**
     * Recursively to draw ocean with random locations
     * @param x x coordinate
     * @param y y coordinate
     * @param a: altitude
     */
    public void drawOceanRec(int x, int y, int a){
        Random rand = new Random();
        if(map[x][y]!=null && map[x][y].getAltitude() == 0){
            if(a<=-100){
                map[x][y].setAltitude(a);
                map[x][y].addModifier(new OceanModifier());
                if(x>0) drawOceanRec(x-1,y,(a + rand.nextInt(MaxOceanStep)));
                if(x<this.row-1)drawOceanRec(x+1,y,(a + rand.nextInt(MaxOceanStep)));
                if(y<this.column-1) drawOceanRec(x,y+1,(a+rand.nextInt(MaxOceanStep)));
                if(y>0) drawOceanRec(x,y-1,(a-rand.nextInt(MaxOceanStep)));
            }
        }

    }

    /**
     * Draw a certain number of mountains
     * @param mn mountain number
     */
    public void drawTerrain(int mn){
        Random rand = new Random();
        for(int i=0; i<mn; i++){
            int x = rand.nextInt(this.row);
            int y = rand.nextInt(this.column);
            if(map[x][y].getAltitude() != 0){
                i=i-1;
            }else{
                drawTerrainRec(x,y,rand.nextInt(8000));
                peaks.add(new Coordinate(x,y));
            }
        }
    }

    /**
     * Recursively to draw mountain with random locations;
     * @param x x coordinate
     * @param y y coordinate
     * @param a: altitude
     */
    private void drawTerrainRec(int x, int y,int a){
        Random rand = new Random();
        if(map[x][y]!= null && map[x][y].getAltitude() <= (a * 0.2)){ // this process only draws places without initial terrain.
            if(a >= 0){ //no more mountain to draw if a is smaller than 0
                map[x][y].setAltitude(a);
                if(x>0) drawTerrainRec(x-1,y,(a - rand.nextInt(MaxAltitudeStep)));
                if(x<this.row-1) drawTerrainRec(x+1,y,(a - rand.nextInt(MaxAltitudeStep)));
                if(y<this.column-1) drawTerrainRec(x,y+1,(a - rand.nextInt(MaxAltitudeStep)));
                if(y>0) drawTerrainRec(x,y-1,(a - rand.nextInt(MaxAltitudeStep)));
            }
        }
    }

    /**
     * Draw a river on map from peaks
     */
    public void drawRiver(){
        if(debug_mode==true)System.out.println("Drawing River from peak...");
        for(Coordinate i: peaks){
            if(debug_mode==true)System.out.format("Drawing from: (%d, %d)\n",i.getX(),i.getY());
            River river = new River(this,i,100);
            Rivers.add(river);
        }
        for(River r : Rivers){
            r.flow();
        }
    }


    /**
     * print the map out as a 2d character array
     * @param vm
     * @return
     */
    public void print(ViewMode vm){
        System.out.println("Printing the map...");

        this.printSeperateLine();
        for(int i=0; i < (this.row); i++){
            System.out.print("|\t");
            for(int j = 0; j<(this.column); j++){
                if(vm == ViewMode.Altitude){
                    int a = map[i][j].getAltitude();
                    if(a<=0){
                        System.out.print("\u001b[48;5;220m");
                    }
                    else if(a>0 && a<1000){
                        System.out.print("\u001b[48;5;226m");  // 48 for background color; 38 for font color
                    }else if(a>= 1000 && a<= 3000){
                        System.out.print("\u001b[48;5;228m");
                    }else if(a>3000&& a<= 5000){
                        System.out.print("\u001b[48;5;229m");
                    }else if(a>5000 && a<8000){
                        System.out.print("\u001b[48;5;230m");
                    }
                    if(map[i][j].getTerrainModifiers().contains(new OceanModifier())){
                        System.out.print("\u001b[48;5;27m");
                    }
                    if(map[i][j].getTerrainModifiers().contains(new RiverModifer())){
                        System.out.print("\u001b[38;5;27m");
                    }
                    System.out.format("%5d",a);
                    System.out.print("\u001b[0m");
                }
            }
            System.out.println("\t|");
        }
        this.printSeperateLine();
    }

    private void printSeperateLine(){
        int line_length = this.row ;
        for(int i=0; i<this.column; i++){
            System.out.format("%5s","_");
        }
        System.out.println();
    }

    public MapSpace getLocation(Coordinate c){
        if(map[c.getX()][c.getY()]!=null){
            return map[c.getX()][c.getY()];
        }else{
            return null;
        }
    }

//    public String toString(){
//
//    }
}
