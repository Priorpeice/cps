# CPS

## (Coding Practice Service : 코딩 테스트 서비스)

## 💡 프로젝트 소개

- 대학생들의 사고 확장과 코딩 능력을 향상과 실전 코딩테스트에 대비할 수 있는 코딩 채점 서비스 프로젝트입니다.
- FrontEnd : https://github.com/Priorpeice/tscps
- LlmServer : https://github.com/Priorpeice/llamaServer

## ⚙️ 프로젝트 개발 환경

- 프로그래밍 언어(BackEnd): Java
- 개발도구 : Intellij
- DBMS: Mysql ,Redis
- 프레임워크: Spring , Spring boot

## 🧾 시스템 아키텍처

- 시스템 구조를 간략하게 나태내보았습니다.

![Untitled](https://github.com/user-attachments/assets/4640ec9e-ee7c-4d36-b24a-a7ad3a3889d5)

## 📚 기능

- 코드 실행 및 채점 기능
    - BackEnd
        - 사용자의 코드를 받아서 파일로 저장 후 해당 파일을 Docker Container로 옮겨서 실행하는 방식입니다.
            
            ![Docker%EA%B0%84%ED%8E%B8%ED%99%94](https://github.com/user-attachments/assets/5adc88a6-d62a-466e-9250-06a280accb67)
            
        - 코드 채점의 경우는 지정된 TestCase 수 만큼 반복해서 코드를 실행하는 방식을 사용하였습니다.  현재 채점은 코드 실행 소요시간과 코드 결과 값으로만 정답 여부를 판별하고 있습니다.
        
        - Docker를 사용함으로써 프로그래밍 언어 지원 확장에 용이합니다. 개발 관점에서는 언어에 따른 Service 로직을 구현 후 Docker에 해당 언어의 Docker Image 파일만을 준비하면 됩니다. 현재는  Java,C,C++,Python, Dart 언어를 제공하고 있습니다.
        
        - Dart 언어의 경우 모바일 개발자들도 메모리 최적화 같은 문제에 많이 직면한다고 생각이 들었고 알고리즘 공부가 필히 필요하다고 생각했습니다.
        
- 게시판 기능
    - BackEnd
        - 게시판의 종류에는 문제 게시판과 커뮤니티 게시판이 있습니다.
        
        - 문제 게시판
        
        문제 게시판에서는 사용자가 직접 문제를 올릴 수 있으며 사용자가 풀어보고 싶은 문제를 고를 수 있습니다.
        
        - 커뮤니티 게시판
        
        커뮤니티 게시판에서는 사용자들끼리 문제나 이슈에 대해 자유롭게 토론하는 게시판입니다.
        
        - ORM 기술로 JPA를 이용하여 개발을 진행하였습니다.
        
        조회의 경우는 검색속도 개선을 위해 페이징을 적용하였습니다.  
        
        - 한계점으로 데이터 양이 많아졌을때 검색속도가 느려진다는 근본적인 단점은 해결하지 못하였습니다.
    
- 인증 및 인가 기능
    - BackEnd
        
        인증 및 인가 기능을 위해 Spring Security를 이용하여 개발을 진행하였습니다.
        
        로그인 인증 방식으로는 JWT 방식을 사용하였습니다. 
        
        - JWT를 선택한 이유로는 세션에 비해서 확장성 부분에서 용이하다고 생각했습니다.
        
        세션의 경우는 세션 정보를 서버가 관리함으로써 서버 자원을 많이 소모한다는 단점과 서버 확장시 세션을 분배하여 저장하는 기술이 필요하다는 점에서 JWT가 이점이 있다고 생각했습니다. 
        
        - RTR 기법을 이용하여 사용자의 로그인을 유지시킬수 있도록 구현하였습니다.
        
        RTR 기법을 통해 Redis 를 이용하여 RT와 사용자 pk를 key: value 로 저장하는 것으로 구현하였습니다. 이때 Jwt의 장점이라고 생각되는 Stateless 상태에 대한 고민이 참 많았습니다. 
        
        장점을 소모시키면 ‘무색무취’하지 않나? 라는 생각이 들었지만 로그인 이외에 로직에선 redis에 접근하지 않으므로 타협하였습니다.
        
- 코드 분석 기능
    - BackEnd
        - RestTemplate 라이브러리를 사용하여 외부 Server인 LlmServer로 요청 및 응답을 받는 기능을 구현하였습니다.
        - 사용자가 바로 LlmServer로 요청을 보내는 예시를 생각해보았지만 인가 문제를 처리하기 까다로웠고, LlmServer의 역활은 코드를 검증하는 기능만을 분리하고 싶어기 때문에 WAS에서  외부서버로 요청하는 방식을 채택하였습니다.
