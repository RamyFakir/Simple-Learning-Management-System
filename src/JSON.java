import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

//implements CoursesData
public class JSON extends Student {

    public String createJSONFile() {
        File jsonFile = new File("Student course details.json");
        FileWriter writeToJsonFile = null;
        JSONObject obj = new JSONObject();
        try {
            writeToJsonFile = new FileWriter(jsonFile);


            JSONArray enrolledCourses1 = new JSONArray();
            JSONArray enrolledCourses2 = new JSONArray();
            JSONArray enrolledCourses3 = new JSONArray();
            JSONArray enrolledCourses00 = new JSONArray();
            enrolledCourses00.add(0);
            for (int i=1; i<=super.getTotalNumberOfStudents();i++){

                obj.put(i, enrolledCourses00);
            }
            enrolledCourses1.add(1);
            enrolledCourses1.add(2);
            enrolledCourses1.add(3);
            enrolledCourses1.add(4);

            enrolledCourses2.add(2);
            enrolledCourses2.add(4);
            enrolledCourses2.add(6);

            enrolledCourses3.add(1);
            enrolledCourses3.add(2);
            enrolledCourses3.add(3);



            obj.put(1, enrolledCourses1);
            
            obj.put(2, enrolledCourses2);
            obj.put(3, enrolledCourses3);

             

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert writeToJsonFile != null;
                writeToJsonFile.write(obj.toJSONString());
                writeToJsonFile.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         return jsonFile.getPath();
    }
    
    public  void getSpecificCourseData(int courseID) {
        // creating an array of Course objects and fill each Course with the proper data
        File readCourseData = new File("course_data_sorted.csv");
        Scanner getNumberOfLines = null;
        Scanner courseScanner = null;
        try {
            getNumberOfLines = new Scanner (readCourseData);
            courseScanner = new Scanner(readCourseData);
            int numberOfLines=0, i=0;

            while ( getNumberOfLines.hasNextLine()) {
                numberOfLines++;
                getNumberOfLines.nextLine();
            }

            Courses[] course =new Courses[numberOfLines];
            String nextLine = courseScanner.nextLine();
            while ( courseScanner.hasNextLine()) {
                i++;
                String[] row = nextLine.split(",");
                course[i] = new Courses(Integer.parseInt(row[0]), row[1], row[2], row[3], row[4],row[5]);
                nextLine = courseScanner.nextLine();
            }
            
            System.out.println(course[courseID].toString());
            getNumberOfLines.close();
            courseScanner.close();
        } catch (NumberFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            getNumberOfLines.close();
            courseScanner.close();
        }



    }
    public void enrolledCoursesInfo(String studentID)  {

        File jsonFile = new File("Student course details.json");
        FileReader readFromJSON = null;
        JSONParser parser = new JSONParser();

        try {
            String studentData = JSON.getSpecificStudentData(studentID);
            readFromJSON = new FileReader(jsonFile);
            Object obj = parser.parse(readFromJSON);
            JSONObject jsonObject = (JSONObject) obj;
            int index=0;
            int counter=0;
            String b="";
            JSONArray enrolledCourses = (JSONArray) jsonObject.get(studentID);
            Iterator <Integer> iterator = enrolledCourses.iterator();
            while (iterator.hasNext()) {
               b=  String.valueOf(iterator.next());
                counter++;
            }
            if (!b.equals("0")) {

                System.out.println("==========================================================================");
                System.out.println("Student details page");
                System.out.println("==========================================================================");
                System.out.println(studentData);
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Courses enrolled");
                Iterator <Integer> iterator2 = enrolledCourses.iterator();
                while (iterator2.hasNext()) {
                    String  s = String.valueOf(iterator2.next());
                    if (!s.equals("0")){
                    index++;
                    System.out.print(index + "- ");

                    //System.out.println(s);
                    getSpecificCourseData(Integer.parseInt(s));
                    }

                }
                System.out.println("--------------------------------------------------------------------------");
            } else {
                System.out.println("==========================================================================");
                System.out.println("Student details page");
                System.out.println("==========================================================================");
                System.out.println(studentData);
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Courses enrolled");
                System.out.println("This student hasn't enrolled in any courses");
                System.out.println("--------------------------------------------------------------------------");
            }
           // readFromJSON.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();

        }

    }
    
    public String enrollmentRequest(String studentID, String courseID) {
        // read the existing data first to check if the student reached the max no. of courses
        File jsonFile = new File("Student course details.json");
        JSONParser parser = new JSONParser();
        String buffer ="";
        int counter=0;
        String feedback="";
        try {
            Object object = parser.parse(new FileReader(jsonFile));
            JSONObject obj = (JSONObject) object;
            JSONArray enrolledCourses = (JSONArray) obj.get(studentID);
            
            Iterator <Integer> iterator = enrolledCourses.iterator();

            while (iterator.hasNext()) {
                buffer = String.valueOf(iterator.next());
                 counter++;
            }
            if (buffer.contains(courseID)){
                System.err.println("The student can't enroll in the same course more than one time");
                feedback = "not allowed";
            }
            // if the number of courses < 6 he can enroll
            else if (counter<6) {
                try {
                    FileWriter writeToJsonFile = new FileWriter(jsonFile);
                    enrolledCourses.add(courseID);
                    obj.put(studentID, enrolledCourses);
                    writeToJsonFile.write(obj.toJSONString());
                    writeToJsonFile.flush();
                    writeToJsonFile.close();
                    feedback = "enrolled";
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
            else{
                System.err.println("Students can't enroll in more than 6 programs at the same time.");
                feedback = "not allowed";
            }
          //enrolledCourses2.remove(1);

        }  catch (ParseException e) {
            e.printStackTrace();
        }  catch (IOException e) {
                e.printStackTrace();
            }

        return feedback;
    }
    public String unenrollmentRequest(String studentID, String courseID) {
        // read the existing data first to check if the student reached the max no. of courses
        File jsonFile = new File("Student course details.json");
        JSONParser parser = new JSONParser();
        int bufferID =0;
        String buffer ="";
        int counter=0;
        String feedback="";
        try {
            Object object = parser.parse(new FileReader(jsonFile));
            JSONObject obj = (JSONObject) object;
            JSONArray enrolledCourses = (JSONArray) obj.get(studentID);
            Iterator <Integer> iterator = enrolledCourses.iterator();

            while (iterator.hasNext()) {
                buffer = String.valueOf(iterator.next());
                if (buffer.equals((Object)courseID)){
                    bufferID = counter;
                }
                counter++;  
            }

             if (counter>1) {
                try {
                    FileWriter writeToJsonFile = new FileWriter(jsonFile);
                    //obj.remove(studentID, );
                    enrolledCourses.remove(bufferID);
                    writeToJsonFile.write(obj.toJSONString());
                    writeToJsonFile.flush();
                    writeToJsonFile.close();
                    feedback = "unenrolled";
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
            else{
                System.err.println("Faild to unenroll: The student has only one or no courses to unenroll from");
                feedback = "not allowed";
            }
          //

        }  catch (ParseException e) {
            e.printStackTrace();
        }  catch (IOException e) {
                e.printStackTrace();
            }

        return feedback;
    }
   
    @Override
    public String toString() {
        return "Name: " + super.getName() + "      Grade: " + super.getAddress() + "     Email: " + super.getEmail();
    }
}







