import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Student extends Courses{

    private int studentID, grade;
    private String name;
    private String email;
    private String address;
    private String region;
    private String country;
    private static int totalNumberOfStudents;

    public  Student(){}
    public  Student (int studentID,String name,int grade,String email,String address,String region,String country){
        this.studentID=studentID;
        this.grade=grade;
        this.name=name;
        this.email=email;
        this.address=address;
        this.region=region;
        this.country=country;
    }
    public  Student (int studentID,String name,int grade,String email,String address){
        this.studentID=studentID;
        this.grade=grade;
        this.name=name;
        this.email=email;
        this.address=address;

    }

    public int getTotalNumberOfStudents() {
        return totalNumberOfStudents;
    }

    public void setTotalNumberOfStudents(int totalNumberOfStudents) {
        this.totalNumberOfStudents = totalNumberOfStudents;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int id) {
        this.studentID = studentID;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static void fillStudentObjectsWithData(String studentDatacsvFilePath) {
        // creating an array of Student objects and fill each Student with the proper data
        File readStudentData = new File(studentDatacsvFilePath);
        Scanner getNumberOfLines = null;
        Scanner studentScanner = null;
        try {
            getNumberOfLines = new Scanner (readStudentData);
            studentScanner = new Scanner(readStudentData);
            int numberOfLines=0, i=0;

            while ( getNumberOfLines.hasNextLine()) {
                numberOfLines++;
                getNumberOfLines.nextLine();
            }

            Student[] student =new Student[numberOfLines];
            String nextLine = studentScanner.nextLine();
            while ( studentScanner.hasNextLine()) {
                i++;
                nextLine = studentScanner.nextLine();
                String[] row = nextLine.split(",");
                student[i] = new Student(Integer.parseInt(row[0]), row[1], Integer.parseInt(row[2]), row[3], row[4]);
                totalNumberOfStudents++;
            }
            //printing sample value
//        System.out.println(student[10].toString());
//        getNumberOfLines.close();
//        studentScanner.close();
        } catch (NumberFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            getNumberOfLines.close();
            studentScanner.close();

        }
    }
    public static String getSpecificStudentData( String studentID) {
        // creating an array of Student objects and fill each Student with the proper data
        File readStudentData = new File("student_data_with_ID.csv");
        Scanner getNumberOfLines = null;
        Scanner studentScanner = null;
        String studentData=null;
        try {
            getNumberOfLines = new Scanner (readStudentData);
            studentScanner = new Scanner(readStudentData);
            int numberOfLines=0, i=0;

            while ( getNumberOfLines.hasNextLine()) {
                numberOfLines++;
                getNumberOfLines.nextLine();
            }

            Student[] student =new Student[numberOfLines];
            String nextLine = studentScanner.nextLine();
            while ( studentScanner.hasNextLine()) {
                i++;
                nextLine = studentScanner.nextLine();
                String[] row = nextLine.split(",");
                student[i] = new Student(Integer.parseInt(row[0]), row[1], Integer.parseInt(row[2]), row[3], row[4]);
            }
            //printing sample value
            //System.out.println(student[10].toString());
             studentData = student[Integer.parseInt(studentID)].toString();

        } catch (NumberFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            getNumberOfLines.close();
            studentScanner.close();

        }
        return studentData;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "      Grade: " + this.grade + "     Email: " + this.email;
    }
}
