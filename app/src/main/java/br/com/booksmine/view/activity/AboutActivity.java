package br.com.booksmine.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.booksmine.R;
import br.com.booksmine.databinding.ActivityAboutBinding;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Developed by.:   @thiagozg on 25/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */
public class AboutActivity extends AppCompatActivity {

    ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_about);

        this.populateTextViewContent();
        PhotoViewAttacher photoView = new PhotoViewAttacher(this.binding.ivDiagram);
    }

    private void populateTextViewContent() {
        String[] contentArray = getResources().getStringArray(R.array.about_content_array);
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : contentArray) {
            stringBuilder.append("\n" + item);
        }
        this.binding.tvContent.setText(stringBuilder.toString());
    }

}
