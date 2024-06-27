package utilities.appium_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.cmd.CommandWindows;
import utilities.get_port.FreePort;

public class AppiumServer {
    static Process process;
    private static final Logger logger = LogManager.getLogger();
    public static void startServer() {
        if (process == null) {
            // Get appium server port
            int appiumServerPort = FreePort.get();

            // Start appium server
            process = CommandWindows.execute("appium -a 0.0.0.0 -p %s -pa /wd/hub --allow-cors".formatted(appiumServerPort));

            // Log
            logger.info("Start appium server with port: {}", appiumServerPort);
        }
    }

    public static void stopServer() {
        if (process != null) {
            // Stop appium server
            process.destroy();

            // Log
            logger.info("Stop appium server.");
        }
    }
}
