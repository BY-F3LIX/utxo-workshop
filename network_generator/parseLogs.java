import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class parseLogs {

    static String PATH = "./logs";

    static ArrayList<ArrayList<String>> timestamps = new ArrayList<>();

    public static void main(String[] args) {
        File[] files = new File(PATH).listFiles();
        for (File file : files) {
            parseFile(file);
        }
        System.out.println("Block 3");
        for (String string : timestamps.get(2)) {
            System.out.println(string);
        }
        
    }

    private static void parseFile(File file) {
        String name = "";
        boolean miner = false;
        Pattern nameLinePattern = Pattern.compile(".*(Node name: )\\S*$");
        Pattern roleLinePattern = Pattern.compile(".*(Role: )\\S*$");
        Pattern importedPattern = Pattern.compile(".*(Imported #)(\\d+).*");
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                //load name for Node
                if(name.equals("") && nameLinePattern.matcher(data).matches()){
                    String[] temp = data.split(" ");
                    name = temp[temp.length - 1];
                    System.out.println(name);
                }
                if(miner == false && roleLinePattern.matcher(data).matches()){
                    String[] temp = data.split(" ");
                    miner = temp[temp.length - 1].equals("AUTHORITY");
                    System.out.println(miner ? "IS MINER!" : "is note");
                }

                if(importedPattern.matcher(data).matches()){
                    String[] temp = data.split(" ");
                    int block = Integer.parseInt(temp[temp.length - 2].substring(1));
                    if(timestamps.size() < block){
                        timestamps.add(new ArrayList<>());
                    }
                    String time = data.split(" ")[1];
                    timestamps.get(block - 1).add(time);

                    System.out.println("Block #" + block + " at " + time);
                }

            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getAllFiles(String path) {
        File folder = new File(path);
        System.out.println(folder.getName());
        for (File file : folder.listFiles()) {
            System.out.println(file.getName());
        }
    }
}
