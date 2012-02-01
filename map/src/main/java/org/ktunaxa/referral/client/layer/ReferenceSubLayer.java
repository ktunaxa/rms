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
	
	public double getMinScale() {
		return minScale;
	}

	
	public double getMaxScale() {
		return maxScale;
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
