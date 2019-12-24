package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapSpace {

    private int passability;
    private int humidity;
    private int altitude;
    private ArrayList<TerrainModifier> terrainModifiers = new ArrayList<>();

    public MapSpace(){
        this.passability = 0;
        this.humidity = 0;
        this.altitude = 0;
    }

    public int getPassability() {
        return passability;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    /**
     * Need to update when MapSpace is changed
     * @param modifier the modifier to be added to this space
     * @return
     */
    public boolean addModifier(TerrainModifier modifier){
        this.terrainModifiers.add(modifier);
        this.passability = this.altitude + modifier.getPass_modify();
        this.humidity = this.altitude + modifier.getHumi_modify();
        this.altitude = this. altitude + modifier.getAltitude_modify();

        return true;
    }

    public boolean removeModifier(TerrainModifier modifier){
        this.terrainModifiers.remove(modifier);
        this.passability = this.altitude - modifier.getPass_modify();
        this.humidity = this.altitude - modifier.getHumi_modify();
        this.altitude = this. altitude - modifier.getAltitude_modify();

        return true;
    }

    public ArrayList<TerrainModifier> getTerrainModifiers(){
        return this.terrainModifiers;
    }
    public boolean checkModifier(TerrainModifier tm){
        if(this.terrainModifiers.contains(tm)) return true;
        return false;
    }
}
