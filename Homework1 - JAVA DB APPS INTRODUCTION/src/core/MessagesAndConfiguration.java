package core;

public class MessagesAndConfiguration {


    public static void showIntroMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("1.Get Villain's Name%n2.Get Minions Name%n3.Add Minion%n" +
                "4.Change Town Names Casing%n5.Remove Villain%n6.Print All Minion Names%n7.Increase Minions Age%n" +
                "8.Increase Age Stored Procedure"));

        System.out.println("Choose which task to be executed:");
        System.out.printf("%s",stringBuilder.toString());
        System.out.println();
        System.out.println("Copy and paste ONLY the task name:");

    }


    //CONFIGURATION
    private static final String DATABASE_NAME = "minions_db";
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/" +
            DATABASE_NAME +
            "?serverTimezone=UTC"; //!!! - you may need to delete this line. It just doesn't work without it for me.
}
