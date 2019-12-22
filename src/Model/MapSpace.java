package Model;

public class MapSpace {

    private int passability;
    private int humidity;
    private int altitude;

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


}
