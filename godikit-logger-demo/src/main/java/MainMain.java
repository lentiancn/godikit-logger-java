import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "MMM")
public class MainMain {

    public static void main(String[] args) {
        Object log1 = log.getName();
        System.out.println(log1);
        Logger logger = LoggerFactory.getLogger("Test");

        System.out.println(logger.getProvider());
        System.out.println(logger.getOriginalLogger() + " " + logger.getOriginalLogger().getClass());
//
//        logger.trace("Hello world!");
//        logger.debug("Hello world!");
//        logger.info("Hello world!");
//        logger.warn("Hello world!");
//        logger.error("Hello world!");
    }
}
