
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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

		// Craete a JSON file and put sample data on it
		JSON jsonFile =new JSON();
		jsonFile.createJSONFile();

		Courses courses = new Courses();
		Scanner scanner = new Scanner(System.in);
			String studentID="";
			String action = "b";

			// enter s to terminate the app
			while (!action.equals("s") ) {

				try  {


						if (action.equals("b")) {
							// studentData.homePage() will read students data from source file and return total number of students
							int numberOfStudents = studentData.homePage();

							// Get a specific Student id to show its data
							System.out.print("Please select the required student: ");
							studentID = scanner.nextLine();

							// validating student id
							while (Integer.parseInt(studentID) > numberOfStudents || Integer.parseInt(studentID) < 1) {
								System.err.println("Invalid Student ID");
								studentID = scanner.nextLine();
							}
							//Now show the enrolled courses after getting a valid student id
							jsonFile.enrolledCoursesInfo(studentID);

							// Getting input from below list
							System.out.println("Please choose from the following: ");
							System.out.println("a - Enroll in a course");
							System.out.println("d - Unenrollfrom an existing course");
							System.out.println("r - Replacing an existing course");
							System.out.println("b - Back to the main page");
							System.out.print("please select the required action: ");
							action = scanner.nextLine();
						}


						// start enrollment
					if (action.equals("a") ){
						System.out.println("inside a");
						action = courses.enrollment(action, studentID);

					}


						// Unenrollment
					if (action.equals("d") ) {
						action = courses.unenrollment(action, studentID);
					}

						//Replacement
					if (action.equals("r") ){
						JSON jObj = new JSON();
						System.out.print("Please enter the course id to be replaced:");
						action = scanner.nextLine();
						String feedback =jObj.unenrollmentRequest(studentID, action);
							if (!action.equals("b") && !feedback.equals("not allowed")) {
								courses.courseReplacement(action,studentID);
								jsonFile.enrolledCoursesInfo(studentID);

								System.out.println("Please choose from the following: ");
								System.out.println("a - Enroll in a course");
								System.out.println("d - Unenrollfrom an existing course");
								System.out.println("r - Replacing an existing course");
								System.out.println("b - Back to the main page");
								System.out.print("please select the required action: ");
								action = scanner.nextLine();
							}
						}
				} catch (NumberFormatException e) {

					e.printStackTrace();
				}catch (InputMismatchException e){
					e.printStackTrace();
				}catch(NoSuchElementException e){
					e.printStackTrace();
				}

			}
		}
		
	}

	






