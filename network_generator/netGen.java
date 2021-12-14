import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * netGen
 */
public class netGen {

    static String IMAGE = "node";
    static String FILE_NAME = "docker-compose.yaml";
    static int NODE_COUNT = 350;
    static int SUBNET_COUNT = 120;
    static int RANDOM_NODES = 4; // Random nodes to connect with the connector node
    static int CONNECTORS = 5;
    static String RETRIEVE_LOCATION = "/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_generator/App.jar";

    static ArrayList<String> networks = new ArrayList<>();
    static ArrayList<Node> nodes = new ArrayList<>();

    static String from = "";
    static String to = "";

    public static void main(String[] args) {
        Node.MAX_PEER_COUNT = 35;
        Node.DELAY = 0;
        //makeLineTop(); DONT USE
        //lineTop();
        generateCompose();
        generateBash();

    }

    public static void generateBash() {
        String content = "#!/bin/bash\n";
        content += "sudo rm ~/logs/* \n";
        content += "sudo chmod 666 /var/run/docker.sock\n";
        content += "docker network prune -f \n";

        for (String string : networks) {
            content += "docker network create " + string + "\n";
        }

        content += "docker-compose up -d --remove-orphans";
        int counter = 0;
        for (Node node : nodes) {
            if (node.otherNetworks.size() == 0) {
                if(counter > 20){
                    content += "\ndocker-compose up -d --remove-orphans";
                    counter = 0;
                }
                counter++;
                content += " " + node.name;
            }
        }
        content += "\n";

        if (SUBNET_COUNT < NODE_COUNT) {
            content += "sleep 10\n";
            content += "java -jar " + RETRIEVE_LOCATION + "\n";

            // for (Node node : nodes) {
            // if(node.otherNetworks.size() > 0){
            // for (String string : node.otherNetworks) {
            // content += "docker network connect " + string + " " + node.name + "\n";
            // }
            // }
            // }

            content += "docker-compose up -d --remove-orphans";
            for (Node node : nodes) {
                if (node.otherNetworks.size() > 0) {
                    content += " " + node.name;
                }
            }
            content += "\n";
        }

        writeFile(content, "startNetwork.sh");
    }

    public static void generateCompose() {
        Random rand = new Random();
        int net_count = 0;
        int net = 0;
        ArrayList<Integer> inNetwork = new ArrayList<>();
        for (int i = 0; i < NODE_COUNT; i++) {
            if (net_count >= SUBNET_COUNT) {
                net++;
                net_count = 0;
                inNetwork = new ArrayList<>();
            }
            if (net_count == 0) {
                networks.add("net" + net);
            }
            Node tempNode = new Node("Node" + i, false, false);
            if (i == 0)
                tempNode.validator = true;
            tempNode.networks.add("net" + net);
            net_count++;
            nodes.add(tempNode);

            // for Graph
            //for (Integer others : inNetwork) {
            //    from += " " + i;
            //    to += " " + others;
            //}
            inNetwork.add(i);
        }

        System.out.println("made nodes");

        if (net > 0) {
            for (int i = 0; i < ((CONNECTORS > 0) ?  CONNECTORS:net + 2); i++) {
                Node tempNode = new Node("Node" + (i+NODE_COUNT), false, true);
                for (int j = 0; j < RANDOM_NODES; j++) {
                    tempNode.networks.add("connect" + i + "-" + j);
                    networks.add("connect" + i  + "-" + j);

                    int number = rand.nextInt(NODE_COUNT);
                    if (nodes.get(number).otherNetworks.contains("connect" + i  + "-" + j)) {
                        j--;
                        continue;
                    }
                    nodes.get(number).otherNetworks.add("connect" + i  + "-" + j);
                    from += " " + (int) (i + NODE_COUNT);
                    to += " " + number;
                }
                nodes.add(tempNode);
            }
        }
        System.out.println("made connectors");

        String compose = "version: '3.3'\nservices:\n";

        for (Node node : nodes) {
            compose += node.getStringWithOffset(1);
        }

        compose += "networks:\n";
        for (String string : networks) {
            compose += " ".repeat(2) + string + ":\n";
            compose += " ".repeat(4) + "external: True\n";
        }

        writeFile(compose, FILE_NAME);

        writeFile(from, "from");
        writeFile(to, "to");
    }

    public static void makeLineTop() {
        // DONT USE
        int net = 0;
        networks.add("net" + net);
        for (int i = 0; i < NODE_COUNT; i++) {

            Node tempNode = new Node("Node" + i, false, false);
            if (i == 0)
                tempNode.validator = true;
            tempNode.cmd = "--reserved-only";
            // tempNode.withList = true;
            tempNode.networks.add("net" + net);
            nodes.add(tempNode);

            // for Graph

        }

        for (int i = 1; i < NODE_COUNT; i++) {
            from += " " + i;
            to += " " + (i - 1);
        }

        String compose = "version: '3.3'\nservices:\n";

        for (Node node : nodes) {
            compose += node.getStringWithOffset(1);
        }

        compose += "networks:\n";
        for (String string : networks) {
            compose += " ".repeat(2) + string + ":\n";
            compose += " ".repeat(4) + "external: True\n";
        }

        writeFile(compose, FILE_NAME);

        writeFile(from, "from");
        writeFile(to, "to");

        /*
         * 
         * ____ _ | __ ) __ _ ___| |__ | _ \ / _` / __| '_ \ s | |_) | (_| \__ \ | | |
         * |____/ \__,_|___/_| |_|
         * 
         * 
         */

        String content = "#!/bin/bash\n";
        content += "sudo rm ~/logs/* \n";
        content += "sudo chmod 666 /var/run/docker.sock\n";
        content += "docker network prune -f \n";

        for (String string : networks) {
            content += "docker network create " + string + "\n";
        }

        for (Node node : nodes) {
            content += "docker-compose up -d --remove-orphans " + node.name + "\n";
        }

        if (SUBNET_COUNT < NODE_COUNT) {
            content += "sleep 10\n";
            content += "java -jar " + RETRIEVE_LOCATION + "\n";

            // for (Node node : nodes) {
            // if(node.otherNetworks.size() > 0){
            // for (String string : node.otherNetworks) {
            // content += "docker network connect " + string + " " + node.name + "\n";
            // }
            // }
            // }

            content += "docker-compose up -d --remove-orphans";
            for (Node node : nodes) {
                if (node.otherNetworks.size() > 0) {
                    content += " " + node.name;
                }
            }
            content += "\n";
        }

        writeFile(content, "startNetwork.sh");

    }

    public static void lineTop() {
        Random rand = new Random();
        int net = 0;
        Node previousNode = null;
        for (int i = 0; i < NODE_COUNT; i++) {
            Node tempNode = new Node("Node" + i, false, true);
            tempNode.withList = true;
            if (i == 0) {
                tempNode.validator = true;
                tempNode.withList = false;
            }
            networks.add("net" + i);
            tempNode.networks.add("net" + i);

            if (previousNode != null) {
                previousNode.networks.add("net" + i);
            }

            nodes.add(tempNode);
            previousNode = tempNode;

            if (i > 0) {
                from += " " + i;
                to += " " + (i - 1);
            }

        }

        String compose = "version: '3.3'\nservices:\n";

        for (Node node : nodes) {
            compose += node.getStringWithOffset(1);
        }

        compose += "networks:\n";
        for (String string : networks) {
            compose += " ".repeat(2) + string + ":\n";
            compose += " ".repeat(4) + "external: True\n";
        }

        writeFile(compose, FILE_NAME);

        writeFile(from, "from");
        writeFile(to, "to");

        String content = "#!/bin/bash\n";
        content += "sudo rm ~/logs/* \n";
        content += "sudo chmod 666 /var/run/docker.sock\n";
        content += "docker network prune -f \n";

        for (String string : networks) {
            content += "docker network create " + string + "\n";
        }

        for (Node node : nodes) {
            content += "docker-compose up -d --remove-orphans " + node.name + "\n";
            content += "sleep 5\n";
            content += "java -jar " + RETRIEVE_LOCATION + "\n";
        }

        // boolean skip = true;;
        // content += "docker-compose up -d --remove-orphans";
        // for (Node node : nodes) {
        // skip = !skip;
        // if(skip){
        // continue;
        // }
        // content += " " + node.name;
        //
        // }
        // content += "\n";
        //
        // content += "sleep 10\n";
        // content += "java -jar " + RETRIEVE_LOCATION + "\n";
        //
        // skip = false;
        // content += "docker-compose up -d --remove-orphans";
        // for (Node node : nodes) {
        // skip = !skip;
        // if(skip){
        // continue;
        // }
        // content += " " + node.name;
        //
        // }
        // content += "\n";

        writeFile(content, "startNetwork.sh");

    }

    public static void writeFile(String content, String file_name) {
        // Generate File
        try {
            File myObj = new File(file_name);
            if (myObj.createNewFile()) {
                System.out.println("File  \"" + myObj.getName() + "\" created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Write to File
        try {
            FileWriter myWriter = new FileWriter(file_name);
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}

/**
 * Node
 */
class Node {
    static String IMAGE = "node";
    static int SPACES = 2;
    static int MAX_PEER_COUNT = 20;
    static int DELAY = 100;

    String name;
    String cmd = "";
    boolean withList = false;
    boolean validator = false;
    boolean connector = false;
    ArrayList<String> otherNetworks = new ArrayList<>();
    ArrayList<String> networks = new ArrayList<>();
    // Node3:
    // image: node
    // volumes:
    // - ~/logs:/var/tmp/
    // command: ["Node-3"]
    // networks:
    // - two

    public Node(String name, boolean validator, boolean connector) {
        this.name = name;
        this.validator = validator;
        this.connector = connector;
    }

    public String getStringWithOffset(int offset) {
        String result = "";

        result += " ".repeat(SPACES * offset) + name + ":\n";
        result += " ".repeat(SPACES * offset) + " ".repeat(SPACES * 1) + "image: " + IMAGE + "\n";
        result += " ".repeat(SPACES * offset) + " ".repeat(SPACES * 1) + "volumes:" + "\n";
        result += " ".repeat(SPACES * offset) + " ".repeat(SPACES * 2) + "- ~/logs:/var/tmp/" + "\n";
        result += " ".repeat(SPACES * offset) + " ".repeat(SPACES * 1) + "command: [\"" + name + "\" " + getCommand()
                + "]" + "\n";

        if (networks.size() > 0)
            result += " ".repeat(SPACES * offset) + " ".repeat(SPACES * 1) + "networks:" + "\n";
        for (String net : networks) {
            result += " ".repeat(SPACES * offset) + " ".repeat(SPACES * 2) + "- " + net + "\n";
        }
        for (String net : otherNetworks) {
            result += " ".repeat(SPACES * offset) + " ".repeat(SPACES * 2) + "- " + net + "\n";
        }

        if(connector){
            result += " ".repeat(SPACES * offset) + " ".repeat(SPACES * 1) + "cap_add:\n";
            result += " ".repeat(SPACES * offset) + " ".repeat(SPACES * 2) + "- NET_ADMIN\n";
        }

        return result;
    }

    public String getCommand() {
        String result = "";
        if (validator)
            result += ",\" " + cmd + " --validator --max-parallel-downloads " + MAX_PEER_COUNT + "\"";
        else
            result += ",\" " + cmd + " --max-parallel-downloads " + MAX_PEER_COUNT + "\"";
        if (otherNetworks.size() > 0 || withList)
            result += ",\"/var/tmp/public_addresses.txt\"";
        else
            result += ",\"/var/tmp/public_addresses.txt\"";
        if (connector && DELAY > 0) {
            result += ",\"" + DELAY + "\"";
        }
        return result;
    }

    @Override
    public String toString() {
        return getStringWithOffset(0);
    }
}