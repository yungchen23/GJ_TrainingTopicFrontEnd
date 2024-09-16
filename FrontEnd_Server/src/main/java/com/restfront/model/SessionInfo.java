package com.restfront.model;

public class SessionInfo {
	  private String loginAccount;
	    private String employeeName;

	    public SessionInfo(String loginAccount, String employeeName) {
	        this.loginAccount = loginAccount;
	        this.employeeName = employeeName;
	    }

	    // Getters and Setters
	    public String getLoginAccount() {
	        return loginAccount;
	    }

	    public void setLoginAccount(String loginAccount) {
	        this.loginAccount = loginAccount;
	    }

	    public String getEmployeeName() {
	        return employeeName;
	    }

	    public void setEmployeeName(String employeeName) {
	        this.employeeName = employeeName;
	    }
}
