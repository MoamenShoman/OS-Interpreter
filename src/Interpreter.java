import java.io.*;

public class Interpreter {

    private final OS os;
    private int curProcessID;
    private int[] quanta;

    public Interpreter() throws IOException {
        quanta = new int[3];
        os = new OS();
        scheduler();
    }


    public void parser(String instruction) throws IOException {

        System.out.println("Executing: ");
        System.out.println(instruction);
        System.out.println("--------------------------------------------");
        System.out.println();

        String[] arr = instruction.split(" ");
        switch (arr[0]) {
            case "assign":
                switch (arr[2]) {
                    case "input":
                        assign(arr[1], os.input());
                        break;
                    case "readFile":
                        assign(arr[1], os.readFile(os.readMemory(arr[3], curProcessID)));
                        break;
                    default:
                        assign(arr[1], os.readMemory(arr[2], curProcessID));
                }
                break;

            case "print":
                switch (arr[1]) {
                    case "input":
                        os.print(os.input());
                        break;
                    case "readFile":
                        os.print(os.readFile(os.readMemory(arr[2], curProcessID)));
                        break;
                    default:
                        os.print(os.readMemory(arr[1], curProcessID));
                        break;
                }
                break;
            case "add":
                add(arr[1], arr[2]);
                break;

            case "readFile":
                os.readFile(os.readMemory(arr[1], curProcessID));
                break;

            case "writeFile":
                os.writeFile(os.readMemory(arr[1],curProcessID), os.readMemory(arr[2], curProcessID));
                break;
        }
    }


    public void scheduler() throws IOException {

        while (!os.readyQueue.isEmpty()) {

            curProcessID = os.readyQueue.poll();
            quanta[curProcessID - 1]++;
            os.updateState(curProcessID, "Running");
            int pc = os.getPC(curProcessID);

            parser((String) os.memory[pc]);
            pc++;

            if (os.memory[pc] == null) {

                os.updatePC(curProcessID, pc);
                os.updateState(curProcessID, "Finished");
                System.out.println("Process " + curProcessID + " finished in " + quanta[curProcessID - 1] + " quanta.");
                System.out.println("--------------------------------------------");
                System.out.println();
                continue;
            }

            parser((String) os.memory[pc]);
            pc++;
            os.updatePC(curProcessID, pc);
            if (os.memory[pc] == null) {
                os.updateState(curProcessID, "Finished");
                System.out.println("Process " + curProcessID + " finished in " + quanta[curProcessID - 1] + " quanta.");
                System.out.println("--------------------------------------------");
                System.out.println();
                continue;
            }
            os.updateState(curProcessID, "Not Running");

            os.readyQueue.add(curProcessID);
        }
    }

    private void assign(String assignee, String assigned) {
        os.writeMemory(assignee, assigned, curProcessID);
    }

    private void add(String a, String b) {

        int x = Integer.parseInt(os.readMemory(a, curProcessID));
        int y = Integer.parseInt(os.readMemory(b, curProcessID));

        int z = x + y;
        os.writeMemory(a, "" + z, curProcessID);
    }

    public static void main(String[] args) throws IOException {
          Interpreter i = new Interpreter();

    }
}
