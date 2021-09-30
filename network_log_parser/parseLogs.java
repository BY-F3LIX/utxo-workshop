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

    static String PATH = System.getProperty("user.home") + "/logs";

    static ArrayList<ArrayList<String>> timestamps = new ArrayList<>();

    static int BLOCK = 5;

    public static void main(String[] args) {
        File[] files = new File(PATH).listFiles();
        for (File file : files) {
            parseFile(file);
        }
        makeRecieverFile(timestamps, "r_graph");

    }

    private static void makeRecieverFile(ArrayList<ArrayList<String>> times, String fileName) {
        long biggest = 0;
        long nodes = 0;

        try {
            FileWriter writer = new FileWriter(fileName);
            for (int block = 3; block < times.size(); block++) {

                SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss.SSS");

                long[] timesLong = new long[times.get(block).size()];
                for (int i = 0; i < times.get(block).size(); i++) {
                    timesLong[i] = formatter.parse(times.get(block).get(i)).getTime();
                }
                Arrays.sort(timesLong);
                long min = timesLong[0];
                for (int i = 0; i < timesLong.length; i++) {
                    timesLong[i] = timesLong[i] - min;
                }
                nodes = nodes < timesLong.length ? timesLong.length : nodes;
                biggest = biggest < timesLong[timesLong.length - 1] ? timesLong[timesLong.length - 1] : biggest;
                int[] atTime = new int[(int) timesLong[timesLong.length - 1] + 1];
                for (int i = 0; i < atTime.length; i++) {
                    for (int j = 0; j < timesLong.length; j++) {
                        if (i >= timesLong[j])
                            atTime[i]++;
                    }
                }
                // writer.write("" + date.getTime() + " ");
                // writer.write(Arrays.toString(atTime));

                for (int i = 0; i < atTime.length; i++) {
                    writer.write("" + atTime[i] + " ");
                }
                writer.write("\n");

            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            FileWriter writer = new FileWriter("longestTime");
            writer.write("" + biggest + " " + times.size() + " " + nodes);
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void makeRecieverFile(ArrayList<ArrayList<String>> times, int block, String fileName) {
        int miner = times.get(block).size();

        // TODO maybe 12/24h
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss.SSS");

        try {
            FileWriter writer = new FileWriter(fileName);

            long[] timesLong = new long[times.get(block).size()];
            for (int i = 0; i < times.get(block).size(); i++) {
                timesLong[i] = formatter.parse(times.get(block).get(i)).getTime();
            }
            Arrays.sort(timesLong);
            long min = timesLong[0];
            for (int i = 0; i < timesLong.length; i++) {
                timesLong[i] = timesLong[i] - min;
            }
            int[] atTime = new int[(int) timesLong[timesLong.length - 1] + 1];
            for (int i = 0; i < atTime.length; i++) {
                for (int j = 0; j < timesLong.length; j++) {
                    if (i >= timesLong[j])
                        atTime[i]++;
                }
            }
            // writer.write("" + date.getTime() + " ");
            // writer.write(Arrays.toString(atTime));

            for (int i = 0; i < atTime.length; i++) {
                writer.write("" + atTime[i] + " ");
            }
            writer.write("\n");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseFile(File file) {
        //String name = "";
        //boolean miner = false;
        //boolean firstBlock = false;
        //Pattern nameLinePattern = Pattern.compile(".*(Node name: )\\S*$");
        //Pattern roleLinePattern = Pattern.compile(".*(Role: )\\S*$");
        Pattern importedPattern = Pattern.compile(".*(Imported #)(\\d+).*");
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                // load name for Node
                //if (name.equals("") && nameLinePattern.matcher(data).matches()) {
                //    String[] temp = data.split(" ");
                //    name = temp[temp.length - 1];
                //    System.out.println(name);
                //}
                //if (!firstBlock && miner == false && roleLinePattern.matcher(data).matches()) {
                //    String[] temp = data.split(" ");
                //    miner = temp[temp.length - 1].equals("AUTHORITY");
                //}

                if (importedPattern.matcher(data).matches()) {
                    String[] temp = data.split(" ");
                    int block = Integer.parseInt(temp[temp.length - 2].substring(1));
                    while (timestamps.size() < block) {
                        timestamps.add(new ArrayList<>());
                    }
                    String time = data.split(" ")[1];
                    timestamps.get(block - 1).add(time);
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
