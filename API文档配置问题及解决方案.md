# API文档配置问题及解决方案

## 问题概述

在配置 Knife4j API 文档时遇到了 `Multiple Dockets with the same group name are not supported` 错误，导致应用无法正常启动。

## 问题详情

### 错误信息
```
org.springframework.context.ApplicationContextException: Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.IllegalStateException: Multiple Dockets with the same group name are not supported. The following duplicate groups were discovered. default
```

### 根本原因
存在两个同名的 Docket 组配置：
1. `application.yml` 中 Knife4j 配置的 "default" 组
2. 手动创建的 `SwaggerConfig.java` 中的默认 Docket 实例

Springfox 检测到组名冲突，抛出 `IllegalStateException` 异常。

## 解决过程

### 第一次尝试
**方案**：为手动创建的 Docket 指定不同组名
```java
@Bean
public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("api")  // 添加组名区分
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.itheima.backend.controller"))
            .paths(PathSelectors.any())
            .build();
}
```

### 最终解决方案
**方案**：移除手动 Swagger 配置，完全依赖 Knife4j 自动配置

1. **删除手动创建的 SwaggerConfig.java**
2. **修改 FileController 注解**：
   - 使用 Knife4j 的 `@ApiSupport` 和 `@ApiOperationSupport` 注解
   - 移除 Swagger 的 `@Api`、`@ApiOperation`、`@ApiParam` 注解

## 最终配置

### 保留的配置文件

**application.yml** (保持不变)
```yaml
knife4j:
  enable: true
  openapi:
    title: "接口文档"
    version: 1.0
    group:
      default:
        api-rule: package
        api-rule-resources:
          - com.itheima.backend.controller
```

**FileController.java** (简化注解)
```java
/**
 * 文件接口
 */
@ApiSupport(order = 5)
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    
    @ApiOperationSupport(order = 1)
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/upload")
    public BaseResponse<String> testUploadFile(@RequestPart("file") MultipartFile multipartFile) {
        // 实现代码...
    }
}
```

## 验证方法

1. 重新启动 Spring Boot 应用
2. 访问 `http://localhost:8124/api/doc.html` 查看 Knife4j 文档
3. 确认 FileController 的接口正常显示

## 方案优势

✅ **避免配置冲突** - 消除了组名重复问题  
✅ **简化配置** - 减少手动配置代码  
✅ **充分利用框架特性** - 依赖 Knife4j 的自动配置能力  
✅ **维护性好** - 配置集中在一个地方管理  

## 注意事项

- 确保 `application.yml` 中的包扫描路径正确
- Knife4j 注解提供了足够的文档生成功能
- 如需更复杂的配置，可以在 `application.yml` 中进行扩展

## 相关技术栈

- **Spring Boot**: 2.6.13
- **Knife4j**: 4.4.0 (OpenAPI2 版本)
- **Springfox**: 用于 Swagger 文档生成
- **MyBatis-Plus**: 3.5.9

---
*文档创建时间: 2024年*