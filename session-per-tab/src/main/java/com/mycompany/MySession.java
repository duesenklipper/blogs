package com.mycompany;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class MySession extends AuthenticatedWebSession {

	public MySession(Request request) {
		super(request);
	}

	@Override
	protected boolean authenticate(String userEMail, String password) {
		return true;
	}

	@Override
	public Roles getRoles() {
		Roles userRoles = new Roles();
		return userRoles;
	}

}
