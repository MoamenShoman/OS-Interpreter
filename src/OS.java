import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class OS {

    Object[] memory;
    Queue<Integer> readyQueue;


    public OS() throws IOException {
        memory = new Object[60];
        readyQueue = new LinkedList<>();

        for (int i = 0; i <= 2; i++) {
            BufferedReader br = new BufferedReader(new FileReader("Program " + (i + 1) + ".txt"));


            memory[i * 20] = i + 1; //Process ID
            memory[i * 20 + 1] = "Not Running";// Process State
            memory[i * 20 + 2] = i * 20 + 5; // Program Counter
            memory[i * 20 + 3] = i * 20; // Min Boundary
            memory[i * 20 + 4] = i * 20 + 19; // Max Boundary

            String curLine;
            int lineCounter = i * 20 + 5;
            while ((curLine = br.readLine()) != null)
                memory[lineCounter++] = curLine;

            readyQueue.add((i + 1));
        }
    }

    public void updatePC(int processID, int value) {
        memory[(processID - 1) * 20 + 2] = value;
    }

    public void updateState(int processID, String state) {
        memory[(processID - 1) * 20 + 1] = state;

        System.out.println("Process ID: " + processID);
        System.out.println("Process State: " + state);
        System.out.println("PC: " + getPC(processID));
        System.out.println("Min Boundary:" + memory[(processID - 1) * 20 + 3]);
        System.out.println("Max Boundary:" + memory[(processID - 1) * 20 + 4]);
        System.out.println("--------------------------------------------");
        System.out.println();
    }

    public int getPC(int processID) {
        return (int) memory[(processID - 1) * 20 + 2];
    }

    public String readMemory(String variable, int processID) {

        String value = "";
        for (int i = 19; i >= 13; i--) {
            if (memory[(processID - 1) * 20 + i] == null)
                return variable;
            else if (((Pair) memory[(processID - 1) * 20 + i]).variable.equals(variable)) {
                value = ((Pair) memory[(processID - 1) * 20 + i]).value;
                System.out.println("Reading Memory[" + ((processID - 1) * 20 + i) + "]");
                System.out.println(variable + " = " + value);
                System.out.println("--------------------------------------------");
                System.out.println();
                break;
            }
        }
        return value;
    }

    public void writeMemory(String variable, String value, int processID) {

        for (int i = 19; i >= 13; i--)
            if (memory[(processID - 1) * 20 + i] == null) {
                memory[(processID - 1) * 20 + i] = new Pair(variable, value);
                System.out.println("Writing in Memory[" + ((processID - 1) * 20 + i) + "]");
                System.out.println(variable + " = " + value);
                System.out.println("--------------------------------------------");
                System.out.println();
                return;
            }
            else if(((Pair) memory[(processID - 1) * 20 + i]).variable.equals(variable))
            {
                ((Pair) memory[(processID - 1)*20 + i]).value = value;
                System.out.println("Writing in Memory[" + ((processID - 1) * 20 + i) + "]");
                System.out.println(variable + " = " + value);
                System.out.println("--------------------------------------------");
                System.out.println();
                return;
            }
    }

    public void print(String s) {
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(s);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");


        System.out.println();
    }

    public String input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }


    public String readFile(String fileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"));
        String curLine;
        StringBuilder text = new StringBuilder();
        while ((curLine = br.readLine()) != null)
            text.append(curLine).append("\n");

        return text.toString();
    }

    public void writeFile(String fileName, String data) throws IOException {
        String oldData = "";
        File newFile;
        try {
            oldData = readFile(fileName);
        } catch (IOException e) {
            newFile = new File(fileName + ".txt");
            newFile.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(fileName + ".txt");
        fileWriter.write(oldData + data);
        fileWriter.close();
    }

    static class Pair {

        String variable;
        String value;

        public Pair(String variable, String value) {
            this.variable = variable;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "variable='" + variable + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }



}
