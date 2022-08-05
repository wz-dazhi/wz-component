[TOC]

# 拉取源码使用

1. 拉取代码后需要install到本地仓库
2. 创建普通maven项目
3. pom.xml 加入依赖
   ```shell
   <dependencyManagement>
        <!-- 需要提前install到本地仓库-->
        <dependencies>
            <dependency>
                <groupId>com.wz</groupId>
                <artifactId>wz-component</artifactId>
                <version>${wz-component.version}</version>
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
        <finalName>${artifactId}</finalName>

        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
   ```
4. 编写App启动类
   ```java
   @SpringBootApplication
   public class App {

      public static void main(String[] args) {
         SpringApplication.run(App.class, args);
      }
   }
   ```

# jitpack使用
1. 创建普通maven项目
2. 跟pom.xml文件加入
   ```xml
   <dependencyManagement>
       <!-- 引入jitpack 打好的依赖-->
       <dependencies>
           <dependency>
               <groupId>com.github.wz-dazhi.wz-component</groupId>
               <artifactId>wz-component</artifactId>
               <version>${wz-component.version}</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
       </dependencies>
   </dependencyManagement>
   
   <!-- 加入jitpack仓库 -->
   <repositories>
       <repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
       </repository>
   </repositories>
   
   <build>
      <finalName>${artifactId}</finalName>
      <plugins>
         <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <executions>
               <execution>
                  <goals>
                     <goal>repackage</goal>
                  </goals>
               </execution>
            </executions>
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