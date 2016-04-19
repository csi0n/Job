package com.csi0n.searchjob.ui.widget.photoview;

import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by csi0n on 2015/9/26 0026.
 */
public interface IPhotoView {
    boolean canZoom();
    RectF getDisplayRect();
    float getMinScale();
    float getMidScale();
    float getMaxScale();
    float getScale();
    ImageView.ScaleType getScaleType();
    void setAllowParentInterceptOnEdge(boolean allow);
    void setMinScale(float minScale);
    void setMidScale(float midScale);
    void setMaxScale(float maxScale);
    void setOnLongClickListener(View.OnLongClickListener listener);
    void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener);
    void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener);
    void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener);
    void setScaleType(ImageView.ScaleType scaleType);
    void setZoomable(boolean zoomable);
    void zoomTo(float scale, float focalX, float focalY);
}
