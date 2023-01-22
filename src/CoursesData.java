import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public interface CoursesData {

    public String readCourseDataFromXML(String courseDataFilePath);
     public void fillCourseObjectsWithData(String courseDatacsvFilePath);

     public int showCoursesData();


}
