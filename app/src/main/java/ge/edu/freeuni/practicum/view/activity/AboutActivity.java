package ge.edu.freeuni.practicum.view.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import ge.edu.freeuni.practicum.R;

public class AboutActivity extends AppCompatActivity {

    private CoordinatorLayout mRootLayout;
    private String mLogoClickMsg1;
    private String mLogoClickMsg2;
    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initToolbar();
        initRootLayout();
        initInstances();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initRootLayout() {
        mRootLayout = (CoordinatorLayout) findViewById(R.id.root_layout);
    }

    private void initInstances() {
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(getString(R.string.about_us_title));

        TextView repoText = (TextView) mRootLayout.findViewById(R.id.app_github_repo);
        repoText.setText(Html.fromHtml("GitHub: <a href=\"https://github.com/gati3478/Practicum\">Repository</a>"));
        repoText.setMovementMethod(LinkMovementMethod.getInstance());

        TextView gpetr12Email = (TextView) mRootLayout.findViewById(R.id.text_view_gpetr12_email);
        gpetr12Email.setText(Html.fromHtml("<a href=\"mailto:gpetr12@freeuni.edu.ge\">gpetr12@freeuni.edu.ge</a>"));
        gpetr12Email.setMovementMethod(LinkMovementMethod.getInstance());

        TextView gpetr12Repo = (TextView) mRootLayout.findViewById(R.id.text_view_gpetr12_repo);
        gpetr12Repo.setText(Html.fromHtml("<a href=\"https://github.com/gati3478\">GitHub</a>"));
        gpetr12Repo.setMovementMethod(LinkMovementMethod.getInstance());

        TextView gkink12Email = (TextView) mRootLayout.findViewById(R.id.text_view_gkink12_email);
        gkink12Email.setText(Html.fromHtml("<a href=\"mailto:gkink12@freeuni.edu.ge\">gkink12@freeuni.edu.ge</a>"));
        gkink12Email.setMovementMethod(LinkMovementMethod.getInstance());

        TextView gkink12Repo = (TextView) mRootLayout.findViewById(R.id.text_view_gkink12_repo);
        gkink12Repo.setText(Html.fromHtml("<a href=\"https://github.com/gkink\">GitHub</a>"));
        gkink12Repo.setMovementMethod(LinkMovementMethod.getInstance());

        mLogoClickMsg1 = getString(R.string.logo_click_msg_1);
        mLogoClickMsg2 = getString(R.string.logo_click_msg_2);

        rand = new Random();
    }

    public void onLogoClick(View view) {
        int res = rand.nextInt(2);
        String msg = res == 0 ? mLogoClickMsg1 : mLogoClickMsg2;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
