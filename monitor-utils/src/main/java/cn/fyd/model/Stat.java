package cn.fyd.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.fyd.common.Constant.*;

/**
 * 监控结果统计信息实体类
 * @author fanyidong
 * @date Created in 2019-02-22
 */
public class Stat {

    /**
     * 监控任务出现异常的时段
     */
    private List<Integer> errorsInHours;

    /**
     * 监控任务名
     */
    private List<String> monitorNames;

    /**
     * 监控任务的可用率(保留两位小数)
     */
    private List<Double> availableRates;

    /**
     * 监控任务的平均响应时间(保留两位小数)
     */
    private List<Double> responseTimes;

    /**
     * 监控项目的异常情况
     */
    private List<Integer> errors;

    /**
     * 构造器
     */
    public Stat() {
        this.errorsInHours = new ArrayList<Integer>(Collections.nCopies(TWENTY_FOUR, ZERO));
        this.errors = new ArrayList<Integer>(ONE);
        this.availableRates =  new ArrayList<Double>(ONE);
        this.responseTimes =  new ArrayList<Double>(ONE);
    }

    public List<Integer> getErrorsInHours() {
        if (errorsInHours == null) {
            return new ArrayList<Integer>(Collections.nCopies(TWENTY_FOUR, ZERO));
        }
        return errorsInHours;
    }

    public void setErrorsInHours(List<Integer> errorsInHours) {
        this.errorsInHours = errorsInHours;
    }

    public List<String> getMonitorNames() {
        if (monitorNames == null) {
            return new ArrayList<String>();
        }
        return monitorNames;
    }

    public void setMonitorNames(List<String> monitorNames) {
        this.monitorNames = monitorNames;
    }

    public List<Double> getAvailableRates() {
        if (availableRates == null) {
            return new ArrayList<Double>(ONE);
        }
        return availableRates;
    }

    public void setAvailableRates(List<Double> availableRates) {
        this.availableRates = availableRates;
    }

    public List<Double> getResponseTimes() {
        if (responseTimes == null) {
            return new ArrayList<Double>(ONE);
        }
        return responseTimes;
    }

    public void setResponseTimes(List<Double> responseTimes) {
        this.responseTimes = responseTimes;
    }

    public List<Integer> getErrors() {
        if (errors == null) {
            return new ArrayList<Integer>(ONE);
        }
        return errors;
    }

    public void setErrors(List<Integer> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "errorsInHours=" + errorsInHours +
                ", monitorNames=" + monitorNames +
                ", availableRates=" + availableRates +
                ", responseTimes=" + responseTimes +
                ", errors=" + errors +
                '}';
    }
}
