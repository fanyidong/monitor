package cn.fyd.monitorlogin.common;

import cn.fyd.model.Monitor;
import cn.fyd.model.Result;
import cn.fyd.util.CheckUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

import static cn.fyd.common.Constant.*;

/**
 * http请求工具类
 * @author fanyidong
 * @date Created in 2019-01-07
 */
@Component
public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     *  连接主机服务超过时间
     */
    @Value("${http.request.connect-timeout}")
    private Integer connectTimeout;
    /**
     * 设置连接请求超时时间
     */
    @Value("${http.request.connection-request-timeout}")
    private Integer connectionRequestTimeout;
    /**
     * 设置读取数据连接超时时间
     */
    @Value("${http.request.socket-timeout}")
    private Integer socketTimeout;


    /**
     * get请求
     * @param monitor 监控实体类
     * @return Result
     */
    public Result doRequest(Monitor monitor) {
        log.info("开始执行请求 -> 监控名:" + monitor.getName());
        String url = monitor.getUrl();
        if (!CheckUtils.hasHttp(url)) {
            monitor.setUrl(HTTP + "://" + url);
        }
        // 通过默认配置创建一个HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 请求类型get或者post
        HttpRequestBase httpMethod;
        switch (monitor.getRequestMethod()) {
            case POST:
                httpMethod = new HttpPost(monitor.getUrl());
                // 生成提交内容
                HttpEntity entity = new UrlEncodedFormEntity(createParam(monitor.getCommitContent()), Consts.UTF_8);
                ((HttpPost) httpMethod).setEntity(entity);
                break;
            case GET:
                // 定义get请求
            default:
                httpMethod = new HttpGet(monitor.getUrl());
                break;
        }
        log.info("请求类型：【" + httpMethod.getClass() + "】");
        // 请求设置
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(connectTimeout).
                setConnectionRequestTimeout(connectionRequestTimeout).
                setSocketTimeout(socketTimeout).
                build();
        // 为HttpGet实例设置配置内容
        httpMethod.setConfig(requestConfig);
        // 监控结果表实体类
        Result requestResultModel = new Result();
        // 对象复制(monitorId)
        BeanUtils.copyProperties(monitor, requestResultModel);
        // 设置请求开始时间
        requestResultModel.setStartTime(new Date());
        // 执行get请求得到返回对象
        CloseableHttpResponse httpResponse;
        try {
            //执行请求，获得响应内容
            httpResponse = httpClient.execute(httpMethod);
        } catch (IOException e) {
            requestResultModel.setStatus(REQUEST_FAILED_CODE);
            // 设置请求结束时间
            requestResultModel.setEndTime(new Date());
            requestResultModel.setResponseTime(requestResultModel.getEndTime().getTime()-requestResultModel.getStartTime().getTime());
            // 不可用
            requestResultModel.setUsable(0);
            // 设置请求失败原因
            requestResultModel.setDisabledReason(e.getCause().toString());
            log.info("http-get请求失败 -> " + e.getMessage(), e + ";监控任务【" + monitor.getName() + "】的监控结果为【不可用】");
            return requestResultModel;
        }
        // 设置请求结束时间
        requestResultModel.setEndTime(new Date());
        // 计算响应时间
        requestResultModel.setResponseTime(requestResultModel.getEndTime().getTime()-requestResultModel.getStartTime().getTime());
        // 如果请求返回不为null则为请求状态码赋值
        if (httpResponse != null) {
            requestResultModel.setStatus(httpResponse.getStatusLine().getStatusCode());
            // 处理高级设置，是否满足用户设定
            if (monitor.getMatchTarget()!=null) {
                String mess;
                try {
                    // 是否可用
                    mess = handlingHigherSetUp(httpResponse, monitor);
                    if (SUCCESS.equals(mess)) {
                        requestResultModel.setUsable(ONE);
                    } else {
                        requestResultModel.setUsable(ZERO);
                        requestResultModel.setDisabledReason(mess);
                    }
                } catch (Exception e) {
                    // 如果出现任何异常，则不可用
                    requestResultModel.setUsable(ZERO);
                    requestResultModel.setDisabledReason(FAILED);
                    log.error("处理高级设置出错 -> " + e.getMessage(), e);
                }
            } else {
                // 如果仅是GET请求，判断网页是否可访问，则可用
                if (requestResultModel.getStatus().equals(REQUEST_FAILED_CODE)) {
                    requestResultModel.setUsable(ZERO);
                    requestResultModel.setDisabledReason(REQUEST_FAILED);
                }
                requestResultModel.setUsable(ONE);
            }
            if (!StringUtils.isEmpty(requestResultModel.getDisabledReason())) {
                log.info("不可用原因 -> " + requestResultModel.getDisabledReason());
            }
            log.info("监控任务【" + monitor.getName() + "】的监控结果是 -> " + (requestResultModel.getUsable()==1?"可用":"不可用"));
        }
        log.info("执行http-get请求成功 -> 监控名 -> " + monitor.getName());
        return requestResultModel;
    }

    /**
     * 转换为post的提交内容
     * @param commitContent
     * @return
     */
    public List<NameValuePair> createParam(String commitContent) {
        //String转map
        Map<String, Object> map = new HashMap<>(FIVE);
        // 每一行为一个参数及值
        String[] commits = commitContent.split(";");
        for (int i = 0; i < commits.length; i++) {
            String[] keyValue = commits[i].split(":");
            map.put(keyValue[0], keyValue[1]);
        }
        //建立一个NameValuePair数组，用于存储欲传送的参数
        List<NameValuePair> nvps = new ArrayList<>();
        if (!map.isEmpty()) {
            for (String k : map.keySet()) {
                nvps.add(new BasicNameValuePair(k, map.get(k).toString()));
            }
        }
        return nvps;
    }

    /**
     * 监控任务高级设置处理
     * 查看返回内容中是否匹配用户设置的内容
     * @param httpResponse 返回内容实体类
     * @param monitor 监控实体类
     * @return 返回信息
     */
    private String handlingHigherSetUp(CloseableHttpResponse httpResponse, Monitor monitor) throws IOException {
        // 匹配内容
        String matchContent = monitor.getMatchContent();
        // 匹配方式(是否包含匹配内容),false=>不包含匹配内容
        boolean isContain = (monitor.getMatchType() == 1);
        String content;
        if (monitor.getMatchTarget() == 1) {
            // 匹配目标为1-响应内容
            content = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        } else {
            // 匹配目标为2-响应头信息
            content = JSON.toJSONString(httpResponse.getAllHeaders());
        }
        log.info("**********开始处理监控任务高级设置**********");
        log.info("匹配方式 -> " + (isContain?"包含匹配内容":"不包含匹配内容"));
        log.info("需匹配内容 -> " + matchContent);
        log.info("响应内容 -> " + content);
        // 返回匹配结果，判断出错原因
        if (isMatch(content, matchContent, isContain)) {
            return SUCCESS;
        } else {
            if (isContain) {
                // 如果匹配方式为包含则返回不包含
                return NOT_CONTAINS_CONTENT + "【" + matchContent + "】";
            } else {
                // 如果匹配方式为不包含则返回包含
                return CONTAINS_CONTENT + "【" + matchContent + "】";
            }
        }
    }

    /**
     * 是否符合匹配规则
     * @param content 被匹配内容
     * @param matchContent 用户指定的匹配内容
     * @param matchRule 匹配规则
     * @return boolean
     */
    private boolean isMatch(String content, String matchContent, boolean matchRule) {
        // 如果用户指定的匹配内容包含在返回内容中,isMatch=1,否则为零
        boolean isMatch = content.contains(matchContent);
        //只有判断结果和匹配规则的布尔值相同，即都是true或都是false的时候才返回true
        return isMatch == matchRule;
    }
}
