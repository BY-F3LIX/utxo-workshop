import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;


public class retrieve_Addresses {
    static String PATH = System.getProperty("user.home") + "/logs";
    static String SCRIPT_PATH = "/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_generator/getIps.sh";
    //static String PATH = "/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_generator/logs";
    static String FILE_NAME = System.getProperty("user.home") + "/logs/public_addresses.txt";
    static String output = "";

    public static void main(String[] args) {
        
        File[] files = new File(PATH).listFiles();
        for (File file : files) {
            parseFile(file);
        }
        try {
            FileWriter myWriter = new FileWriter(FILE_NAME);
            myWriter.write(output);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    private static String getIps(String name){
        String result = "";
        Runtime r = Runtime.getRuntime();
        String[] command = {"bash", SCRIPT_PATH, name};
        try {
            Process p = r.exec(command);
    
            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
    
            String line = "";


            while ((line = b.readLine()) != null) {
                result += line + " ";
            }
    
            b.close();
        } catch (Exception e) {
            System.err.println("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return result;
    }

    private static void parseFile(File file) {
        Pattern addressPattern = Pattern.compile(".*\\/(ip4)\\/.*");
        Pattern namePattern = Pattern.compile(".*Node name:.*");
        Pattern idPattern = Pattern.compile(".*Local node identity is:.*");
        String name = "";
        String id = "";
        String[] ips = new String[0];
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                // load name for Node

                if (namePattern.matcher(data).matches()) {
                    String[] temp = data.split(" ");
                    name = temp[temp.length - 1];
                    ips = getIps(name).split(" ");
                }

                if (idPattern.matcher(data).matches()) {
                    String[] temp = data.split(" ");
                    id = temp[temp.length - 4];
                }


                //if (addressPattern.matcher(data).matches()) {
                //    String[] temp = data.split(" ");
                //    output += temp[temp.length - 1] + " ";
                //}
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String ip : ips) {
            if(ip.equals("")) continue;
            System.out.println("/ip4/"+ip+"/tcp/30333/p2p/" + id);
            output += "/ip4/"+ip+"/tcp/30333/p2p/" + id+  " ";
        }
    }
}

///ip4/172.27.0.5/tcp/30333/p2p/12D3KooWQHqsciroFcWgL5EZEbDYuxWAZwiB8JrQcZ7wkHv2CtLm