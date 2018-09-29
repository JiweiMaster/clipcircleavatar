# clipcircleavatar
a light circle-picture-clip tool

#具体的使用方法请见release里面的说明

一个很轻量的圆形头像裁剪工具
使用方法：
1--添加jitpack
在你的build.gradle中添加
allprojects {
repositories {
...
  maven { url 'https://jitpack.io' }
  }
}


添加依赖 app.builder中
dependencies {
	implementation 'com.github.User:Repo:Tag'
}


2--builder之后就可以直接使用了
1、layout添加
    <com.jiwei.clipcircleavatar.ClipAvatarView
        android:id="@+id/myAvatarView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
	
2、调用类
    ClipAvatarView clipAvatarView = this.findViewById(R.id.myAvatarView);
    
3、设置图片源
    clipAvatarView.setSourceBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.pic2));
    
4、获取裁剪之后的图片
    outputImageView.setImageBitmap(clipAvatarView.getClipImage());

效果如下图：（见release）










