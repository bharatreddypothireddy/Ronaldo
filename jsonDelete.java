
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class jsonDelete {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("./src/json2.json"));
        JSONObject jobject  = (JSONObject) obj;
        //System.out.println(jobject);
        String id = (String) jobject.get("uniqueId");
        Object data = jobject.get("data");
        JSONObject japplication = (JSONObject)data;
        Object application = japplication.get("application");
        JSONObject japplicationinfo = (JSONObject)application;
        japplicationinfo.remove("applicationInfo");
        System.out.println(application);
        FileWriter fw =new FileWriter("./src/out2.json");
        fw.write(jobject.toString());
        fw.close();
    }

}
