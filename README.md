# Spring Rest Docs & Spock & Rest Assured & Markdown 적용하기

팀의 API 문서 자동화를 위해 [Spring Rest Docs](https://docs.spring.io/spring-restdocs/docs/current/reference/html5/)를 적용해보기로 했습니다.  
Spring Boot Rest Docs의 기본 조합인 Mock MVC & Asciidoc 을 사용하지 않고, Spock & Rest Assured & Markdown을 써야겠다고 생각했는데요.  

* 이미 Groovy & Spock 기반으로 동적 언어로 테스트 코드 작성이 익숙한 상태
  * 문서화를 위해 테스트 프레임워크를 변경하는건 배보다 배꼽이 크다는 생각
* Mock MVC보다는 Rest Assured가 API 테스트 코드에 좀 더 직관적
* 팀내 위키를 비롯한 많은 문서들이 Markdown 기반으로 진행
  * Asciidoc을 별도로 다 익혀야 하는건 낭비라고 생각

> 이번 포스팅은 Spring Rest Docs를 마크다운으로 관리하기이지만, 끝까지 읽어보시면 **귀찮더라도 Asciidoc을 배워서 쓰자**란 결론이 나오실 것 같습니다.  
저는 그냥 Asciidoc을 써야겠다고 생각했습니다. ㅠㅠ

모든 코드는 [Github](https://github.com/jojoldu/springboot-rest-docs-spock-markdown)에 있으니 참고하시면 좋을것 같습니다.  
  
프로젝트 환경은 스프링부트 2.0.1, Gradle 기반입니다.

## 1. 프로젝트 생성

프로젝트를 생성하시고 build.gradle을 다음과 같이 작성합니다.  
먼저 Rest Assured를 위한 의존성들을 추가합니다.

```groovy
...
dependencies {
    ...
    testCompile('io.rest-assured:rest-assured:3.0.2') // for rest assured
    testCompile('org.springframework.restdocs:spring-restdocs-restassured') // for rest assured
    ...
}
```

spock을 위한 의존성과 플러그인을 설치합니다.

```groovy
...
apply plugin: 'groovy' // for spock
...
dependencies {
    ...
    testCompile('org.spockframework:spock-core:1.1-groovy-2.4') // for spock
    testCompile('org.spockframework:spock-spring:1.1-groovy-2.4') //for spock
}

```

마지막으로 Markdown으로 Rest Docs를 작성할 수 있게 Gradle Task를 추가합니다.


```groovy
...
ext {
    snippetsDir = file('build/generated-snippets')
}

test {
    outputs.dir snippetsDir // 자동 생성되는 마크다운 파일들이 저장될 장소
}

task(bundleInstall, type: Exec) {
    workingDir file('slate')
    executable 'bundle'
    args 'install'
}

task(slate, type: Exec) {
    dependsOn 'bundleInstall', 'test'
    workingDir file('slate')
    executable 'bundle'
    args 'exec', 'middleman', 'build', '--verbose' // debug mode
}

build {
    dependsOn 'slate'
}
...
```

이렇게 하시면 전체 코드는 아래와 같습니다.

```groovy
buildscript {
    ext {
        springBootVersion = '2.0.1.RELEASE'
    }
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}

apply plugin: 'groovy' // for spock
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'

group = 'com.jojoldu'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = 1.8
targetCompatibility = 1.8

repositories {
    mavenCentral()
}

ext {
    snippetsDir = file('build/generated-snippets')
}

test {
    outputs.dir snippetsDir // 자동 생성되는 마크다운 파일들이 저장될 장소
}

task(bundleInstall, type: Exec) {
    workingDir file('slate')
    executable 'bundle'
    args 'install'
}

task(slate, type: Exec) {
    dependsOn 'bundleInstall', 'test'
    workingDir file('slate')
    executable 'bundle'
    args 'exec', 'middleman', 'build', '--verbose' // debug mode
}

build {
    dependsOn 'slate'
}

dependencies {
    compile('org.springframework.boot:spring-boot-starter-data-jpa')
    compile('org.springframework.boot:spring-boot-starter-web')

    runtime('com.h2database:h2')

    compileOnly('org.projectlombok:lombok')

    testCompile('io.rest-assured:rest-assured:3.0.2') // for rest assured
    testCompile('org.springframework.restdocs:spring-restdocs-restassured') // for rest assured
    testCompile('org.springframework.boot:spring-boot-starter-test')
    testCompile('org.spockframework:spock-core:1.1-groovy-2.4') // for spock
    testCompile('org.spockframework:spock-spring:1.1-groovy-2.4') //for spock
}

```

> 참고: Mac에 기본 설치된 Ruby를 이용하시면 **Permission Denied** 문제가 발생할 수 있습니다.  
그럴 경우 [이전 포스팅](http://jojoldu.tistory.com/288)를 참고해 문제를 해결하시고 계속 진행해주세요.

bundler 설치

```bash
gem install bundler
```

bundler 설치가 잘 되셨으면 

> 일단 첫 빌드때 gem 의존성 받는 시간이 너무 오래걸립니다.  
10분정도가 소모 됐습니다.  
두번째 build 부터는 다를수는 있겠지만

## 2. 테스트 코드 작성

## 참고 

* [spring-restdocs docs](https://docs.spring.io/spring-restdocs/docs/current/reference/html5/)
* [sample-rest-notes-slate](https://github.com/spring-projects/spring-restdocs/tree/v2.0.1.RELEASE/samples/rest-notes-slate)
* [sample-rest-assured](https://github.com/spring-projects/spring-restdocs/blob/v2.0.1.RELEASE/samples/rest-assured)