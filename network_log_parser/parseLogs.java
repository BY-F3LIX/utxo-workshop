import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.naming.TimeLimitExceededException;

public class parseLogs {

    static String PATH = "/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_generator/logs";

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
        writeToFile(timestamps, "R_file");
        makeRecieverFile(timestamps, 3, "r_graph");

    }

    private static void makeRecieverFile(ArrayList<ArrayList<String>> times, int block, String fileName) {
        int miner = times.get(block).size();

        // TODO maybe 12/24h
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss.SSS");

        try {
            FileWriter writer = new FileWriter(fileName);

            System.out.println(miner);

            long[] timesLong = new long[times.get(block).size()];
            for (int i = 0; i < times.get(block).size(); i++) {
                timesLong[i] = formatter.parse(times.get(block).get(i)).getTime();
            }
            Arrays.sort(timesLong);
            long min = timesLong[0];
            for (int i = 0; i < timesLong.length; i++) {
                timesLong[i] = timesLong[i] - min;
            }
            int[] atTime = new int[ (int) timesLong[timesLong.length -1] + 1]; 
            for (int i = 0; i < atTime.length; i++) {
                for (int j = 0; j < timesLong.length; j++) {
                    if( i >= timesLong[j]) atTime[i]++;
                }
            }
            //writer.write("" + date.getTime() + " ");
            //writer.write(Arrays.toString(atTime));

            for (int i = 0; i < atTime.length; i++) {
                writer.write("" + atTime[i] + " ");
            }
            writer.write("\n");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(ArrayList<ArrayList<String>> times, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("test");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
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
                // load name for Node
                if (name.equals("") && nameLinePattern.matcher(data).matches()) {
                    String[] temp = data.split(" ");
                    name = temp[temp.length - 1];
                    System.out.println(name);
                }
                if (miner == false && roleLinePattern.matcher(data).matches()) {
                    String[] temp = data.split(" ");
                    miner = temp[temp.length - 1].equals("AUTHORITY");
                    System.out.println(miner ? "IS MINER!" : "is note");
                }

                if (importedPattern.matcher(data).matches()) {
                    String[] temp = data.split(" ");
                    int block = Integer.parseInt(temp[temp.length - 2].substring(1));
                    while (timestamps.size() < block) {
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
