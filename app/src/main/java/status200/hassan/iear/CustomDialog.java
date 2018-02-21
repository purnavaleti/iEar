package status200.hassan.iear;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by saravana on 26/2/17.
 */

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button yes, no;
    TextView txt;
    String words;
    Context context;
    EditText nameedit;

    public CustomDialog(Activity a, String w,Context context) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.words = w;
        this.context = context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        txt = (TextView) findViewById(R.id.dialogt);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        nameedit = (EditText) findViewById(R.id.nameedit) ;
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        txt.setText(words);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:

//
                if(nameedit.getText().toString() == null){
                    Toast.makeText(context, "Enter the name", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getContext(), Dialogs.class);

                    intent.putExtra("edittext", txt.getText().toString());
                    intent.putExtra("nameedit", nameedit.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }


}
