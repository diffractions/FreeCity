package entity.impl;

import entity.Rating;

public class SimpleRatingImpl implements Rating {

	private int stars;
	private int voice;

	
	
	public SimpleRatingImpl(int stars, int voice) {
		super();
		this.stars = stars;
		this.voice = voice;
	}

	@Override
	public int getStars() {
		// TODO Auto-generated method stub
		return stars;
	}

	@Override
	public int getVoice() {
		// TODO Auto-generated method stub
		return voice;
	}

	@Override
	public void addVoice() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delVoice() {
		// TODO Auto-generated method stub

	}

}
