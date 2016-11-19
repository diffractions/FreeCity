package entity.impl;

import entity.Tag;

public class SimpleTagImpl implements Tag {

	private int tagId;
	private String tagName;

	public SimpleTagImpl(int tagId, String tagName) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
	}

	@Override
	public String getTagName() {
		return tagName;
	}

	@Override
	public int getTagId() {
		return tagId;
	}

	@Override
	public String toString() {
		return "SimpleTagImpl [tagId=" + tagId + ", tagName=" + tagName + "]";
	}

}
