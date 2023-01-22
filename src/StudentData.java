import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StudentData {

    public String txtToCvs(String filepath) {

        File file = new File("student_data_without_ID.csv");
        FileReader reader = null;
        FileWriter writer = null;
        try {
            reader = new FileReader(filepath);
            writer = new FileWriter(file);
            int data = reader.read();
            while (data != -1) {
                if ((char) data == '$') {
                    data = '\n';
                }
                if ((char) data == '#') {
                    data = ',';
                }
                writer.write(data);
                //System.out.print((char)data);
                data = reader.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert writer != null;
                reader.close();
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return file.getPath();
    }


    public String add_ID(String filepath) {
        File file = new File("student_data_with_ID.csv");
        FileReader readFromCVS = null;
        FileWriter writeToCSV = null;
        BufferedReader readBuf;
        String line;
        String addID;
        int x = 0;
        try {
            readFromCVS = new FileReader(filepath);
            writeToCSV = new FileWriter(file);
            readBuf = new BufferedReader(readFromCVS);
            line = readBuf.readLine();
            addID = "id" + "," + line;
            while (line != null) {
                //String[] row = addID.split(",");
                //dataToCSV.write(Arrays.toString(row));
                writeToCSV.write(addID);
                writeToCSV.write('\n');
                //System.out.println(addID);
                x++;
                line = readBuf.readLine();
                addID = x + "," + line;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//                assert dataFromtxt != null;
//                assert dataToCSV != null;
                readFromCVS.close();
                writeToCSV.flush();
                writeToCSV.close();
            } catch (IOException e) {
                System.out.println("Something went wrong while closing student-data.txt");
            }
        }
        return file.getPath();
    }

    public int homePage() {

        File file = new File("student_data_with_ID.csv");
        FileReader readFromCVS = null;
        BufferedReader readBuf;
        String line;
        int numberOfStudents=0;
        try {
            readFromCVS = new FileReader(file);
            readBuf = new BufferedReader(readFromCVS);
            line = readBuf.readLine();
            System.out.println("Welcome to LMS");
            System.out.println("created by {Ramy Fakir - 21.01.2023}");
            System.out.println("=======================================================================================");
            System.out.println(" Home page     ");
            System.out.println("=======================================================================================");
            System.out.println(" Student list     ");
            String[] row = line.split(",");
            //                 id             name                   Grade               email                            address                           region              country
            System.out.println(row[0] + " " + row[1] + "          " + row[2] + "     " + row[3] + "                    " + row[4] + "                       " + row[5] + "       " + row[6]);
            line = readBuf.readLine();
            while (line != null) {
                row = line.split(",");
                numberOfStudents++;
                if (row.length > 7) {
                    System.out.printf("%-3s", row[0] + ",");
                    System.out.printf("%-16s", row[1] + ",");
                    System.out.printf("%-3s", row[2] + ",");
                    System.out.printf("%-30s", row[3] + ",");
                    System.out.printf("%-1s", row[4]);
                    System.out.printf(",%-10s", row[5] + ",");
                    System.out.printf("%-12s", row[6] + ",");
                    System.out.printf("%s", row[7]);
                } else {
                    System.out.printf("%-3s", row[0] + ",");
                    System.out.printf("%-16s", row[1] + ",");
                    System.out.printf("%-3s", row[2] + ",");
                    System.out.printf("%-30s", row[3] + ",");
                    System.out.printf("%-30s", row[4] + ",");
                    System.out.printf("%-10s", row[5] + ",");
                    System.out.printf("%s", row[6]);

                }

                System.out.println("\n");
                line = readBuf.readLine();

            }
            System.out.println("---------------------------------------------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberOfStudents;
    }
}
