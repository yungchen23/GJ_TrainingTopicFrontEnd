package com.restfront.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class SessionManager {

    private Map<String, String> sessionStore = new ConcurrentHashMap<>();

    public void createSession(String sessionId, String userAccount) {
        sessionStore.put(sessionId, userAccount);
    }

    public String getUserFromSessionId(String sessionId) {
        return sessionStore.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessionStore.remove(sessionId);
    }
    
    public String getUser(String sessionId) {
        return sessionStore.get(sessionId);
    }

}