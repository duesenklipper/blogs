package com.mycompany;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends AuthenticatedWebApplication
{
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public void init()
	{
		super.init();

		mountPage("login", LoginPage.class);
		mountPage("home", HomePage.class);
		mountPage("another", AnotherPage.class);

//		getRequestCycleListeners().add(new PageRequestHandlerTracker());
//
//		setSessionStoreProvider(SessionPerTabHttpSessionStore::new);
//
//		getComponentInstantiationListeners().add(component -> {
//			if (component instanceof Page) {
//				component.add(new SessionPerTabBehavior());
//			}
//		});
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return MySession.class;
	}


}
