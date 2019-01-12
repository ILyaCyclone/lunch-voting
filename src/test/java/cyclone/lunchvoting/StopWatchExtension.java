package cyclone.lunchvoting;

import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class StopWatchExtension implements
        BeforeTestExecutionCallback, AfterTestExecutionCallback, BeforeAllCallback, AfterAllCallback {
    private static final Logger logger = LoggerFactory.getLogger(StopWatchExtension.class);

    private StopWatch stopWatch;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        stopWatch = new StopWatch("Execution time of " + extensionContext.getRequiredTestClass().getSimpleName());
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        logger.info("Start stopWatch");
        stopWatch.start(extensionContext.getDisplayName());
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        stopWatch.stop();
        logger.info("stop stopWatch");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        logger.info('\n' + stopWatch.prettyPrint() + '\n');
    }
}
