package com.maistruk.javaQuizMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WebMapper {
    
    public static void main(String[] args) {
        mapping();
    }

    public static void mapping() {
        List<String> questionsAnswers = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(
                "D:/Programming/Java/JAVA QUIZ PROJECT(FOR WORK)/java question/Spring Hibernate Java Quiz/SQL/WEB/Questions.txt"))) {
            int letter;
            while ((letter = reader.read()) != -1) {
                questionsAnswers.add(new String((char) letter + reader.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int questionId = 0;
        int answerId = 0;
        int questionNum = 1;
        int answerNum = 1;
        try (Writer writer = Files.newBufferedWriter(Paths.get(
                "D:/Programming/Java/JAVA QUIZ PROJECT(FOR WORK)/java question/Spring Hibernate Java Quiz/SQL/WEB/QuestionsSQL.txt"))) {
            for (String str : questionsAnswers) {
                str = replaceApostrophe(str);
                if (questionNum < 2) {
                    questionNum++;
                    questionId++;
                    writeQuestion(writer, str, questionId);
                } else if (answerNum < 5) {
                    answerNum++;
                    answerId++;
                    writeAnswer(writer, str, answerId);
                }
                if (questionNum == 2 && answerNum == 5) {
                    questionNum = 1;
                    answerNum = 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String replaceApostrophe(String str) {
        if (str.contains("'")) {
            str = str.replaceAll("'", "''");
        }
        if (str.contains("’")) {
            str = str.replaceAll("’", "''");
        }
        return str;
    }

    public static void writeQuestion(Writer writer, String str, int questionId) throws IOException {
        str = str.replaceAll(" +", " ");
        writer.write("INSERT INTO web_question (id, question) VALUES (" + questionId + ", '" + str + "');\n");
    }

    public static void writeAnswer(Writer writer, String str, int answerId) throws IOException {
        if (str.contains("(True)")) {
            int indexOfTrue = str.indexOf("(True)");
            str = str.substring(0, indexOfTrue);
            writer.write("INSERT INTO web_answer (id, answer, flag) VALUES (" + answerId + ", '" + str + "', true);\n");
        } else if (str.contains("(Truth)")) {
            int indexOfTrue = str.indexOf("(Truth)");
            str = str.substring(0, indexOfTrue);
            writer.write("INSERT INTO web_answer (id, answer, flag) VALUES (" + answerId + ", '" + str + "', true);\n");
        } else if (str.contains("( True)")) {
            int indexOfTrue = str.indexOf("( True)");
            str = str.substring(0, indexOfTrue);
            writer.write("INSERT INTO web_answer (id, answer, flag) VALUES (" + answerId + ", '" + str + "', true);\n");
        } else if (str.contains("( True )")) {
            int indexOfTrue = str.indexOf("( True )");
            str = str.substring(0, indexOfTrue);
            writer.write("INSERT INTO web_answer (id, answer, flag) VALUES (" + answerId + ", '" + str + "', true);\n");
        } else {
            writer.write(
                    "INSERT INTO web_answer (id, answer, flag) VALUES (" + answerId + ", '" + str + "', false);\n");
        }
    }
}
