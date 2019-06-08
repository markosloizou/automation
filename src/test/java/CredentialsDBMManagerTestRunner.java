
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;



public class CredentialsDBMManagerTestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(CredentialsDBManagerTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
