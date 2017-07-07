package com.mycompany;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LoginPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public LoginPage(final PageParameters parameters) {
		super(parameters);

		add(new BookmarkablePageLink<>("pageB", HomePage.class));

		AjaxLink<Void> loginLink = new AjaxLink<Void>("login") {
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				((MySession)getSession()).signIn("onlyFor", "testing");
				setResponsePage(new HomePage(null));
			}
		};
		
		add(loginLink);
		
		setStatelessHint(false);
    }
}
