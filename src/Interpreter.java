import java.io.*;
import java.util.Hashtable;

public class Interpreter {
    Hashtable<String, String> values;

    public Interpreter(String[] paths) {
        values = new Hashtable<>();
        for (String path : paths) {

        }
    }
    public Interpreter(){}

    private void parser(String path) throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader br = new BufferedReader(fileReader);
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split(" ");
            for (int i = 0; i < arr.length; i++) {
                switch (arr[i]) {
                    case "assign":
                        switch (arr[i + 2]) {
                            case "input":
                                assign(arr[i + 1], input());
                            case "readFile":
                                assign(arr[i + 1], readFile(arr[i + 3]));
                            default:
                                if (!values.containsKey(arr[i + 1]))
                                    assign(arr[i + 1], arr[i + 2]);
                                else
                                    assign(arr[i + 1], values.get(arr[i + 2]));
                        }
                        break;

                    case "print":
                        switch (arr[i + 1]) {
                            case "input":
                                print(input());
                            case "readFile":
                                print(readFile(arr[i + 2]));
                            default:
                                if (!values.containsKey(arr[i + 1]))
                                    print(arr[i + 1]);
                                else
                                    print(values.get(arr[i + 1]));
                                break;
                        }
                    case "add":
                        add(arr[i + 1], arr[i + 2]);

                    case "readFile":
                        readFile(arr[i + 1]);

                    case "writeFile":
                        writeFile(arr[i + 1], arr[i + 2]);
                }
            }

        }

    }

    private void print(String s) {
        System.out.println(s);
    }

    private String input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    private void assign(String assignee, String assigned) {
        values.put(assignee, assigned);
    }

    private void add(String a, String b) {
        values.put(a, values.get(a) + values.get(b));
    }

    private String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String curLine = "";
        String text = "";
        while ((curLine = br.readLine())!=null)
            text += curLine + "\n";

        return text;
    }

    private void writeFile(String fileName, String data) throws IOException {
        String oldData = "";
        try {
            oldData = readFile(fileName);
        }catch ( IOException e){

        }
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(oldData + data);
        fileWriter.close();

    }

}
