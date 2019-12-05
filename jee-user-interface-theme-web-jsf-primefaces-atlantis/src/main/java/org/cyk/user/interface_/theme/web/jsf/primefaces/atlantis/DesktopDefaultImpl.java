package org.cyk.user.interface_.theme.web.jsf.primefaces.atlantis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.bean.Property;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderGetter;
import org.cyk.utility.client.controller.component.tab.Tab;
import org.cyk.utility.client.controller.component.tab.Tabs;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.window.Window;
import org.cyk.utility.client.controller.tag.Tag;
import org.cyk.utility.client.controller.tag.TagForm;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractThemeImpl;
import org.cyk.utility.scope.ScopeSession;
import org.primefaces.model.menu.MenuModel;

import ci.gouv.dgbf.sib.menu.generator.MenuGenerator;
import ci.gouv.dgbf.sib.menu.generator.domain.MenuTab;
import lombok.Getter;

public class DesktopDefaultImpl extends AbstractThemeImpl implements DesktopDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter private Tabs leftMenuTabs;
	
	@Getter private Map<Object,Tabs> menuMapKeys = new HashMap<>();
	
	@Getter private MenuModel topMenu;
	
	@Override
	public Theme build() {
		Theme theme = super.build();
		Object request = getRequest();
		
		__addTagLinkStyleSheet__("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css");
		
		__addTagScriptResource__(request, "fr_FR.js","js");
		__addTagScriptResource__(request, "nanoscroller.js","js");
		__addTagScriptResource__(request, "layout.js","js");
		__addTagScriptResource__(request, "common.js","js");
		
		__addTagLinkResourceStyleSheet__(request, "nanoscroller.css","css");
		__addTagLinkResourceStyleSheet__(request, "animate.css","css");
		__addTagLinkResourceStyleSheet__(request, "main.css","css");
		
		if(color == null)
			color = StringUtils.substringAfter(ConfigurationHelper.getValueAsString(VariableName.USER_INTERFACE_THEME_PRIMEFACES), "atlantis-");
		__addTagLinkResourceStyleSheet__(request, "layout-"+color+".css","css");
		
		Tag tag = __inject__(TagForm.class);
		tag.setIdentifier(__inject__(ComponentHelper.class).getGlobalFormComponentIdentifier());
		tag.getProperties().setEnctype("multipart/form-data");
		mapTags("content.form",tag);
		
		tag = __inject__(TagForm.class);
		tag.setIdentifier("menu-form");
		mapTags("menu.form",tag);
		return theme;
	}
	
	@Override
	protected void __buildMenu__(Object menuMapKey) {
		leftMenuTabs = menuMapKeys.get(menuMapKey);
		if(leftMenuTabs != null)
			return;			
		/*
		Object request = getRequest();
		MenuBuilder menuBuilder = MenuBuilderGetter.getInstance().get(menuMapKey,ScopeSession.class, request);
		if(menuBuilder == null)
			return;
		//TODO reduce build time to maximum 1 second
		*/
		leftMenuTabs = __build__(DependencyInjection.inject(MenuGenerator.class).generateServiceMenu("SIIBC-ACTEUR"));		
		MenuTab topMenuTab = CollectionHelper.getFirst(DependencyInjection.inject(MenuGenerator.class).generateServiceMenu("SIIBC-MYOWNER"));
		if(topMenuTab != null)
			topMenu = topMenuTab.getMenuModel();
		
		//List<MenuTab> accountListMenu = new ArrayList<>();
		/*
		MenuItemBuilders oldMenuItemBuilders = menuBuilder.getItems();
		if(oldMenuItemBuilders != null) {
			for(MenuItemBuilder index : oldMenuItemBuilders.get()) {
				MenuItemBuilders items = null;
				if(index.getChildren()!=null) {
					items = __inject__(MenuItemBuilders.class);
					for(Object indexChild : index.getChildren())
						if(indexChild instanceof MenuItemBuilder)
							items.add((MenuItemBuilder)indexChild);
					index.getChildren().clear();
				}
				if(index.getRequest() == null)
					index.setRequest(request);
				MenuItem item = index.execute().getOutput();
				
				Tab tab = __inject__(Tab.class);
				tab.setProperty(Properties.NAME, item.getCommandable().getName());
				tab.setProperty(Properties.ICON, item.getCommandable().getProperties().getIcon());
				if(items != null)
					tab.setProperty(Properties.MENU,__inject__(MenuBuilder.class).setItems(items).setRequest(request).execute().getOutput());
				menuTabs.add(tab);
			}
		}
		*/
		menuMapKeys.put(menuMapKey, leftMenuTabs);
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
	protected String __getIdentifier__() {
		return ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.USER_INTERFACE_THEME_JSF_CONTRACT)
				,"org.cyk.user.interface.theme.web.jsf.primefaces.atlantis.desktop.default");
	}
	
	@Override
	protected String __getTemplateIdentifier__() {
		return "/template/default.xhtml";
	}
	
	@Override
	public Theme process(Window window) {
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
	
	@Override
	public Tabs getMenuTabs() {
		return leftMenuTabs;
	}
	
	@Override
	public Tabs getMenuTabs(Boolean injectIfNull) {
		if(leftMenuTabs == null && Boolean.TRUE.equals(injectIfNull))
			leftMenuTabs = __inject__(Tabs.class);
		return leftMenuTabs;
	}
	
	@Override
	public DesktopDefault setMenuTabs(Tabs menuTabs) {
		this.leftMenuTabs = menuTabs;
		return this;
	}

	public Collection<String[]> getUserMenuUlLis() {
		return USER_MENU_UL_LIS;
	}
	
	public String getRoles() {
		return "ROLES";
	}
	
	/**/
	
	public static final String FIELD_MENU_TABS = "menuTabs";
	
	public static final Collection<String[]> USER_MENU_UL_LIS = new ArrayList<>();
	public static void addUserMenuUlLi(String name,String url,String icon) {
		USER_MENU_UL_LIS.add(new String[] {name,url,icon});
	}
	static {
		UniformResourceIdentifierAsFunctionParameter parameter;
		
		parameter = new UniformResourceIdentifierAsFunctionParameter();
		parameter.getPath(Boolean.TRUE).setIdentifier("logoutView");		
		addUserMenuUlLi("Me déconnecter", UniformResourceIdentifierHelper.build(parameter),"fa fa-sign-out");
		
		addUserMenuUlLi("Modifier mon mot de passe", null,"fa fa-key");
		addUserMenuUlLi("Modifier mon profile", null,"fa fa-user");
		addUserMenuUlLi("Mes préférences", null,"fa fa-cog");
		addUserMenuUlLi("Mes visibilités", null,"fa fa-eye");
		addUserMenuUlLi("Demandes de compte utilisateur", null,"fa fa-file-text");
		addUserMenuUlLi("M'abonner à une publication", null,"fa fa-comments");
	}
}
