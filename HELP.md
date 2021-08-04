[TOC]

# 使用

1. 创建普通maven项目
2. pom.xml 加入依赖
   ```shell
   <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.wz</groupId>
                <artifactId>wz-component</artifactId>
                <version>0.0.1.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>

        <dependency>
            <groupId>com.wz</groupId>
            <artifactId>wz-common</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
   ```
3. 编写App启动类
   ```java
   @SpringBootApplication
   public class App {

      public static void main(String[] args) {
         SpringApplication.run(App.class, args);
      }
   }
   ```

# 统一升级版本

1. 修改banner版本

   修改wz-common项目, 打开`banner.txt`文件. 版本号修改为`v0.0.2.RELEASE`

2. 进入`wz-component`目录

   ```shell
   # 设置新版本号
   mvn versions:set -DnewVersion=0.0.2.RELEASE
   # 提交版本
   mvn versions:commit
   ```
