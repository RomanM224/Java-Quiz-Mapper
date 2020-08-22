package com.maistruk.javaQuizMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SpringMapper {

    public static void main(String[] args) {
        mapping();
    }

    public static void mapping() { // BufferedReader 10 times faster than simply Reader
        List<String> questionsAnswers = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(
                "D:/Programming/Java/JAVA QUIZ PROJECT(FOR WORK)/java question/Spring Hibernate Java Quiz/SQL/Spring/Questions.txt"))) {
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
        try (Writer writer = Files.newBufferedWriter(Paths.get("D:/Programming/Java/JAVA QUIZ PROJECT(FOR WORK)/java question/Spring Hibernate Java Quiz/SQL/Spring/QuestionsSQL.txt"))) {
            for(String str : questionsAnswers) {
                if(questionNum < 2) {
                    questionNum++;
                    questionId++;
                    str = str.replaceAll(" +", " ");
                    writer.write("INSERT INTO spring_question (id, question) VALUES (" + questionId + ", '" + str + "');\n");
                } else if (answerNum < 5) {
                    str = str.replace("'", "''");
                    answerNum++;
                    answerId++;
                    if(str.contains("(True)")) {
                        int indexOfTrue = str.indexOf("(True)");
                        str = str.substring(0, indexOfTrue);
                        writer.write("INSERT INTO spring_answer (id, answer, flag) VALUES (" + answerId + ", '" + str + "', true);\n");
                    }else {
                        writer.write("INSERT INTO spring_answer (id, answer, flag) VALUES (" + answerId + ", '" + str + "', false);\n");
                    }
                } 
                if(questionNum ==2 && answerNum == 5){
                    questionNum = 1;
                    answerNum = 1;
                }

             //   writer.write(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
