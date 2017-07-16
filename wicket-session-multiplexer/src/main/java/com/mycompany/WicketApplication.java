package com.mycompany;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestMapper;

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

		mountPage("a", LoginPage.class);
		mountPage("b", HomePage.class);

		setSessionStoreProvider(MultiplexingHttpSessionStore::new);

		getRequestCycleListeners().add(new SessionCleanupRequestCycleListener());

		IRequestMapper original = getRootRequestMapper();
		setRootRequestMapper(new SessionMultiplexingRequestMapper(original));
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
