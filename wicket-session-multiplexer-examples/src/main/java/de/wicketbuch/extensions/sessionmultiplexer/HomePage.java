/**
 * Copyright (C) 2017 Carl-Eric Menzel <cmenzel@wicketbuch.de>
 * and possibly other session-multiplexer contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.wicketbuch.extensions.sessionmultiplexer;

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
