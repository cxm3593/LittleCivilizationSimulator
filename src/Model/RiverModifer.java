package Model;

public class RiverModifer extends TerrainModifier {

    public RiverModifer(){
        super();
        this.setAltitude_modify(-10);
        this.setHumi_modify(40);
        this.setPass_modify(-50);
        this.setWater_resource_modify(50); // legacy not using this
    }

}
