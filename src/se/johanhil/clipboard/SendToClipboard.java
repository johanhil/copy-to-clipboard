package se.johanhil.clipboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.widget.Toast;

public class SendToClipboard extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // get the clipboard system service
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);
        
        // and the text 
        // TODO perhaps there are more fields like "EXTRA_SUBJECT", this should be handled.
        Intent intent = getIntent();
        CharSequence text = intent.getCharSequenceExtra(Intent.EXTRA_TEXT);
        
        // and put the text the clipboard
        clipboardManager.setText(text);
        
        // alert the user that the text is in the clipboard and we're done
        Toast.makeText(this, R.string.text_copied, Toast.LENGTH_SHORT).show();
        
        finish();
    }
}