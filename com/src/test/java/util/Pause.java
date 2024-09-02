package util;

import java.util.logging.Logger;

public class Pause {
    final static Logger logger = Logger.getLogger("Pause");
    
    /**
     * 休眠X秒
     *
     * @param sleeptime
     */
    public Pause(long sleeptime) {
        logger.info(String.format("%s", sleeptime));
        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public Pause() {
        
    }
    
    /**
     * @param sleeptime
     *                  seconds
     */
    public void seconds(long sleeptime) {
        logger.info(String.format("to: %s(s)", sleeptime));
        try {
            Thread.sleep(sleeptime * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * @param sleeptime
     *                  seconds
     * @param coms
     */
    public void seconds(long sleeptime, String coms) {
        logger.info(String.format("to %s(s) for: %s", sleeptime, coms));
        try {
            Thread.sleep(sleeptime * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
