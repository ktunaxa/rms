/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.widget.attribute;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * A layout that shows all elements of a one-to-many attribute as blocks in a list.
 * 
 * @author Jan De Moerloose
 * 
 */
public class AttributeBlockList extends VLayout {

	private Button createNewButton;

	private VLayout blockLayout;

	private Map<String, Comparator<AbstractAttributeBlock>> sortAttributes;

	private String sortedBy;

	private boolean sortedInverse;

	private List<AbstractAttributeBlock> blocks;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	/**
	 * Create a new list view instance with the given sort parameters.
	 * 
	 * @param sortAttributes A mapping of sort attribute names (used in the GUI) with respective comparators. These
	 *        comparators should be able to sort the actual collapsible blocks.
	 */
	public AttributeBlockList(Map<String, Comparator<AbstractAttributeBlock>> sortAttributes) {
		super(10);
		this.sortAttributes = sortAttributes;
		setWidth100();
		buildGui();
	}

	// ------------------------------------------------------------------------
	// Public methods:
	// ------------------------------------------------------------------------

	/**
	 * Populate the list with a given list of collapsible blocks.
	 * 
	 * @param blocks The actual list.
	 */
	public void populate(List<AbstractAttributeBlock> blocks) {
		this.blocks = blocks;
		blockLayout.removeMembers(blockLayout.getMembers());
		for (AbstractAttributeBlock block : blocks) {
			blockLayout.addMember(block);
		}
	}

	public void addBlock(AbstractAttributeBlock block) {
		blocks.add(block);
		blockLayout.addMember(block);
	}

	public void removeBlock(AbstractAttributeBlock block) {
		blocks.remove(block);
		blockLayout.removeMember(block);
	}

	public List<AbstractAttributeBlock> getBlocks() {
		return blocks;
	}

	/** Expend all blocks within the list. */
	public void expand() {
		for (AbstractAttributeBlock block : blocks) {
			block.expand();
		}
	}

	/** Collapse all blocks within the list. */
	public void collapse() {
		for (AbstractAttributeBlock block : blocks) {
			block.collapse();
		}
	}

	/**
	 * Search for a certain filter within the entire list. Leave only those blocks visible that actually contain the
	 * text.
	 * 
	 * @param filter The actual filtering text to search for. If 'null', all blocks will be made visible.
	 */
	public void search(String filter) {
		for (AbstractAttributeBlock block : blocks) {
			if (filter == null) {
				block.setVisible(true);
			} else {
				if (block.evaluate(filter)) {
					block.setVisible(true);
				} else {
					block.setVisible(false);
				}
			}
		}
	}

	/**
	 * Sort the list by a certain attribute. If the list was already sorted by that attribute, then use inverse sorting.
	 * 
	 * @param attribute The attribute to sort for. Must be part of the sort parameter mapping that was given to the
	 *        constructor.
	 */
	public void sort(String attribute) {
		if (sortAttributes != null && attribute != null) {
			Comparator<AbstractAttributeBlock> comparator = sortAttributes.get(attribute);
			if (comparator != null) {
				// Empty the blockLayout:
				for (int i = blockLayout.getMembers().length - 1; i >= 0; i--) {
					blockLayout.removeMember(blockLayout.getMembers()[i]);
				}

				// See if we need to inverse the sort:
				if (attribute.equals(sortedBy) && !sortedInverse) {
					sortedInverse = true;
					comparator = new InverseComparator(sortAttributes.get(attribute));
				} else {
					sortedInverse = false;
				}

				// Execute the sort:
				Collections.sort(blocks, comparator);
				for (AbstractAttributeBlock block : blocks) {
					blockLayout.addMember(block);
				}
				sortedBy = attribute;
			}
		}
	}

	/**
	 * Get the button for creating a new object in the list.
	 * 
	 * @return Returns the actual button.
	 */
	public Button getCreateNewButton() {
		return createNewButton;
	}

	// ------------------------------------------------------------------------
	// Private methods concerning GUI:
	// ------------------------------------------------------------------------

	private void buildGui() {
		setMargin(10);
		HLayout header = new HLayout(5);
		header.setStyleName("listViewHeader");
		header.setSize("100%", "32");
		createNewButton = new Button("Create new");
		createNewButton.setAutoFit(true);
		header.addMember(createNewButton);
		header.addMember(new LayoutSpacer());

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setMembersMargin(2);
		toolStrip.setSize("450", "32");
		toolStrip.setBackgroundImage("");
		toolStrip.setBorder("none");

		// Sorting:
		HTMLFlow sortText = new HTMLFlow(
				"<div style='text-align:right; line-height:32px; font-size:12px;'>Sort by:</div>");
		sortText.setSize("50px", "32");
		toolStrip.addMember(sortText);
		for (final String sortKey : sortAttributes.keySet()) {
			ToolStripButton sortBtn = new ToolStripButton(sortKey);
			sortBtn.setActionType(SelectionType.RADIO);
			sortBtn.setRadioGroup("sort-group");
			sortBtn.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					sort(sortKey);
				}
			});
			toolStrip.addMember(sortBtn);
		}
		toolStrip.addSeparator();

		// Expand button;
		IButton expandBtn = new IButton();
		expandBtn.setIcon("[ISOMORPHIC]/skins/ActivitiBlue/images/ktunaxa/expanded_list.png");
		expandBtn.setIconSize(16);
		expandBtn.setSize("27", "30");
		expandBtn.setTooltip("Expand list");
		expandBtn.setActionType(SelectionType.RADIO);
		expandBtn.setRadioGroup("view-group");
		expandBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				expand();
			}
		});
		toolStrip.addMember(expandBtn);

		// Collapse button:
		IButton collapseBtn = new IButton();
		collapseBtn.setIcon("[ISOMORPHIC]/skins/ActivitiBlue/images/ktunaxa/collapsed_list.png");
		collapseBtn.setIconSize(16);
		collapseBtn.setSize("27", "30");
		collapseBtn.setTooltip("Collapse list");
		collapseBtn.setActionType(SelectionType.RADIO);
		collapseBtn.setRadioGroup("view-group");
		collapseBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				collapse();
			}
		});
		toolStrip.addMember(collapseBtn);
		toolStrip.addSeparator();

		// Searching:
		DynamicForm form = createSearchForm();
		form.setPadding(0);
		form.setStyleName("button");
		form.setLayoutAlign(VerticalAlignment.CENTER);
		toolStrip.addMember(form);

		header.addMember(toolStrip);
		addMember(header);

		blockLayout = new VLayout(10);
		blockLayout.setWidth100();
		addMember(blockLayout);
	}

	public void updateBlocks() {
		for (AbstractAttributeBlock block : blocks) {
			block.redrawValue();
		}
	}

	private DynamicForm createSearchForm() {
		DynamicForm searchForm = new DynamicForm();
		final TextItem filterItem = new TextItem("search");
		filterItem.setWidth("150");
		filterItem.setShowTitle(false);

		PickerIcon searchPicker = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {

			public void onFormItemClick(FormItemIconClickEvent event) {
				search((String) event.getItem().getValue());
			}
		});
		PickerIcon clearPicker = new PickerIcon(PickerIcon.CLEAR, new FormItemClickHandler() {

			public void onFormItemClick(FormItemIconClickEvent event) {
				filterItem.setValue("");
				search(null);
			}
		});
		filterItem.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if ("Enter".equalsIgnoreCase(event.getKeyName())) {
					search((String) event.getItem().getValue());
				}
			}
		});
		filterItem.setIcons(searchPicker, clearPicker);
		searchForm.setFields(filterItem);
		return searchForm;
	}

	// ------------------------------------------------------------------------
	// Private classes:
	// ------------------------------------------------------------------------

	/**
	 * Compares two blocks by a specific attribute.
	 */
	public static class AttributeComparator implements Comparator<AbstractAttributeBlock> {

		private String attributeName;

		public AttributeComparator(String attributeName) {
			this.attributeName = attributeName;
		}

		public int compare(AbstractAttributeBlock a1, AbstractAttributeBlock a2) {
			Object attr1 = a1.getValue().getAttributeValue(attributeName);
			Object attr2 = a2.getValue().getAttributeValue(attributeName);
			if (attr1 == null) {
				return -1;
			} else if (attr2 == null) {
				return 1;
			} else if (attr1 instanceof Comparable) {
				return ((Comparable) attr1).compareTo(attr2);
			}
			return 0;
		}

	}

	/**
	 * Comparator implementation that inverses the result of another comparator.
	 * 
	 * @author Pieter De Graef
	 */
	private class InverseComparator implements Comparator<AbstractAttributeBlock> {

		private Comparator<AbstractAttributeBlock> original;

		public InverseComparator(Comparator<AbstractAttributeBlock> original) {
			this.original = original;
		}

		public int compare(AbstractAttributeBlock o1, AbstractAttributeBlock o2) {
			return -original.compare(o1, o2);
		}
	}


}