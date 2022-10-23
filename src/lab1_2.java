import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lab1_2 {
    public static void main(String[] args) {
        try {
            solution("C:\\Users\\ZYC\\Desktop\\source.c", 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void solution(String url, int level) throws IOException {
        File file = new File(url);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder s2 = new StringBuilder();
        String s1 = null;
        while ((s1 = reader.readLine()) != null) {
            s2.append(s1);
        }
        String s = s2.toString();
        if (level == 1) {
            level1(s);
        } else if (level == 2) {
            level2(s);
        } else if (level == 3) {
            //level3(s);
        } else if (level == 4) {
            //level4(s);
        }
    }

    public static void level1(String s) {
        int num = 0;
        String keyword[] = {"auto", "break", "case", "char", "const", "continue", "default", "do", "double", "else", "enum", "extern",
                "float", "for", "goto", "if", "int", "long", "register", "return", "short", "signed", "sizeof", "static",
                "struct", "switch", "typedef", "unsigned", "union", "void", "volatile", "while"};
        for (int i = 0; i < keyword.length; i++) {
            String temp = "[^a-zA-Z_]" + keyword[i] + "[^a-zA-Z_]";
            Pattern pattern = Pattern.compile(temp);
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                num++;
            }
        }
        System.out.println("total num: " + num);
    }

    public static void level2(String s) {
        level1(s);
        int num = 0;
        Pattern pattern = Pattern.compile("switch");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            num++;
        }
        System.out.println("switch num: " + num);
        String temp[] = s.split("switch");
        int caseNum[] = new int[num];
        int count = 0;
        Pattern pattern2 = Pattern.compile("case");
        for(int i=0;i<num;i++){
            Matcher matcher2 = pattern2.matcher(temp[i+1]);
            while (matcher2.find()) {
                count++;
            }
            caseNum[i] = count;
            count = 0;
        }
        System.out.print("case num: "+caseNum[0]+" "+caseNum[1]);


    }

    public static void level3(String s) {
        level2(s);
        int num = 0;
        Pattern pattern = Pattern.compile("switch");
        Matcher matcher = pattern.matcher(s);

    }
}
