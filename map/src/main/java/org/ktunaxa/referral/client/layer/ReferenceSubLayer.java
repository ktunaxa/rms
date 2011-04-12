/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.layer;

import org.ktunaxa.referral.server.dto.ReferenceLayerDto;

import com.google.gwt.i18n.client.NumberFormat;

/**
 * A reference sublayer is a layer subset of a single type.
 * 
 * @author Jan De Moerloose
 */
public class ReferenceSubLayer {

	private static final String DENOMINATOR_FORMAT = "###,###";

	private ReferenceLayer referenceLayer;

	private ReferenceLayerDto referenceLayerDto;

	private double minScale;

	private double maxScale;

	private boolean showing;

	private boolean selected;

	private boolean labeled;

	private boolean visible;

	public ReferenceSubLayer(ReferenceLayer referenceLayer, ReferenceLayerDto referenceLayerDto) {
		this.referenceLayer = referenceLayer;
		this.referenceLayerDto = referenceLayerDto;
		double pixelLength = referenceLayer.getPixelLength();
		minScale = stringToScale(referenceLayerDto.getScaleMin()) / pixelLength;
		maxScale = stringToScale(referenceLayerDto.getScaleMax()) / pixelLength;
		visible = referenceLayerDto.isVisibleByDefault();
	}

	public long getCode() {
		return referenceLayerDto.getCode();
	}

	public ReferenceLayerDto getDto() {
		return referenceLayerDto;
	}

	/**
	 * Is this layer currently selected or not?
	 * 
	 * @return true if selected, false otherwise
	 */
	public boolean isSelected() {
		return selected;
	}

	public void updateShowing() {
		double scale = referenceLayer.getCurrentScale();
		showing = visible && scale >= minScale && scale <= maxScale;
	}

	public boolean isLabeled() {
		return showing && labeled;
	}

	public void setLabeled(boolean labeled) {
		this.labeled = labeled;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isShowing() {
		return showing;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
		referenceLayer.updateShowing();
	}

	public ReferenceLayer getReferenceLayer() {
		return referenceLayer;
	}

	protected String scaleToString(double scale) {
		NumberFormat numberFormat = NumberFormat.getFormat(DENOMINATOR_FORMAT);
		if (scale > 0 && scale < 1.0) {
			int denom = (int) Math.round(1. / scale);
			return "1 : " + numberFormat.format(denom);
		} else if (scale >= 1.0) {
			int denom = (int) Math.round(scale);
			return numberFormat.format(denom) + " : 1";
		} else {
			return "negative scale not allowed";
		}
	}

	protected Double stringToScale(String s) {
		NumberFormat numberFormat = NumberFormat.getFormat(DENOMINATOR_FORMAT);
		String[] scale2 = s.split(":");
		if (scale2.length == 1) {
			double denominator = numberFormat.parse(scale2[0].trim());
			return denominator == 0 ? 0 : 1 / denominator;
		} else {
			return numberFormat.parse(scale2[0].trim()) / numberFormat.parse(scale2[1].trim());
		}
	}

	public double getScaleMinDenominator() {
		return 1.0 / stringToScale(referenceLayerDto.getScaleMin());
	}

	public double getScaleMaxDenominator() {
		return 1.0 / stringToScale(referenceLayerDto.getScaleMax());
	}

}
