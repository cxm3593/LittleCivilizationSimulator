package Model;

import java.util.Objects;

public abstract class TerrainModifier {
    private int pass_modify;
    private int humi_modify;
    private int altitude_modify;
    private int water_resource;

    public TerrainModifier(){
    }

    public int getPass_modify() {
        return pass_modify;
    }

    public int getHumi_modify() {
        return humi_modify;
    }

    public int getAltitude_modify() {
        return altitude_modify;
    }

    public int getWater_resource_modify() {
        return water_resource;
    }

    public void setPass_modify(int pass_modify) {
        this.pass_modify = pass_modify;
    }

    public void setHumi_modify(int humi_modify) {
        this.humi_modify = humi_modify;
    }

    public void setAltitude_modify(int altitude_modify) {
        this.altitude_modify = altitude_modify;
    }

    public void setWater_resource_modify(int water_resource){
        this.water_resource = water_resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerrainModifier that = (TerrainModifier) o;
        return pass_modify == that.pass_modify &&
                humi_modify == that.humi_modify &&
                altitude_modify == that.altitude_modify;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pass_modify, humi_modify, altitude_modify);
    }
}
