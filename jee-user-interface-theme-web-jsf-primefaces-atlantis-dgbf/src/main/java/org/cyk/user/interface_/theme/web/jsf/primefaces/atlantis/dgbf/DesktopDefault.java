package org.cyk.user.interface_.theme.web.jsf.primefaces.atlantis.dgbf;

import java.io.Serializable;
import java.util.List;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.bean.Property;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.tab.Tab;
import org.cyk.utility.client.controller.component.tab.Tabs;

import ci.gouv.dgbf.sib.menu.generator.MenuGenerator;
import ci.gouv.dgbf.sib.menu.generator.domain.MenuTab;

public class DesktopDefault extends org.cyk.user.interface_.theme.web.jsf.primefaces.atlantis.DesktopDefault implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void ____buildMenu____(Object menuMapKey) {
		if(Boolean.TRUE.equals(DYNAMIC_MENU)) {
			leftMenuTabs = __build__(DependencyInjection.inject(MenuGenerator.class).generateServiceMenu("SIIBC-ACTEUR"));		
			MenuTab topMenuTab = CollectionHelper.getFirst(DependencyInjection.inject(MenuGenerator.class).generateServiceMenu("SIIBC-MYOWNER"));
			if(topMenuTab != null)
				topMenu = topMenuTab.getMenuModel();	
		}else
			super.____buildMenu____(menuMapKey);
		
	}
	
	private Tabs __build__(List<MenuTab> tabs) {
		if(CollectionHelper.isEmpty(tabs))
			return null;
		Tabs __tabs__ = __inject__(Tabs.class);
		for(MenuTab index : tabs) {
			Tab tab = __inject__(Tab.class);
			tab.setProperty(Properties.NAME, index.getTitle());
			tab.setProperty(Properties.ICON, index.getIcon());
			if(index.getMenuModel() != null) {
				Menu menu = __inject__(Menu.class);
				menu.setTargetModel(__inject__(Property.class));
				menu.getTargetModel().setValue(index.getMenuModel());
				menu.getTargetModel().setIsDerived(Boolean.TRUE);
				tab.setProperty(Properties.MENU,menu);
			}
			__tabs__.add(tab);
		}
		return __tabs__;
	}
	
	@Override
	protected String __getIdentifierDefault__() {
		return "org.cyk.user.interface.theme.web.jsf.primefaces.atlantis.dgbf.desktop.default";
	}
	
	public String getSystemName() {
		return SYSTEM_NAME;
	}
	
	public String getSystemLink() {
		return SYSTEM_LINK;
	}
	
	public Boolean getIsShowUserMenu() {
		return IS_SHOW_USER_MENU;
	}
	
	/**/
	
	public static Boolean DYNAMIC_MENU = Boolean.TRUE;
	public static Boolean IS_SHOW_USER_MENU = Boolean.TRUE;
	public static String SYSTEM_NAME = "SIIBC";
	public static String SYSTEM_LINK = "http://10.3.4.20:30300/sib/portail/";
}