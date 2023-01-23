import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

import static java.util.Spliterators.iterator;

public class JSON extends Student implements CoursesData{

    public String createJSONFile() {
        File jsonFile = new File("Student course details.json");
        FileWriter writeToJsonFile = null;
        JSONObject obj = new JSONObject();
        try {
            writeToJsonFile = new FileWriter(jsonFile);


            JSONArray enrolledCourses1 = new JSONArray();
            JSONArray enrolledCourses2 = new JSONArray();
            JSONArray enrolledCourses3 = new JSONArray();

            enrolledCourses1.add(1);
            enrolledCourses1.add(2);
            enrolledCourses1.add(3);
            enrolledCourses1.add(4);

            enrolledCourses2.add(2);
            enrolledCourses2.add(4);
            enrolledCourses2.add(6);

            enrolledCourses3.add(1);
            enrolledCourses3.add(3);
            enrolledCourses3.add(5);

            obj.put(1, enrolledCourses1);
            obj.put(2, enrolledCourses2);
            obj.put(3, enrolledCourses3);
             //enrolledCourses2.remove(1);

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
    public String enrollmentRequest(String studentID, String courseID) {
        // read the existing data first to check if the student reached the max no. of courses
        File jsonFile = new File("Student course details.json");
        JSONParser parser = new JSONParser();
        int counter=0;
        String feedback="";
        try {
            Object object = parser.parse(new FileReader(jsonFile));
            JSONObject obj = (JSONObject) object;
            JSONArray enrolledCourses = (JSONArray) obj.get(studentID);
            Iterator <Integer> iterator = enrolledCourses.iterator();

            while (iterator.hasNext()) {
                 iterator.next();
                 counter++;
            }

            // if the number of courses < 6 he can enroll
            if (counter<6) {
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
                System.err.println("Students can’t enroll in more than 6 programs at the same time.");
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
                nextLine = courseScanner.nextLine();
                String[] row = nextLine.split(",");
                course[i] = new Courses(Integer.parseInt(row[0])-1, row[1], row[2], row[3], row[4],row[5]);
            }
            //printing sample value
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

            if (jsonObject.get(studentID) != null) {
                JSONArray enrolledCourses = (JSONArray) jsonObject.get(studentID);
                Iterator <Integer> iterator = enrolledCourses.iterator();
                System.out.println("==========================================================================");
                System.out.println("Student details page");
                System.out.println("==========================================================================");
                System.out.println(studentData);
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Courses enrolled");

                while (iterator.hasNext()) {
                    index++;
                    System.out.print(index + "- ");
                    String s = String.valueOf(iterator.next());
                    //System.out.println(s);
                    getSpecificCourseData(Integer.parseInt(s));

                }
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
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();

        }

    }
    @Override
    public String toString() {
        return "Name: " + super.getName() + "      Grade: " + super.getAddress() + "     Email: " + super.getEmail();
    }
}







