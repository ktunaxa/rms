/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.widget;

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
 * be placed on the "bottom", meaning the last member of a VLayout parent. As maximizing requires that all siblings are
 * hidden, this layer is supposed to be a member of a parent VLayout.
 * </p>
 * <p>
 * The main methods all revolve around the minimizing, maximizing and restoring of this layouts size.
 * </p>
 * 
 * @author Pieter De Graef
 */
public class ResizableBottomLayout extends VLayout {

	private static final int CLOSED_WIDTH = 24;

	private HLayout titleCanvas;

	private HTMLFlow titleDiv;

	private ImgButton minimizeImage;

	private ImgButton restoreImage;

	private ImgButton maximizeImage;

	private VLayout contentsCanvas;

	private String title;

	private ViewState viewState = ViewState.RESTORED;

	private int restoredHeight = 150;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	/**
	 * This constructors immediately require a title to be set. It will build all necessary GUI.
	 * 
	 * @param title
	 *            The title for this layout.
	 */
	public ResizableBottomLayout(String title) {
		this.title = title;

		addMember(buildGui());
		contentsCanvas = new VLayout();
		contentsCanvas.setSize("100%", "100%");
		addMember(contentsCanvas);
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

	/** Minimize this layout. As a result the contents are hidden, leaving only the title visible. */
	public void minimize() {
		if (viewState != ViewState.MINIMIZED) {
			if (viewState == ViewState.RESTORED) {
				restoredHeight = getHeight();
			} else if (viewState == ViewState.MAXIMIZED) {
				showSiblings();
			}
			contentsCanvas.setVisible(false);
			setHeight(32);
		}
		viewState = ViewState.MINIMIZED;
		minimizeImage.setVisible(false);
		restoreImage.setVisible(true);
		maximizeImage.setVisible(true);
	}

	/**
	 * Maximize this layout. This means that all siblings will be hidden, so that this layout takes up 100% of the
	 * parent space.
	 */
	public void maximize() {
		if (viewState == ViewState.MINIMIZED) {
			hideSiblings();
			setHeight100();
			contentsCanvas.setVisible(true);
		} else if (viewState == ViewState.RESTORED) {
			restoredHeight = getHeight();
			hideSiblings();
			setHeight100();
		}
		viewState = ViewState.MAXIMIZED;
		minimizeImage.setVisible(true);
		restoreImage.setVisible(true);
		maximizeImage.setVisible(false);
	}

	/** Restore this layout to the last known height. */
	public void restore() {
		if (viewState == ViewState.MINIMIZED) {
			setHeight(restoredHeight);
			contentsCanvas.setVisible(true);
		} else if (viewState == ViewState.MAXIMIZED) {
			showSiblings();
			setHeight(restoredHeight);
		}
		viewState = ViewState.RESTORED;
		minimizeImage.setVisible(true);
		restoreImage.setVisible(false);
		maximizeImage.setVisible(true);
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
	// Private methods concerning Minimize/Maximize operations:
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

	/** Build the GUI for this layout widget. */
	private HLayout buildGui() {
		titleCanvas = new HLayout(5);
		titleCanvas.setSize("100%", CLOSED_WIDTH + "px");
		titleCanvas.setStyleName("blockTitle");

		titleDiv = new HTMLFlow(title);
		titleDiv.setSize("100%", CLOSED_WIDTH + "px");
		titleDiv.setStyleName("blockTitleText");

		minimizeImage = new ImgButton();
		minimizeImage.setSrc("[ISOMORPHIC]/skins/KtunaxaTheme/images/headerIcons/minimize.gif");
		minimizeImage.setSize(16);
		minimizeImage.setLayoutAlign(VerticalAlignment.CENTER);
		minimizeImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				minimize();
			}
		});

		restoreImage = new ImgButton();
		restoreImage.setLayoutAlign(VerticalAlignment.CENTER);
		restoreImage.setSrc("[ISOMORPHIC]/skins/KtunaxaTheme/images/headerIcons/cascade.gif");
		restoreImage.setSize(16);
		restoreImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				restore();
			}
		});
		restoreImage.setVisible(false);

		maximizeImage = new ImgButton();
		maximizeImage.setLayoutAlign(VerticalAlignment.CENTER);
		maximizeImage.setSrc("[ISOMORPHIC]/skins/KtunaxaTheme/images/headerIcons/maximize.gif");
		maximizeImage.setSize(16);
		maximizeImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				maximize();
			}
		});

		titleCanvas.addMember(titleDiv);
		titleCanvas.addMember(minimizeImage);
		titleCanvas.addMember(restoreImage);
		titleCanvas.addMember(maximizeImage);
		return titleCanvas;
	}
}
