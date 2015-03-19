package com.duckduckgo.mobile.android.download;

import com.duckduckgo.mobile.android.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

//TODO: Instead of using DownloadDrawable, we can just subclass ImageView with an AsyncImageView or some such...
public class AsyncImageView extends ImageView {
  public String type = null;
  private boolean hideOnDefault = false;
  /**
   * The corner radius of the view (in pixel).
   */
  private float cornerRadius = 0;

  private boolean usePicasso = true;

  private float heightRatio = 0;

  public AsyncImageView(Context context, AttributeSet attr) {
    super(context, attr);
    getXMLAttribute(context, attr);
  }

  public AsyncImageView(Context context, AttributeSet attr, int defStyle) {
    super(context, attr, defStyle);
    getXMLAttribute(context, attr);
  }

  public AsyncImageView(Context context) {
    super(context);
  }

  /**
   * Get parameters in xml layout.
   *
   * @param context
   * @param attrs
   */
  private void getXMLAttribute(Context context, AttributeSet attrs) {
    // Get proportion.
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AsyncImageView);
    cornerRadius = a.getDimension(R.styleable.AsyncImageView_cornerRadius, 0);
    usePicasso = a.getBoolean(R.styleable.AsyncImageView_usePicasso, true);
    heightRatio = a.getFloat(R.styleable.AsyncImageView_heightRatio, 0);
    a.recycle();
  }

  public float getCornerRadius() {
    return this.cornerRadius;
  }

  /**
   * Set corner radius.
   *
   * @param radius Corder radius in pixel.
   */
  public void setCornerRadius(int radius) {
    this.cornerRadius = radius;
  }

  public void setBitmap(Bitmap bitmap) {
    if (bitmap == null)
      return;

    setImageBitmap(bitmap);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (usePicasso) {
      Picasso.with(getContext()).cancelRequest(this);
    }
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);

    if (visibility != View.VISIBLE && usePicasso) {
      Picasso.with(getContext()).cancelRequest(this);
    }
  }
/*
    @Override
    public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        //Log.e("aaa", "Feed image on measure");
        final int width;
        final int height;
        if(heightRatio!=0) {
            //Log.e("aaa", "height ratio != 0: "+heightRatio);
            width = MeasureSpec.getSize(widthMeasureSpec);
            //float tempHeight = widthMeasureSpec * heightRatio;
            height = (int) (width * heightRatio);
            Log.e("aaa", "width: "+width+" - widthMeasureSpec: "+widthMeasureSpec);
            Log.e("aaa", "height: "+height+" - heightMeasureSpec: "+heightMeasureSpec);
            Log.e("aaa", "ratio: "+heightRatio);
            setMeasuredDimension(width, height);
        } else {
            //Log.e("aaa", "height ratio ==0 ")
            //width = widthMeasureSpec;
            //height = heightMeasureSpec;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        //super.onMeasure(width, height);
    }
*/
}
