import java.io.*;
import java.util.Hashtable;

public class OS {

    private Hashtable<String, String> memory;

    public OS(){
        memory = new Hashtable<>();
    }


    public String readMemory(String address){
        return memory.getOrDefault(address, address);
    }
    public void writeMemory(String address, String value){
        memory.put(address, value);
    }


    public void print(String s) {
        System.out.println(s);
    }

    public String input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    public String readFile(String fileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName+".txt"));
        String curLine = "";
        String text = "";
        while ((curLine = br.readLine()) != null)
            text += curLine + "\n";

        return text;
    }

    public void writeFile(String fileName, String data) throws IOException {
        String oldData = "";
        File newFile;
        try {
            oldData = readFile(fileName);
        } catch (IOException ignored) {
            newFile = new File(fileName+".txt");
            newFile.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(fileName+".txt");
        fileWriter.write(oldData + data);
        fileWriter.close();
    }

}
