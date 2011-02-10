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

package org.ktunaxa.referral.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ktunaxa.referral.client.widget.CollapsibleListBlock;
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
		setSize("100%", "100%");

		Map<String, Comparator<CollapsibleListBlock<CommentDto>>> sortAttributes;
		sortAttributes = new HashMap<String, Comparator<CollapsibleListBlock<CommentDto>>>();
		sortAttributes.put("date", new DateComparator());
		sortAttributes.put("author", new AuthorComparator());
		sortAttributes.put("title", new TitleComparator());
		ListView<CommentDto> listView = new ListView<CommentDto>(sortAttributes);
		DetailView<CommentDto> detailView = new DetailView<CommentDto>();
		ListLayout<CommentDto> layout = new ListLayout<CommentDto>(listView, detailView);
		layout.populate(getBlocks());
		addMember(layout);
	}

	/**
	 * Comparator for comments that compares the creation dates.
	 * 
	 * @author Pieter De Graef
	 */
	private class DateComparator implements Comparator<CollapsibleListBlock<CommentDto>> {

		public int compare(CollapsibleListBlock<CommentDto> one, CollapsibleListBlock<CommentDto> two) {
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
	private class AuthorComparator implements Comparator<CollapsibleListBlock<CommentDto>> {

		public int compare(CollapsibleListBlock<CommentDto> one, CollapsibleListBlock<CommentDto> two) {
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
	private class TitleComparator implements Comparator<CollapsibleListBlock<CommentDto>> {

		public int compare(CollapsibleListBlock<CommentDto> one, CollapsibleListBlock<CommentDto> two) {
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

	private List<CollapsibleListBlock<CommentDto>> getBlocks() {
		List<CollapsibleListBlock<CommentDto>> list = new ArrayList<CollapsibleListBlock<CommentDto>>();
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
		c1.setCheckedContent("Let's take a longer version of the above, shall we? " + lorum);
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