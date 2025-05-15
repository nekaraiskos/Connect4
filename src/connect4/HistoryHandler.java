/*
 * This class is used to save every completed game on the computer and then recover any information needed from them
 */
package connect4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.*;
// import netscape.javascript.JSObject;
/**
 *
 * @author nekaraiskos
 */
public class HistoryHandler {
    public String dateAndTime;
    public String fileName;
    public int roundDifficuly;
    public File connect4Dir;
    

    //Connect4Dir: total path to file
    //Connect4Directory: total path to connect4 dir
    //dateAndTime: JList String
    //fileName: name of file - merged date to turn into double for JList

    //Get the path of the saved games directory and create it if it does not exist
    public HistoryHandler (int difficulty) {

        // String filePath = System.getProperty("user.home");
        // String connect4Directory = "connect4";

        String filePath = System.getProperty("user.dir");
        // String connect4Directory = "history";

        connect4Dir = new File(filePath, "history");

        if (!connect4Dir.exists()) {
            connect4Dir.mkdir();
        }
    }

    //Update the saved date and time in order to save the round started correctly
    public void updateForNewGame(int difficulty) {

        roundDifficuly = difficulty;

        //get the string used for the history JList
        DateTimeFormatter dateAndTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd - HH:mm:ss");
        dateAndTime = dateAndTimeFormat.format(LocalDateTime.now());

        //get the string used for naming the round's saved game
        DateTimeFormatter fileNameFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        fileName = fileNameFormatter.format(LocalDateTime.now());
    }

    //Create a new save for the round that has just finished. It contains a JSONObject that has 4 keys
    public void createGameEntry(int winner, int[] moves) throws IOException {

        //Get path for the new save
        File newFile = new File(connect4Dir, fileName);
        newFile.createNewFile();

        String winnerStr, roundDifficultyStr;

        //Make winner key
        if(winner == 1) {
            winnerStr = "AI";
        }
        else winnerStr = "P";

        //Make difficulty key
        switch (roundDifficuly) {
            case 0 : roundDifficultyStr = "Trivial"; break;
            case 2 : roundDifficultyStr = "Medium"; break;
            default : roundDifficultyStr = "Hard"; break;
        }

        JSONObject object = new JSONObject();

        //Put all 4 keys in new JSONObject
        object.put("dateAndTime", dateAndTime);
        object.put("difficulty", roundDifficultyStr);
        object.put("winner", winnerStr);
        object.put("moves", moves);

        //Turn object into string and write to file
        String jsonString = object.toString();
        FileWriter fileWriter = new FileWriter(newFile);
        fileWriter.write(jsonString);
        fileWriter.close();

    }

    //Return the information of a game in the form of the string that is shown when creating the history list
    public String getGameEntry(File fileName) throws FileNotFoundException, IOException {

        //get string from saved file
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Turn the retrieved string into a JSONObject 
        String jsonString = stringBuilder.toString();
        JSONObject object = new JSONObject(jsonString);

        //Get the 3 string keys from the JSONObject, and return the completed fused string
        String dateAndTime = object.getString("dateAndTime");
        String difficulty = object.getString("difficulty");
        if(difficulty.equals("Trivial")) {
            difficulty = "Trivial    ";
        }
        else if(difficulty.equals("Medium")) {
            difficulty = "Medium ";
        }
        else {
            difficulty = "Hard       ";
        }

        String winner = object.getString("winner");
        String jlistString = dateAndTime + "  L: " + difficulty + "W: " + winner;

        return jlistString;
    }

    //This method is used to get the moves array from a saved file
    public int[] getMoves(File fileName) {

        //get String from file and turn it into a JSONObject
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString = stringBuilder.toString();
        JSONObject object = new JSONObject(jsonString);

        //Get array from object, turn it into an int array and return it
        JSONArray movesArray = object.getJSONArray("moves");
        
        int[] moves = new int[43];
        for(int i = 0 ; i < 43; i++) {
            moves[i] = movesArray.getInt(i);
        }
        return moves;
    }

    //Return a String array with all the names of the files, already sorted because of their names
    public String[] getFileNames(){
        
        String[] savedGames = connect4Dir.list();
        
        return savedGames;
    } 
    
    //Make a String array of every String used as a description of a game for the JList
    public String[] makeGameStrings(String[] fileNames) throws IOException {

        String[] jListStrings = new String[fileNames.length];

        for(int i = fileNames.length - 1; i >= 0; i--) {
            File newPath = new File(connect4Dir, fileNames[i]);
            String tempString = getGameEntry(newPath);
            jListStrings[fileNames.length - 1 - i] = tempString;
        }

        return jListStrings;
    }
}
