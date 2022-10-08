package com.example.khedutmitra;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostVH>{

    Display mDisplay;
    ArrayList<Post> post;
    Context context;
    String path;
    String imagesUri;

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    String PREF_NAME = "LOGIN";
    Bitmap bitmap;
    int totalHeight;
    int totalWidth;
    public static final int READ_PHONE = 110;
    String file_name = "SuccessStory";
    File myPath;
    public PostAdapter(ArrayList<Post> post, Context context) {
        this.post = post;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @Override
    public PostAdapter.PostVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_post,parent,false);
        PostVH svh = new PostVH(view);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = wm.getDefaultDisplay();
        if(Build.VERSION.SDK_INT >= 23){
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
            }else{
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PHONE);
                ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PHONE);
            }
        }
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostVH holder, int position) {
        final Post post1 = post.get(position);
        View v = holder.itemView;
        v = v.findViewById(R.id.post123);
        NestedScrollView z = v.findViewById(R.id.post123);
        holder.txtTitle.setText(post1.getTitle());

        holder.txtDesc.setText(post1.getDescription());

        holder.created_by.setText(post1.getCreatedBy());
        Picasso.get().load(post1.getImg_url()).into(holder.postImg);
        View finalV = v;
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btn.setVisibility(View.GONE);
                File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Success_Story_/");

                if(!folder.exists()){
                    boolean success = folder.mkdir();
                }

                path = folder.getAbsolutePath();
                path = path + "/" + file_name + holder.created_by.getText() + ".pdf";

//        View u = findViewById(R.id.post123);
//
//        NestedScrollView z = findViewById(R.id.post123);
                totalHeight = z.getChildAt(0).getHeight();
                totalWidth = z.getChildAt(0).getWidth();

                String extr = Environment.getExternalStorageDirectory() + "/Success_Story_/";
                File file = new File(extr);
                if(!file.exists()){
                    file.mkdir();}
                String fileName = file_name + ".jpg";
                myPath = new File(extr, fileName);
                imagesUri = myPath.getPath();
                bitmap = getBitmapFromView(finalV, totalHeight, totalWidth);
                try{
                    FileOutputStream fos = new FileOutputStream(myPath);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                createPdf();
                holder.btn.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    class PostVH extends RecyclerView.ViewHolder{

        TextView txtTitle,txtDesc,created_by;
        ImageView postImg;
        Button btn;

        public PostVH(@Nullable View v){
            super(v);
            txtTitle = v.findViewById(R.id.txtTitle);
            txtDesc = v.findViewById(R.id.txtDesc);
            created_by = v.findViewById(R.id.created_by);
            postImg = v.findViewById(R.id.postImg);
            btn = v.findViewById(R.id.btnExplore);
        }
    }
    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth){

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();

        if(bgDrawable != null){
            bgDrawable.draw(canvas);
        }else{
            canvas.drawColor(Color.WHITE);
        }

        view.draw(canvas);
        return returnedBitmap;
    }

    private void createPdf() {

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        Bitmap bitmap = Bitmap.createScaledBitmap(this.bitmap, this.bitmap.getWidth(), this.bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);
        File filePath = new File(path);
        try{
            document.writeTo(new FileOutputStream(filePath));
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(context,"Something Wrong: "+ e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("query",e.toString());

        }
        document.close();
        if (myPath.exists())
            myPath.delete();
        openPdf(path);

    }
    private void openPdf(String path) {
        File file = new File(path);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open FIle");
        try{
            context.startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(context, "No Apps to read PDF FIle", Toast.LENGTH_SHORT).show();
        }
    }
}
