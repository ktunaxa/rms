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
 * @author Jan De Moerloose
 * 
 * @param <W>
 */
public abstract class AttributeBlockLayout<W extends Widget> extends VLayout {

	/**
	 * Card type enumeration
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	enum Card {
		LIST, DETAIL
	};

	private AttributeBlockList listView;

	private AttributeBlockDetail<W> detailView;

	private CardLayout cardLayout;

	private HandlerManager manager = new HandlerManager(this);

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	/**
	 * Build the entire GUI, starting from a given ListView and a DetailView. This constructor will automatically attach
	 * the needed click handlers to the correct buttons to switch from one view to the other.
	 * 
	 * @param listView ListView implementation that represents the list of objects T.
	 * @param detailView DetailView implementation that represents a way of editing existing objects or creating new
	 *        objects of type T.
	 */
	public AttributeBlockLayout(AttributeBlockList list, FeatureForm<W> form) {
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
				showDetails(newInstance());
			}
		});
		detailView.getBackButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				showList();
			}
		});
		detailView.getSaveButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				AssociationValue value = detailView.getValue();
				// if the value is new add it to the list
				if (value.getId().isEmpty()) {
					final AbstractAttributeBlock block = newBlock(detailView.getValue());
					block.addEditHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							showDetails(block.getValue());
						}
					});
					listView.addBlock(block);
				}
				// refresh the blocks
				listView.updateBlocks();
				// we've changed, let the handlers know
				manager.fireEvent(new ChangedEvent(null));
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
			block.addEditHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					detailView.showDetails(value);
				}
			});
			list.add(block);
		}
		populate(list);
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
	 * @return a new block
	 */
	protected abstract AbstractAttributeBlock newBlock(AssociationValue value);

	// ------------------------------------------------------------------------
	// Public methods:
	// ------------------------------------------------------------------------

	/**
	 * Populate the list with block that each display a single instance of type T.
	 * 
	 * @param block The full list of blocks.
	 */
	private void populate(List<AbstractAttributeBlock> blocks) {
		for (final AbstractAttributeBlock block : blocks) {
			block.addEditHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					showDetails(block.getValue());
				}
			});
		}
		listView.populate(blocks);
	}

	public void showList() {
		cardLayout.showCard(Card.LIST);
	}

	public void showDetails(AssociationValue value) {
		detailView.showDetails(value);
		cardLayout.showCard(Card.DETAIL);
	}
}