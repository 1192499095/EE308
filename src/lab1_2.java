import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class lab1_2 {
    public static void main(String[] args) {
        lab1_2 lab1_2 = new lab1_2();
        try {
            lab1_2.solution("C:\\Users\\ZYC\\Desktop\\source.c", 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solution(String url, int level) throws IOException {
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
            level3(s);
        } else if (level == 4) {
            level4(s);
        }
    }

    public void level1(String s) {
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

    public void level2(String s) {
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
        for (int i = 0; i < num; i++) {
            Matcher matcher2 = pattern2.matcher(temp[i + 1]);
            while (matcher2.find()) {
                count++;
            }
            caseNum[i] = count;
            count = 0;
        }
        System.out.println("case num: " + caseNum[0] + " " + caseNum[1]);
    }

    public void level3(String s) {
        level2(s);
        System.out.println("if-else num: " + getNum(s, 3));
    }

    public void level4(String s) {
        level3(s);
        System.out.println("if-elseif-else num: " + getNum(s, 4));
    }

    public int getNum(String s, int level) {
        int ifelNum = 0;
        int esifNum = 0;
        boolean lock = true;
        Stack stack = new Stack();
        Pattern pattern = Pattern.compile("else if|if|else");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            String temp = matcher.group();
            if ("if".equals(temp)) {
                stack.push(temp);
            } else if ("else if".equals(temp)) {
                stack.push(temp);
            } else if ("else".equals(temp)) {
                if ("if".equals(stack.peek())) {
                    stack.pop();
                    ifelNum++;
                } else {
                    while (!"if".equals(stack.peek())) {
                        stack.pop();
                        if (lock == true) {
                            esifNum++;
                            lock = false;
                        }
                    }
                    stack.pop();
                    lock = true;
                }
            }
        }
        if (level == 3) {
            return ifelNum;
        } else if (level == 4) {
            return esifNum;
        } else {
            return 0;
        }
    }
}

class TestCode {
    lab1_2 lab1_2 = new lab1_2();

    @ParameterizedTest
    @MethodSource("parameterDataProvider")
    void test(String url, int l, String result) throws IOException {
        lab1_2.solution(url, l);
        assertEquals(result, lab1_2.toString());
    }

    public static Stream<Arguments> parameterDataProvider() {
        return Stream.of(
                Arguments.of("C:\\Users\\ZYC\\Desktop\\source.c", 1, "total num: 35"),
                Arguments.of("C:\\Users\\ZYC\\Desktop\\source.c", 2, "switch num: 2\ncase num: 3 2"),
                Arguments.of("C:\\Users\\ZYC\\Desktop\\source.c", 3, "if-else num: 2"),
                Arguments.of("C:\\Users\\ZYC\\Desktop\\source.c", 4, "if-elseif-else num: 2")
        );
    }

}

