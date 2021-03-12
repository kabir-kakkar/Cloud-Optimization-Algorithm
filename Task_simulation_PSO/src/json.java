import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

 
public class json
{
    @SuppressWarnings("unchecked")
    public static void main( String[] args ) throws ParseException, IOException
    {
        try {
            //First Employee
            JSONObject employeeDetails = new JSONObject();
            employeeDetails.put("firstName", "Lokesh");
            employeeDetails.put("lastName", "Gupta");
            employeeDetails.put("website", "howtodoinjava.com");
            
           
            //Add employees to list
            JSONArray employeeList = new JSONArray();
            employeeList.add(employeeDetails);
            //Write JSON file
            FileWriter file = new FileWriter("employees.json");
                
                file.write(employeeList.toString());
                file.flush();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
       
        JSONParser parser = new JSONParser();
   
            Object obj = null;
        try {
            obj = parser.parse(new FileReader("sample.json"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(json.class.getName()).log(Level.SEVERE, null, ex);
        }

            JSONObject jsonObject =  (JSONObject) obj;
            List<Integer> l= (List<Integer>) jsonObject.get("name");
            System.out.println(l);
        
        
        
        
    }
}