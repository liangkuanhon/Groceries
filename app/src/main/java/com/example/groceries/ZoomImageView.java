package com.example.groceries;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.appcompat.widget.AppCompatImageView;

public class ZoomImageView extends AppCompatImageView {

    private boolean imageInitialized = false;
    private int viewWidth = 0;
    private int viewHeight = 0;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    private enum Mode { NONE, DRAG, ZOOM }
    private Mode mode = Mode.NONE;

    private PointF start = new PointF();
    private ScaleGestureDetector scaleDetector;

    private float[] matrixValues = new float[9];
    private float minScale = 0.25f;
    private float maxScale = 1.5f;

    public ZoomImageView(Context context) {
        super(context);
        init(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setScaleType(ScaleType.MATRIX);
        setImageMatrix(matrix);
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public void setImageDrawable(android.graphics.drawable.Drawable drawable) {
        super.setImageDrawable(drawable);
        imageInitialized = false; // Reset so it fits again
        fitImageToView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = Mode.DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                savedMatrix.set(matrix);
                mode = Mode.ZOOM;
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == Mode.DRAG && !scaleDetector.isInProgress()) {
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.set(savedMatrix);
                    matrix.postTranslate(dx, dy);
                    constrainTranslation();
                    setImageMatrix(matrix);
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = Mode.NONE;
                break;
        }

        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();

            matrix.getValues(matrixValues);
            float currentScale = matrixValues[Matrix.MSCALE_X];
            float newScale = currentScale * scaleFactor;

            // Clamp
            if (newScale < minScale) {
                scaleFactor = minScale / currentScale;
            } else if (newScale > maxScale) {
                scaleFactor = maxScale / currentScale;
            }

            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            constrainTranslation();
            setImageMatrix(matrix);
            return true;
        }
    }

    private void constrainTranslation() {
        RectF bounds = getImageBounds();
        float dx = 0, dy = 0;
        float viewWidth = getWidth();
        float viewHeight = getHeight();

        if (bounds.width() > viewWidth) {
            if (bounds.left > 0) dx = -bounds.left;
            else if (bounds.right < viewWidth) dx = viewWidth - bounds.right;
        } else {
            dx = (viewWidth - bounds.width()) / 2f - bounds.left;
        }

        if (bounds.height() > viewHeight) {
            if (bounds.top > 0) dy = -bounds.top;
            else if (bounds.bottom < viewHeight) dy = viewHeight - bounds.bottom;
        } else {
            dy = (viewHeight - bounds.height()) / 2f - bounds.top;
        }

        matrix.postTranslate(dx, dy);
    }

    private RectF getImageBounds() {
        if (getDrawable() == null) return new RectF();
        Matrix tempMatrix = new Matrix(matrix);
        RectF rect = new RectF(0, 0,
                getDrawable().getIntrinsicWidth(),
                getDrawable().getIntrinsicHeight());
        tempMatrix.mapRect(rect);
        return rect;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        fitImageToView();
    }

    private void fitImageToView() {
        if (getDrawable() == null || imageInitialized || viewWidth == 0 || viewHeight == 0) return;

        float imgWidth = getDrawable().getIntrinsicWidth();
        float imgHeight = getDrawable().getIntrinsicHeight();

        float scale = Math.min(viewWidth / imgWidth, viewHeight / imgHeight);

        float dx = (viewWidth - imgWidth * scale) / 2;
        float dy = (viewHeight - imgHeight * scale) / 2;

        matrix.setScale(scale, scale);
        matrix.postTranslate(dx, dy);
        setImageMatrix(matrix);

        imageInitialized = true;
    }

}