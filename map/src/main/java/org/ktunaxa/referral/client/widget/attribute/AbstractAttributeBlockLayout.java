/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.widget.attribute;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.OneToManyAttribute;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.widget.utility.smartgwt.client.widget.CardLayout;
import org.ktunaxa.referral.client.gui.LayoutConstant;

/**
 * Layout for editing one-to-many attributes, consisting of a list view ({@link AttributeBlockList}) and a detail view (
 * {@link AttributeBlockDetail}). The user can switch back-and-forth to the detail view for editing a single one-to-many
 * value.
 * 
 * @param <W>
 *
 * @author Jan De Moerloose
 */
public abstract class AbstractAttributeBlockLayout<W extends Widget> extends VLayout {

	/**
	 * Card type enumeration
	 * 
	 * @author Jan De Moerloose
	 */
	enum Card {
		LIST, DETAIL
	}

	private AttributeBlockList listView;

	private AttributeBlockDetail<W> detailView;

	private CardLayout<Card> cardLayout;

	private HandlerManager manager = new HandlerManager(this);
	
	private AbstractAttributeBlock currentBlock;
	
	private HandlerRegistration saveRegistration;
	
	private HandlerRegistration cancelRegistration;
	

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	/**
	 * Build the entire GUI, starting from a given ListView and a DetailView. This constructor will automatically attach
	 * the needed click handlers to the correct buttons to switch from one view to the other.
	 * 
	 * @param list list of attribute blocks
	 * @param form form for attribute
	 */
	public AbstractAttributeBlockLayout(AttributeBlockList list, FeatureForm<W> form) {
		super(LayoutConstant.MARGIN_LARGE);
		setWidth100();
		listView = list;
		detailView = new AttributeBlockDetail<W>(form);
		detailView.setVisible(false);
		cardLayout = new CardLayout<Card>();
		cardLayout.setWidth100();
		cardLayout.setHeight(1);
		cardLayout.addCard(Card.LIST, listView);
		cardLayout.addCard(Card.DETAIL, detailView);
		cardLayout.showCard(Card.LIST);
		addMember(cardLayout);

		listView.getCreateNewButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				currentBlock = null;
				showDetails(newInstance());
			}
		});
		
		setCancelAction(new CancelAction());
		setSaveAction(new SaveAction());
	}

	public HandlerRegistration addChangedHandler(ChangedHandler handler) {
		return manager.addHandler(ChangedEvent.getType(), handler);
	}

	public void toLayout(OneToManyAttribute attribute) {
		List<AbstractAttributeBlock> list = new ArrayList<AbstractAttributeBlock>();
		for (final AssociationValue value : attribute.getValue()) {
			AbstractAttributeBlock block = newBlock(value);
			initBlock(block);
			list.add(block);
			if (currentBlock != null && currentBlock.valueEquals(block)) {
				currentBlock = block;
				showDetails(block.getValue());
			}
		}
		listView.populate(list);
	}

	public void fromLayout(OneToManyAttribute attribute) {
		List<AssociationValue> list = new ArrayList<AssociationValue>();
		for (AbstractAttributeBlock block : listView.getBlocks()) {
			list.add(block.getValue());
		}
		attribute.setValue(list);
	}
	
	
	public void setSaveAction(ClickHandler saveAction) {
		if (saveRegistration != null) {
			saveRegistration.removeHandler();
		}
		saveRegistration = detailView.getSaveButton().addClickHandler(saveAction);
	}

	public void setCancelAction(ClickHandler cancelAction) {
		if (cancelRegistration != null) {
			cancelRegistration.removeHandler();
		}
		cancelRegistration = detailView.getBackButton().addClickHandler(cancelAction);
	}
	
	public boolean isNewBlock() {
		return currentBlock == null;
	}
	
	public void addToList() {
		if (detailView.validate()) {
			detailView.fromForm();
			// if the block is new add it to the list
			if (isNewBlock()) {
				currentBlock = newBlock(detailView.getValue());
				initBlock(currentBlock);
				listView.addBlock(currentBlock);
			}
			// the association value has changed, refresh the blocks
			listView.updateBlocks();
			// we've changed, let the handlers know
			manager.fireEvent(new ChangedEvent(null));
			// go back to list
			showList();			
		}
	}

	/**
	 * Creates a new instance of the association value to edit.
	 * 
	 * @return a new value
	 */
	protected abstract AssociationValue newInstance();

	/**
	 * Creates a new block with the specified value.
	 *
	 * @param value value for new block
	 * @return a new block
	 */
	protected abstract AbstractAttributeBlock newBlock(AssociationValue value);

	// ------------------------------------------------------------------------
	// Public methods:
	// ------------------------------------------------------------------------


	private void initBlock(final AbstractAttributeBlock block) {
		block.addEditHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				currentBlock = block;
				showDetails(block.getValue());
			}
		});
		block.addDeleteHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				SC.confirm(block.getDeleteMessage(), new BooleanCallback() {
					
					public void execute(Boolean yes) {
						if (yes) {
							listView.removeBlock(block);
							// we've changed, let the handlers know
							manager.fireEvent(new ChangedEvent(null));
						} else {
							// nothing
						}
					}
				});
			}
		});
	}

	public void showList() {
		currentBlock = null;
		cardLayout.showCard(Card.LIST);
	}

	public void showDetails(AssociationValue value) {
		detailView.showDetails(value);
		cardLayout.showCard(Card.DETAIL);
	}
	
	/**
	 * Default save action.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class SaveAction implements ClickHandler {

		public void onClick(ClickEvent event) {
			addToList();
		}

	}

	/**
	 * Default cancel action.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class CancelAction implements ClickHandler {

		public void onClick(ClickEvent event) {
			showList();
		}

	}

}