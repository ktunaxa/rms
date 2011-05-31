/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.widget;

import java.io.Serializable;
import java.util.List;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * General layout for managing a list of objects. This layout can display the full list of objects, but can also focus
 * on a single instance for editing, or create new objects for the list. It is the central list manager GUI.
 * 
 * @author Pieter De Graef
 * 
 * @param <T>
 *            The main object of interest. These are usually objects of the domain model of the application.
 */
public class ListLayout<T extends Serializable> extends VLayout {

	private ListView<T> listView;

	private DetailView<T> detailView;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	/**
	 * Build the entire GUI, starting from a given ListView and a DetailView. This constructor will automatically attach
	 * the needed click handlers to the correct buttons to switch from one view to the other.
	 * 
	 * @param listView
	 *            ListView implementation that represents the list of objects T.
	 * @param detailView
	 *            DetailView implementation that represents a way of editing existing objects or creating new objects of
	 *            type T.
	 */
	public ListLayout(ListView<T> listView, DetailView<T> detailView) {
		super(10);
		this.listView = listView;
		this.detailView = detailView;
		setWidth100();
		setPadding(10);
		setOverflow(Overflow.AUTO);

		detailView.setVisible(false);
		addMember(listView);
		addMember(detailView);

		listView.getCreateNewButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				showDetails(null);
			}
		});
		detailView.getBackButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				showList();
			}
		});
	}

	// ------------------------------------------------------------------------
	// Public methods:
	// ------------------------------------------------------------------------

	/**
	 * Populate the list with block that each display a single instance of type T.
	 * 
	 * @param blocks
	 *            The full list of blocks.
	 */
	public void populate(List<AbstractCollapsibleListBlock<T>> blocks) {
		for (final AbstractCollapsibleListBlock<T> block : blocks) {
			Button button = block.getEditButton();
			if (null != button) {
				button.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						showDetails(block.getObject());
					}
				});
			}
		}
		listView.populate(blocks);
	}

	/** Change the view to display the list. */
	public void showList() {
		detailView.setVisible(false);
		listView.setVisible(true);
	}

	/** Change the view to display the details of a single object of type T. */
	public void showDetails(T object) {
		detailView.setObject(object);
		listView.setVisible(false);
		detailView.setVisible(true);
	}
}