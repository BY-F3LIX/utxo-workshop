import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * netGen
 */
public class netGen {

    static String FILE_NAME = "network.yaml";
    static int NODE_COUNT = 30;
    static String MINER_IMAGE = "node";

    public static void main(String[] args) {
        // Generate File
        try {
            File myObj = new File(FILE_NAME);
            if (myObj.createNewFile()) {
                System.out.println("Network file  \"" + myObj.getName() + "\" created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String setup = "setup:\n    version: '3'\n    services:\n";

        for (int i = 0; i < NODE_COUNT; i++) {
            String temp = "      Node" + i + ":\n        image: " + MINER_IMAGE + "\n        volumes:\n          - ~/logs:/var/tmp/\n        command: [\"Node-" + i +"\"]\n";
            setup += temp;
        }


        // Write to File
        try {
            FileWriter myWriter = new FileWriter(FILE_NAME);
            myWriter.write(setup);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}