/**
 * LoggerConfig.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/08/04
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package net.ysuga.statemachine;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * @author ysuga
 *
 */
public class LoggerConfig {
	public LoggerConfig() {
        try {
            LogManager.getLogManager().readConfiguration(
                getClass().getResourceAsStream("logging.properties"));
            // このクラスと同じパッケージでは無い場合は /myapp/logging.properties など絶対パス指定
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}