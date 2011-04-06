/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.widget;

import java.io.Serializable;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * General definition of a "block" that can be a part of a list (see {@ListView}) in a GUI. In order to be
 * part of such a list, some required need to be met. It must be collapsible/expandable, so users can change the way the
 * list look like.
 * 
 * @author Pieter De Graef
 * 
 * @param <T>
 *            The main object of interest. These are usually objects of the domain model of the application.
 */
public abstract class CollapsibleListBlock<T extends Serializable> extends VLayout {

	private T object;

	/**
	 * Protected constructor that all implementing classes need to call.
	 * 
	 * @param object
	 *            The central object to display in this block.
	 */
	protected CollapsibleListBlock(T object) {
		this.object = object;
	}

	/** Expand the GUI of this block. Note that expanded should be the default. */
	public abstract void expand();

	/** Collapse the GUI of this block, making it a lot thinner then when expanded. */
	public abstract void collapse();

	/**
	 * Can a certain text be found somewhere within the GUI of this block? This method can be used for filtering in the
	 * list.
	 * 
	 * @param text
	 *            The text to search for.
	 * @return Returns true or false.
	 */
	public abstract boolean containsText(String text);

	/**
	 * Get the edit button embedded within this GUI. Every instance should contain a button that can be used for
	 * navigating to some page where the central object can be edited. Don't attach any click handlers to this button.
	 * 
	 * @return Returns the actual button.
	 */
	public abstract Button getEditButton();

	public T getObject() {
		return object;
	}
}