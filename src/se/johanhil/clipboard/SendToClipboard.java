/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package se.johanhil.clipboard;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.ClipData;
import android.util.Log;
import android.widget.Toast;

public class SendToClipboard extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // and the text 
        // TODO perhaps there are more fields like "EXTRA_SUBJECT", this should be handled.
        Intent intent = getIntent();
        
        if (intent.hasExtra(Intent.EXTRA_STREAM) && "text/x-vcard".equals(intent.getType()))
        {
        	Uri stream = (Uri) intent.getExtras().get(Intent.EXTRA_STREAM);
        	Cursor contact = managedQuery(stream, null, null, null, null);
        	
        	if (! (contact != null && contact.isFirst()))
        	{
        		contact.moveToFirst();
        		String[] names = contact.getColumnNames();
        		for (String name : names)
        		{
        			Log.d("foo", name);
        		}
        	}
        }
        
        CharSequence text = intent.getCharSequenceExtra(Intent.EXTRA_TEXT);
        
        // and put the text the clipboard
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            // get the clipboard system service
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(label, text);
            // https://developer.android.com/reference/android/content/ClipData.html
            clipboard.setPrimaryClip(clip);
        }
        else{
            // get the clipboard system service
            // https://stackoverflow.com/a/19253868/4723940
            android.text.ClipboardManager deprecatedClipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            // https://stackoverflow.com/a/18355356/4723940
            deprecatedClipboard.setText(text);
        }
        
        // alert the user that the text is in the clipboard and we're done
        Toast.makeText(this, R.string.text_copied, Toast.LENGTH_SHORT).show();
        
        finish();
    }
}
