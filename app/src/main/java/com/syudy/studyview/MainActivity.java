package com.syudy.studyview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jiwei.clipcircleavatar.ClipAvatarView;
import com.syudy.studyview.BallView.BallMoveView;
import com.syudy.studyview.BallView.CheckCodingView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
//    BallMoveView ballMoveView;
//    CheckCodingView checkCodingView;
    ImageView clipeImageView;
    Button clipButton;
    ClipAvatarView clipAvatarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ballMoveView = this.findViewById(R.id.ballView);
//        checkCodingView = this.findViewById(R.id.checkCodeView);
        clipAvatarView = this.findViewById(R.id.clipAvatarView);
        clipeImageView = this.findViewById(R.id.clipImageView);
        clipButton = this.findViewById(R.id.clipButton);

//        Bitmap sourceBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.sun);
//        clipAvatarView.setSourceBitmap(sourceBitmap);
        clipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clipeImageView.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.example));
                clipeImageView.setImageBitmap(clipAvatarView.getClipImage());
            }
        });

    }
}
