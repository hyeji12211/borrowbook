# 클라우드 네이티브 아키텍처

<img src="https://github.com/user-attachments/assets/c36e9eba-0c37-48b3-be31-b6f10d964bb2" alt="image" style="zoom:80%;" />



# 클라우드 네이티브 모델링

### 서비스 시나리오

**기능적 요구사항**

1. 사용자가 도서를 검색한다.
2. 사용자가 도서를 대여한다.
3. 사용자가 도서를 반납한다.
4. 관리자가 도서 정보를 등록하고, 재고 상태를 관리한다.
5. 사용자가 빌리고자하는 도서의 재고가 없는 경우에는 빌릴 수 없다.
6. 연체가 된 경우 책을 빌릴 수 없다.

**비기능적 요구사항**

1. 트랜잭션
   1. 도서의 수량이 부족한 경우에는 대여가 되지 않아야 한다.
2. 장애격리
   1. 대여 시스템에 과부하가 발생하면 사용자를 잠시동안 받지 않고 잠시후에 진행하도록 한다.
3. 성능
   1. 사용자가 자신의 대여 상태를 확인할 수 있어야 한다.

### 도메인분석 - 이벤트스토밍

![image](https://github.com/user-attachments/assets/af7376b6-e028-46c6-9f77-096a194dabd9)

# 클라우드 네이티브 개발 MSA

### 1) Saga(분산트랜잭션) & Compensation(보상처리)

- 책 4권 빌리기

![image](https://github.com/user-attachments/assets/eee605ad-b446-45ef-aefa-aee5fcc89b15)

- 책 3권 추가하기

![image](https://github.com/user-attachments/assets/2d022bc2-4cf0-4033-a849-e2f9cd8f060b)

- 책을 5번 빌리고 난 뒤 OutOfStock 이벤트 수신 후 status가 canceled로 변경

<img src="https://github.com/user-attachments/assets/c1c7a9a2-2946-4f99-9ffe-3a70729799a4" alt="image" style="zoom:80%;" />

### 2) 단일 진입점 - Gateway

- 외부에서 마이크로서비스들을 접근하기 위해서는 단일 진입점 gateway가 필요로 합니다.

<img src="https://github.com/user-attachments/assets/df3da5cc-586f-4883-84a5-ba1861844819" alt="image1" width="40%" style="margin-right: 10px;"> <img src="https://github.com/user-attachments/assets/8b8ec638-0fc8-4e5d-a607-6f651d48cb47" alt="image2" width="40%">




### 3) 분산 데이터 프로젝션 - CQRS

- CQRS를 통해 데이터 쓰기, 읽기를 분리하여 관리
- BookBorrowed 이벤트가 발생하면 데이터가 자동으로 업데이트됨 -> 항상 최신 상태 유지
  - Mypage의 값을 BookBorrowed에서 가져옴 (사용자 아이디, 빌린 책의 수, 빌린 상태)


<img src="https://github.com/user-attachments/assets/939b5655-f4f5-45a9-b7df-1591175e2fbe" alt="image1" width="40%" style="margin-right: 10px;"> <img src="https://github.com/user-attachments/assets/5e06c5ac-3910-45eb-9271-d779ba1cb513" alt="image2" width="40%">



# 컨테이너 인프라 설계 및 구성 역량

### 클라우드 배포 - Jenkins

![image](https://github.com/user-attachments/assets/1de9aaf0-439b-4671-95a1-9d7ce808be39)

![image](https://github.com/user-attachments/assets/79155f40-5645-4ba8-b2da-37375c0105a6)

- 기존의 service와 deployment borrow 삭제 후 다시 생성 확인

<img src="https://github.com/user-attachments/assets/3d5e2af1-ee5c-4591-8b81-e8658a4ee7bf" alt="image" style="zoom: 67%;" />

### 1) 컨테이너 자동확장 - HPA

- Auto Scaler 설정

![image](https://github.com/user-attachments/assets/ce4bb86d-85dd-499d-b745-876d710dd62b)

- kubectl get po -w 명령을 사용하여 pod가 생성되는 것을 확인

![image](https://github.com/user-attachments/assets/1b5323a1-f7d7-478b-80aa-c4863d48f1a0)



### 2) 컨테이너로부터 환경분리 - CofigMap

- 주문서비스의 Logging 레벨을 Configmap의 ORDER_DEBUG_INFO 참조하도록 설정 확인

![image](https://github.com/user-attachments/assets/51209fe5-c506-475d-aaf6-9e519bcaf0d5)

<img src="https://github.com/user-attachments/assets/04d3bb7f-be90-4756-8a24-3c5273727e9a" alt="image" style="zoom:80%;" />

### 3) 클라우드스토리지 활용 - PVC

- pvc 생성

![image](https://github.com/user-attachments/assets/9acc6553-4b9e-4aa5-99f3-74096763b96e)

- Pod내 한 컨테이너에서 생성한 볼륨이 다른 컨테이너에서 가시적인지 확인

![image](https://github.com/user-attachments/assets/b12813e0-e46e-42ac-bc28-49ae93b5f48c)

- borrow 서비스 2개로 스케일아웃

![image](https://github.com/user-attachments/assets/8e84738b-aa6f-4fb3-9798-f351a9d72846)

- 확장된 대여 서비스에서도 test.txt가 확인되는지 검증

<img src="https://github.com/user-attachments/assets/5c495239-066c-40e3-b661-48912d6b412e" alt="image" style="zoom:80%;" />

### 4) 무정지배포 - Rediness Probe 

- borrow 서비스의 deployment.yaml 파일에 redinessProbe 를 설정하고 배포를 진행

<img src="https://github.com/user-attachments/assets/996b26b4-e34f-45c8-b042-9c3672bc7d7c" alt="image1" width="40%" style="margin-right: 10px;"> <img src="https://github.com/user-attachments/assets/936e12c2-435a-45ba-b1b5-ea64c2c9c600" alt="image2" width="50%">


### 5) 서비스 메쉬 응용 - Mesh 

- istio-injection=enabled 라벨 설정 후 배포

<img src="https://github.com/user-attachments/assets/db1c65a2-4348-44cb-9449-9ee8272cda04" alt="image" style="zoom: 67%;" />

### 6) 통합 모니터링 - Loggregation/Monitoring

- loki pod 생성

<img src="https://github.com/user-attachments/assets/13ad8659-da97-48ba-ab1a-f50a907f3eed" alt="image" style="zoom:80%;" />

- EXTERNAL-IP로 Grafana 접속

<img src="https://github.com/user-attachments/assets/dacbbad4-cb6b-4147-a4b7-e3b40081bfcf" alt="image" style="zoom:80%;" />

- Prometheus 접속

![image](https://github.com/user-attachments/assets/e58b7067-077b-4416-9a3b-3aaecee088b6)

- borrow에 siege로 부하발생 후 Prometheus 그래프 확인

![image](https://github.com/user-attachments/assets/27f5b683-dbfb-48d1-ade9-0b51cb42e4c2)

- borrow에 siege로 부하발생 후 Grafana 그래프 확인

![image](https://github.com/user-attachments/assets/02e06e7b-08cc-4e83-bcbd-662681a5a382)
