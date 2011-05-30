/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.client.widget.DetailView;
import org.ktunaxa.referral.client.widget.ListLayout;
import org.ktunaxa.referral.client.widget.ListView;
import org.ktunaxa.referral.server.dto.CommentDto;

import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel for managing the comments of a referral. This panel display the full list of comments or a more detailed view
 * on a single comment.
 * 
 * @author Pieter De Graef
 */
public class CommentPanel extends VLayout {

	public CommentPanel() {
		setWidth100();
		Map<String, Comparator<AbstractCollapsibleListBlock<CommentDto>>> sortAttributes;
		sortAttributes = new HashMap<String, Comparator<AbstractCollapsibleListBlock<CommentDto>>>();
		sortAttributes.put("date", new DateComparator());
		sortAttributes.put("author", new AuthorComparator());
		sortAttributes.put("title", new TitleComparator());
		ListView<CommentDto> listView = new ListView<CommentDto>(sortAttributes);
		DetailView<CommentDto> detailView = new DetailView<CommentDto>();
		ListLayout<CommentDto> layout = new ListLayout<CommentDto>(listView, detailView);
		layout.populate(getBlocks());
		addMember(layout);
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// nothing to do for now
	}

	/**
	 * Comparator for comments that compares the creation dates.
	 * 
	 * @author Pieter De Graef
	 */
	private class DateComparator implements Comparator<AbstractCollapsibleListBlock<CommentDto>> {

		public int compare(AbstractCollapsibleListBlock<CommentDto> one, AbstractCollapsibleListBlock<CommentDto> two) {
			if (one == null && two == null) {
				return 0;
			}
			if (one == null) {
				return -1;
			}
			if (two == null) {
				return 1;
			}
			return one.getObject().getCreationDate().compareTo(two.getObject().getCreationDate());
		}
	}

	/**
	 * Comparator for comments that compares the users who created the comments.
	 * 
	 * @author Pieter De Graef
	 */
	private class AuthorComparator implements Comparator<AbstractCollapsibleListBlock<CommentDto>> {

		public int compare(AbstractCollapsibleListBlock<CommentDto> one, AbstractCollapsibleListBlock<CommentDto> two) {
			if (one == null && two == null) {
				return 0;
			}
			if (one == null) {
				return -1;
			}
			if (two == null) {
				return 1;
			}
			return one.getObject().getCreatedBy().compareTo(two.getObject().getCreatedBy());
		}
	}

	/**
	 * Comparator for comments that compares the comment titles.
	 * 
	 * @author Pieter De Graef
	 */
	private class TitleComparator implements Comparator<AbstractCollapsibleListBlock<CommentDto>> {

		public int compare(AbstractCollapsibleListBlock<CommentDto> one, AbstractCollapsibleListBlock<CommentDto> two) {
			if (one == null && two == null) {
				return 0;
			}
			if (one == null) {
				return -1;
			}
			if (two == null) {
				return 1;
			}
			return one.getObject().getTitle().compareTo(two.getObject().getTitle());
		}
	}

	private List<AbstractCollapsibleListBlock<CommentDto>> getBlocks() {
		List<AbstractCollapsibleListBlock<CommentDto>> list = new ArrayList<AbstractCollapsibleListBlock<CommentDto>>();
		for (CommentDto comment : getCommentData()) {
			list.add(new CommentBlock(comment));
		}
		return list;
	}

	private List<CommentDto> getCommentData() {
		String lorum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae dolor rutrum sapien"
				+ " dapibus eleifend sed vitae massa. Suspendisse tortor tortor, porttitor quis blandit eget, dictum "
				+ "sed ipsum. Curabitur luctus turpis in diam accumsan convallis. Cras ultrices, ligula id adipiscing "
				+ "facilisis, arcu justo consequat quam, id pulvinar mauris magna ut tortor. Fusce vestibulum ligula"
				+ " at augue feugiat non elementum metus gravida. Nulla elit diam, interdum at lacinia id, pharetra "
				+ "eget tortor. Praesent tincidunt euismod posuere. Nulla sagittis adipiscing lacus, at suscipit leo "
				+ "fringilla sit amet. Cras pellentesque consequat turpis vel interdum. Nunc ligula nunc, porta quis "
				+ "aliquet non, luctus a odio. Sed feugiat pulvinar libero nec commodo. Nulla sed mi nisl. Nulla "
				+ "vulputate pulvinar leo, at accumsan odio fermentum et.";

		CommentDto c1 = new CommentDto();
		c1.setTitle("Comment that expresses my deepest lorum ipsum feelings.");
		c1.setContent(lorum);
		c1.setReportContent("Let's take a longer version of the above, shall we? " + lorum);
		c1.setIncludeInReport(true);
		c1.setCreatedBy("Pieter De Graef");
		c1.setCreationDate(new Date());
		CommentDto c2 = new CommentDto();
		c2.setTitle("Comment that is here just for testing purposes.");
		c2.setContent("Second time...." + lorum);
		c2.setIncludeInReport(false);
		c2.setCreatedBy("Bob De Kerpel");
		c2.setCreationDate(new Date());
		List<CommentDto> list = new ArrayList<CommentDto>();
		Collections.addAll(list, c1, c2, c1, c2, c1, c2, c1, c2, c1, c2, c1, c2);
		return list;
	}
}