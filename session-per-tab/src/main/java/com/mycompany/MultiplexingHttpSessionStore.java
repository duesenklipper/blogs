package com.mycompany;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.session.HttpSessionStore;

/**
 *
 */
public class MultiplexingHttpSessionStore extends HttpSessionStore
{
	public static final MetaDataKey<String> WICKET_SESSION_NAME_KEY = new MetaDataKey<String>()
	{
	};
	public static final MetaDataKey<Long> WICKET_SESSION_LAST_ACCESS_KEY = new MetaDataKey<Long>() {
	};

	private static final String WICKET_SESSIONS_SET_KEY = "WICKET_SESSIONS_SET_KEY";

	@Override
	protected Session getWicketSession(Request request)
	{
		String sessionName = getWicketSessionName(request);
		Session session = (Session) getWicketSessions(request).get(sessionName);
		if (session != null) {
			session.setMetaData(WICKET_SESSION_LAST_ACCESS_KEY, System.currentTimeMillis());
		}
		return session;
	}

	@Override
	protected void setWicketSession(Request request, Session session)
	{
		final String wicketSessionName = getWicketSessionName(request);
		getWicketSessions(request).put(wicketSessionName, session);
	}

	private String getWicketSessionName(final Request request) {
		final RequestCycle cycle = RequestCycle.get();
		String wicketSessionName = cycle.getMetaData(WICKET_SESSION_NAME_KEY);
		if (wicketSessionName == null || wicketSessionName.trim().length() == 0) {
			wicketSessionName = newWicketSessionName();
			cycle.setMetaData(WICKET_SESSION_NAME_KEY, wicketSessionName);
		}
		return wicketSessionName;
	}

	static String newWicketSessionName() {
		return UUID.randomUUID().toString();
	}

	private Map<String, Session> getWicketSessions(Request req) {
		Map<String, Session> wicketSessions = (Map<String, Session>) getAttribute(req, WICKET_SESSIONS_SET_KEY);
		if (wicketSessions == null) {
			wicketSessions = new ConcurrentHashMap<>();
			setAttribute(req, WICKET_SESSIONS_SET_KEY, (Serializable) wicketSessions);
		}
		return wicketSessions;
	}

	void removeInactiveSessions(Request req) {
		long maxInactiveIntervalInMillis = getHttpServletRequest(req).getSession().getMaxInactiveInterval() * 1000;
		long currentTime = System.currentTimeMillis();
		Iterator<Session> wicketSessions = getWicketSessions(req).values().iterator();
		while (wicketSessions.hasNext()) {
			Session ws = wicketSessions.next();
			Long lastAccess = ws.getMetaData(WICKET_SESSION_LAST_ACCESS_KEY);
			if (lastAccess != null) {
				if (currentTime - maxInactiveIntervalInMillis > lastAccess) {
					wicketSessions.remove();
				}
			}
		}
	}
}
