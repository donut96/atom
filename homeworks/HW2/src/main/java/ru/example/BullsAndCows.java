package ru.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BullsAndCows {

    private static final Logger LOGGER = Logger.getLogger(BullsAndCows.class.getName());
    private static String time_for_log = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

    public static void main(String[] args) {
        try {
            boolean flag;
            FileHandler fileHandler = new FileHandler("log_" + time_for_log + ".log");
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            System.out.println("Игра 'Быки и коровы!'");
            LOGGER.info("Логгер пошёл!");

            do flag = playGame();
            while (flag);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    static boolean playGame() {
        Scanner in = new Scanner(System.in);
        int[] bullsAndCows;
        String userSting;
        int countOfAttempt = 10;
        String word = getWordFromFile("dictionary.txt");

        LOGGER.info("Есть слово из " + word.length() + " букв, ты сможешь угадать слово ?");
        System.out.println("Есть слово из " + word.length() + " букв, ты сможешь угадать слово ?");

        for (int i = 0; i < countOfAttempt; i++) {
            userSting = in.nextLine();

            if (userSting.length() != word.length()) {
                LOGGER.info("Не угадал с длинной, давай сначала...");
                System.out.println("Не угадал с длинной, давай сначала...");
                return true;
            }

            bullsAndCows = checkBullsAndCows(userSting, word);

            if (bullsAndCows[0] == word.length()) {
                LOGGER.info("А ты смышлённый, поздравляю!");
                System.out.println("А ты смышлённый, поздравляю!");
                i = countOfAttempt - 1;
            } else {
                LOGGER.info("Быки: " + bullsAndCows[0] + ";  Коровы: " + bullsAndCows[1]);
                System.out.println("Быки: " + bullsAndCows[0] + ";  Коровы: " + bullsAndCows[1]);
                if (i == countOfAttempt - 1) {
                    LOGGER.info("Ты проиграл! Было загадано: " + word);
                    System.out.println("Ты проиграл! Было загадано: " + word);
                }
            }

            if (i == countOfAttempt - 1) {
                LOGGER.info("Хочешь ещё? да/нет");
                System.out.println("Хочешь ещё? да/нет");
                if (in.nextLine().equals("да")) {
                    return true;
                }
            }
        }
        return false;
    }

    static String getWordFromFile(String fileName) {
        Random random = new Random(System.currentTimeMillis());
        ArrayList<String> list = new ArrayList<>();
        String str;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((str = reader.readLine()) != null) {
                if (!str.isEmpty()) list.add(str);
            }
        } catch (IOException ex) {
            LOGGER.info("Косяк с файлом");
            System.out.println("Косяк с файлом");
        }

        str = list.get(random.nextInt(list.size()));

        LOGGER.info(str);
        return str;
    }

    static int[] checkBullsAndCows(String userString, String word) {
        int[] bullsAndCows = new int[2];
        for (int i = 0; i < word.length(); i++) {
            if (userString.charAt(i) == word.charAt(i)) {
                bullsAndCows[0]++;
            } else {
                if (word.contains(Character.toString(userString.charAt(i)))) bullsAndCows[1]++;
            }
        }
        return bullsAndCows;
    }
}