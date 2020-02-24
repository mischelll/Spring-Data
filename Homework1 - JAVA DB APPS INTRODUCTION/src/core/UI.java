package core;

import java.io.BufferedReader;
import java.io.IOException;

public class UI {
    public static void goON(BufferedReader bufferedReader, Engine engine) throws IOException {
        String command = "";
        while (true){
            System.out.println("Try another task? [YES]/[NO]");
            command = bufferedReader.readLine();
            if (command.equalsIgnoreCase("YES")){
                MessagesAndConfiguration.showIntroMessage();
                engine.run();
                System.out.println("------------------");
            }else if (command.equalsIgnoreCase("NO")){
                System.out.println("See ya!");
                break;
            }else {
                System.out.println("Sorry, I didn't catch that...Please make sure you understood the question properly :)");
                continue;
            }
        }
    }
}
