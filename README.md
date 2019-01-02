# monitor——主服务
## 1. 登录服务

### 1. 登录
* url地址：/login
* 请求方式：post
* 参数：
```$xslt
account:dd
password:1
```  
* 返回格式：
```$xslt
{
    "result": "success",
    "message": null,
    "data": null
}
```

### 2. 注册
* url地址：/apply
* 请求方式：post
* 参数：
```$xslt
account:dd
password:1
email:123@qq.com
mobile:10086
```
* 返回格式：
```$xslt
{
    "result": "success",
    "message": null,
    "data": null
}
```

### 3. 获取个人信息
* url地址：/userInfo
* 请求方式：post
* 参数：
```$xslt
userId:f6e51629-b2e0-4536-b2db-bdf98d97cc3d
```
* 返回格式：
```$xslt
{
	"result": "success",
	"message": null,
	"data": {
		"userId": "bf6eb812-4b38-4965-a121-bf297d58447c",
		"account": "dd",
		"password": "1",
		"email": "123@qq.com",
		"mobile": "10086"
	}
}
```

### 4. 修改个人信息
* url地址：/apply
* 请求方式：post
* 参数：
```$xslt
userId:f6e51629-b2e0-4536-b2db-bdf98d97cc3d
account:dd
email:1234@qq.com
mobile:10086
```
* 返回格式：
```$xslt
{
    "result": "success",
    "message": null,
    "data": null
}
```

### 5. 发送邮件
* url地址：/sendEmail
* 请求方式：post
* 参数：
```$xslt
email:123@qq.com
```
* 返回格式：
```$xslt
{
    "result": "success",
    "message": null,
    "data": null
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
* 返回格式：
```$xslt
{
    "result": "success",
    "message": null,
    "data": null
}
```