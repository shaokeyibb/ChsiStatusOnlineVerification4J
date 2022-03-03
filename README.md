# ChsiStatusOnlineVerification4J

学信网学籍/学历在线验证报告查询 API

## 使用

Kotlin:

```Kotlin
class Demo {
    fun get(onlineVerificationCode: String): ChsiStatus {
        return ChsiStatusOnlineVerification4J.getStatus(onlineVerificationCode)
    }
}
```

Java:

```Java
public class Demo {
    public ChsiStatus get(String onlineVerificationCode) {
        return ChsiStatusOnlineVerification4J.INSTANCE.getStatus(onlineVerificationCode);
    }
}
```

## 编译

Linux:
```Shell
gradle build
```

Windows:
```Bash
gradle.bat build
```

## 开源协议

本软件是自由软件，遵循 [GNU AFFERO GENERAL PUBLIC LICENSE](/LICENSE) 协议开源