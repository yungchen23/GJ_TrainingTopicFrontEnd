package com.restfront.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfront.model.LoginForMember;
import com.restfront.model.LoginForMemberRepository;
import com.restfront.model.Member;
import com.restfront.model.MemberRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/member")
public class MemberController implements CommandLineRunner {

	@Autowired
	private MemberRepository dao;

	@Autowired
	private LoginForMemberRepository dao2;

	@GetMapping
	public List<Member> getAllMember() {
		return dao.findAll();
	}

//	@GetMapping("/{id}")
//	public Member getById(@PathVariable("id") Integer id, @RequestBody Member mem) {
//		Member m = dao.findAll().stream().filter(m1 -> m1.getMember_id() == id).findAny().orElse(null);
//		System.out.println("update:" + m.toString());
//		m.setMember_name(mem.getMember_name());
//        m.setMember_address(mem.getMember_address());
//        m.setMember_birth(mem.getMember_birth());
//        m.setMember_mail(mem.getMember_mail());
//        m.setMember_phone(mem.getMember_phone());
//        m.setMember_sex(mem.getMember_sex());
//	    return dao.findById(id).orElse(null);
//	}

	// 查詢個人數據
	@GetMapping("/{loginAccount}")
	public Member getById(@PathVariable String loginAccount) {
		return dao.findByLoginAccount(loginAccount);
	}

	// 修改個人數據
	@PutMapping("/{loginAccount}")
	public Member updateMemberByAccount(@PathVariable String loginAccount, @RequestBody Member updatedMember) {
		Member member = dao.findByLoginAccount(loginAccount);
		if (member != null) {
			member.setMember_name(updatedMember.getMember_name());
			member.setMember_sex(updatedMember.getMember_sex());
			member.setMember_birth(updatedMember.getMember_birth());
			member.setMember_phone(updatedMember.getMember_phone());
			member.setMember_mail(updatedMember.getMember_mail());
			member.setMember_address(updatedMember.getMember_address());
			dao.save(member);
			return member; // 返回更新後的會員資料
		} else {
			return null; // 如果會員不存在，返回 null
		}
	}

//	驗證原密碼 API
	@PostMapping("/verifyPassword")
	public boolean validatePassword(@RequestBody Map<String, String> requestData) {
		String loginAccount = requestData.get("loginAccount");
		String oldPassword = requestData.get("oldPassword");
		System.out.println("loginAccount--" + loginAccount);
		System.out.println("oldPassword--" + oldPassword);

		// 檢查原密碼是否正確
		LoginForMember user = dao2.findByLoginAccount(loginAccount);
		System.out.println("驗證密碼user--" + user);
		if (user != null && user.getLogin_password().equals(oldPassword)) {
			return true; // 原密碼正確
		} else {
			return false; // 原密碼錯誤
		}
	}

	@PutMapping("/changePassword")
	public String changePassword(@RequestBody Map<String, String> requestData) {
		String loginAccount = requestData.get("loginAccount");
		String newPassword = requestData.get("newPassword");

		 System.out.println("Received login account: " + loginAccount);  // 打印帳號
		System.out.println("Received new password: " + newPassword);  // 打印新密碼
		
		
		// 更新用戶密碼
		LoginForMember user = dao2.findByLoginAccount(loginAccount);
		if (user != null) {
			user.setLogin_password(newPassword);
			dao2.save(user);
			return "密碼更新成功"; // 更新成功
		} else {
			return "帳號不存在"; // 如果帳號不存在
		}
	}

	@PostMapping
	public void saveMember(@RequestBody Member mem) {
		int v = dao.findAll().stream().max(Comparator.comparing(m -> m.getMember_id())).get().getMember_id() + 1;
		mem.setMember_id(v);
		System.out.println("save:" + mem.toString());
		dao.save(mem);
	}

//	public void deleteMember(@PathVariable("id") Integer id) {
//		Member m = dao.findAll().stream().filter(m1 -> m1.getMember_id() == id).findAny().orElse(null);
//		System.out.println("delete:" + m.toString());
//		if (m != null)
//			dao.delete(m);
//	}

	// 驗證帳號與手機號碼
	@PostMapping("/verify-account")
	public ResponseEntity<?> verifyAccount(@RequestBody Map<String, String> request) {
		String loginAccount = request.get("login_account");
		String phone = request.get("phone");
		Member member = dao.findByLoginAccount(loginAccount);

		if (member != null && member.getMember_phone().equals(phone)) {
			return ResponseEntity.ok(Map.of("success", true));
		} else {
			return ResponseEntity.ok(Map.of("success", false, "message", "帳號或手機號碼不正確"));
		}
	}

	// 驗證安全提示問題的答案
	@PostMapping("/verify-security-answer")
	public ResponseEntity<?> verifySecurityAnswer(@RequestBody Map<String, String> request) {
		String loginAccount = request.get("login_account");
		String securityAnswer = request.get("security_answer");

		LoginForMember loginForMember = dao2.findByLoginAccount(loginAccount);
		if (loginForMember != null && loginForMember.getLogin_answer().equals(securityAnswer)) {
			return ResponseEntity.ok(Map.of("success", true));
		} else {
			return ResponseEntity.ok(Map.of("success", false, "message", "提示問題答案不正確"));
		}
	}

	@GetMapping("/security-question/{loginAccount}")
	public ResponseEntity<?> getSecurityQuestion(@PathVariable String loginAccount) {
		LoginForMember user = dao2.findByLoginAccount(loginAccount);
		if (user != null) {
			return ResponseEntity.ok(Map.of("login_question", user.getLogin_question()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "帳號不存在"));
		}
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		dao.save(new Member(100, "John", "先生", "0912345678" , "john@com.tw", "1990-01-01", "台北市中正區"));
//		dao.save(new Member(101, "Mary", "小姐", "0922345678" , "mary@com.tw", "1990-02-02", "桃園市桃園區"));
//
//		dao.save(new Member(1, "王大明", "先生", "0912345678", "wang@example.com", "1985-03-21", "台北市"));
//		dao.save(new Member(2, "李小美", "小姐", "0987654321", "li@example.com", "1990-05-15", "新北市"));
//		dao.save(new Member(3, "陳志強", "先生", "0976543210", "chen@example.com", "1978-12-01", "桃園市"));
//		dao.save(new Member(4, "林雅婷", "小姐", "0954321098", "lin@example.com", "1988-08-09", "台中市"));
//		dao.save(new Member(5, "黃宗翰", "先生", "0932109876", "huang@example.com", "1992-02-28", "高雄市"));
//		dao.save(new Member(6, "吳依婷", "小姐", "0921098765", "wu@example.com", "1995-07-19", "台南市"));
//		dao.save(new Member(7, "蔡志成", "先生", "0911098765", "tsai@example.com", "1983-11-23", "基隆市"));
//		dao.save(new Member(8, "張靜宜", "小姐", "0910987654", "chang@example.com", "1987-10-10", "新竹市"));
//		dao.save(new Member(9, "林志玲", "小姐", "0932987654", "linzl@example.com", "1980-04-05", "嘉義市"));
//		dao.save(new Member(10, "吳宗憲", "先生", "0987654321", "wuzx@example.com", "1975-06-15", "台東市"));
//		dao.save(new Member(11, "鄭愷文", "先生", "0912345678", "chengkw@example.com", "1982-09-22", "苗栗縣"));
//		dao.save(new Member(12, "簡文彬", "先生", "0932987654", "chienwb@example.com", "1989-01-30", "屏東縣"));
//		dao.save(new Member(13, "曾雅妮", "小姐", "0954321098", "tsengyn@example.com", "1993-03-14", "宜蘭縣"));
//		dao.save(new Member(14, "廖文中", "先生", "0911098765", "liaowz@example.com", "1981-02-17", "花蓮縣"));
//		dao.save(new Member(15, "鍾小琴", "小姐", "0910987654", "chungxq@example.com", "1990-12-25", "南投縣"));
	}

}
