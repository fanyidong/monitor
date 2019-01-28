# monitor——主服务(http://127.0.0.1:8000)
## 1. 登录服务（已全部更新）
### 1. 登录
* url地址：/login
* 请求方式：post
* 参数：
```$xslt
account:root
password:1
```  
* 返回格式（成功）：
```$xslt
{
    "data": {
        "account": "root",
        "createTime": "2018-11-24 15:28:45",
        "email": "2922470093@qq.com",
        "mobile": 10087,
        "password": null,
        "updateTime": "2019-01-22 14:21:17",
        "userId": "3cd08494-fdb3-11e8-85c0-00163e0097c7"
    },
    "message": "成功",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
	"data": null,
	"message": "密码错误",
	"success": false
}
```

### 2. 注册
* url地址：/regist
* 请求方式：post
* 参数：
```$xslt
account:dd
password:1
email:123@qq.com
mobile:10086
```
* 返回格式（成功）：
```$xslt
{
    "data": null,
    "message": "成功",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
    "data": null,
    "message": "邮箱已被注册",
    "success": false
}
```

### 3. 获取个人信息
* url地址：/userInfo
* 请求方式：post
* 参数：
```$xslt
userId:f6e51629-b2e0-4536-b2db-bdf98d97cc3d
```
* 返回格式（成功）：
```$xslt
{
    "data": {
        "account": "root",
        "createTime": "2018-11-24 15:28:45",
        "email": "2922470093@qq.com",
        "mobile": 10087,
        "password": "c4ca4238a0b923820dcc509a6f75849b",
        "updateTime": "2019-01-22 14:21:17",
        "userId": "3cd08494-fdb3-11e8-85c0-00163e0097c7"
    },
    "message": "成功",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
    "data": null,
    "message": "操作非本人数据，请重试",
    "success": false
}
```

### 4. 修改个人信息
* url地址：/edit
* 请求方式：post
* 参数：
```$xslt
userId:f6e51629-b2e0-4536-b2db-bdf98d97cc3d
account:dd
email:1234@qq.com
mobile:10086
```
* 返回格式（成功）：
```$xslt
{
    "data": null,
    "message": "成功",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
    "data": null,
    "message": "邮箱已被注册",
    "success": false
}
```

### 5. 发送邮件
* url地址：/sendEmail
* 请求方式：post
* 参数：
```$xslt
email:123@qq.com
```
* 返回格式（成功）：
```$xslt
{
    "data": null,
    "message": "已经发送找回密码安全码到您邮箱。请在30分钟内重置密码",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
    "data": null,
    "message": "该邮箱尚未注册",
    "success": false
}
```

### 6. 修改密码
* url地址：/reset
* 请求方式：post
* 参数：
```$xslt
secretKey:KEPC
email:2922470093@qq.com
password:1
```
* 返回格式（成功）：
```$xslt
{
    "data": null,
    "message": "成功",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
    "data": null,
    "message": "安全码或邮箱不正确,请重新申请",
    "success": false
}
```

## 2. 监控任务服务（接口未投入使用）
### 1.新增监控
* url地址：/addMonitor
* 请求方式：post
* 参数：
```$xslt
userId:f6e51629-b2e0-4536-b2db-bdf98d97cc3d
name:监控项目6
url:www.baidu.com
watchTime:10
type:1
warnMethod:1
```
* 返回格式（成功）：
```$xslt
{
    "data": null,
    "message": "成功",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
    "data": null,
    "message": "邮箱已被注册",
    "success": false
}
```

### 2.关闭/开启监控
* url地址：/closeMonitor
* 请求方式：post
* 参数：
```$xslt
monitorId:73db27b5-1314-11e9-a14c-00163e0097c7
state:1
```
state：启用状态 0-禁用 1-启用
* 返回格式（成功）：
```$xslt
{
    "data": null,
    "message": "成功",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
    "data": null,
    "message": "邮箱已被注册",
    "success": false
}
```

### 3.修改监控
* url地址：/editMonitor
* 请求方式：post
* 参数：
```$xslt
monitorId:2aff34b7-1316-11e9-8f76-00163e0097c7
userId:f6e51629-b2e0-4536-b2db-bdf98d97cc3d
name:项目1
url:www.baidu.com
watchTime:19
type:1
warnMethod:1
```
* 返回格式（成功）：
```$xslt
{
    "data": null,
    "message": "成功",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
    "data": null,
    "message": "邮箱已被注册",
    "success": false
}
```

### 4.查询监控
* url地址：/getMonitors
* 请求方式：post
* 参数：
```$xslt
userId:f6e51629-b2e0-4536-b2db-bdf98d97cc3d
```
* 返回格式（成功）：
```$xslt
{
    "data": [
        {
            "createTime": "2019-01-08 15:22:34",
            "monitorId": "2aff34b7-1316-11e9-8f76-00163e0097c7",
            "name": "项目1",
            "state": 0,
            "type": 1,
            "updateTime": "2019-01-09 15:38:41",
            "url": "www.baidu.com",
            "userId": "f6e51629-b2e0-4536-b2db-bdf98d97cc3d",
            "warnMethod": 1,
            "watchTime": 19
        },
        {
            "createTime": "2019-01-09 14:52:58",
            "monitorId": "330ff54f-13db-11e9-8f76-00163e0097c7",
            "name": "监控项目1",
            "state": 1,
            "type": 1,
            "updateTime": "2019-01-09 14:52:58",
            "url": "www.baidu.com",
            "userId": "f6e51629-b2e0-4536-b2db-bdf98d97cc3d",
            "warnMethod": 1,
            "watchTime": 10
        }
    ],
    "message": "查询成功",
    "success": true
}
```
* 返回格式（失败）：
```$xslt
{
    "data": null,
    "message": "邮箱已被注册",
    "success": false
}
```