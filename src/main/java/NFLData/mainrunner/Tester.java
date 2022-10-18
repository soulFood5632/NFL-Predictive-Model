package NFLData.mainrunner;

import NFLData.dataanalyzer.DataAnalyzerCorrelator;
import NFLData.dataanalyzer.ValueHolder;
import NFLData.datareader.DataRead;
import NFLData.datareader.ReadFileGameData;

import java.io.FileNotFoundException;
import java.util.*;

public class Tester {

    public static void main(String[] args){
        DataRead tester1;
        try {
            tester1 = new ReadFileGameData("data/machinetester.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<ValueHolder> list = tester1.returnList();
        DataAnalyzerCorrelator pointsFor = new DataAnalyzerCorrelator(list);
        System.out.println(pointsFor.conditionalProb(21, 1));
        System.out.println(pointsFor.conditionalProb(21, 0));

    }
}
