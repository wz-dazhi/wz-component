[TOC]

# 帮助

## 统一升级版本

1. 进入`wz-component`目录

   ```shell
   # 设置新版本号
   mvn versions:set -DnewVersion=0.0.2-SNAPSHOT
   # 提交版本
   mvn versions:commit
   ```

2. 修改`wz-component`的`pom.xml`

   ```xml
   <wz-component.version>0.0.2-SNAPSHOT</wz-component.version>
   ```

