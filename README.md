# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.4/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.4/gradle-plugin/packaging-oci-image.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/3.4.4/specification/configuration-metadata/annotation-processor.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.4/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

## api 테스트 
http://localhost:8080/swagger-ui/index.html

## application.properties 에서 변경해야 할 값
spring.datasource.url=jdbc:mariadb://localhost:3306/springboot
spring.datasource.username=[자기로컬db이름으로 변경]
spring.datasource.password=[자기로컬db 비밀번호로 변경]
spring.mvc.pathmatch.matching-strategy=ant_path_matcher

## 웹 알림 관련(웹소켓)

## 마이페이지 관련 
header 에 X-AUTH-TOKEN 이름에 accessToken 실어 보낼것.
