package Model;

import javax.swing.*;
import java.util.Random;

public class WorldMap {

    private MapSpace map[][];
    private int length;
    private int wide;
    public final int MaxAltitudeStep = 500;


    public WorldMap(int length, int wide){
        this.length = length;
        this.wide = wide;
        map = new MapSpace[length][wide];
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
        for(int i=0; i < (this.length-1); i++){
            for(int j=0; j<(this.wide-1);j++){
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
            drawTerrainRec(rand.nextInt(this.length),rand.nextInt(this.wide),rand.nextInt(8000));
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
        if(map[x][y].getAltitude() == 0){ // this process only draws places without initial terrain.
            if(a >= 200){ //no more mountain to draw if a is smaller than 200
                map[x][y].setAltitude(a);
                if(x>0)drawTerrainRec(x-1,y,(a - rand.nextInt(MaxAltitudeStep)));
                if(x<this.length)drawTerrainRec(x+1,y,(a - rand.nextInt(MaxAltitudeStep)));
                if(y<this.wide)drawTerrainRec(x,y+1,(a - rand.nextInt(MaxAltitudeStep)));
                if(y>0)drawTerrainRec(x,y-1,(a - rand.nextInt(MaxAltitudeStep)));
            }
        }
    }

    /**
     * print the map out as a 2d character array
     * @param vm
     * @return
     */
    public void print(ViewMode vm){
        JTextArea textarea = new JTextArea();
        textarea.setTabSize(5);
        System.out.println("Printing the map...");

        this.printSeperateLine();
        for(int i=0; i < (this.length-1); i++){
            System.out.print("|\t");
            for(int j=0; j<(this.wide-1);j++){
                if(vm == ViewMode.Altitude){
                    int a = map[i][j].getAltitude();
                    System.out.format("%5d",a);
                }
            }
            System.out.println("\t|");
        }
        this.printSeperateLine();
    }

    private void printSeperateLine(){
        int line_length = this.length * 2;
        for(int i=0; i<line_length ; i++){
            System.out.print("_");
        }
        System.out.println();
    }

//    public String toString(){
//
//    }
}
