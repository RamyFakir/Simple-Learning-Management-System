import java.io.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

    	// Read the student data from (txt)file , arrange it and convert it to a (csv) file
    	StudentData studentData =new StudentData();
        String studentDatacsvFilePath = studentData.txtToCvs("student-data.txt");

        // adding id for each row
        String studentDataWithIdPath = studentData.add_ID(studentDatacsvFilePath);

        // print all student data to console


		// creating an array of Student objects and fill each object with the proper data
		Student student =new Student();
		Student.fillStudentObjectsWithData(studentDataWithIdPath);


		 // Read the Courses data from (xml)file , arrange it and convert it to a (csv) file
    	CoursesData readCourseData = new Courses();
		String courseDatacsvFilePath = readCourseData.readCourseDataFromXML("coursedata.xml");

		// creating an array of Course objects and fill each Course with the proper data
		readCourseData.fillCourseObjectsWithData(courseDatacsvFilePath);

		// Craete a JSON file and put sample data
		JSON jsonFile =new JSON();
		jsonFile.createJSONFile();



		while (true) {
			int numberOfStudents =studentData.homePage();
			// Get Student id to show its data
			System.out.println("Please select the required student: ");
			Scanner scanner = new Scanner(System.in);
			String studentID= scanner.next();
			while (Integer.parseInt(studentID)>numberOfStudents ||Integer.parseInt(studentID)<1 ){
				System.err.println("Invalid Student ID");
				studentID= scanner.next();

			}
			jsonFile.enrolledCoursesInfo(studentID);

			// Getting input from below list
			System.out.println("Please choose from the following: ");
			System.out.println("a - Enroll in a course");
			System.out.println("d - Unenrollfrom an existing course");
			System.out.println("r - Replacing an existing course");
			System.out.println("b - Back to the main page");
			System.out.print("please select the required action: ");

			String action = scanner.next();
			Courses courses = new Courses();

			if (action.equals("a")) {
				String home =courses.enrollment("a",studentID);
				if (home.equals("b")){
					continue;
				}
			}

			else if (action.equals("b")) {
				continue;
			}
			 else {
				System.err.println("Wrong entry ");
				System.out.println("--------------------------------------------------------------------------");
			}
		}
	}



}




