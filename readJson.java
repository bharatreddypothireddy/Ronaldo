
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class readJson {
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
        Object applicationinfo = japplicationinfo.get("applicationInfo");
        JSONObject jincome = (JSONObject) applicationinfo;
        jincome.replace("lastName","Reddy");
        jincome.replace("firstName","Bharat");
        Object income = jincome.get("monthlyIncome");
        System.out.println(id);
        System.out.println();
        System.out.println(data);
        System.out.println();
        System.out.println(applicationinfo);
        System.out.println();
        System.out.println(income);
        FileWriter fw =new FileWriter("./src/out.json");
        fw.write(jobject.toString());
        fw.close();
}

}
