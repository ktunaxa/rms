/*
 * This file is part of Geomajas, a component framework for building
 * rich Internet applications (RIA) with sophisticated capabilities for the
 * display, analysis and management of geographic information.
 * It is a building block that allows developers to add maps
 * and other geographic data capabilities to their web applications.
 *
 * Copyright 2008-2010 Geosparc, http://www.geosparc.com, Belgium
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.client.widget;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * <p>
 * Layout that allows for minimizing, maximizing and restoring of its contents. As the name suggests, it is assumed to
 * be placed on the "left", meaning the first member of a HLayout parent. As maximizing requires that all siblings are
 * hidden, this layer is supposed to be a member of a parent HLayout.
 * </p>
 * <p>
 * The main methods all revolve around the minimizing, maximizing and restoring of this layouts size.
 * </p>
 * 
 * @author Pieter De Graef
 */
public class ResizableLeftLayout extends VLayout {

	private static final int CLOSED_WIDTH = 24;

	private HLayout titleCanvas;

	private HTMLFlow titleDiv;

	private VLayout contentsCanvas;

	private Canvas closedCanvas;

	private String title;

	private ViewState viewState = ViewState.RESTORED;

	private int restoredWidth = 350;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	/**
	 * This constructors immediately require a title to be set. It will build all necessary GUI.
	 * 
	 * @param title
	 *            The title for this layout.
	 */
	public ResizableLeftLayout(String title) {
		this.title = title;

		addMember(createTitle());

		contentsCanvas = new VLayout();
		contentsCanvas.setSize("100%", "100%");
		addMember(contentsCanvas);

		closedCanvas = createClosedCanvas();
	}

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * Set the content canvas for this layout. Note that only 1 canvas is allowed as content.
	 * 
	 * @param pane
	 *            The new canvas to apply as content.
	 */
	public void setPane(Canvas pane) {
		for (int i = contentsCanvas.getChildren().length - 1; i >= 0; i--) {
			contentsCanvas.removeChild(contentsCanvas.getChildren()[i]);
		}
		pane.setSize("100%", "100%");
		pane.setStyleName("closeableLayoutPanel");
		contentsCanvas.addChild(pane);
	}

	/**
	 * Minimize this layout. The result will be a small vertical block on the left-side of the parent.
	 */
	public void minimize() {
		if (viewState != ViewState.MINIMIZED) {
			if (viewState == ViewState.RESTORED) {
				restoredWidth = getWidth();
			} else if (viewState == ViewState.MAXIMIZED) {
				showSiblings();
			}
			for (int i = getChildren().length - 1; i >= 0; i--) {
				removeChild(getChildren()[i]);
			}
			setWidth(24);
			addMember(closedCanvas);
		}
		viewState = ViewState.MINIMIZED;
	}

	/**
	 * Maximize this layout. This means that all siblings will be hidden, so that this layout takes up 100% of the
	 * parent space.
	 */
	public void maximize() {
		if (viewState == ViewState.MINIMIZED) {
			for (int i = getChildren().length - 1; i >= 0; i--) {
				removeChild(getChildren()[i]);
			}
			hideSiblings();
			setWidth100();
			addMember(titleCanvas);
			addMember(contentsCanvas);
		} else if (viewState == ViewState.RESTORED) {
			restoredWidth = getWidth();
			hideSiblings();
			setWidth100();
		}
		viewState = ViewState.MAXIMIZED;
	}

	/**
	 * Restore this layout to the last known width.
	 */
	public void restore() {
		if (viewState == ViewState.MINIMIZED) {
			for (int i = getChildren().length - 1; i >= 0; i--) {
				removeChild(getChildren()[i]);
			}
			setWidth(restoredWidth);
			addMember(titleCanvas);
			addMember(contentsCanvas);
		} else if (viewState == ViewState.MAXIMIZED) {
			showSiblings();
			setWidth(restoredWidth);
		}
		viewState = ViewState.RESTORED;
	}

	/**
	 * Get the current title of this layout.
	 * 
	 * @return The current title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Apply a new title for this layout.
	 * 
	 * @param title
	 *            The new title value.
	 */
	public void setTitle(String title) {
		this.title = title;
		if (titleDiv != null) {
			titleDiv.setContents(title);
		}
	}

	/**
	 * Get the current view state of this layout. Options are:
	 * <ul>
	 * <li>ViewState.MINIMIZED</li>
	 * <li>ViewState.RESTORED</li>
	 * <li>ViewState.MAXIMIZED</li>
	 * </ul>
	 * 
	 * @return Return the current view state.
	 */
	public ViewState getViewState() {
		return viewState;
	}

	// ------------------------------------------------------------------------
	// Private methods concerning minimize/maximize operations:
	// ------------------------------------------------------------------------

	/** Called when maximizing. */
	private void hideSiblings() {
		for (Canvas sibling : getParentElement().getChildren()) {
			if (!this.equals(sibling)) {
				sibling.setVisible(false);
			}
		}
	}

	/** Called when restoring or minimizing and the viewState was maximized. */
	private void showSiblings() {
		for (Canvas sibling : getParentElement().getChildren()) {
			if (!this.equals(sibling)) {
				sibling.setVisible(true);
			}
		}
	}

	// ------------------------------------------------------------------------
	// Private methods concerning GUI:
	// ------------------------------------------------------------------------

	/** Build the title canvas for the normal view. */
	private HLayout createTitle() {
		titleCanvas = new HLayout(5);
		titleCanvas.setSize("100%", CLOSED_WIDTH + "px");
		titleCanvas.setStyleName("blockTitle");

		titleDiv = new HTMLFlow(title);
		titleDiv.setSize("100%", CLOSED_WIDTH + "px");
		titleDiv.setStyleName("blockTitleText");

		ImgButton minimizeImage = new ImgButton();
		minimizeImage.setSrc("[ISOMORPHIC]/skins/KtunaxaTheme/images/headerIcons/minimize.gif");
		minimizeImage.setSize(16);
		minimizeImage.setLayoutAlign(VerticalAlignment.CENTER);
		minimizeImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				minimize();
			}
		});

		final ImgButton maximizeImage = new ImgButton();
		maximizeImage.setLayoutAlign(VerticalAlignment.CENTER);
		maximizeImage.setSrc("[ISOMORPHIC]/skins/KtunaxaTheme/images/headerIcons/maximize.gif");
		maximizeImage.setSize(16);
		maximizeImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (viewState == ViewState.MAXIMIZED) {
					maximizeImage.setSrc("[ISOMORPHIC]/skins/KtunaxaTheme/images/headerIcons/maximize.gif");
					restore();
				} else if (viewState == ViewState.RESTORED) {
					maximizeImage.setSrc("[ISOMORPHIC]/skins/KtunaxaTheme/images/headerIcons/cascade.gif");
					maximize();
				}
			}
		});

		titleCanvas.addMember(titleDiv);
		titleCanvas.addMember(minimizeImage);
		titleCanvas.addMember(maximizeImage);
		return titleCanvas;
	}

	/** Build the GUI when minimized. Has a vertical title and other differences... */
	private Canvas createClosedCanvas() {
		VLayout layout = new VLayout();
		layout.setStyleName("blockTitle");
		layout.setSize(CLOSED_WIDTH + "px", "100%");

		ImgButton maximizeImage = new ImgButton();
		maximizeImage.setLayoutAlign(Alignment.CENTER);
		maximizeImage.setSrc("[ISOMORPHIC]/skins/KtunaxaTheme/images/headerIcons/maximize.gif");
		maximizeImage.setSize(16);
		maximizeImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				maximize();
			}
		});
		layout.addMember(maximizeImage);

		ImgButton restoreImage = new ImgButton();
		restoreImage.setLayoutAlign(Alignment.CENTER);
		restoreImage.setSrc("[ISOMORPHIC]/skins/KtunaxaTheme/images/headerIcons/cascade.gif");
		restoreImage.setSize(16);
		restoreImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				restore();
			}
		});
		layout.addMember(restoreImage);

		String contents = "";
		for (int i = 0; i < title.length(); i++) {
			contents += title.charAt(i) + "<br/>";
		}
		HTMLFlow verticalTitle = new HTMLFlow(contents);
		verticalTitle.setSize(CLOSED_WIDTH + "px", "100%");
		verticalTitle.setStyleName("blockVerticalTitleText");
		layout.addMember(verticalTitle);

		return layout;
	}
}