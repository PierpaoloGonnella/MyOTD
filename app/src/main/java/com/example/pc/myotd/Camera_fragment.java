package com.example.pc.myotd;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

import com.example.pc.myotd.Aggiungi_capo_fragment;
import com.example.pc.myotd.DatabaseClasses.CustomCursorAdapter;
import com.example.pc.myotd.DatabaseClasses.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;


public class Camera_fragment extends Fragment {
    DatabaseHelper mydb;
    static Cursor cursor;
    Button button;
    ImageView image_view, image_view_shape;
    FloatingActionButton cropButton;
    public static Cursor getCursorfromCamera(){return cursor;}


    Bitmap shape = null;
    public String tipo = "", tag = "";



    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;

    //PARAMETRO CAM_REQUEST IMPORTANTE PER LA FOTOCAMERA
    static final int CAM_REQUEST = 1;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
  /*  public static Camera_fragment newInstance(String param1, String param2) {
        Camera_fragment fragment = new Camera_fragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/
    public Camera_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb=new DatabaseHelper(getContext());

        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(camera_intent, CAM_REQUEST);
     /*   if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_layout_fragment, container, false);
        button = (Button) rootView.findViewById(R.id.capture_button);
        button.setVisibility(View.INVISIBLE);

        image_view = (ImageView) rootView.findViewById(R.id.image_view);
        cropButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        cropButton.hide();
        image_view.setDrawingCacheEnabled(true);
        image_view.buildDrawingCache(true); //this might hamper performance use hardware acc if available. see: http://developer.android.com/reference/android/view/View.html#buildDrawingCache(boolean)
        image_view_shape = (ImageView)rootView.findViewById(R.id.shape);

        image_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // TODO Auto-generated method stub

                ImageView view = (ImageView) v;
                dumpEvent(event);

                // Handle touch events here...
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        savedMatrix.set(matrix);
                        start.set(event.getX(), event.getY());
                        Log.d(String.valueOf(this), "mode=DRAG");
                        mode = DRAG;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = (float) spacing(event);
                        Log.d(String.valueOf(this), "oldDist=" + oldDist);
                        if (oldDist > 10f) {
                            savedMatrix.set(matrix);
                            midPoint(mid, event);
                            mode = ZOOM;
                            Log.d(String.valueOf(this), "mode=ZOOM");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        Log.d(String.valueOf(this), "mode=NONE");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            // ...
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - start.x, event.getY()
                                    - start.y);
                        } else if (mode == ZOOM) {
                            float newDist = (float) spacing(event);
                            Log.d(String.valueOf(this), "newDist=" + newDist);
                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                float scale = newDist / oldDist;
                                matrix.postScale(scale, scale, mid.x, mid.y);
                            }
                        }
                        break;
                }

                view.setImageMatrix(matrix);
                return true;
            }

            private void dumpEvent(MotionEvent event) {
                String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                        "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
                StringBuilder sb = new StringBuilder();
                int action = event.getAction();
                int actionCode = action & MotionEvent.ACTION_MASK;
                sb.append("event ACTION_").append(names[actionCode]);
                if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                        || actionCode == MotionEvent.ACTION_POINTER_UP) {
                    sb.append("(pid ").append(
                            action >> MotionEvent.ACTION_POINTER_INDEX_SHIFT);
                    sb.append(")");
                }
                sb.append("[");
                for (int i = 0; i < event.getPointerCount(); i++) {
                    sb.append("#").append(i);
                    sb.append("(pid ").append(event.getPointerId(i));
                    sb.append(")=").append((int) event.getX(i));
                    sb.append(",").append((int) event.getY(i));
                    if (i + 1 < event.getPointerCount())
                        sb.append(";");
                }
                sb.append("]");
                Log.d(String.valueOf(this), sb.toString());
            }

            /**
             * Determine the space between the first two fingers
             */
            private double spacing(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return Math.sqrt(x * x + y * y);
            }

            /**
             * Calculate the mid point of the first two fingers
             */
            private void midPoint(PointF point, MotionEvent event) {
                float x = event.getX(0) + event.getX(1);
                float y = event.getY(0) + event.getY(1);
                point.set(x / 2, y / 2);
            }

        });
 
   /*     button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent, CAM_REQUEST);
            }
        });*/
        return rootView;
    }
    private File getFile()
    {
        File folder = new File("sdcard/camera_app");
        if(!folder.exists())
        {
            folder.mkdir();
        }
        File image_file = new File(folder,"cam_image.jpg");
        return image_file;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        shape=null;

        tipo = Aggiungi_capo_fragment.getTipo();

        if (tipo.equals("Cravatta")) {
            //  shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_cravatta);
        }
        if (tipo.equals("Cappello")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_cappello);
        }
        if (tipo.equals("Sciarpa")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_sciarpa);
        }
        if (tipo.equals("Cappotto")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_cappotto);
        }
        if (tipo.equals("T-shirt")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_tshirt);
        }
        if (tipo.equals("Maglia")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_maglia);
        }
        if (tipo.equals("Maglione")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_maglione);
        }
        if (tipo.equals("Felpa")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_felpa);
        }
        if (tipo.equals("Cardigan")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_cardigan);
        }
        if (tipo.equals("Polo")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_polo);
        }
        if (tipo.equals("Jeans")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_jeans);
        }
        if (tipo.equalsIgnoreCase("Pantaloni")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_pantaloni);
        }
        if (tipo.equals("Tuta")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_tuta);
        }
        if (tipo.equals("Pantaloncini")) {
            //   shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_pantaloncini);
        }
        if (tipo.equals("Scarpe")) {
            shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_scarpe);
        }
        if (tipo.equals("Calzini")) {
            //   shape = BitmapFactory.decodeResource(getResources(), R.drawable.contorno_calzini);
        }

        image_view_shape.setImageBitmap(shape);
        cropButton.show();
        button.setVisibility(View.INVISIBLE);
        String path = "sdcard/camera_app/cam_image.jpg";

        image_view.setImageDrawable(Drawable.createFromPath(path));

        //SERVE AD ACQUISIRE LA TRAMA E A TAGLIARLA A SECONDA DELLA MASK
        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //use image from cache
                //create the bitmaps
                final Bitmap zoomedBitmap = Bitmap.createScaledBitmap(image_view.getDrawingCache(true), image_view.getWidth(), image_view.getHeight(), false);
                image_view.setDrawingCacheEnabled(false);
                //image_view.setVisibility(View.INVISIBLE);
                //image_view.setImageBitmap(zoomedBitmap);
                image_view.setScaleType(ImageView.ScaleType.CENTER);
                Bitmap mask=null;
                if (tipo.equals("Cravatta")) {
                    //   mask = BitmapFactory.decodeResource(getResources(), R.drawable.cravatta);
                }
                if (tipo.equals("Cappello")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.cappello);
                }
                if (tipo.equals("Sciarpa")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.sciarpa);
                }
                if (tipo.equals("Cappotto")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.cappotto);
                }
                if (tipo.equals("T-shirt")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.tshirt);
                }
                if (tipo.equals("Maglia")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.maglia);
                }
                if (tipo.equals("Maglione")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.maglione);
                }
                if (tipo.equals("Felpa")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.felpa);
                }
                if (tipo.equals("Cardigan")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.cardigan);
                }
                if (tipo.equals("Polo")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.polo);
                }
                if (tipo.equals("Jeans")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.jeans);
                }
                if (tipo.equals("Pantaloni")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.pantaloni);
                }
                if (tipo.equals("Tuta")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.tuta);
                }
                if (tipo.equals("Pantaloncini")) {
                    //    mask = BitmapFactory.decodeResource(getResources(), R.drawable.pantaloncini);
                }
                if (tipo.equals("Scarpe")) {
                    mask = BitmapFactory.decodeResource(getResources(), R.drawable.scarpe);
                }
                if (tipo.equals("Calzini")) {
                    //    mask = BitmapFactory.decodeResource(getResources(), R.drawable.calzini);
                }



                final Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas mCanvas = new Canvas(result);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                mCanvas.drawBitmap(zoomedBitmap, 0, 0, null);
                //mCanvas.drawBitmap(shape, 0, 0, paint);
                mCanvas.drawBitmap(mask, 0, 0, paint);
                paint.setXfermode(null);
                image_view.setImageBitmap(result);
                image_view.setScaleType(ImageView.ScaleType.CENTER);
                final Bitmap finalMask = mask;
                cropButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveImage(result);
                        result.recycle();
                        zoomedBitmap.recycle();
                        shape.recycle();
                        finalMask.recycle();
                        cursor=mydb.getAllData();
                        CustomCursorAdapter nuovo_cca=new CustomCursorAdapter(getContext(), cursor,2);
                        ListView ehi=Armadio_fragment.getlistview();
                        ehi.setAdapter(nuovo_cca);
                        nuovo_cca.notifyDataSetChanged();

                        getActivity().finish();
                    }
                });
            }
        });
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/otd_saved_images");
        myDir.mkdirs();
        tag = Aggiungi_capo_fragment.getNome();
        String fname = "Image-"+ tag +".png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}