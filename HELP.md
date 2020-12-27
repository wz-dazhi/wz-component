[TOC]

# 帮助

## 统一升级版本

1. 修改`wz-component`的`pom.xml`, 改成要升级的版本号

   ```xml
   <wz-component.version>0.0.2-SNAPSHOT</wz-component.version>
   ```
   
2. 修改banner版本

   修改wz-common项目, 打开`banner.txt`文件. 版本号修改为`v0.0.2.SNAPSHOT`
   
3. 进入`wz-component`目录

   ```shell
   # 设置新版本号
   mvn versions:set -DnewVersion=0.0.2-SNAPSHOT
   # 提交版本
   mvn versions:commit
   ```

