/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

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

	/**
	 * The view state of the layout.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public enum ViewState {
		MINIMIZED, RESTORED, MAXIMIZED
	}

	private static final String BUTTONGROUP = "the-only-one";

	private static final int CLOSED_WIDTH = 24;

	private HLayout titleCanvas;

	private HTMLFlow titleDiv;

	private VLayout contentsCanvas;

	private Canvas closedCanvas;

	private ViewState viewState = ViewState.RESTORED;

	private int restoredWidth = 550;

	private HashMap<String, Card> cards = new HashMap<String, Card>();

	private Card currentCard;

	private List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();

	private HTMLFlow verticalTitle;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	/**
	 * This constructors immediately require a title to be set. It will build all necessary GUI.
	 * 
	 * @param title The title for this layout.
	 */
	public ResizableLeftLayout() {
		setSize(restoredWidth + "px", "100%");

		addMember(createTitle());

		contentsCanvas = new VLayout();
		contentsCanvas.setSize("100%", "100%");
		addMember(contentsCanvas);

		closedCanvas = createClosedCanvas();

		restore();
	}

	/**
	 * Add a card to this layout. The last card added will become the current card.
	 * 
	 * @param key key for the card, used to show it later
	 * @param card the card canvas
	 */
	public void addCard(String name, String title, Canvas canvas) {
		canvas.setWidth100();
		canvas.setHeight100();
		canvas.setStyleName("closeableLayoutPanel");
		canvas.setLeft(0);
		canvas.setTop(0);
		canvas.hide();
		contentsCanvas.addChild(canvas);
		final Card card = new Card(name, title, canvas);
		cards.put(name, card);

		ToolStripButton button = new ToolStripButton(name);
		button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				showCard(card.getName());
			}
		});
		button.setActionType(SelectionType.RADIO);
		button.setRadioGroup(BUTTONGROUP);
		buttons.add(button);
	}

	/**
	 * Show the specified card.
	 * 
	 * @param key the key that was used to add the card
	 */
	public void showCard(String name) {
		restore();
		if (cards.containsKey(name)) {
			Card old = currentCard;
			currentCard = cards.get(name);
			currentCard.getCanvas().show();
			titleDiv.setContents(name);
			StringBuilder contents = new StringBuilder();
			for (int i = 0; i < currentCard.getName().length(); i++) {
				contents.append(name.charAt(i));
				contents.append("<br/>");
			}
			verticalTitle.setContents(currentCard.getTitle().toString());
			if (old != null) {
				old.getCanvas().hide();
			}
			for (ToolStripButton button : buttons) {
				if (button.getTitle().equals(name)) {
					button.setSelected(true);
				} else {
					button.setSelected(false);
				}
			}
		}
	}

	/**
	 * Gets the list of buttons to show the individual cards.
	 * 
	 * @return list of buttons
	 */
	public List<ToolStripButton> getButtons() {
		return buttons;
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
			setMembers();
			setWidth(24);
			addMember(closedCanvas);
		}
		viewState = ViewState.MINIMIZED;
		disableResizeBar();
	}

	/**
	 * Maximize this layout. This means that all siblings will be hidden, so that this layout takes up 100% of the
	 * parent space.
	 */
	public void maximize() {
		if (viewState == ViewState.MINIMIZED) {
			setMembers();
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
		disableResizeBar();
	}

	/**
	 * Restore this layout to the last known width.
	 */
	public void restore() {
		if (viewState == ViewState.MINIMIZED) {
			setMembers();
			setWidth(restoredWidth);
			addMember(titleCanvas);
			addMember(contentsCanvas);
		} else if (viewState == ViewState.MAXIMIZED) {
			showSiblings();
			setWidth(restoredWidth);
		}
		viewState = ViewState.RESTORED;
		enableResizeBar();
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

	private void enableResizeBar() {
		setShowResizeBar(true);
		if (getParentElement() instanceof Layout) {
			Layout parent = (Layout) getParentElement();
			parent.setMembersMargin(0);
		}
	}

	private void disableResizeBar() {
		setShowResizeBar(false);
		if (getParentElement() instanceof Layout) {
			Layout parent = (Layout) getParentElement();
			parent.setMembersMargin(10);
		}
	}

	/** Build the title canvas for the normal view. */
	private HLayout createTitle() {
		titleCanvas = new HLayout(5);
		titleCanvas.setSize("100%", CLOSED_WIDTH + "px");
		titleCanvas.setStyleName("blockTitle");

		titleDiv = new HTMLFlow();
		titleDiv.setSize("100%", CLOSED_WIDTH + "px");
		titleDiv.setStyleName("blockTitleText");

		ImgButton minimizeImage = new ImgButton();
		minimizeImage.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/minimize.gif");
		minimizeImage.setSize(16);
		minimizeImage.setLayoutAlign(VerticalAlignment.CENTER);
		minimizeImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				minimize();
			}
		});

		final ImgButton maximizeImage = new ImgButton();
		maximizeImage.setLayoutAlign(VerticalAlignment.CENTER);
		maximizeImage.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/maximize.gif");
		maximizeImage.setSize(16);
		maximizeImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (viewState == ViewState.MAXIMIZED) {
					maximizeImage.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/maximize.gif");
					restore();
				} else if (viewState == ViewState.RESTORED) {
					maximizeImage.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/cascade.gif");
					maximize();
				}
			}
		});

		titleCanvas.addMember(titleDiv);
		titleCanvas.addMember(minimizeImage);
		titleCanvas.addMember(maximizeImage);
		return titleCanvas;
	}

	/**
	 * Build the GUI when minimized. Has a vertical title and other differences...
	 */
	private Canvas createClosedCanvas() {
		VLayout layout = new VLayout();
		layout.setStyleName("blockTitle");
		layout.setSize(CLOSED_WIDTH + "px", "100%");

		ImgButton maximizeImage = new ImgButton();
		maximizeImage.setLayoutAlign(Alignment.CENTER);
		maximizeImage.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/maximize.gif");
		maximizeImage.setSize(16);
		maximizeImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				maximize();
			}
		});
		layout.addMember(maximizeImage);

		ImgButton restoreImage = new ImgButton();
		restoreImage.setLayoutAlign(Alignment.CENTER);
		restoreImage.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/cascade.gif");
		restoreImage.setSize(16);
		restoreImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				restore();
			}
		});
		layout.addMember(restoreImage);

		verticalTitle = new HTMLFlow("");
		verticalTitle.setSize(CLOSED_WIDTH + "px", "100%");
		verticalTitle.setStyleName("blockVerticalTitleText");
		layout.addMember(verticalTitle);

		return layout;
	}

	/**
	 * A holder for each "card" in the card layout.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	private class Card {

		private String name;

		private String title;

		private Canvas canvas;

		public Card(String name, String title, Canvas canvas) {
			this.name = name;
			this.title = title;
			this.canvas = canvas;
		}

		public String getName() {
			return name;
		}

		public String getTitle() {
			return title;
		}

		public Canvas getCanvas() {
			return canvas;
		}

	}

}