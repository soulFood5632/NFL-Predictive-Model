package NFLData.dataanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class DataAnalyzerCorrelator {

    /**
     * the HashMap to store the final value vs. expected wins
     */
    private Map<Double, Double> valueMap = new HashMap<>(); //(value, expectedWins)

    /**
     * The entry list of data
     */
    private List<ValueHolder> valueData;


    /**
     * @return the wins vs. value for probability map.
     */
    public Map<Double, Double> getMap(){
        Map<Double, Double> returnMap = new HashMap<>();
        valueMap.forEach((pointsFor, expectedWins) -> returnMap.put(pointsFor, expectedWins));
        return returnMap;
    }


    /**
     * The DataAnalyzerCorrelator constructor
     *
     * @param data a non-null list of ValueHolder's containing a
     */
    public DataAnalyzerCorrelator(List<ValueHolder> data){
        this.valueData = data;
        findCorrelation();
    }

    /**
     * A map of the likelihood of a certain win total existing
     */
    private Map<Double, Double> pWins = new HashMap<>(); //(win total, likelihood)

    /**
     * A map of the likelihood of a certain value happening
     */
    private Map<Double, Double> pValue = new HashMap<>(); //(value, likelihood)

    /**
     * A map of the likelihood of a certain value happening given a rating.
     */
    private Map<Double, Map<Double, Double>> pValueByWins = new HashMap<>(){ //(win index, Map(value, likelihood))
        {
            for(double winValue = 0; winValue <= 1; winValue += 0.01){
                put(winValue, new HashMap<>());
            }
        }
    };

    /**
     * Finds the individual correlations of each stat to a win value. If the
     * stat is not contained within the database it uses the closest value. If
     * that value does not exist under this win total, use 0.01 / size of database
     * at that win total.
     *
     * @param stat a non-null double value enclosing a stat value
     * @return A double containing its likelihood based on the given stat.
     */
    public double conditionalProb(double stat, double wins){
        if(!pValue.containsKey(stat)){
            stat = findClosestValue(stat);
        } if(pValueByWins.get(wins).containsKey(stat)) {
            return pValueByWins.get(wins).get(stat) * pWins.get(wins) / pValue.get(stat);
        } else {
            return 0.01/pValueByWins.get(wins).size() * pWins.get(wins) / pValue.get(stat);
        }
    }

    /**
     * Finds the closest matching stat and returns that value
     *
     * @param stat a non-null double value that is the stat value
     * @return A double value of the closest stat value
     */
    private double findClosestValue(double stat){
        double closestValue = Math.abs(valueData.get(0).getValue() - stat);
        double closestNum = valueData.get(0).getValue();

        for(int indexCounter = 1; indexCounter < valueData.size(); indexCounter++){
            if(closestValue > Math.abs(valueData.get(indexCounter).getValue() - stat)){
                closestValue = Math.abs(valueData.get(indexCounter).getValue() - stat);
                closestNum = valueData.get(indexCounter).getValue();
            }
        }

        return closestNum;

    }


    /**
     * Finds the individual correlations of each stat to a win value
     *
     * @return A map containing a key of the win total and its likelihood
     * based on the given stat.
     */
    private void findCorrelation(){

        for(ValueHolder piece: valueData){
            pWins = addToMap(pWins, piece.getWins());
            pValue = addToMap(pValue, piece.getValue());
            pValueByWins.put(piece.getWins(), addToMap(pValueByWins.get(piece.getWins()), piece.getValue()));
        }

        findPWins();
        findPValue();
        findPValueByWins();
    }

    /**
     * Converts the raw count of pWins to their probabilities
     */
    private void findPWins() {
        Map<Double, Double> returnMap = new HashMap<>();
        pWins.forEach((wins, count) -> returnMap.put(wins, count / valueData.size()));
        pWins = returnMap;
    }

    /**
     * Converts the raw count of pValue to their probabilities
     */
    private void findPValue() {
        Map<Double, Double> returnMap = new HashMap<>();
        pValue.forEach((value, count) -> returnMap.put(value, count / valueData.size()));
        pValue = returnMap;
    }


    /**
     * Converts the raw count of pValueByWins to their probabilities
     */
    private void findPValueByWins() {
        Map<Double, Map<Double, Double>> returnMap = new HashMap<>();
        pValueByWins.forEach((wins, map) -> {
            returnMap.put(wins, new HashMap<>());
            map.forEach((value, count) -> returnMap.get(wins).put(value, (count + 0.01) / (pWins.get(wins))));
        });
    }


    /**
     * Increments an entry in a map by 1
     *
     * @param map a non-null map containing 2 double values
     * @param keyToAddTo a double value containing the key which is
     *                   to be incremented
     * @return A map containing the incremented value.
     */
    private Map<Double, Double> addToMap(Map<Double, Double> map, Double keyToAddTo){ //this could have a big bug in double value precision
        if(map != null) {
            if (map.containsKey(keyToAddTo)) {
                map.replace(keyToAddTo, map.get(keyToAddTo) + 1);
            } else {
                map.put(keyToAddTo, 1D);
            }
        } else {
            map.put(keyToAddTo, 1D);
        }
        return map;
    }






}
