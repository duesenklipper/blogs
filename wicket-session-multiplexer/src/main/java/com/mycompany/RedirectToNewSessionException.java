package com.mycompany;

import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class RedirectToNewSessionException extends RestartResponseException {

	public <C extends Page> RedirectToNewSessionException(Class<C> pageClass) {
		this(pageClass, new PageParameters());
	}

	public <C extends Page> RedirectToNewSessionException(Class<C> pageClass, PageParameters params) {
		super(pageClass, params.add(SessionMultiplexingRequestMapper.WSN_PARAMETER_NAME,
				MultiplexingHttpSessionStore.newWicketSessionName()));
	}
}
