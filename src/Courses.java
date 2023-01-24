import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Courses implements CoursesData{
	
	 private int courseID;
	    private String name;
	    private String instructor;
	    private String duration;
	    private String time;
	    private String location;
	    private static Courses[] course;


	public  Courses(){}
	 public  Courses (int courseID,String name,String instructor,String duration,String time,String location){
	        this.courseID=courseID;
	        this.name=name;
	        this.instructor=instructor;
	        this.duration=duration;
	        this.time=time;
	        this.location=location;

	    }
		public int getCourseID() {
			return courseID;
		}
	
		public void setCourseID(int courseID) {
			this.courseID = courseID;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public String getInstructor() {
			return instructor;
		}
	
		public void setInstructor(String instructor) {
			this.instructor = instructor;
		}
	
		public String getDuration() {
			return duration;
		}
	
		public void setDuration(String duration) {
			this.duration = duration;
		}
	
		public String getTime() {
			return time;
		}
	
		public void setTime(String time) {
			this.time = time;
		}
	
		public String getLocation() {
			return location;
		}
	
		public void setLocation(String location) {
			this.location = location;
		}



	public String readCourseDataFromXML(String courseDataFilePath) {
		File file1 = new File(courseDataFilePath);
		File file2 = new File("course_data_sorted.csv");
		FileWriter writer = null;
		Scanner scanner = null;
		String course = null;
		try {
			writer = new FileWriter(file2);
			scanner = new Scanner (file1);

			while (scanner.hasNextLine()) {
				course=scanner.nextLine()
						.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","")
						.replace(",",".").replace("<root>","")
						.replace("</root>","")
						.replace("<row>","").replace("</row>","")
						.replace("<id>","").replace("</id>",",")
						.replace("<CourseName>","").replace("</CourseName>",",")
						.replace("<Instructor>","").replace("</Instructor>",",")
						.replace("<CourseDuration>","").replace("</CourseDuration>",",")
						.replace("<CourseTime>","").replace("</CourseTime>",",")
						.replace("<Location>","").replace("</Location>","\n").replace(" ","");
				//System.out.println(course);
				writer.write(course);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}finally {

			try {

				writer.flush();
				writer.close();
				scanner.close();
			}catch (IOException e){}

		}
		return file2.getPath();
	}





	public  void fillCourseObjectsWithData(String courseDatacsvFilePath) {
		// creating an array of Course objects and fill each Course with the proper data
		File readCourseData = new File(courseDatacsvFilePath);
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

			 course =new Courses[numberOfLines];
			String nextLine = null;
			while ( courseScanner.hasNextLine()) {
				nextLine = courseScanner.nextLine();
				String[] row = nextLine.split(",");
				course[i] = new Courses(Integer.parseInt(row[0]), row[1], row[2], row[3], row[4],row[5]);
				//System.out.println(course[i]);
				i++;
			}
			//printing sample value
			// System.out.println(course[10].toString());
			getNumberOfLines.close();
			courseScanner.close();
		} catch (NumberFormatException | FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			getNumberOfLines.close();
			courseScanner.close();

		}
	}
	public int showCoursesData(){
		File file = new File("course_data_sorted.csv");
		FileReader readFromCVS = null;
		BufferedReader readBuf;
		String line;
		int numberOfCourses=0;
		try {
			readFromCVS = new FileReader(file);
			readBuf = new BufferedReader(readFromCVS);
			line = "";
			Scanner scanner =new Scanner(file);
			System.out.println("Enrollment page");
			System.out.println("=======================================================================================");
			String[] row = null;
			System.out.println("id,      course name,                 instructor,         Course duration ,    Course time,        location ");
			System.out.println("---------------------------------------------------------------------------------------");

			while (scanner.hasNextLine()) {
			    scanner.nextLine();
				numberOfCourses++;
			}
			line = readBuf.readLine();
			while (line != null) {
				row = line.split(",");

					System.out.printf("%-8s", row[0] + ",");
					System.out.printf("%-30s", row[1] + ",");
					System.out.printf("%-25s", row[2] + ",");
					System.out.printf("%-18s", row[3] + ",");
					System.out.printf("%-18s", row[4] + ",");
					System.out.printf(",%s", row[5]);
					System.out.println("\n");
				line = readBuf.readLine();

			}
			System.out.println("---------------------------------------------------------------------------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return numberOfCourses;
	}




	public  String enrollment(String a, String studentID){
		Scanner scanner = new Scanner(System.in);
		String action = a;
		Courses courses = new Courses();
		int numberOfCourses = courses.showCoursesData();
		while (!Objects.equals(action, "b")) {
			System.out.println("Please make one of the following:");
			System.out.println("Enter the course id that you want to enroll the student to");
			System.out.println("Enter b to go back to the home page");
			System.out.print("Please select the required action:");

			action = scanner.next();
			try {
				for (int i = 1; i <= numberOfCourses; i++) {
					if (Integer.parseInt(action) == i) {
						JSON request = new JSON();
						String feedback = request.enrollmentRequest(studentID,action);
						if (feedback.equals("enrolled")){
						System.out.println("The student is Enrolled Successfully in the " +  this.course[Integer.parseInt(action)-1].name + " course");
						break;}
						else {
							System.err.println("Failed to enroll:");
							break;
						}
					}
				}
  				if (Integer.parseInt(action) > numberOfCourses || Integer.parseInt(action) < 1) {
					System.err.println("Failed to enroll: The course with id =" + Integer.parseInt(action) + " is not exist");
				}
				  if (action.equals("b")){
					  break;
				  }
				  

			}
			catch (NumberFormatException e){
				System.err.println("Wrong choice! please enter a valid choice");
			}
		}//scanner.close();
		return action;
	}



	public  String unenrollment(String a, String studentID){
		Scanner scanner = new Scanner(System.in);
		String action = a;
		try {
			JSON request = new JSON();
			System.out.println("Please enter course id:");
			action = scanner.next();
		while (!Objects.equals(action, "b")) {
						String feedback = request.unenrollmentRequest(studentID,action);
						if (feedback.equals("unenrolled")){
						System.out.println("Unenrolled successfully from the " +  this.course[Integer.parseInt(action)-1].name + " course");
						request.enrolledCoursesInfo(studentID);
						}
						System.out.println("Please enter course id:");
						action = scanner.next();
						
				}
				
				
			}catch (NumberFormatException e){
				System.err.println("Wrong choice! please enter a valid choice");
			}return action;

    }




	
	@Override
	public String toString() {
		return  this.courseID + ",  " + this.name + ",         " + this.instructor + ",          " + this.duration + ",      " + this.time + ",     " + this.location;
	}
	
}