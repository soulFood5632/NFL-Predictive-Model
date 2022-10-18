package NFLData.datareader;

import NFLData.dataanalyzer.DataAnalyzerCorrelator;
import NFLData.dataanalyzer.ValueHolder;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ReadFileGameData implements DataRead {

    /**
     * List of ValueHolder's of the read file data
     */
    private List<ValueHolder> listOfData = new ArrayList<>();
    private String fileName;

    public ReadFileGameData(String fileName) throws FileNotFoundException{
        this.fileName = fileName;
        readData();


    }

    private void readData() throws FileNotFoundException {
        try{
            new DataWrapper(fileName);
        } catch (FileNotFoundException e){
            throw new FileNotFoundException();
        }
        DataWrapper file = new DataWrapper(fileName);
        String currentLine;

        file.nextLine();

        String[] lineArray;
        int indexOfWinner = 2;
        int indexOfCount = 6;
        int indexOfLoser = 3;

        for(currentLine = file.nextLine(); currentLine != null; currentLine = file.nextLine()){
            lineArray = currentLine.split(",");
            for(int numberOfWinsCounter = 0; numberOfWinsCounter < Integer.parseInt(lineArray[indexOfCount]); numberOfWinsCounter++){
                listOfData.add(new ValueHolder(1, Integer.parseInt(lineArray[indexOfWinner])));
                listOfData.add(new ValueHolder(0, Integer.parseInt(lineArray[indexOfLoser])));
            }
        }


    }

    @Override
    public List<ValueHolder> returnList() {
        List<ValueHolder> returnList = new ArrayList<>();
        for(ValueHolder v: listOfData){
            returnList.add(v);
        }
        return returnList;
    }



}