package com.mycompany;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@AuthorizeInstantiation(UserRoles.SIGNED_USER)
public class AnotherPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public AnotherPage(final PageParameters parameters) {
		super(parameters);

		add(new AjaxLink<Void>("logout") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				((MySession)getSession()).invalidate();
				setResponsePage(new LoginPage(null));
			}
		});
		
		add(new Label("signedIn", Model.of(((MySession)getSession()).isSignedIn())));

		setStatelessHint(false);
	}
}
