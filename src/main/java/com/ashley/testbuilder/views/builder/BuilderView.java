package com.ashley.testbuilder.views.builder;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

import com.ashley.testbuilder.data.entity.Action;
import com.ashley.testbuilder.data.entity.Step;
import com.ashley.testbuilder.data.entity.Test;
import com.ashley.testbuilder.service.BuilderApplicationService;
import com.ashley.testbuilder.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Builder")
@Route(value = "builder", layout = MainLayout.class)
public class BuilderView extends VerticalLayout implements AfterNavigationObserver, HasUrlParameter<String>{

	private Grid<Step> grid = new Grid<>();
	private Dialog addNewStepDialog = createDialog();
	private Test test;
	private BuilderApplicationService service;
	
	public BuilderView(BuilderApplicationService service) {
		this.service = service;
		test = new Test("Builder Test");
		addClassName("home-view");
		setWidth("100%");
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
		grid.addComponentColumn(step -> createStepCard(step));
		add(new Button(new Icon(VaadinIcon.PLUS), e -> addNewStepDialog.open()));
		add(new Button(new Icon(VaadinIcon.DISC), new SaveTestEvent(service, test)));
		add(grid);
	}

	private HorizontalLayout createStepCard(Step step) {
		HorizontalLayout card = new HorizontalLayout();
		card.addClassName("card");
		card.setSpacing(false);
		card.getThemeList().add("spacing-s");
		
		TextArea actionLabel = new TextArea();
		actionLabel.setReadOnly(true);
		actionLabel.setLabel("Action");
		actionLabel.setValue(step.getAction().name());
		card.add(actionLabel);
		
		if (step.getLocator() != null) {
			TextArea locatorLabel = new TextArea();
			locatorLabel.setReadOnly(true);
			locatorLabel.setLabel("Locator");
			locatorLabel.setValue(step.getLocator());
			card.add(locatorLabel);
		}
		if (step.getDestination() != null) {
			TextArea destinationLabel = new TextArea();
			destinationLabel.setReadOnly(true);
			destinationLabel.setLabel("Destination");
			destinationLabel.setValue(step.getDestination());
			card.add(destinationLabel);
		}
		if (step.getInput() != null) {
			TextArea inputLabel = new TextArea();
			inputLabel.setReadOnly(true);
			inputLabel.setLabel("Input");
			inputLabel.setValue(step.getInput());
			card.add(inputLabel);
		}
		
		return card;
	}
	
	private Dialog createDialog()
	{
		Dialog dialog = new Dialog();
		dialog.setHeaderTitle("Add Test Step");
		
		VerticalLayout dialogLayout = new VerticalLayout();
		dialog.add(dialogLayout);
		Select<Action> actionSelect = new Select<Action>();
		actionSelect.setLabel("Action");
		actionSelect.setItems(Action.values());
		
		TextArea locator = new TextArea();
		locator.setLabel("Locator");
		
		TextArea input = new TextArea();
		input.setLabel("Input");
		
		Button saveButton = new Button("Add", new AddNewStepEvent(actionSelect, locator, input));
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Button cancelButton = new Button("Cancel", e -> dialog.close());
		
		dialog.getFooter().add(cancelButton, saveButton);
		dialogLayout.add(actionSelect, locator, input);
		return dialog;
	}
	
	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		
		if (!test.getSteps().isEmpty()) {
			grid.setItems(test.getSteps());
		}
	}
	
	class AddNewStepEvent implements ComponentEventListener<ClickEvent<Button>>
	{
		Map<String, Component> dialogMap = new HashMap<>();
		
		AddNewStepEvent(Select<Action> actionSelect, TextArea locator, TextArea input) {
			dialogMap.put("action", actionSelect);
			dialogMap.put("locator", locator);
			dialogMap.put("input", input);
		}
		
		@Override
		public void onComponentEvent(ClickEvent<Button> event) {
			Step newStep = createStepfromDialog();
			test.addStep(newStep);
			
			System.out.println("Step Added " + newStep.toString());
			
			grid.removeAllColumns();
			grid.setItems(test.getSteps());
			grid.addComponentColumn(step -> createStepCard(step));
			addNewStepDialog.close();
		}
		
		private Step createStepfromDialog()
		{
			Step newStep = new Step(test.getSteps().size()-1);
			
			Select<Action> action = (Select<Action>) dialogMap.get("action");
			String location = ((TextArea) dialogMap.get("locator")).getValue();
			String input = ((TextArea) dialogMap.get("input")).getValue();
			
			switch (action.getValue()) {
			case CLICK:
				newStep = newStep.clickStep(location);
				break;
			case INPUT:
				newStep = newStep.inputStep(location, input);
				break;
			case NAVIGATE:
				newStep = newStep.navigateStep(location);
				break;
			default:
				break;
			}
			
			return newStep;
		}
	}
	
	class SaveTestEvent implements ComponentEventListener<ClickEvent<Button>>
	{
		private BuilderApplicationService service;
		private Test test;
		
		public SaveTestEvent(BuilderApplicationService service, Test test) {
			this.service = service;
			this.test = test;
		}

		@Override
		public void onComponentEvent(ClickEvent<Button> event) {
			service.saveTest(test);
		}
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
		if(parameter != null)
		{
			test = service.findTestById(Long.parseLong(parameter));
		}
	}
}
