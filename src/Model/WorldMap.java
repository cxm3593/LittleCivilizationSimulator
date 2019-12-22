package Model;

public class WorldMap {

    private MapSpace map[][];
    private int length;
    private int wide;


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

    }

    /**
     * print the map out as a 2d character array
     * @param vm
     * @return
     */
    public void print(ViewMode vm){
        System.out.println("Printing the map...");

        this.printSeperateLine();
        for(int i=0; i < (this.length-1); i++){
            System.out.print("| ");
            for(int j=0; j<(this.wide-1);j++){
                if(vm == ViewMode.Altitude){
                    System.out.print(map[i][j].getAltitude() + " ");
                }
            }
            System.out.println(" |");
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
