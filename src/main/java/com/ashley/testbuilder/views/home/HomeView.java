package com.ashley.testbuilder.views.home;

import com.ashley.testbuilder.data.entity.Test;
import com.ashley.testbuilder.service.BuilderApplicationService;
import com.ashley.testbuilder.testRunner.TestRun;
import com.ashley.testbuilder.testRunner.TestRunner;
import com.ashley.testbuilder.views.MainLayout;
import com.ashley.testbuilder.views.builder.BuilderView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends Div implements AfterNavigationObserver {

	private Grid<Test> grid = new Grid<>();
	private BuilderApplicationService service;

	public HomeView(BuilderApplicationService service) {
		this.service = service;
		addClassName("home-view");
		setSizeFull();
		grid.setHeight("100%");
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
		grid.addComponentColumn(test -> createCard(test));
		add(grid);
	}

	private HorizontalLayout createCard(Test test) {

		HorizontalLayout card = new HorizontalLayout();
		card.addClassName("card");
		card.setSpacing(false);
		card.getThemeList().add("spacing-s");

		TextArea nameArea = new TextArea();
		nameArea.setReadOnly(true);
		nameArea.setLabel("Test Version");
		nameArea.setValue(test.getName());

		nameArea.getStyle().set("border-color", "transparent");

		TextArea lastRunDate = new TextArea();
		lastRunDate.setReadOnly(true);
		lastRunDate.setLabel("Last Run Date");
		lastRunDate.setValue("14/07/2023");

		TextArea passRate = new TextArea();
		passRate.setReadOnly(true);
		passRate.setLabel("Pass Rate");
		passRate.setValue("100%");

		MenuBar menuBar = new MenuBar();
		menuBar.setOpenOnHover(true);
		MenuItem actions = menuBar.addItem("Actions");
		SubMenu actionsSubMenu = actions.getSubMenu();
		actionsSubMenu.addItem("Run", new RunTestClickListener(test));
		actionsSubMenu.addItem("Edit", new EditTestClickListener(test));
		actionsSubMenu.addItem("Info");

		card.setWidth("70%");
		card.add(nameArea, lastRunDate, passRate, menuBar);
		return card;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {

		grid.setItems(service.findTests());
	}

	class EditTestClickListener implements ComponentEventListener<ClickEvent<MenuItem>>
	{
		private Test test;
		
		public EditTestClickListener(Test test) {
			this.test = test;
		}
		
		@Override
		public void onComponentEvent(ClickEvent<MenuItem> event) {
			event.getSource().getUI().ifPresent(ui -> ui.navigate(BuilderView.class, String.valueOf(test.getId())));
		}
		
	}
	
	class RunTestClickListener implements ComponentEventListener<ClickEvent<MenuItem>> {
		private Test test;

		public RunTestClickListener(Test test) {
			this.test = test;
		}

		@Override
		public void onComponentEvent(ClickEvent<MenuItem> event) {
			
			TestRun run = new TestRun(test);
			run.addOption("--remote-allow-origins=*");
			
			System.out.println(test.getSteps().toString());
			
			new TestRunner(run).runTest();
		}

	}
}
