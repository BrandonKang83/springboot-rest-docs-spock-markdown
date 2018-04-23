# Spring Boot Rest Docs & Spock & Markdown & Rest Assured 적용하기



## Markdown 적용

## bundle Permission Denied 문제

```bash
brew install rbenv
```
설치 가능한 루비 버전 목록을 확인합니다.

```bash
rbenv install -l
```

현재 최신 버전 (2018.04.22)이 2.5.0이므로 2.5.1을 설치합니다.

```bash
rbenv install 2.5.1 && rbenv rehash
```

여기서 본인이 사용하는 쉘에 따라 다른 방식을 취해야합니다.  
  
**bash**

```bash
echo 'eval "$(rbenv init -)"' >> ~/.bash_profile

# 반영
source ~/.bash_profile
```

**zsh**

```bash
# ~/.zshrc을 열어
vim ~/.zshrc

# 제일 하단에 다음과 같이 추가

export PATH="$HOME/.rbenv/bin:$PATH"
eval "$(rbenv init -)"

# 반영
source ~/.zshrc
```

bundler 설치

```bash
gem install bundler
```

bundler 설치가 잘 되셨으면 

> 일단 첫 빌드때 gem 의존성 받는 시간이 너무 오래걸립니다.  
10분정도가 소모 됐습니다.  
두번째 build 부터는 다를수는 있겠지만

## 커맨드

상세 오류 로그 확인

```bash
# slate 디렉토리로 이동
cd slate

# middleman 직접 실행
bundle exec middleman build 


```

## 참고 

* [spring-restdocs docs](https://docs.spring.io/spring-restdocs/docs/current/reference/html5/)
* [sample-rest-notes-slate](https://github.com/spring-projects/spring-restdocs/tree/v2.0.1.RELEASE/samples/rest-notes-slate)
* [sample-rest-assured](https://github.com/spring-projects/spring-restdocs/blob/v2.0.1.RELEASE/samples/rest-assured)