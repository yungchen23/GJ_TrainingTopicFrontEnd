package com.restfront.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfront.model.Member;
import com.restfront.model.MemberRepository;
import com.restfront.model.MemberService;
import com.restfront.model.RegistrationForm;
import com.restfront.model.SessionManager;

import jakarta.servlet.http.HttpSession;

import com.restfront.model.SessionInfo;
import com.restfront.model.LoginForMember;
import com.restfront.model.LoginForMemberRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginForMemberController implements CommandLineRunner {

	@Autowired
	private LoginForMemberRepository dao;

	@Autowired
	private MemberRepository dao2;

	@Autowired
	private MemberService memberService;

	private Map<String, String> sessionStore = new HashMap<>();

	@GetMapping("/all")
	public List<LoginForMember> getAllMember() {
		return dao.findAll();
	}

	@GetMapping("/{id}") // 400
	public Optional<LoginForMember> getMemberId(@PathVariable Integer id) {
		return dao.findById(id);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginForMember loginData) {

		LoginForMember user = dao.findByLoginAccount(loginData.getLogin_account());
		Member member = dao2.findByLoginAccount(loginData.getLogin_account());

		System.out.println("1.user->" + user);
		System.out.println("1.member->" + member);

		if (user != null && user.getLogin_password().equals(loginData.getLogin_password())) {

			if (user.getMember_state()) {
				// 生成一個Session ID
				String sessionId = UUID.randomUUID().toString();

//	                // 創建SessionInfo對象，並存儲到sessionStore中
//	                SessionInfo sessionInfo = new SessionInfo(user.getLogin_account(), emp.getEmployee_name());
//	                sessionStore.put(sessionId, sessionInfo);

				sessionStore.put(sessionId, user.getLogin_account());
//	                sessionStore.put(sessionId, emp.getEmployee_name());
//					
				// 打印所有Session信息
//	                for (Map.Entry<String,SessionInfo> x : sessionStore.entrySet()) {
//						String key = x.getKey();
//						SessionInfo value = x.getValue();
//						System.out.println("key:"+key+"--"+"value:"+value);
//					}

				// 返回Session ID给前端
				return ResponseEntity.ok(Map.of("success", true, "sessionId", sessionId));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(Map.of("success", false, "message", "帳號已停用"));
			}
		} else {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "帳號密碼錯誤"));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerMember(@RequestBody RegistrationForm registrationForm) {
		// 調用 MemberService 進行註冊
		memberService.registerMember(registrationForm);
		return ResponseEntity.ok("註冊成功");
	}

	/**
	 * @PostMapping("/register") public ResponseEntity<String>
	 * registerMember(@RequestBody RegistrationForm registrationForm) { // 創建並保存
	 * LoginForMember LoginForMember loginForMember = new LoginForMember();
	 * loginForMember.setLogin_account(registrationForm.getLogin_account());
	 * loginForMember.setLogin_password(registrationForm.getLogin_password());
	 * loginForMember.setLogin_question(registrationForm.getLogin_question());
	 * loginForMember.setLogin_answer(registrationForm.getLogin_answer());
	 * loginForMember.setMember_state(true); // 設定會員狀態為有效 dao.save(loginForMember);
	 * 
	 * // 先保存 loginForMember，這樣 login_id 才會生成 // LoginForMember savedLoginForMember
	 * = dao.save(loginForMember);
	 * 
	 * // 創建並保存 Member 資訊 Member member = new Member();
	 * member.setMember_phone(registrationForm.getLogin_phone()); //
	 * member.setMember_name(registrationForm.getMember_name()); // 根據表單增加需要的字段
	 * member.setMember_mail(registrationForm.getLogin_account()); // 使用 email 作為
	 * mail member.setLoginformember(loginForMember); // 將 LoginForMember 關聯起來 //
	 * member.setLoginformember(savedLoginForMember); // 將保存後的 LoginForMember 對象與
	 * Member 關聯 dao2.save(member);
	 * 
	 * return ResponseEntity.ok("註冊成功"); }
	 **/

//	@GetMapping("/session")
//	public ResponseEntity<?> checkSession(@RequestHeader("X-Session-Id") String sessionId) {
//		String user = sessionStore.get(sessionId);
//		System.out.println("2.user->" + user);
//		if (user != null) {
//			return ResponseEntity.ok(Map.of("user", user));
//		} else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "用戶未登入"));
//		}
//	}

	@GetMapping("/session")
	public ResponseEntity<?> checkSession(@RequestHeader("X-Session-Id") String sessionId) {
		// 從 sessionStore 中獲取 login_account
		String loginAccount = sessionStore.get(sessionId);
			System.out.println("Generated sessionId: " + sessionId);
			System.out.println("Current sessionStore: " + sessionStore);
				

		// 如果 loginAccount 存在，根據 loginAccount 查詢對應的會員信息
		if (loginAccount != null) {
			Member member = dao2.findByLoginAccount(loginAccount); // 查詢對應的 member
			if (member != null) {
				// 返回會員的相關信息，例如 member_id 和 login_account
				
				System.out.println("_loginAccount-"+loginAccount);
				System.out.println("_member.getMember_id()-"+ member.getMember_id());
				System.out.println("_member.getMember_name()-"+member.getMember_name());
				
				return ResponseEntity.ok(Map.of("user", loginAccount, 
												"member_id", member.getMember_id(), 
												"member_name",	member.getMember_name()));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "找不到會員信息"));
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "用戶未登入"));
		}
	}

	@PostMapping("/check-account")
	public Map<String, Boolean> checkAccount(@RequestBody Map<String, String> request) {
		String loginAccount = request.get("login_account");
		boolean exists = dao.existsByLogin_account(loginAccount);
		return Collections.singletonMap("available", !exists);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("X-Session-Id") String sessionId) {
		// 刪除 session

		sessionStore.remove(sessionId);
		return ResponseEntity.ok(Map.of("message", "登出成功"));
	}

//	
//	@PutMapping("/{account}")
//	public void updateLoginForMember(@PathVariable("account") String account, @RequestBody LoginForMember loge) {
//		LoginForMember l = dao.findAll().stream().filter(l1 -> l1.getLogin_account().equals(account)).findAny()
//				.orElse(null);
//		if (l != null) {
//			System.out.println("update:" + l.toString());
//			l.setLogin_account(loge.getLogin_account());
//			l.setLogin_password(loge.getLogin_password());
//			dao.save(l);
//		} else {
//			System.out.println("Login information not found for account:" + account);
//			// 可以選擇拋出自定義異常或返回適當的錯誤響應
//		}
//	}
//	
////	@PostMapping
////	public void saveLoginForEmployee(@RequestBody LoginForMember loge) {
////		int v = dao.findAll().stream().max(Comparator.comparing(l -> l.getLogin_id())).get().getLogin_id() + 1;
////		loge.setLogin_id(v);
////		System.out.println("save:" + loge.toString());
////		dao.save(loge);
////	}
//	
////	@GetMapping("/session/check")
//	public Map<String, Boolean> checkSession(HttpSession session, SessionManager sessionManager) {
//	    Map<String, Boolean> response = new HashMap<>();
//	    String sessionId = session.getId();
//	    response.put("loggedIn", sessionManager.getUser(sessionId) != null);
//	    return response;
//	}
//	
//	@DeleteMapping("/{account}")
//	public void deleteLoginForMember(@PathVariable("account") String account) {
//		LoginForMember l = dao.findAll().stream().filter(l1 -> l1.getLogin_account().equals(account)).findAny()
//				.orElse(null);
//		if (l != null) {
//			// 取得與 LoginForMember 關聯的 Member
//			Integer memberId = l.getLogin_id();
//			if (memberId != null) {
//				// 刪除 Member
//				Member m = dao2.findAll().stream().filter(e1 -> e1.getMember_id().equals(memberId)).findAny()
//						.orElse(null);
//				if (m != null) {
//					dao2.delete(m);
//					System.out.println("Member deleted: " + m.toString());
//				}
//			}
//			// 刪除 LoginForMember
//			dao.delete(l);
//			System.out.println("LoginForMember deleted: " + l.toString());
//		} else {
//			// 如果未找到 LoginForMember，輸出提示信息
//			System.out.println("Login information not found for account: " + account);
//		}
//	}
//	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		dao.save(new LoginForMember(1, "wang@example.com", "123", "你小時候好朋友的名字?", "AAA", true));
		dao.save(new LoginForMember(2, "li@example.com", "123", "你父母第一次約會的地方?", "BBB", true));
		dao.save(new LoginForMember(3, "chen@example.com", "123", "你小學的校名?", "CCC", true));
		dao.save(new LoginForMember(4, "lin@example.com", "123", "你的偶像的名字?", "AAA", true));
		dao.save(new LoginForMember(5, "huang@example.com", "123", "你第一次寵物是什麼?", "BBB", true));
		dao.save(new LoginForMember(6, "wu@example.com", "123", "你最喜歡的流行歌曲?", "CCC", true));
		dao.save(new LoginForMember(7, "tsai@example.com", "123", "你最喜歡的旅遊景點?", "AAA", true));
		dao.save(new LoginForMember(8, "chang@example.com", "123", "你的初戀是在幾歲?", "BBB", false));
		dao.save(new LoginForMember(9, "linzl@example.com", "123", "你小時候好朋友的名字?", "CCC", true));
		dao.save(new LoginForMember(10, "wuzx@example.com", "123", "你父母第一次約會的地方?", "AAA", true));
		dao.save(new LoginForMember(11, "chengkw@example.com", "123", "你小學的校名?", "BBB", true));
		dao.save(new LoginForMember(12, "chienwb@example.com", "123", "你的偶像的名字?", "CCC", true));
		dao.save(new LoginForMember(13, "tsengyn@example.com", "123", "你第一次寵物是什麼?", "AAA", true));
		dao.save(new LoginForMember(14, "liaowz@example.com", "123", "你最喜歡的流行歌曲?", "BBB", false));
		dao.save(new LoginForMember(15, "chungxq@example.com", "123", "你最喜歡的旅遊景點?", "CCC", true));

		dao2.save(new Member(1, "王大明", "先生", "0912345678", "wang@example.com", "1985-03-21", "台北市"));
		dao2.save(new Member(2, "李小美", "小姐", "0987654321", "li@example.com", "1990-05-15", "新北市"));
		dao2.save(new Member(3, "陳志強", "先生", "0976543210", "chen@example.com", "1978-12-01", "桃園市"));
		dao2.save(new Member(4, "林雅婷", "小姐", "0954321098", "lin@example.com", "1988-08-09", "台中市"));
		dao2.save(new Member(5, "黃宗翰", "先生", "0932109876", "huang@example.com", "1992-02-28", "高雄市"));
		dao2.save(new Member(6, "吳依婷", "小姐", "0921098765", "wu@example.com", "1995-07-19", "台南市"));
		dao2.save(new Member(7, "蔡志成", "先生", "0911098765", "tsai@example.com", "1983-11-23", "基隆市"));
		dao2.save(new Member(8, "張靜宜", "小姐", "0910987654", "chang@example.com", "1987-10-10", "新竹市"));
		dao2.save(new Member(9, "林志玲", "小姐", "0932987654", "linzl@example.com", "1980-04-05", "嘉義市"));
		dao2.save(new Member(10, "吳宗憲", "先生", "0987654321", "wuzx@example.com", "1975-06-15", "台東市"));
		dao2.save(new Member(11, "鄭愷文", "先生", "0912345678", "chengkw@example.com", "1982-09-22", "苗栗縣"));
		dao2.save(new Member(12, "簡文彬", "先生", "0932987654", "chienwb@example.com", "1989-01-30", "屏東縣"));
		dao2.save(new Member(13, "曾雅妮", "小姐", "0954321098", "tsengyn@example.com", "1993-03-14", "宜蘭縣"));
		dao2.save(new Member(14, "廖文中", "先生", "0911098765", "liaowz@example.com", "1981-02-17", "花蓮縣"));
		dao2.save(new Member(15, "鍾小琴", "小姐", "0910987654", "chungxq@example.com", "1990-12-25", "南投縣"));

	}

}
