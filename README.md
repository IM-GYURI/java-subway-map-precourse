# 지하철 노선도 미션

## 📑개요

지하철 역과 노선을 관리하는 지하철 노선도 프로그램입니다.

## 🛠️기능 목록

### 초기 설정

- ✅ 프로그램 시작 시 사전 등록 정보를 활용하여 초기 설정

```
- 지하철역 : 교대역, 강남역, 역삼역, 남부터미널역, 양재역, 양재시민의숲역, 매봉역
- 지하철 노선 : 2호선, 3호선, 신분당선
- 노선에 등록된 역 (왼쪽 끝이 상행 종점)
   - 2호선 : 교대역 - 강남역 - 역삼역
   - 3호선 : 교대역 - 남부터미널역 - 양재역 - 매봉역
   - 신분당선 : 강남역 - 양재역 - 양재시민의숲역
```

- 지하철 역 -> stations.md
- 지하철 노선 -> lines.md
- 각 md 파일을 파싱하여 처리

### 지하철 역 관련 기능

- ✅ 역 등록
    - ✅ 이미 존재하는 역 이름일 경우
    - ✅ 역 이름이 2글자 미만일 경우
- 역 삭제
    - ✅ 노선에 등록된 역은 삭제 불가
- ✅ 역 조회

### 지하철 노선 관련 기능

- ✅ 노선 등록
    - ✅ 이미 존재하는 노선 이름일 경우
    - ✅ 노선 이름이 2글자 미만일 경우
- ✅ 노선 삭제
- ✅ 노선 조회

### 지하철 구간 기능

- ✅ 구간 추가
    - 구간 : 역과 역 사이
    - 하나의 역은 여러 개의 노선에 추가 가능
    - 역과 역 사이에 새로운 역 추가 가능
    - 갈래길 불가
- ✅ 구간 삭제
    - 노선에 등록된 역 제거
    - 종점을 제거할 경우 다음 역이 종점
    - ✅ 노선에 포함된 역이 2개 이하일 경우 제거 불가

### 입출력 기능

- ✅ 메인 화면 출력

```
## 메인 화면
1. 역 관리
2. 노선 관리
3. 구간 관리
4. 지하철 노선도 출력
Q. 종료
```

- ✅ 기능 선택
    - ✅ 출력
  ```
  ## 원하는 기능을 선택하세요.
  ```
    - ✅ 입력 : 숫자 혹은 문자
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 존재하지 않는 숫자 혹은 문자일 경우 : `[ERROR] 선택할 수 없는 기능입니다.`
- ✅ 역 관리 화면 출력

```
## 역 관리 화면
1. 역 등록
2. 역 삭제
3. 역 조회
B. 돌아가기
```

- ✅ 역 등록 화면
    - ✅ 출력 1
  ```
  ## 등록할 역 이름을 입력하세요.
  ```
    - ✅ 입력 : 역 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 지하철 역 이름이 중복될 경우
        - ✅ 지하철 역 이름이 2글자 미만일 경우 - 출력 2
  ```
  [INFO] 지하철 역이 등록되었습니다.
  ```
- ✅ 역 조회 화면

```
[INFO] 교대역 
[INFO] 강남역
[INFO] 역삼역
[INFO] 남부터미널역
[INFO] 양재역
[INFO] 양재시민의숲역
[INFO] 매봉역
[INFO] 잠실역
```

- ✅ 역 삭제 화면
    - ✅ 출력 1
  ```
  ## 삭제할 역 이름을 입력하세요.
  ```
    - ✅ 입력 : 역 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 존재하지 않는 역 이름일 경우
        - ✅ 노선에 등록된 역일 경우
    - ✅ 출력 2
  ```
  [INFO] 지하철 역이 삭제되었습니다.
  ```
- ✅ 노선 관리 화면

```
## 노선 관리 화면
1. 노선 등록
2. 노선 삭제
3. 노선 조회
B. 돌아가기
```

- ✅ 노선 등록 화면
    - ✅ 출력 1
  ```
  ## 등록할 노선 이름을 입력하세요.
  ```
    - ✅ 입력 1 : 노선 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 노선 이름이 중복될 경우
        - ✅ 노선 이름이 2글자 미만일 경우
    - ✅ 출력 2
  ```
  ## 등록할 노선의 상행 종점역 이름을 입력하세요.
  ```
    - ✅ 입력 2 : 상행 종점역 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 존재하지 않는 역 이름일 경우
    - ✅ 출력 3
  ```
  ## 등록할 노선의 하행 종점역 이름을 입력하세요.
  ```
    - ✅ 입력 3 : 하행 종점역 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 존재하지 않는 역 이름일 경우
        - ✅ 상행역 이름과 동일할 경우
    - ✅ 출력 4
  ```
  [INFO] 지하철 노선이 등록되었습니다.
  ```
- ✅ 노선 삭제 화면
    - ✅ 출력 1
  ```
  ## 삭제할 노선 이름을 입력하세요.
  ```
    - ✅ 입력 : 노선 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 존재하지 않는 노선 이름일 경우
    - ✅ 출력 2
  ```
  [INFO] 지하철 노선이 삭제되었습니다.
  ```
- ✅ 노선 조회 화면

```
[INFO] 2호선
[INFO] 3호선
[INFO] 신분당선
[INFO] 1호선
```

- ✅ 구간 관리 화면

```
## 구간 관리 화면
1. 구간 등록
2. 구간 삭제
B. 돌아가기
```

- ✅ 구간 등록 화면
    - ✅ 출력 1
  ```
  ## 노선을 입력하세요.
  ```
    - ✅ 입력 1 : 노선 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 존재하지 않는 노선 이름일 경우
    - ✅ 출력 2
  ```
  ## 역이름을 입력하세요.
  ```
    - ✅ 입력 2 : 역 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 존재하지 않는 역 이름일 경우
        - ✅ 이미 해당 노선에 존재하는 역 이름일 경우
    - ✅ 출력 3
  ```
  ## 순서를 입력하세요.
  ```
    - ✅ 입력 3 : 순서
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 범위를 벗어날 경우
            - 해당 노선의 역이 `0 - 1 - 2`일 경우 입력될 수 있는 순서는 `1 - 2`
            - `역과 역 사이에 새로운 역이 추가될 수 있다.`
    - ✅ 출력 4
    ```
    [INFO] 구간이 등록되었습니다.
    ```
- ✅ 구간 삭제 화면
    - ✅ 출력 1
  ```
  ## 삭제할 구간의 노선을 입력하세요.
  ```
    - ✅ 입력 1 : 노선 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 존재하지 않는 노선 이름일 경우
        - ✅ 노선에 포함된 역이 2개 이하일 경우
    - ✅ 출력 2
  ```
  ## 삭제할 구간의 역을 입력하세요.
  ```
    - ✅ 입력 2 : 역 이름
        - ✅ null일 경우
        - ✅ 빈 값일 경우(""") || 공백일 경우(" ")
        - ✅ 존재하지 않는 역 이름일 경우
        - ✅ 해당 노선에 존재하지 않는 역 이름일 경우
    - ✅ 출력 3
  ```
  [INFO] 구간이 삭제되었습니다.
  ```
- ✅ 지하철 노선도 출력

```
## 지하철 노선도
[INFO] 2호선
[INFO] ---
[INFO] 교대역
[INFO] 강남역
[INFO] 역삼역

[INFO] 3호선
[INFO] ---
[INFO] 교대역
[INFO] 남부터미널역
[INFO] 양재역
[INFO] 매봉역

[INFO] 신분당선
[INFO] ---
[INFO] 강남역
[INFO] 양재역
[INFO] 양재시민의숲역
```

## 💻프로그래밍 요구사항

- indent depth <= 2
- 3항 연산자 사용 불가
- 함수 내부 15라인 이하
- else 사용 불가
- switch/case 사용 불가
- 파일 수정과 패키지 수정 가능
- 예외 상황 시 에러 문구 출력 : `"[ERROR]""`
- Application 패키지 구조 변경 불가
- Station, Line 클래스 활용
    - 각 클래스의 기본 생성자 추가 불가
    - 필드의 `private` 변경 불가
    - 가능하면 setter 메소드 추가 지양
- StationRepository, LineRepository 활용
    - Station과 Line의 상태 저장
    - 필요 시 추가 Repository 생성 가능
    - 추가로 생성되는 객체에 대해 XXXRepository 네이밍으로 저장 클래스 생성 가능
    - 객체들의 상태를 관리하기 위해 XXXRepository 클래스를 활용해 저장 로직 구현
    - 필요에 따라 수정 가능