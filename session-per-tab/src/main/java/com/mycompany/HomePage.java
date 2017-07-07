package com.mycompany;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@AuthorizeInstantiation(UserRoles.SIGNED_USER)
public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Link<Void>("pageB") {

			@Override
			public void onClick() {
				throw new RedirectToNewSessionException(HomePage.class);
			}
		});

		add(new BookmarkablePageLink<>("bookmarkable", HomePage.class));

		add(new AjaxLink<Void>("logout") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				((MySession)getSession()).invalidate();
				setResponsePage(new HomePage(null));
			}
		});

		setStatelessHint(false);
	}
}
