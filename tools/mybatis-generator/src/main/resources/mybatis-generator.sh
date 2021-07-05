# 1. 使用命令行方式生成
#cd lejane-tools/mybatis-generator/src/main/resources/
#sh mybatis-generator.sh

# 2. 使用maven 插件方式生成
cd tools/mybatis-generator/
## -Dmybatis.generator.overwrite=true 参数覆盖
mvn mybatis-generator:generate -Dmybatis.generator.overwrite=true