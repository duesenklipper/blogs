package com.mycompany;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PageB extends WebPage {
	private static final long serialVersionUID = 1L;

	public PageB(final PageParameters parameters) {
		super(parameters);

		add(new BookmarkablePageLink<>("pageA", PageA.class));

		add(new Label("logout", "Logout") {
			@Override
			protected void onConfigure() {
				if (((MySession) Session.get()).isSignedIn()) {
					setVisible(true);
				} else {
					setVisible(false);
				}
				super.onConfigure();
			}
		});

		setStatelessHint(false);
	}
}
