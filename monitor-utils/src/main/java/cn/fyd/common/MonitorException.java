package cn.fyd.common;

/**
 * 自定义异常类
 * @author fanyidong
 * @date 2018-12-11
 */
public class MonitorException extends Exception {

    public MonitorException() {
    }

    public MonitorException(String message) {
        super(message);
    }
}
