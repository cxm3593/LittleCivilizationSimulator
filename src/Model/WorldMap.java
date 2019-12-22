package Model;

import java.util.Random;

public class WorldMap {

    private MapSpace map[][];
    private int row;
    private int column;
    public final int MaxAltitudeStep = 500;


    public WorldMap(int row, int column){
        this.row = row;
        this.column = column;
        map = new MapSpace[row][column];
    }

    /**
     * Draw a map
     */
    public void drawMap(){
        System.out.println("Drawing the map...");
        this.initialize();
        this.drawTerrain(5); //draw mountains
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
            }
            drawTerrainRec(x,y,rand.nextInt(8000));
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
        if(map[x][y]!= null && map[x][y].getAltitude() == 0){ // this process only draws places without initial terrain.
            if(a >= 200){ //no more mountain to draw if a is smaller than 200
                map[x][y].setAltitude(a);
                if(x>0) drawTerrainRec(x-1,y,(a - rand.nextInt(MaxAltitudeStep)));
                if(x<this.row-1) drawTerrainRec(x+1,y,(a - rand.nextInt(MaxAltitudeStep)));
                if(y<this.column-1) drawTerrainRec(x,y+1,(a - rand.nextInt(MaxAltitudeStep)));
                if(y>0) drawTerrainRec(x,y-1,(a - rand.nextInt(MaxAltitudeStep)));
            }
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
                    if(a==0){
                        System.out.print("\u001b[48;5;220m");
                    }
                    else if(a>0 && a<1000){
                        System.out.print("\u001b[48;5;226m");  // 48 for background color; 38 for font color
                    }else if(a>= 1000 && a<= 3000){
                        System.out.print("\u001b[48;5;228m");
                    }else if(a>3000){
                        System.out.print("\u001b[48;5;230m");
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

//    public String toString(){
//
//    }
}
