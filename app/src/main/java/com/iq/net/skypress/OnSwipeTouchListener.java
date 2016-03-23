package com.iq.net.skypress;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

/**
 * Detects left and right swipes across a view.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {
//	private ViewSwitcher imageViewSwitcher;
//	private ViewSwitcher textViewSwitcher;
	private ViewFlipper viewFlipper;
	private final GestureDetector gestureDetector;
	private Animation slideInRightAnimation;
	private Animation slideOutLeftAnimation;
	private Animation slideInLeftAnimation;
	private Animation slideOutRightAnimation;

	public OnSwipeTouchListener(Context context, ViewFlipper viewFlipper, Animation slideInRightAnimation,
								Animation slideOutLeftAnimation, Animation slideInLeftAnimation,
								Animation slideOutRightAnimation) {
//	public OnSwipeTouchListener(Context context, ViewSwitcher imageViewSwitcher, ViewSwitcher textViewSwitcher) {
		gestureDetector = new GestureDetector(context, new GestureListener());
		this.viewFlipper = viewFlipper;
		this.slideInRightAnimation = slideInRightAnimation;
		this.slideOutLeftAnimation = slideOutLeftAnimation;
		this.slideInLeftAnimation = slideInLeftAnimation;
		this.slideOutRightAnimation = slideOutRightAnimation;
//		this.imageViewSwitcher = imageViewSwitcher;
//		this.textViewSwitcher = textViewSwitcher;
	}

	public void onSwipeLeft() {
//		if (imageViewSwitcher.getDisplayedChild() == imageViewSwitcher.getChildCount()) {
//			imageViewSwitcher.showPrevious();
//			textViewSwitcher.showPrevious();
//		} else {
//			imageViewSwitcher.showNext();
//			textViewSwitcher.showNext();
//		}
//		imageViewSwitcher.showPrevious();
//		textViewSwitcher.showPrevious();
		this.viewFlipper.setInAnimation(slideInRightAnimation);
		this.viewFlipper.setOutAnimation(slideOutLeftAnimation);
		this.viewFlipper.showPrevious();
		this.viewFlipper.stopFlipping();
	}

	public void onSwipeRight() {
//		if (imageViewSwitcher.getDisplayedChild() == 0) {
//			imageViewSwitcher.showNext();
//			textViewSwitcher.showNext();
//		} else {
//			imageViewSwitcher.showPrevious();
//			textViewSwitcher.showPrevious();
//		}
//		imageViewSwitcher.showNext();
//		textViewSwitcher.showNext();
		this.viewFlipper.setInAnimation(slideInLeftAnimation);
		this.viewFlipper.setOutAnimation(slideOutRightAnimation);
		this.viewFlipper.showNext();
		this.viewFlipper.stopFlipping();
	}

	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

		private static final int SWIPE_DISTANCE_THRESHOLD = 100;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float distanceX = e2.getX() - e1.getX();
			float distanceY = e2.getY() - e1.getY();
			if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
				if (distanceX > 0)
					onSwipeRight();
				else
					onSwipeLeft();
				return true;
			}
			return false;
		}
	}
}