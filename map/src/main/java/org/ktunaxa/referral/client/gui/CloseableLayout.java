package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CloseableLayout extends VLayout {

	private static final int CLOSED_WIDTH = 24;

	public enum LayoutAlignment {
		LEFT, RIGHT
	};

	private Img openCloseImage;

	private HLayout titleCanvas;

	private VLayout contentsCanvas;

	private Canvas closedCanvas;

	private LayoutAlignment alignment;

	private String title;

	private boolean open = true;

	private int openedWidth = 350;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public CloseableLayout(LayoutAlignment alignment, String title) {
		this.alignment = alignment;
		this.title = title;

		setStyleName("closeableLayout");
		addMember(createTitle());

		contentsCanvas = new VLayout();
		contentsCanvas.setBackgroundColor("#E0E0E0");
		contentsCanvas.setSize("100%", "100%");
		addMember(contentsCanvas);

		closedCanvas = createClosedCanvas();
	}

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	public void setPanel(Canvas panel) {
		for (int i = contentsCanvas.getChildren().length - 1; i >= 0; i--) {
			contentsCanvas.removeChild(contentsCanvas.getChildren()[i]);
		}
		panel.setSize("100%", "100%");
		panel.setStyleName("closeableLayoutPanel");
		contentsCanvas.addChild(panel);
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;

		for (int i = getChildren().length - 1; i >= 0; i--) {
			removeChild(getChildren()[i]);
		}

		// Open or close the panel:
		if (open) {
			setWidth(openedWidth);
			addMember(titleCanvas);
			addMember(contentsCanvas);
		} else {
			setWidth(24);
			addMember(closedCanvas);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		createTitle();
	}

	// ------------------------------------------------------------------------
	// Private methods concerning GUI:
	// ------------------------------------------------------------------------

	private HLayout createTitle() {
		titleCanvas = new HLayout(5);
		titleCanvas.setBackgroundColor("#647386");
		titleCanvas.setSize("100%", CLOSED_WIDTH + "px");

		HTMLFlow titleDiv = new HTMLFlow(title);
		titleDiv.setSize("100%", CLOSED_WIDTH + "px");
		titleDiv.setStyleName("closeableLayoutTitle");

		if (alignment.equals(LayoutAlignment.LEFT)) {
			openCloseImage = new Img("[ISOMORPHIC]/images/arrow_left.png", CLOSED_WIDTH, CLOSED_WIDTH);
		} else {
			openCloseImage = new Img("[ISOMORPHIC]/images/arrow_right.png", CLOSED_WIDTH, CLOSED_WIDTH);
		}
		openCloseImage.setCursor(Cursor.HAND);
		openCloseImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (isOpen()) {
					setOpen(false);
				} else {
					setOpen(true);
				}
			}
		});

		titleCanvas.addMember(titleDiv);
		titleCanvas.addMember(openCloseImage);
		return titleCanvas;
	}

	private Canvas createClosedCanvas() {
		VLayout layout = new VLayout();
		layout.setBackgroundColor("#647386");
		layout.setSize(CLOSED_WIDTH + "px", "100%");

		Img openImage;
		if (alignment.equals(LayoutAlignment.LEFT)) {
			openImage = new Img("[ISOMORPHIC]/images/arrow_right.png", CLOSED_WIDTH, CLOSED_WIDTH);
		} else {
			openImage = new Img("[ISOMORPHIC]/images/arrow_left.png", CLOSED_WIDTH, CLOSED_WIDTH);
		}
		openImage.setCursor(Cursor.HAND);
		openImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				setOpen(true);
			}
		});
		layout.addMember(openImage);

		String contents = "";
		for (int i = 0; i < title.length(); i++) {
			contents += title.charAt(i) + "<br/>";
		}
		HTMLFlow verticalTitle = new HTMLFlow(contents);
		verticalTitle.setSize(CLOSED_WIDTH + "px", "100%");
		verticalTitle.setStyleName("closeableLayoutVerticalTitle");
		layout.addMember(verticalTitle);

		return layout;
	}
}
