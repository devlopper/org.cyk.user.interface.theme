package org.cyk.user.interface_.theme.web.jsf.primefaces.adminfaces;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeColumnPanel;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.window.Window;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractThemeImpl;
import org.cyk.utility.scope.ScopeSession;

import lombok.Getter;

public class DesktopDefaultImpl extends AbstractThemeImpl implements DesktopDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter private Menu menu;
	
	@Override
	public Theme build() {
		Theme theme = super.build();
		Object request = getRequest();
		__addTagLinkResource__("stylesheet", "text/css", request, "starter.css","css");
		return theme;
	}
	
	@Override
	protected String __getIdentifier__() {
		return "org.cyk.user.interface.theme.web.jsf.primefaces.adminfaces.desktop.default";
	}
	
	@Override
	protected String __getTemplateIdentifier__() {
		//return "/template/default.xhtml";
		return "/templates/template.xhtml";
	}
	
	@Override
	public Theme process(Window window) {
		menu = window.getMenu(ScopeSession.class);
		menu.setRenderType(__inject__(MenuRenderTypeColumnPanel.class));
		__north__(window);
		__center__(window);
		__south__(window);
		return this;
	}
	
	private void __north__(Window window) {
		
	}
	
	private void __center__(Window window) {
		mapViews("center",window.getView());
	}

	private void __south__(Window window) {
		
	}
	
}
