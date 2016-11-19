package entity.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import entity.Section;

public class SimpleSection implements Section {

	private String sectionName;
	private int sectionId;
	private List<Section> childSections;
	private Section parrent;
	private int parrentId ;
	private int level ;
	private List<Section> allChilds;
	private int sectionView;

	public void setAllChilds(List<Section> allChilds) {
		this.allChilds = allChilds;
	}

	@Override
	public int getSectionId() {
		return sectionId;
	}

//	private SimpleSection(int sectionId, String sectionName, int parrentId) {
//		super();
//		this.sectionId = sectionId;
//		this.sectionName = sectionName;
//		this.childSections = null;
//		this.parrentId = parrentId;
//	}

	public SimpleSection(int sectionId, String sectionName, int parrentId, int sectionView) {
		super();
		this.sectionId = sectionId;
		this.sectionName = sectionName;
		this.childSections = null;
		this.parrentId = parrentId;
		this.sectionView = sectionView;
	}
	
	@Override
	public String getSectionName() {
		return sectionName;
	}

	@Override
	public List<Section> getChildSections() {
		return childSections;
	}

	@Override
	public void addChildSection(Section section) {
		if (this.childSections == null) {
			this.childSections = new LinkedList<Section>();
		}
		
		
		childSections.add(section);
		
		 Collections.sort(childSections, new Comparator<Section>() {
	         @Override
	         public int compare(Section o1, Section o2) {
	             return o1.getParrentId()-o2.getParrentId();
	         }
	     });
	}

	@Override
	public int getParrentId() {
		return parrentId;
	}

	@Override
	public Section getParrent() {
		return parrent;
	}

	@Override
	public void setParrent(Section parrent) {
		if (this.getParrentId() == parrent.getSectionId()) {
			this.parrent = parrent;
		} else
			throw new RuntimeException("wrong parent");

	}

	@Override
	public void setLevel(int level) {
		this.level = level;

	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public List<Section> getAllChilds() {
		return allChilds;
	}

	@Override
	public Section getRootSetion() {
		Section root = this;
		while(root.getParrent()!=null){
			root=root.getParrent();
		}
		return root;
	}

	@Override
	public int getSectionView() {
		return sectionView;
	}
	
	 


}
