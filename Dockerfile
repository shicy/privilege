FROM java:8

# 制度工作目录
WORKDIR /mnt/app

# 复制打包好的项目执行文件
COPY priv_service/target/privilege-*.jar ./app.jar

# 默认挂载日志目录
VOLUME /mnt/app/logs

# 开放端口 12031
EXPOSE 12031

# 设置时区，添加时区软连接
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo Asia/Shanghai > /etc/timezone

# 容器启动开始允许项目
ENTRYPOINT exec java -jar app.jar
