import java.util.*;

import static java.util.Map.entry;

public class MonthlyReport {
    String year;
    /**
     * HashMaps for separated storing data (read from file) about monthly income/outcome
     */
    HashMap<String, Integer[]> income = new HashMap<>(); //ХТ для записи доходов, ключ - номер месяца

    HashMap<String, Integer[]> outcome = new HashMap<>(); //ХТ для записи расходов, ключ - номер месяца

    Map<Integer, String> monthName = Map.ofEntries(entry(1, "Январь"), entry(2, "Февраль"), entry(3, "Март"), entry(4, "Апрель"), entry(5, "Май"), entry(6, "Июнь"), entry(7, "Июль"), entry(8, "Август"), entry(9, "Сентябрь"), entry(10, "Октябрь"), entry(11, "Ноябрь"), entry(12, "Декабрь"));

    /**
     * method for reading data from all monthly report files
     *
     * @param path - pre-defined directory containing files to read
     * @param year - selected year to read files for
     * @return - returns an arraylist containing information divided into objects for each read file
     */
    public static ArrayList readMonthReport(String path, String year) {
        String path1 = path;
        ArrayList<MonthlyReport> monthReports = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {

            path = path1;
            String month = "";
            if (i >= 1 && i <= 9) {
                month = "0" + String.valueOf(i);
            } else month = String.valueOf(i);

            path = path + year + month + ".csv";

            System.out.println(path);

            List<String> readFromFile = Main.readFileContents(path);

            if (readFromFile.equals(Collections.emptyList())) {
                continue;
            }

            readFromFile.remove(0);
            System.out.println(readFromFile);

            MonthlyReport mReport = new MonthlyReport();

            for (String record : readFromFile) {
                String[] lineContents = record.split(",");
                String key = lineContents[0];

                int expenseQuantity = Integer.parseInt(lineContents[2]);
                int expenseSumOfOne = Integer.parseInt(lineContents[3]);

                Integer[] monthlyValues = new Integer[2];
                monthlyValues[0] = expenseQuantity;
                monthlyValues[1] = expenseSumOfOne;

                if (lineContents[1].equalsIgnoreCase("FALSE")) {
                    mReport.income.put(key, monthlyValues);
                } else if (lineContents[1].equalsIgnoreCase("true")) {
                    mReport.outcome.put(key, monthlyValues);
                } else {
                    System.out.println("Ошибка в исходных данных: невозможно определить, указанная в отчете сумма отнесена к доходам или расходам. Данные за " + i + " месяц не будут записаны. Проверьте правильность заполнения исходных данных в файле " + path);
                }
            }
            monthReports.add(mReport);
        }
        return monthReports;
    }

    /**
     * method for displaying statistics
     * @param monthReports - arraylist containig information
     */
    public static void monthStatistic(ArrayList<MonthlyReport> monthReports) {

        System.out.println();
        System.out.println("Выводим доходы за все месяцы: ");
        int j = 1;
        int Sum;
        String tovar;

        for (MonthlyReport monthReport : monthReports) {
            Sum = 0;
            tovar = "";

            System.out.println("Доходы за " + monthReport.monthName.get(j) + ": ");
            j += 1;
            for (String val : monthReport.income.keySet()) {

                System.out.println(val);

                Integer[] inc = monthReport.income.get(val);
                System.out.println("Реализовано " + inc[0] + " штук по " + inc[1] + " руб./штука. На общую сумму " + inc[0] * inc[1] + " рублей.");

                if ((inc[0] * inc[1]) > Sum) {
                    Sum = inc[0] * inc[1];
                    tovar = val;
                }
            }

            System.out.println();
            System.out.println("Самый прибыльный товар в этом месяце - это " + tovar + " всего реализовано на сумму " + Sum);
            System.out.println();
        }

        System.out.println();
        System.out.println("Выводим расходы за все месяцы: ");
        int f = 1;
        for (MonthlyReport monthReport : monthReports) {
            Sum = 0;
            tovar = "";

            System.out.println("Расходы за " + monthReport.monthName.get(f) + ": ");
            f += 1;
            for (String val : monthReport.outcome.keySet()) {
                System.out.println(val);

                Integer[] inc = monthReport.outcome.get(val);
                System.out.println("Приобретено " + inc[0] + " штук по " + inc[1] + " руб./штука. На общую сумму " + inc[0] * inc[1] + " рублей.");


                if ((inc[0] * inc[1]) > Sum) {
                    Sum = inc[0] * inc[1];
                    tovar = val;
                }
            }

            System.out.println();
            System.out.println("Наименование самой большой траты в этом месяце - " + tovar + ", всего потрачено " + Sum);
            System.out.println();

        }
    }

}
