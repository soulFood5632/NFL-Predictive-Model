package NFLData.dataanalyzer;

public class ValueHolder {
    private final double wins;
    private final double value;

    public ValueHolder(double wins, double value){
        double winsRounded = (double) Math.round(wins * 100) / 100;
        this.wins = winsRounded;
        this.value = value;
    }


    public double getValue(){
        return value;
    }

    public double getWins(){
        return wins;
    }
}
