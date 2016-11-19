package entity.impl;

import java.util.Arrays;

import entity.Img;

public class SimpleImgImpl implements Img {

	private int imgId;

	@Override
	public String toString() {
		return "SimpleImgImpl [imgId=" + imgId + ", name=" + name + ", contentType=" + contentType + ", img="
				+ Arrays.toString(img) + "]";
	}

	public SimpleImgImpl(int imgId, String name, String contentType, byte[] img) {
		super();
		this.imgId = imgId;
		this.name = name;
		this.contentType = contentType;
		this.img = img;
	}

	private String name;
	private String contentType;
	private byte[] img;

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public String grtName() {
		return name;
	}

	@Override
	public byte[] getImg() {
		return img;
	}

	@Override
	public int getImgId() {
		return imgId;
	}

}
