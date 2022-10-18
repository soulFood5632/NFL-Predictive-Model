package NFLData.team;


public class Team {
    private String teamName;
    private String fileName;
    private double offencePowerIndex, defencePowerIndex;


    /**
     * Constructor for an NFL team
     * @param teamName a non-null team name string.
     * @param fileName a non-null String containing a reference to a file containing the season
     *                 by season data.
     * @throws IllegalArgumentException if fileName is not valid
     */
    public Team(String teamName, String fileName) throws IllegalArgumentException{
        this.teamName = teamName;
        this.fileName = fileName;

        findPowerIndex();
    }

    /**
     * Finds the power index of the team from the given file.
     *
     */
    private void findPowerIndex(){

    }
}
