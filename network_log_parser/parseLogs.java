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
    static ArrayList<ArrayList<Pair<Long, Integer>>> timestampsWithId = new ArrayList<>();

    static int BLOCK = 5;
    static int STARTBLOCK = 50;

    public static void main(String[] args) {
        File[] files = new File(PATH).listFiles();
        for (File file : files) {
            parseFileWithID(file);
            parseFile(file);
        }
        makeRecieverFile(timestamps, "r_graph");
        makeGif(timestampsWithId.get(55), "gifFile");

    }

    public static void makeGif(ArrayList<Pair<Long,Integer>> times, String fileName) {
        int frames = 10;
        int msPerFrame = 0;

        try {
            FileWriter writer = new FileWriter(fileName);
            

            long minTime = Integer.MAX_VALUE;
            long maxTime = Integer.MIN_VALUE;
            int biggestId = Integer.MIN_VALUE;
            for (Pair<Long,Integer> item : times) {
                if(minTime > item.first){
                    minTime = item.first;
                }
                if(maxTime < item.first){
                    maxTime = item.first;
                }
                if(biggestId < item.second){
                    biggestId = item.second;
                }
            }
            for (Pair<Long,Integer> item : times) {
                item.first -= minTime;
            }

            msPerFrame = (int) ((maxTime - minTime) / frames) + 1;
            System.out.println("INFO: " + msPerFrame + "ms per Frame");
            
            for(int i = 0; i <= frames; i++){
                int ms = i * msPerFrame;
                int[] state = new int[biggestId + 1];
                for (Pair<Long,Integer> item : times) {
                    if(item.first <= ms){
                        state[item.second] = 1;
                    }
                }
                String output = Arrays.toString(state);
                output = output.replace("[", "");
                output = output.replace("]", "");
                output = output.replace(",", "");
                writer.write(output);
                writer.write("\n");
            }

            
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void makeRecieverFile(ArrayList<ArrayList<String>> times, String fileName) {
        long biggest = 0;
        long nodes = 0;

        try {
            FileWriter writer = new FileWriter(fileName);
            FileWriter writerGaus = new FileWriter("Gaus");
            for (int block = STARTBLOCK; block < times.size(); block++) {

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
                Arrays.sort(timesLong);
                String gausOutput = Arrays.toString(Arrays.copyOfRange(timesLong, 1, timesLong.length -1 ));
                System.out.println(gausOutput);
                gausOutput = gausOutput.replace("[", "");
                gausOutput = gausOutput.replace("]", "");
                gausOutput = gausOutput.replace(",", "");
                writerGaus.write(gausOutput + " ");
                // writer.write("" + date.getTime() + " ");
                // writer.write(Arrays.toString(atTime));

                for (int i = 0; i < atTime.length; i++) {
                    writer.write("" + atTime[i] + " ");
                }
                writer.write("\n");

            }
            writer.close();
            writerGaus.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter("longestTime");
            writer.write("" + biggest + " " + times.size() + " " + nodes);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void parseFileWithID(File file) {
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss.SSS");
        String name = "";
        int id = -1;
        // boolean miner = false;
        // boolean firstBlock = false;
        Pattern nameLinePattern = Pattern.compile(".*(Node name: )\\S*$");
        // Pattern roleLinePattern = Pattern.compile(".*(Role: )\\S*$");
        Pattern importedPattern = Pattern.compile(".*(Imported #)(\\d+).*");
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                // load name for Node
                if (name.equals("") && nameLinePattern.matcher(data).matches()) {
                    String[] temp = data.split(" ");
                    name = temp[temp.length - 1];
                    id = Integer.parseInt(name.substring(4));
                }

                if (importedPattern.matcher(data).matches()) {
                    String[] temp = data.split(" ");
                    int block = Integer.parseInt(temp[temp.length - 2].substring(1));
                    while (timestampsWithId.size() < block) {
                        timestampsWithId.add(new ArrayList<>());
                    }
                    String time = data.split(" ")[1];
                    timestampsWithId.get(block - 1).add(new Pair<Long, Integer>(formatter.parse(time).getTime(), id));
                }

            }
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseFile(File file) {
        // String name = "";
        // boolean miner = false;
        // boolean firstBlock = false;
        // Pattern nameLinePattern = Pattern.compile(".*(Node name: )\\S*$");
        // Pattern roleLinePattern = Pattern.compile(".*(Role: )\\S*$");
        Pattern importedPattern = Pattern.compile(".*(Imported #)(\\d+).*");
        ArrayList<Integer> checkedBlocks = new ArrayList<>();
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                // load name for Node
                // if (name.equals("") && nameLinePattern.matcher(data).matches()) {
                // String[] temp = data.split(" ");
                // name = temp[temp.length - 1];
                // System.out.println(name);
                // }
                // if (!firstBlock && miner == false && roleLinePattern.matcher(data).matches())
                // {
                // String[] temp = data.split(" ");
                // miner = temp[temp.length - 1].equals("AUTHORITY");
                // }

                if (importedPattern.matcher(data).matches()) {
                    String[] temp = data.split(" ");
                    String time = data.split(" ")[1];
                    int block = Integer.parseInt(temp[temp.length - 2].substring(1));
                    if(checkedBlocks.contains(block)){
                        timestamps.get(block - 1).set(timestamps.get(block-1).size() - 1, time);
                    }else{
                        checkedBlocks.add(block);
                        while (timestamps.size() < block) {
                            timestamps.add(new ArrayList<>());
                        }
                        timestamps.get(block - 1).add(time);
                    }
                }

            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

class Pair<T, U> {
    T first;
    U second;

    Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}
