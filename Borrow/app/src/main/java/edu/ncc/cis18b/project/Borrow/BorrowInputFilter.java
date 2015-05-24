package edu.ncc.cis18b.project.Borrow;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

/**
 * Created by S on 5/23/2015.
 */
public class BorrowInputFilter implements InputFilter
{
    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        if (source instanceof SpannableStringBuilder) {
            SpannableStringBuilder sourceAsSpannableBuilder = (SpannableStringBuilder)source;
            for (int i = end - 1; i >= start; i--) {
                char currentChar = source.charAt(i);
                if (!Character.isLetterOrDigit(currentChar) && !Character.isSpaceChar(currentChar)) {
                    sourceAsSpannableBuilder.delete(i, i+1);
                }
            }
            return source;
        } else {
            StringBuilder filteredStringBuilder = new StringBuilder();
            for (int i = start; i < end; i++) {
                char currentChar = source.charAt(i);
                if (Character.isLetterOrDigit(currentChar) || Character.isSpaceChar(currentChar)) {
                    filteredStringBuilder.append(currentChar);
                }
            }
            return filteredStringBuilder.toString();
        }
    }
}