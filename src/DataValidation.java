import java.util.ArrayList;
import java.util.HashMap;

public class DataValidation {

    /**
     * method for data consistency checking
     *
     * @param monthReports - arraylist containing objects with an information from monthly reports
     * @param yReport      - object containing data from yearly report
     */
    public static ArrayList<String> consistencyCheck(ArrayList<MonthlyReport> monthReports, YearlyReport yReport) {
        int j = 1;
        int sumOfMonthIncome;
        int sumOfMonthOutcome;
        int monthTotal;
        int yearTotal;
        boolean err = false;
        ArrayList<String> consistencyCheckResult = new ArrayList<>();

        for (MonthlyReport monthReport : monthReports) {
            sumOfMonthIncome = 0;
            sumOfMonthOutcome = 0;
            monthTotal = 0;
            yearTotal = 0;

            for (String val : monthReport.income.keySet()) {
                Integer[] inc = monthReport.income.get(val);
                sumOfMonthIncome += (inc[0] * inc[1]);
            }

            for (String val : monthReport.outcome.keySet()) {
                Integer[] inc = monthReport.outcome.get(val);
                sumOfMonthOutcome += (inc[0] * inc[1]);
            }

            monthTotal = sumOfMonthIncome - sumOfMonthOutcome;
            System.out.println("Сальдо за " + monthReport.monthName.get(j) + " по данным месячного отчета: " + monthTotal);

             yearTotal = yReport.income.get(j) - yReport.outcome.get(j);

          // yearTotal = yReport.income.get(j) - yReport.outcome.get(j) + 1; //строка для тестирования срабатывания ошибки, чтобы в исходники не лазить

            System.out.println("Сальдо за " + monthReport.monthName.get(j) + " по данным годового отчета: " + yearTotal);

            if (yearTotal != monthTotal) {
                err = true;
                consistencyCheckResult.add("Данные за " + monthReport.monthName.get(j) + " не совпадают с годовым отчетом");

            }
            j++;
        }
        if (err == false) {

            consistencyCheckResult.add("Сверка отчетных данных пройдена успешно.");

        }
        return consistencyCheckResult;
    }
}






