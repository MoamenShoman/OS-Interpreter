import java.io.*;
import java.util.Hashtable;

public class Interpreter {

    private OS os;


    public Interpreter() throws IOException {
        os = new OS();
    }

    public void parser(String path) throws IOException {
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
                                assign(arr[i + 1], os.input());
                                break;
                            case "readFile":
                                assign(arr[i + 1], os.readFile(os.readMemory(arr[i + 3])));
                                break;
                            default:
                                assign(arr[i + 1], os.readMemory(arr[i + 2]) );
                        }
                        break;

                    case "print":
                        switch (arr[i + 1]) {
                            case "input":
                                os.print(os.input());
                                break;
                            case "readFile":
                                os.print(os.readFile(os.readMemory(arr[i + 2])));
                                break;
                            default:
                                os.print(os.readMemory(arr[i + 1]));
                                break;
                        }
                        break;
                    case "add":
                        add(arr[i + 1], arr[i + 2]);
                        break;

                    case "readFile":
                        os.readFile(os.readMemory(arr[i + 1]));
                        break;

                    case "writeFile":
                        os.writeFile(arr[i + 1], os.readMemory(arr[i + 2]));
                        break;
                }
            }
        }
    }

    private void assign(String assignee, String assigned) {
        os.writeMemory(assignee, assigned);
    }

    private void add(String a, String b) {

        int x = Integer.parseInt(os.readMemory(a));
        int y = Integer.parseInt(os.readMemory(b));

        int z = x + y;
        os.writeMemory(a, ""+z);
    }

    public static void main(String[] args) throws IOException {
        Interpreter i = new Interpreter();
        i.parser("Program 1.txt");
        i.parser("Program 2.txt");
        i.parser("Program 3.txt");
    }
}
