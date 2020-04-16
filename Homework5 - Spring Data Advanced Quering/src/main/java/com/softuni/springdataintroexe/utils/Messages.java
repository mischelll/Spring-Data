package com.softuni.springdataintroexe.utils;

public class Messages {
    public static String showIntroMessage(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1. Books Titles by Age Restriction").append(System.getProperty("line.separator"));
        stringBuilder.append("2. Golden Books").append(System.getProperty("line.separator"));
        stringBuilder.append("3. Books by Price").append(System.getProperty("line.separator"));
        stringBuilder.append("4. Not Released Books").append(System.getProperty("line.separator"));
        stringBuilder.append("5. Books Released Before Date").append(System.getProperty("line.separator"));
        stringBuilder.append("6. Authors Search").append(System.getProperty("line.separator"));
        stringBuilder.append("7. Books Search").append(System.getProperty("line.separator"));
        stringBuilder.append("8. Book Titles Search").append(System.getProperty("line.separator"));
        stringBuilder.append("9. Count Books").append(System.getProperty("line.separator"));
        stringBuilder.append("10. Total Book Copies").append(System.getProperty("line.separator"));
        stringBuilder.append("11. Reduced Book").append(System.getProperty("line.separator"));
        stringBuilder.append("12. * Increase Book Copies").append(System.getProperty("line.separator"));
        stringBuilder.append("13. * Remove Books").append(System.getProperty("line.separator"));
        stringBuilder.append("14. * Stored Procedure").append(System.getProperty("line.separator"));

        return stringBuilder.toString();
    }
}
