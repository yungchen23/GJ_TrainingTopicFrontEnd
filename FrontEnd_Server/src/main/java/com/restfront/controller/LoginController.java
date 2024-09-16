package com.restfront.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfront.model.LoginForMember;
import com.restfront.model.LoginForMemberRepository;

import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController
@RequestMapping("/api3")
public class LoginController {

    @Autowired
    private LoginForMemberRepository loginRepository;
    
    

    private Map<String, String> sessionStore = new ConcurrentHashMap<>();

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginForMember loginData) {
//        LoginForMember user = loginRepository.findByLoginAccount(loginData.getLogin_account());
//
//        if (user != null && user.getLogin_password().equals(loginData.getLogin_password())) {
//            if (user.getMember_state()) {
//                String sessionId = UUID.randomUUID().toString();
//                sessionStore.put(sessionId, user.getLogin_account());
//                System.out.println("ssid => " + sessionId);
//                return ResponseEntity.ok(Map.of("success", true, "sessionId", sessionId));
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("success", false, "message", "帳號已停用"));
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("success", false, "message", "帳號或密碼錯誤"));
//        }
//    }

//    @GetMapping("/session")
//    public ResponseEntity<?> checkSession(@RequestHeader("X-Session-Id") String sessionId) {
//        String user = sessionStore.get(sessionId);
//        if (user != null) {
//            return ResponseEntity.ok(Map.of("user", user));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("message", "用戶未登入"));
//        }
//    }


//    
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("X-Session-Id") String sessionId) {
//        sessionStore.remove(sessionId);
//        return ResponseEntity.ok(Map.of("message", "登出成功"));
//    }
//    
//    @GetMapping("/session/check")
//    public ResponseEntity<Map<String, Boolean>> checkSession(HttpSession session) {
//        Map<String, Boolean> response = new HashMap<>();
//        String sessionId = session.getId();
//        
//        // 假设 SessionManager 中有一个方法 getUserFromSessionId(String sessionId)
//        String user = sessionManager.getUserFromSessionId(sessionId);
//        
//        response.put("loggedIn", user != null);
//        return ResponseEntity.ok(response);
//    }
 
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForMember loginData, HttpSession session) {
        LoginForMember user = loginRepository.findByLoginAccount(loginData.getLogin_account());

        if (user != null && user.getLogin_password().equals(loginData.getLogin_password())) {
            if (user.getMember_state()) {
                session.setAttribute("user", user.getLogin_account());
                return ResponseEntity.ok(Map.of("success", true));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("success", false, "message", "帳號已停用"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "帳號或密碼錯誤"));
        }
    }

//    @GetMapping("/session")
//    public ResponseEntity<?> checkSession(@RequestHeader("X-Session-Id") String sessionId) {
//        if (sessionId == null || sessionId.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("message", "Session ID is required"));
//        }
//
//        String user = sessionStore.get(sessionId);
//        if (user != null) {
//            return ResponseEntity.ok(Map.of("user", user));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("message", "用戶未登入"));
//        }
//    }
    
    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> checkSession1(HttpSession session) {
        String user = (String) session.getAttribute("user");

        if (user != null) {
            return ResponseEntity.ok(Map.of("user", user, "loggedIn", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "用戶未登入", "loggedIn", false));
        }
    }

    
    @GetMapping("/session/check")
    public ResponseEntity<Map<String, Boolean>> checkSession(HttpSession session) {
        Map<String, Boolean> response = new HashMap<>();
        String user = (String) session.getAttribute("user");
        response.put("loggedIn", user != null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "登出成功"));
    }
    
}