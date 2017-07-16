package com.mycompany;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.IRequestMapperDelegate;
import org.apache.wicket.util.string.StringValue;

public class SessionMultiplexingRequestMapper implements IRequestMapperDelegate {
	static final String WSN_PARAMETER_NAME = "__wsn";
	private final IRequestMapper delegate;

	public SessionMultiplexingRequestMapper(IRequestMapper delegate) {
		this.delegate = delegate;
	}

	@Override
	public int getCompatibilityScore(Request req) {
		return delegate.getCompatibilityScore(req);
	}

	@Override
	public Url mapHandler(IRequestHandler reqH) {
		Url url = delegate.mapHandler(reqH);
		String wicketSessionName = RequestCycle.get().getMetaData(MultiplexingHttpSessionStore.WICKET_SESSION_NAME_KEY);
		if (wicketSessionName != null) {
			if (url.getQueryParameter(WSN_PARAMETER_NAME) == null) {
				url.addQueryParameter(WSN_PARAMETER_NAME, wicketSessionName);
			}
		}
		return url;
	}

	@Override
	public IRequestHandler mapRequest(Request req) {
		StringValue wsnValue = req.getRequestParameters().getParameterValue(WSN_PARAMETER_NAME);
		if (!wsnValue.isEmpty()) {
			RequestCycle.get().setMetaData(MultiplexingHttpSessionStore.WICKET_SESSION_NAME_KEY, wsnValue.toString());
		}
		IRequestHandler handler = delegate.mapRequest(req);
		return handler;
	}

	@Override
	public IRequestMapper getDelegateMapper() {
		return delegate;
	}

}
