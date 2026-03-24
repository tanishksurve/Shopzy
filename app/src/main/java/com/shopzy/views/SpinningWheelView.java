package com.shopzy.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SpinningWheelView extends View {
    private Paint paint;
    private final int[] colors = {Color.parseColor("#FF6E00"), Color.parseColor("#FFFFFF"), 
                                   Color.parseColor("#FF9100"), Color.parseColor("#F5F5F5"), 
                                   Color.parseColor("#FFB74D"), Color.parseColor("#FFFFFF")};
    private final String[] rewards = {"10% OFF", "Better Luck", "20% OFF", "₹100 Cashback", "Free Delivery", "Better Luck"};
    private float rotationAngle = 0;

    public SpinningWheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(40f);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 40;

        RectF rect = new RectF(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);
        float sweepAngle = 360f / colors.length;

        canvas.rotate(rotationAngle, width / 2, height / 2);

        for (int i = 0; i < colors.length; i++) {
            paint.setColor(colors[i]);
            canvas.drawArc(rect, i * sweepAngle, sweepAngle, true, paint);

            // Draw text
            paint.setColor(Color.BLACK);
            canvas.save();
            canvas.rotate(i * sweepAngle + sweepAngle / 2, width / 2, height / 2);
            canvas.drawText(rewards[i], width / 2 + radius / 2, height / 2 + 10, paint);
            canvas.restore();
        }
        
        // Draw center circle
        paint.setColor(Color.WHITE);
        canvas.drawCircle(width / 2, height / 2, 40, paint);
    }

    public void setRotationAngle(float angle) {
        this.rotationAngle = angle;
        invalidate();
    }
    
    public String getRewardAtAngle(float finalAngle) {
        // Normalize angle to [0, 360)
        float normalizedAngle = (finalAngle % 360 + 360) % 360;
        // The wheel is drawn starting from 0 degrees (right) clockwise.
        // We need to find which segment is at the top (270 degrees) or use a pointer.
        // Let's assume a pointer at the top (270 degrees).
        // Since we rotate the canvas by `rotationAngle`, the segment originally at 
        // angle `S` will be at `S + rotationAngle`.
        // To find what's at 270: S + rotationAngle = 270 => S = 270 - rotationAngle
        float pointerAngle = 270f;
        float segmentAngle = (pointerAngle - normalizedAngle + 360) % 360;
        int index = (int) (segmentAngle / (360f / colors.length));
        return rewards[index % rewards.length];
    }
}
