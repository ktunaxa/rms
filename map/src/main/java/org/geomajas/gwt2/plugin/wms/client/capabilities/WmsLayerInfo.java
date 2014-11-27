/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2014 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.geomajas.gwt2.plugin.wms.client.capabilities;

import org.geomajas.annotation.Api;
import org.geomajas.geometry.Bbox;

import java.io.Serializable;
import java.util.List;

/**
 * Layer definition within a WMS GetCapabilities.
 *
 * @author Pieter De Graef
 * @since 2.1.0
 */
@Api
public interface WmsLayerInfo extends Serializable {

	/**
	 * Can this layer be queried?
	 *
	 * @return Can this layer be queried?
	 */
	boolean isQueryable();

	/**
	 * Get the layer name. This name is used in GetMap or GetFeatureInfo requests.
	 *
	 * @return The layer name.
	 */
	String getName();

	/**
	 * Get the layer title. This is a nicely readable name for the layer.
	 *
	 * @return Get the layer title.
	 */
	String getTitle();

	/**
	 * Get a description for the layer.
	 *
	 * @return The layer description.
	 */
	String getAbstract();

	/**
	 * Get a list of keywords for this layer.
	 *
	 * @return A list of keywords for this layer.
	 */
	List<String> getKeywords();

	/**
	 * Get a list of supported coordinate systems for this layer.
	 *
	 * @return The list of supported coordinate systems.
	 */
	List<String> getCrs();

	/**
	 * Get the extent for this layer, expressed in lat-lon.
	 *
	 * @return The extent for this layer.
	 */
	Bbox getLatlonBoundingBox();

	/**
	 * Get the CRS that expresses the layer bounding box. See {{@link #getBoundingBox()}.
	 *
	 * @return The CRS used in the layer bounding box.
	 */
	String getBoundingBoxCrs();

	/**
	 * Get the extent for this layer in the specified CRS.
	 *
	 * @return Get the extent for this layer in the specified CRS.
	 */
	Bbox getBoundingBox(String crs);

	/**
	 * Get the extent for this layer. This bounding box is expressed in the CRS as returned by
	 * {@link #getBoundingBoxCrs()}.
	 *
	 * @return Get the extent for this layer.
	 */
	Bbox getBoundingBox();

	/**
	 * Get the metadata information for this layer.
	 *
	 * @return The metadata information.
	 */
	List<WmsLayerMetadataUrlInfo> getMetadataUrls();

	/**
	 * Get a list of supported styles for this layer.
	 *
	 * @return The list of styles.
	 */
	List<WmsLayerStyleInfo> getStyleInfo();

	/**
	 * Get the minimum scale denominator for this layer.
	 *
	 * @return Returns the minimum scale denominator, or -1 if it is not provided.
	 */
	double getMinScaleDenominator();

	/**
	 * Get the maximum scale denominator for this layer.
	 *
	 * @return Returns the maximum scale denominator, or -1 if it is not provided.
	 */
	double getMaxScaleDenominator();
	
	/**
	 * Get a list of supported coordinate systems for this layer.
	 *
	 * @return The list of supported coordinate systems.
	 */
	List<WmsLayerInfo> getLayers();

}
