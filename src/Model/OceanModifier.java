package Model;

public class OceanModifier extends TerrainModifier {

    public OceanModifier(){
        super();
        this.setAltitude_modify(-50);
        this.setHumi_modify(100);
        this.setPass_modify(-100);
    }

}
