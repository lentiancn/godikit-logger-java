import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;
import org.slf4j.MDC;

public class MainMain {

    public static void main(String[] args) {
        MDC.put("traceId", "fucker");

        Logger logger = LoggerFactory.getLogger("Test");

        System.out.println(logger.getName());
        System.out.println(logger.getProvider());
        System.out.println(logger.getOriginalLogger() + " " + logger.getOriginalLogger().getClass());

        logger.trace("Hello world!");
        logger.debug("Hello world!");
        logger.info("Hello world!");
        logger.warn("Hello world!");
        logger.error("Hello world!");
    }
}
