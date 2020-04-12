package common;

public class Messages {
    public static void showIntroMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("1.Remove Objects%n" +
                "2.Contains Employee%n" +
                "3.Employees with Salary Over 50 000%n" +
                "4.Employees from Department%n" +
                "5.Adding a New Address and Updating Employee%n" +
                "6.Addresses with Employee Count%n" +
                "7.Get Employee With Project%n" +
                "8.Find Latest 10 Projects%n" +
                "9.Increase Salaries%n" +
                "10.Remove Towns%n" +
                "11.Find Employees by First Name%n" +
                "12.Employees Maximum Salaries"));

        System.out.println("Choose which task to be executed:");
        System.out.printf("%s", stringBuilder.toString());
        System.out.println();
        System.out.println("Copy and paste ONLY the task name:");

    }
}
