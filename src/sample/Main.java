package sample;

import com.sun.org.apache.xml.internal.utils.StringToStringTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    private static String[] linesAsArray;
    private static Stage stage;
    private static File curFile;
    private static String param, savedExpression, expression;

    static MathParser parser = new MathParser();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("json Helper");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void loadFile(File file) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) lines.add(line);
        linesAsArray = lines.toArray(new String[lines.size()]);
    }

    //Checking if the current line if the last one for object (you should know json structure to understand it :D )
    private static boolean checkIfParamIsLast(String string) {
        Pattern p = Pattern.compile("^ +},");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    //Checking if the current line consists of necessary parameter e.g. StackSize or RandomVariance etc.
    private static boolean checkParam(String param, String string) {
        Pattern p = Pattern.compile("^ +\"" + param + "\": \\d{1,5},?");
        Matcher m = p.matcher(string);

        return m.matches();
    }

    private static void writeToFile(File file, String[] data) throws IOException {

        FileWriter writer = new FileWriter(file, false);
        for (int i = 0; i < data.length; i++) writer.write(data[i] + "\n");
        writer.flush();
    }

    public static void setFile(File file) throws IOException {
        curFile = file;
        loadFile(file);
    }

    public static void setParam(String string) {
        param = string;
    }

    public static void setExpression(String string) {
        savedExpression = string;
    }

    public static void parseFuckingJson() throws Exception {
        for (int i = 0; i < linesAsArray.length; i++) {
            if (checkParam(param, linesAsArray[i])) {
                if (!checkIfParamIsLast(linesAsArray[i + 1])) {
                    int currentParam = Integer.parseInt(linesAsArray[i].split("^ +\"" + param + "\": ")[1].split(",")[0]);
                    expression = String.valueOf(currentParam) + expression;
                    linesAsArray[i] = linesAsArray[i].split(":")[0] + ": " + String.valueOf(parser.Parse(expression)).split("\\.")[0] + ",";
                } else {
                    int currentParam = Integer.parseInt(linesAsArray[i].split("^ +\"" + param + "\": ")[1]);
                    expression = String.valueOf(currentParam) + expression;
                    linesAsArray[i] = linesAsArray[i].split(":")[0] + ": " + String.valueOf(parser.Parse(expression)).split("\\.")[0];
                }
            }
            expression = savedExpression;
        }
        writeToFile(curFile, linesAsArray);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
