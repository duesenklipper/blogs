package com.mycompany;

import org.apache.wicket.Application;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.session.ISessionStore;

public class SessionCleanupRequestCycleListener extends AbstractRequestCycleListener {
	@Override
	public void onEndRequest(RequestCycle cycle) {
		ISessionStore store = Application.get().getSessionStore();
		if (store instanceof MultiplexingHttpSessionStore) {
			((MultiplexingHttpSessionStore) store).removeInactiveSessions(cycle.getRequest());
		}
	}
}
