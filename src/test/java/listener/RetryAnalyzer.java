package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private static final int maxTry = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (count < maxTry) {
                count++;
                // Log retry attempt
                System.out.println("Retrying test " + result.getName() + " with status "
                        + getResultStatusName(result.getStatus()) + " for the " + count + " time(s)");
                return true;
            }
        }
        return false;
    }

    private String getResultStatusName(int status) {
        switch (status) {
            case 1:
                return "SUCCESS";
            case 2:
                return "FAILURE";
            case 3:
                return "SKIP";
            default:
                return "UNKNOWN";
        }
    }
}