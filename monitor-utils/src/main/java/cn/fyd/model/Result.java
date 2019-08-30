package cn.fyd.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 监控结果表实体类
 * @author fanyidong
 * @date Created in 2019-01-09
 */
public class Result {

    /**
     * 结果表主键uuid
     */
    private String resultId;

    /**
     *  监控id
     */
    private String monitorId;

    /**
     *  请求状态码
     */
    private Integer status;

    /**
     *  请求开始时间(表中不存在此字段)
     */
    private Date startTime;

    /**
     *  请求结束时间(表中不存在此字段)
     */
    private Date endTime;

    /**
     *  响应时间
     */
    private long responseTime;

    /**
     *  预留字段
     */
    private String others;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 监控结果是否匹配（0不可用 1可用）
     */
    private Integer usable;

    /**
     * 不可用原因
     */
    private String disabledReason;

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUsable() {
        return usable;
    }

    public void setUsable(Integer usable) {
        this.usable = usable;
    }

    public String getDisabledReason() {
        return disabledReason;
    }

    public void setDisabledReason(String disabledReason) {
        this.disabledReason = disabledReason;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultId='" + resultId + '\'' +
                ", monitorId='" + monitorId + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", responseTime=" + responseTime +
                ", others='" + others + '\'' +
                ", createTime=" + createTime +
                ", usable=" + usable +
                ", disabledReason='" + disabledReason + '\'' +
                '}';
    }
}
