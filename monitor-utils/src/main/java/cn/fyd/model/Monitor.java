package cn.fyd.model;

import cn.fyd.annotation.IsEmpty;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 监控表实体类
 * @author fanyidong
 * @date Created in 2019-01-07
 */
public class Monitor {

    /**
     * 主键uuid
     */
    private String monitorId;

    /**
     * 关联用户表的userid
     */
    @IsEmpty(name = "用户id")
    private String userId;

    /**
     * 监控项目名称
     */
    @IsEmpty(name = "监控项目名称")
    private String name;

    /**
     * 监控地址
     */
    @IsEmpty(name = "监控地址")
    private String url;

    /**
     * 监控频率(分钟)
     */
    @IsEmpty(name = "监控频率")
    private Integer watchTime;

    /**
     * 监控类型（web网站 api接口 server服务）
     */
    private String type;

    /**
     * 警告方式（mail邮件 phone手机）
     */
    @IsEmpty(name = "警告方式")
    private String warnMethod;

    /**
     * 启用状态(0禁用 1启用)
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 请求方式（get post）默认get
     */
    private String requestMethod;

    /**
     * 匹配目标(1响应内容 2响应头信息)
     */
    private Integer matchTarget;

    /**
     * 匹配方式(0不包含匹配内容 1包含匹配内容)
     */
    private Integer matchType;

    /**
     * 匹配内容
     */
    private String matchContent;

    /**
     * post请求时的提交内容
     */
    private String commitContent;

    /**
     * 可用率(%)
     * 保留两位小数
     * 数据库中不存在此字段
     */
    private String usable;

    /**
     * 平均响应时间(ms)
     * 数据库中不存在此字段
     */
    private String averageResponseTime;


    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(Integer watchTime) {
        this.watchTime = watchTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWarnMethod() {
        return warnMethod;
    }

    public void setWarnMethod(String warnMethod) {
        this.warnMethod = warnMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Integer getMatchTarget() {
        return matchTarget;
    }

    public void setMatchTarget(Integer matchTarget) {
        this.matchTarget = matchTarget;
    }

    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    public String getMatchContent() {
        return matchContent;
    }

    public void setMatchContent(String matchContent) {
        this.matchContent = matchContent;
    }

    public String getCommitContent() {
        return commitContent;
    }

    public void setCommitContent(String commitContent) {
        this.commitContent = commitContent;
    }

    public String getUsable() {
        return usable;
    }

    public void setUsable(String usable) {
        this.usable = usable;
    }

    public String getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(String averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "monitorId='" + monitorId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", watchTime=" + watchTime +
                ", type=" + type +
                ", warnMethod=" + warnMethod +
                ", state=" + state +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", requestMethod=" + requestMethod +
                ", matchTarget=" + matchTarget +
                ", matchType=" + matchType +
                ", matchContent='" + matchContent + '\'' +
                ", commitContent='" + commitContent + '\'' +
                ", usable='" + usable + '\'' +
                ", averageResponseTime='" + averageResponseTime + '\'' +
                '}';
    }
}
