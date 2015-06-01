package edu.ncc.cis18b.project.Borrow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import com.parse.ParseObject;
import com.parse.ParseClassName;

import java.io.ByteArrayOutputStream;

/**
 * Created by S on 5/16/2015.
 */
@ParseClassName("BorrowObject")
public abstract class BorrowObject extends ParseObject
{
    public final static String KEY_PIC = "pic";
    public final static String KEY_DISPLAY_NAME = "desiredUserCase";

    public abstract void viewObject(Context context);
    public abstract int getArrayAdapterStyle();

    public void setPic(Bitmap pic)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        pic.compress(Bitmap.CompressFormat.PNG, 100, stream);

        this.put(KEY_PIC, stream.toByteArray());
    }

    // TODO: replace alt setPic() with this
    public void setPic(byte[] data)
    {
        this.put(KEY_PIC, data);
    }

    public Bitmap getPic()
    {
        byte[] pic = getBytes(KEY_PIC);
        return BitmapFactory.decodeByteArray(pic, 0, pic.length);
    }

    // TODO: remove/keep this method - doesn't have much purpose anymore
    public Bitmap getPic(int width, int height)
    {
        Bitmap pic = getPic();

        width = (width > pic.getWidth()) ? width : pic.getWidth();
        height = (height > pic.getHeight()) ? height : pic.getHeight();

        return ThumbnailUtils.extractThumbnail(pic, width, height);
    }
}