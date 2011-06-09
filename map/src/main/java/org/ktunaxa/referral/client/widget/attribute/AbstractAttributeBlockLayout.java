/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.widget.attribute;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.gwt.client.widget.CardLayout;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.OneToManyAttribute;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

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

	private CardLayout cardLayout;

	private HandlerManager manager = new HandlerManager(this);
	
	private AbstractAttributeBlock currentBlock;

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
		super(10);
		listView = list;
		detailView = new AttributeBlockDetail<W>(form);
		setWidth100();
		setPadding(10);
		detailView.setVisible(false);
		cardLayout = new CardLayout();
		cardLayout.setWidth100();
		// default of 100
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
		detailView.getBackButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				currentBlock = null;
				showList();
			}
		});
		detailView.getSaveButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (detailView.validate()) {
					// if the block is new add it to the list
					if (currentBlock == null) {
						currentBlock = newBlock(detailView.getValue());
						initBlock(currentBlock);
						listView.addBlock(currentBlock);
					}
					// the association value has changed, refresh the blocks
					listView.updateBlocks();
					// we've changed, let the handlers know
					manager.fireEvent(new ChangedEvent(null));
				}
			}
		});
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
				listView.removeBlock(block);
				// we've changed, let the handlers know
				manager.fireEvent(new ChangedEvent(null));
			}
		});
	}

	public void showList() {
		cardLayout.showCard(Card.LIST);
	}

	public void showDetails(AssociationValue value) {
		detailView.showDetails(value);
		cardLayout.showCard(Card.DETAIL);
	}
}