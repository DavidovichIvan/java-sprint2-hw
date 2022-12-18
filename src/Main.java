import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        YearlyReport yReport = new YearlyReport();

        ArrayList<MonthlyReport> monthReports = null;

        Scanner scanner = new Scanner(System.in);

        /**
         * pre-defined route for annual report file
         */
        String path = "C:\\Users\\Вуня\\Desktop\\dev\\second-sprint\\java-sprint2-hw\\resources\\y.2021.csv";

        /**
         * pre-defined route for monthly report files
         */
        String pathM = "C:\\Users\\Вуня\\Desktop\\dev\\second-sprint\\java-sprint2-hw\\resources\\m.";

        System.out.println("Добро пожаловать в приложение -=*Черная бухгалтерия*=-. Выберите действие из списка:");

        while (true) {
            showMenu();
            String menuItem;
            menuItem = scanner.nextLine();

            if (menuItem.equals("quit")) {
                System.out.println("Работа программы завершена. Спасибо.");
                return;
            }
            monthReports = menuProcess(menuItem, yReport, path, pathM, monthReports);
        }
    }

    /**
     * method for reading from file from pre-defined route
     *
     * @param path - route to file, or directory
     * @return file contents if reading was successful or mistake message if not
     */
    public static List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }
    /**
     * Method for displaying start menu
     */
    public static void showMenu() {
        System.out.println();
        System.out.println("      Выберите команду из списка:   ");
        System.out.println("1 - Считать все месячные отчёты.");
        System.out.println("2 - Считать годовой отчет.");
        System.out.println("3 - Сверить отчеты.");
        System.out.println("4 - Вывести информацию о всех месячных отчётах.");
        System.out.println("5 - Вывести информацию о годовом отчёте.");
        System.out.println();
        System.out.println("quit - Выйти из приложения.");
    }

    /**
     * Method for processing start menu items
     *
     * @param menuItem - menu item number or "quit" for exit;
     */
    public static ArrayList<MonthlyReport> menuProcess(String menuItem, YearlyReport yReport, String path, String pathM, ArrayList<MonthlyReport> monthReports) {

        if (menuItem.equals("1")) {
            System.out.println("Считать все месячные отчёты.");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите год, за который необходимо считать отчет в формате 'ГГГГ'. В настоящее время доступны отчеты только за 2021 год.");
            String year = scanner.nextLine();

            monthReports = MonthlyReport.readMonthReport(pathM, year);


        } else if (menuItem.equals("2")) {
            System.out.println("Считываем годовой отчет.");
            YearlyReport.readReport(path, yReport);

            System.out.println("Доходы по месяцам сохранены: " + yReport.income);
            System.out.println("Расходы по месяцам сохранены: " + yReport.outcome);

        } else if (menuItem.equals("3")) {

            if (monthReports == null) {
                System.out.println("Cначала нужно считать месячные отчеты");
            } else if ((yReport.income.isEmpty() || yReport.outcome.isEmpty())) {
                System.out.println("Cначала нужно считать годовые отчеты");
            } else {
                System.out.println("Осуществляем сверку отчетных данных.");
                DataValidation.dataValidation(monthReports, yReport);
            }

        } else if (menuItem.equals("4")) {

            if (monthReports == null) {
                System.out.println("Cначала нужно считать месячные отчеты");
            } else {
                System.out.println("Вывести информацию о всех месячных отчётах.");
                MonthlyReport.monthStatistic(monthReports);
            }

        } else if (menuItem.equals("5")) {

            if (!yReport.income.isEmpty() || !yReport.outcome.isEmpty()) {
                System.out.println("Информация о годовом отчёте:");
                yReport.year(path);
                yReport.totalByMonth(yReport.income, yReport.outcome);
                yReport.averageInOut(yReport.income, yReport.outcome);

            } else
                System.out.println("Отчетные данные не заполнены. Сначала выполните команду 'Считать годовой отчет'.");

        } else {
            System.out.println("Такой команды нет. Выберите номер команду от 1 до 5 или quit - для завершения работы.");
        }
        return monthReports;
    }

}

//Спасибо, Евгению (если Вы по-прежнему со мной) за работу.
// Сложновато было для 3х недельного обучения, вероятно программистам время от времени нужна помощь психологов, а может и более серьезных специалистов )