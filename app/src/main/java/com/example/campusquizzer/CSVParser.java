package com.example.campusquizzer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSVParser {

    public static List<Question> parseCSV(InputStream inputStream) {
        List<Question> questionList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 6) {

                    // Shuffle options (tokens[1] to tokens[4])
                    List<String> options = new ArrayList<>();
                    options.add(tokens[1]);
                    options.add(tokens[2]);
                    options.add(tokens[3]);
                    options.add(tokens[4]);
                    Collections.shuffle(options);

                    Question question = new Question(tokens[0], options.get(0), options.get(1), options.get(2), options.get(3), tokens[5]);
                    questionList.add(question);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return questionList;
    }
}
