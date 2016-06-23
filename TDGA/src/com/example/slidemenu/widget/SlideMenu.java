package com.example.slidemenu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlideMenu extends ViewGroup {

	private final String TAG = SlideMenu.class.getSimpleName();

	private View mMenuView; // �˵�

	private View mContentView;// ����

	private int mPreX; // ֮ǰ��x������

	private int mTouchSlop; // ��Ϊ���û��ǻ�������С�������ϵͳ��

	private Scroller mScroller; // ģ�⻬��ʱʹ��

	private final int LOCATION_CONTENT = 0;
	private final int LOCATION_MENU = 1;

	private int mLocation = LOCATION_CONTENT;

	private int mDuration = 300; // ������ʱ��

	public SlideMenu(Context context, AttributeSet attrs) {
		super(context, attrs);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		mScroller = new Scroller(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.i(TAG, "onMeasure");

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int measureWidth = measureWidth(widthMeasureSpec); // ��ȡ�����Ŀ��

		int measureHeight = measureHeight(heightMeasureSpec);// ��ȡ�����ĸ߶�

		// �����Զ����ViewGroup�������ӿؼ��Ĵ�С
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		// �����Զ���Ŀؼ��Ĵ�С
		setMeasuredDimension(measureWidth, measureHeight);

	}

	private int measureWidth(int widthMeasureSpec) {
		return getSize(widthMeasureSpec);
	}

	private int getSize(int measureSpec) {
		int result = 0;
		int mode = MeasureSpec.getMode(measureSpec);// �õ�ģʽ
		int size = MeasureSpec.getSize(measureSpec);// �õ��ߴ�

		switch (mode) {
		/**
		 * mode�������������ȡֵ�ֱ�ΪMeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
		 * MeasureSpec.AT_MOST��
		 * 
		 * 
		 * MeasureSpec.EXACTLY�Ǿ�ȷ�ߴ磬
		 * �����ǽ��ؼ���layout_width��layout_heightָ��Ϊ������ֵʱ��andorid
		 * :layout_width="50dip"������ΪFILL_PARENT�ǣ����ǿؼ���С�Ѿ�ȷ������������Ǿ�ȷ�ߴ硣
		 * 
		 * 
		 * MeasureSpec.AT_MOST�����ߴ磬
		 * ���ؼ���layout_width��layout_heightָ��ΪWRAP_CONTENTʱ
		 * ���ؼ���Сһ�����ſؼ����ӿռ�����ݽ��б仯����ʱ�ؼ��ߴ�ֻҪ���������ؼ���������ߴ缴��
		 * ����ˣ���ʱ��mode��AT_MOST��size�����˸��ؼ���������ߴ硣
		 * 
		 * 
		 * MeasureSpec.UNSPECIFIED��δָ���ߴ磬����������࣬һ�㶼�Ǹ��ؼ���AdapterView��
		 * ͨ��measure���������ģʽ��
		 */
			case MeasureSpec.AT_MOST:
			case MeasureSpec.EXACTLY:
				result = size;
				break;
		}

		return result;
	}

	private int measureHeight(int heightMeasureSpec) {
		return getSize(heightMeasureSpec);
	}

	/**
	 * ��ȡ�ӿؼ�
	 */
	private void findChildView() {

		mMenuView = getChildAt(0); // ��Ҫ�����ӿؼ��Ĳ���˳��ȷ�����

		mContentView = getChildAt(1);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		findChildView();

		mMenuView.layout(-mMenuView.getMeasuredWidth(), 0, 0, b); // ��menu������Ļ������

		mContentView.layout(l, t, r, b); // ռ��������Ļ

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				mPreX = (int) event.getX(); // ��¼����ʱ��x������
				break;
			}
			case MotionEvent.ACTION_UP: {

				// ��ȡ�ؼ�λ���ĸ�����
				mLocation = getScreenLocation();

				// �������ý���
				toLocation();

				break;
			}
			case MotionEvent.ACTION_MOVE: {

				// ��ȡ�ƶ��е�����
				int moveX = (int) event.getX();
				int dx = mPreX - moveX;

				// �ж��Ƿ��ƶ����Ƿ񳬹��߽�
				// Log.i(TAG, "getScrollX=" + getScrollX() + "   dx=" + dx
				// + "  content width=" + mContentView.getWidth());
				int scrollX = getScrollX() + (int) dx; // getScrollX()Ϊ�ؼ�x�᷽���Ѿ������ľ���
				if (scrollX > 0) { // �����ұ߾�
					scrollTo(0, 0);
				}
				else if (scrollX < -mMenuView.getWidth()) { // ������߾�
					scrollTo(-mMenuView.getWidth(), 0);
				}
				else {
					scrollBy(dx, 0); // ʹ���λ��dx����λ������Ϊ���ƣ�����Ϊ����
				}

				mPreX = moveX;

				break;
			}
			default:
				break;
		}

		return true;
	}

	@Override
	public void computeScroll() {

		Log.i(TAG, "computeScroll");

		// ����x��ƫ��������scroller���ʹ��
		if (mScroller.computeScrollOffset()) { // �����Ƿ����
			scrollTo(mScroller.getCurrX(), 0);
		}

		invalidate();

	}

	private void toLocation() {

		int startX = getScrollX();
		int dx = 0;
		if (mLocation == LOCATION_MENU) {
			dx = -(mMenuView.getWidth() + startX);
		}
		else {
			dx = 0 - startX;
		}

		mScroller.startScroll(startX, 0, dx, 0, mDuration);
	}

	private int getScreenLocation() {
		// ��Ϊ�л���menu����ľ���
		int locateMenuSlop = (int) (mMenuView.getWidth() / 2);

		// ��õ�ǰ�ؼ�λ���ĸ�����
		int scrollX = getScrollX();

		return (Math.abs(scrollX) >= locateMenuSlop ? LOCATION_MENU
				: LOCATION_CONTENT);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// ����listview���һ���ʱ�������˵����ܻ��������

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN: {

				mPreX = (int) ev.getX();

				break;
			}
			case MotionEvent.ACTION_UP: {

				break;
			}
			case MotionEvent.ACTION_MOVE: {

				int moveX = (int) ev.getX();

				int dx = moveX - mPreX;
				if (Math.abs(dx) > mTouchSlop) { // ��Ϊ�Ǻ����ƶ������ĵ����¼������⴫�ݵ�Listview�ĵ���¼�
					return true;
				}

				break;
			}
			default:
				break;
		}

		return super.onInterceptTouchEvent(ev);
	}

}
