/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.client.widget.attribute;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.types.KeyNames;
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
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import org.ktunaxa.referral.client.gui.LayoutConstant;
import org.ktunaxa.referral.client.security.UserContext;

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
	
	private IButton expcolBtn;
	
	private boolean defaultCollapsed;

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
		super(LayoutConstant.MARGIN_LARGE);
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
			if (defaultCollapsed) {
				block.collapse();
			} else {
				block.expand();
			}
			blockLayout.addMember(block);
		}
	}

	public void addBlock(AbstractAttributeBlock block) {
		blocks.add(block);
		blockLayout.addMember(block);
		if (defaultCollapsed) {
			block.collapse();
		} else {
			block.expand();
		}
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

	/**
	 * Should all elements in the list be collapsed by default?
	 * 
	 * @return Returns whether or not all elements in the list should be collapsed by default.
	 */
	public boolean isDefaultCollapsed() {
		return defaultCollapsed;
	}

	/**
	 * Determine whether or not all elements in the list should be collapsed by default. Default value is false.
	 * 
	 * @param defaultCollapsed
	 *            The new value. From this point on, new elements will be collapsed or expanded according to the value
	 *            set here.
	 */
	public void setDefaultCollapsed(boolean defaultCollapsed) {
		this.defaultCollapsed = defaultCollapsed;
		expcolBtn.setSelected(defaultCollapsed);
	}

	// ------------------------------------------------------------------------
	// Private methods concerning GUI:
	// ------------------------------------------------------------------------

	private void buildGui() {
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setMembersMargin(2);
		toolStrip.setSize("100%", "32");
		toolStrip.setBackgroundImage("");
		toolStrip.setBorder("none");

		createNewButton = new Button("Create new");
		createNewButton.setAutoFit(true);
		createNewButton.setDisabled(UserContext.getInstance().isGuest());
		toolStrip.addMember(createNewButton);
		toolStrip.addFill();

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

		// Expand/collapse toggle button:
		expcolBtn = new IButton();
		expcolBtn.setIcon("[ISOMORPHIC]/skins/ActivitiBlue/images/ktunaxa/collapsed_list.png");
		expcolBtn.setIconSize(16);
		expcolBtn.setSize("27", "30");
		expcolBtn.setTooltip("Collapse list");
		expcolBtn.setActionType(SelectionType.CHECKBOX);
		expcolBtn.setRadioGroup("view-group");
		expcolBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (expcolBtn.isSelected()) {
					collapse();
					expcolBtn.setIcon("[ISOMORPHIC]/skins/ActivitiBlue/images/ktunaxa/expanded_list.png");
					expcolBtn.setTooltip("Expand list");
				} else {
					expcolBtn.setIcon("[ISOMORPHIC]/skins/ActivitiBlue/images/ktunaxa/collapsed_list.png");
					expcolBtn.setTooltip("Collapse list");
					expand();
				}
			}
		});
		toolStrip.addMember(expcolBtn);
		toolStrip.addSeparator();

		// Searching:
		DynamicForm form = createSearchForm();
		form.setPadding(0);
		form.setStyleName("button");
		form.setLayoutAlign(VerticalAlignment.CENTER);
		toolStrip.addMember(form);

		addMember(toolStrip);

		blockLayout = new VLayout(LayoutConstant.MARGIN_LARGE);
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
				if (KeyNames.ENTER.equals(event.getKeyName())) {
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

		@SuppressWarnings({ "unchecked", "rawtypes" })
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