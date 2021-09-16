import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * netGen
 */
public class netGen {

    static boolean DOCKER_COMPOSE = true;
    static String FILE_NAME = "docker-compose.yaml";
    static int NODE_COUNT = 25;
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

        System.out.println(DOCKER_COMPOSE ? "Making Docker Compose file" : "Making docker network simulator file");
        String setup = DOCKER_COMPOSE ? "version: '3.3'\nservices:\n" : "setup:\n    version: '3'\n    services:\n";

        for (int i = 0; i < NODE_COUNT; i++) {
            String temp = DOCKER_COMPOSE ? "  Node" + i + ":\n    image: " + MINER_IMAGE + "\n    volumes:\n      - ~/logs:/var/tmp/\n    command: [\"Node-" + i + "\"]\n" : "      Node" + i + ":\n        image: " + MINER_IMAGE + "\n        volumes:\n          - ~/logs:/var/tmp/\n        command: [\"Node-" + i +"\"]\n";
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