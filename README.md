# ParkingSystem

![badge](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![badge](https://img.shields.io/badge/Vue%20js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D)
![badge](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

基于SpringBoot(SSM)的停车场管理系统。

## Compile & Install

编译及部署：

``` sh
git clone https://github.com/Season0518/JavaCourseDesign.git .
mvn compile
```

若重新编译前端页面请切换分支至`frontend`，按`Readme.md`中的步骤进行编译。

## Usage

在使用前请先将`src/resources/schema.sql`导入进MySQL，否则项目无法正常启动。

启动项目:

```
export DB_PASSWORD={YourPassword}
java -jar target/ParkingSystem-0.0.1-SNAPSHOT.jar
```

其中需将`{YourPassword}`替换为你数据库中`root`用户的密码。

## License

MIT © Season0518