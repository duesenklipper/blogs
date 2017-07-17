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
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.util.IProvider;

public class SessionMultiplexer
{
	public static void configure(WebApplication app)
	{
		app.setSessionStoreProvider(new IProvider<ISessionStore>()
		{
			@Override
			public ISessionStore get()
			{
				return new MultiplexingHttpSessionStore();
			}
		});

		app.getRequestCycleListeners().add(new SessionCleanupRequestCycleListener());

		IRequestMapper original = app.getRootRequestMapper();
		app.setRootRequestMapper(new SessionMultiplexingRequestMapper(original));
	}

	public static void launchNewSessionWithPage(Class<? extends WebPage> targetPageClass)
	{
		throw new RedirectToNewSessionException(targetPageClass);
	}

	public static void launchNewSessionWithPage(Class<? extends WebPage> targetPageClass, PageParameters params)
	{
		throw new RedirectToNewSessionException(targetPageClass, params);
	}
}
