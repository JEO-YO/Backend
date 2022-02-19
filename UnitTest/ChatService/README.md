# ChatService

### 구현할 기능 목록
MainActivity
- SignInActivity(메인) SignUpActivity(회원가입)
- 간단한 회원가입, 로그인 (firebase auth)
- 로그인 이후 ChatRoomListActivity로 이동

ChatRoomListActivity
- 로그인 시 다음 페이지
- 채팅방 목록 (등록된 유저 전체 목록 중 대화 중인 유저 목록 (email))
- 클릭 시 채팅방으로
- 최근 메시지 순서대로 정렬 (새로고침 버튼 추가)
- 친구목록 버튼 클릭 시 유저리스트 페이지로 이동

ChatRoomActivity
- 실시간 채팅

UserListActivity
- 등록된 유저 전체 목록 (email)
- 버튼 클릭시 채팅 시작 및 ChatRoomListActivity에 등록

### 리팩토링할 목록
- Firebase 기능 분리
