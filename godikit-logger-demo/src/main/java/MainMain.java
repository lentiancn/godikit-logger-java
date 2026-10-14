import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;

public class MainMain {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("Hello");

        System.out.println(logger.getProvider());
        System.out.println(logger.getOriginalLogger() + " " + logger.getOriginalLogger().getClass());

        logger.trace("Hello world!");
        logger.debug("Hello world!");
        logger.info("Hello world!");
        logger.warn("Hello world!");
        logger.error("Hello world!");
    }
}
