import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class jcomp {
    static int i =1;
    public static void main(String[] args) throws IOException {
        String firstFolderPath = "C:\\Users\\10108\\Downloads\\Hbase_Scrub";
        String secondFolderPath = "C:\\Users\\10108\\Downloads\\oldscrub";
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Diff");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("user");
        header.createCell(1).setCellValue("variable");
        header.createCell(2).setCellValue("old");
        header.createCell(3).setCellValue("new");

        try (Stream<Path> paths = Files.walk(Paths.get(firstFolderPath))) {
            paths.filter(Files::isRegularFile).forEach( filePath -> {
                try {
                    System.out.println(filePath.toString());
                    File tempFile1 = new File(filePath.toString());
                    File tempFile2 = new File(secondFolderPath +"\\"+ filePath.getFileName().toString());
                    boolean exists1 = tempFile1.exists();
                    boolean exists2 = tempFile2.exists();
                    if (exists1 && exists2 == true) {
//                        String content1 = FileUtils.readFileToString(new File(tempFile1.toString()));
//                        String content2 = FileUtils.readFileToString(new File(tempFile2.toString()));
                        String content1 = new String(Files.readAllBytes(Paths.get(tempFile1.toString())));
                        String content2 = new String(Files.readAllBytes(Paths.get(tempFile2.toString())));
                        JSONCompareResult result =
                                JSONCompare.compareJSON(content1, content2, JSONCompareMode.NON_EXTENSIBLE);
                        String output = result.toString();
                        String[] a = output.split(";");
                            //PrintWriter out = new PrintWriter("./src/diff.csv");
                            //System.out.println(output);
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
                                Row row = sheet.createRow(i);
                                row.createCell(0).setCellValue(filePath.getFileName().toString());
                                if (m.find()) {
                                    row.createCell(1).setCellValue(m.group(1));
                                    row.createCell(2).setCellValue(m.group(2));
                                    row.createCell(3).setCellValue(m.group(3));
//                csvData.append(m.group(1).toString());
//                csvData.append(',');
//                csvData.append(m.group(2).toString());
//                csvData.append(',');
//                csvData.append(m.group(3).toString());
//                csvData.append('\n');
                                } else if (m1.find()) {
                                    row.createCell(1).setCellValue(m1.group(1));
                                    row.createCell(2).setCellValue("present");
                                    row.createCell(3).setCellValue("not present");
//                csvData.append(m1.group(1));
//                csvData.append(',');
//                csvData.append("present");
//                csvData.append(',');
//                csvData.append("not present");
//                csvData.append('\n');
                                } else if (m2.find()) {
                                    row.createCell(1).setCellValue(m2.group(1) + "." + m2.group(2));
                                    row.createCell(2).setCellValue("not present");
                                    row.createCell(3).setCellValue("present");
//                csvData.append(m2.group(1));
//                csvData.append('.');
//                csvData.append(m2.group(2));
//                csvData.append(",not present,present \n");
                                }
                                i++;
          /*  if(m.find())
                y = m.group(1)+","+m.group(2)+","+m.group(3);
            else if(m1.find())
                y = m1.group(1)+",present,not present";
            else if(m2.find())
              y =m2.group(1)+"."+ m2.group(2)+",not present ,present";
            System.out.println(y);
            out.println(y.toString());*/
                            }
//        out.write(csvData.toString());
//        out.close();
                    }
                } catch (Exception e) {
e.printStackTrace();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        FileOutputStream path = new FileOutputStream("./src/diff.xlsx");
        workbook.write(path);
        path.close();
        }
    }