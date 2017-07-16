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
