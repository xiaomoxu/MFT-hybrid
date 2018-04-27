import com.antler.mft.MainApplication;
import com.antler.mft.logging.Log;
import com.antler.mft.logging.LogFactory;

public class TestDependency {


    public static void main(String[] args) {
        LogFactory.useStdOutLogging();
        Log log = LogFactory.getLog(MainApplication.class);
        log.debug("Thank you! \n");
    }
}
