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
            // ���̃N���X�Ɠ����p�b�P�[�W�ł͖����ꍇ�� /myapp/logging.properties �Ȃǐ�΃p�X�w��
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}