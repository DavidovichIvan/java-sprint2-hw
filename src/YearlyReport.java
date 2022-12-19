import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YearlyReport {

    ArrayList<String> readFromFile;

    /**
     * HashMaps for separated storing data (read from file) about yearly income/outcome
     */
    HashMap<Integer, Integer> income = new HashMap<>();
    HashMap<Integer, Integer> outcome = new HashMap<>();

    /**
     * method for reading year from file name
     */
    public static void year(String path) {
        String year = path.substring(path.lastIndexOf('\\') + 3, path.lastIndexOf('.'));
        System.out.println("Рассматриваемый год - " + year + " год.");

    }

    /**
     * method for reading data from year report file
     */
    public static void readReport(String path, YearlyReport yReport) {

        List<String> readFromFile = Main.readFileContents(path);

        readFromFile.remove(0);
        yReport.readFromFile = (ArrayList<String>) readFromFile;

        for (String record : yReport.readFromFile) {

            String[] lineContents = record.split(",");

            int key = Integer.parseInt(lineContents[0]);
            int expenseAmount = Integer.parseInt(lineContents[1]);

            if (lineContents[2].equalsIgnoreCase("false")) {
                yReport.income.put(key, expenseAmount);
            } else if (lineContents[2].equalsIgnoreCase("true")) {
                yReport.outcome.put(key, expenseAmount);
            } else {
                System.out.println("Невозможно определить сумма за " + lineContents[2] + " месяц относится к доходам или расходам. " +
                                   "Проверьте исходные данные отчета и перезапустите программу.");
                return;
            }
        }
    }


    /**
     * method for calculating monthly net income
     */
    public static void totalByMonth(HashMap<Integer, Integer> income, HashMap<Integer, Integer> outcome) {
        for (Integer key : income.keySet()) {
            System.out.println("Чистая прибыль за " + key + " месяц составила: " + (income.get(key) - outcome.get(key)) + " рублей.");
        }
    }

    /**
     * method for calculating monthly average income
     */
    public static void averageInOut(HashMap<Integer, Integer> income, HashMap<Integer, Integer> outcome) {
        int sum = 0;
        int counter = 0;
        int avrg;

        for (Integer key : income.keySet()) {
            sum += income.get(key);
            counter++;
        }
        avrg = sum / counter;

        System.out.println("Средний доход за " + counter + " мес." + " составил " + avrg + " рублей в месяц.");

        sum = 0;
        counter = 0;

        for (Integer key : outcome.keySet()) {
            sum += outcome.get(key);
            counter++;
        }
        avrg = sum / counter;

        System.out.println("Средний расход за " + counter + " мес." + " составил " + avrg + " рублей в месяц.");
    }
}