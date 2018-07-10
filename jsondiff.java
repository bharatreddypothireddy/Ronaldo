import org.apache.poi.ss.usermodel.Row;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class jsondiff {
    public static void main(String[] args) throws IOException, JSONException {

    String content1 = new String(Files.readAllBytes(Paths.get("./src/Hbase.txt")));
    String content2 = new String(Files.readAllBytes(Paths.get("./src/Mongo.txt")));
    JSONCompareResult result =
            JSONCompare.compareJSON(content1, content2, JSONCompareMode.NON_EXTENSIBLE);
    String output = result.toString();
    String[] a = output.split(";");
    PrintWriter out = new PrintWriter("./src/different.csv");
    System.out.println(output);
    Pattern p = Pattern.compile("(.*?)Expected:(.*?)got:(.*)");
    Pattern p1 = Pattern.compile("Expected:(.*?)butnonefound(.*)");
    Pattern p2 = Pattern.compile("(.*?)Unexpected:(.*)");
//        StringBuffer csvData = new StringBuffer("");
//        StringBuffer csvHeader = new StringBuffer("");
//        csvHeader.append("Vaariable,oldValue,NewValue\n");
//        out.write(csvHeader.toString());

                            for (String x : a) {
        String y = x.replaceAll("\\s", "");
        Matcher m = p.matcher(y);
        Matcher m1 = p1.matcher(y);
        Matcher m2 = p2.matcher(y);
        //Row row = sheet.createRow(i);

//        if (m.find()) {
//            row.createCell(1).setCellValue(m.group(1));
//            row.createCell(2).setCellValue(m.group(2));
//            row.createCell(3).setCellValue(m.group(3));
////                csvData.append(m.group(1).toString());
////                csvData.append(',');
////                csvData.append(m.group(2).toString());
////                csvData.append(',');
////                csvData.append(m.group(3).toString());
////                csvData.append('\n');
//        } else if (m1.find()) {
//            row.createCell(1).setCellValue(m1.group(1));
//            row.createCell(2).setCellValue("present");
//            row.createCell(3).setCellValue("not present");
////                csvData.append(m1.group(1));
////                csvData.append(',');
////                csvData.append("present");
////                csvData.append(',');
////                csvData.append("not present");
////                csvData.append('\n');
//        } else if (m2.find()) {
//            row.createCell(1).setCellValue(m2.group(1) + "." + m2.group(2));
//            row.createCell(2).setCellValue("not present");
//            row.createCell(3).setCellValue("present");
////                csvData.append(m2.group(1));
////                csvData.append('.');
////                csvData.append(m2.group(2));
////                csvData.append(",not present,present \n");
//        }
//        i++;
           if(m.find())
                y = m.group(1)+","+m.group(2)+","+m.group(3);
            else if(m1.find())
                y = m1.group(1)+",present,not present";
            else if(m2.find())
              y =m2.group(1)+"."+ m2.group(2)+",not present ,present";
            System.out.println(y);
            out.println(y.toString());
    }



       out.close();
}}
