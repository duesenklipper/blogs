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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;

public class HomePage extends WebPage {

	public HomePage() {
		super();
		this.add(new Label("output", new AbstractReadOnlyModel<String>()
		{
			@Override
			public String getObject()
			{
				return MySession.get().getMyValue();
			}
		}));
		Form<Void> form = new Form<Void>("form");
		this.add(form);
		form.add(new TextField<String>("input", new Model<String>() {
			@Override
			public String getObject()
			{
				return MySession.get().getMyValue();
			}

			@Override
			public void setObject(String object)
			{
				MySession.get().setMyValue(object);
			}
		}));
		this.add(new Link<Void>("newSession")
		{
			@Override
			public void onClick()
			{
				SessionMultiplexer.launchNewSessionWithPage(HomePage.class);
			}
		});
	}
}
