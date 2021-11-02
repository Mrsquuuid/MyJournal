package com.example.mydiary.camerafragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import android.view.View;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.mydiary.R;
import com.example.mydiary.cameraview.PictureSelectorDialog;
import com.example.mydiary.db.ImageDatabaseHelper;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Hailin Xiong on 2021/10/25.
 *
 */
public abstract class PictureSelectorFragment extends Fragment implements PictureSelectorDialog.OnSelectedListener {

    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    /**
     * gallery mark
     */
    private static final int GALLERY_REQUEST_CODE = 0;
    /**
     * camera mark
     */
    private static final int CAMERA_REQUEST_CODE = 1;
    /**
     * Temporary picture
     */
    private String mTempPhotoPath;
    /**
     * Picture after crop
     */
    private Uri mDestination;

    /**
     * dialog PopupWindow
     */
    private PictureSelectorDialog mSelectPictureDialog;
    /**
     * Listening callback for picture selection
     */
    private OnPictureSelectedListener mOnPictureSelectedListener;

    /**
     * crop the picture
     */
    protected void selectPicture() {
        mSelectPictureDialog.show(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDestination = Uri.fromFile(new File(getContext().getCacheDir(), "cropImage.jpeg"));
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";
        mSelectPictureDialog = PictureSelectorDialog.getInstance();
        mSelectPictureDialog.setOnSelectedListener(this);
    }

    @Override
    public void OnSelected(View v, int position) {
        switch (position) {
            case 0:
                // "Take Picture" button been clicked
                takePhoto();
                break;
            case 1:
                // "Select from album" button been clicked
                pickFromGallery();
                break;
            case 2:
                // "cancel" button been clicked
                mSelectPictureDialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
                pickFromGallery();
            } else if (requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION) {
                takePhoto();
            }
        }
    }

    private void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            //requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    //getString(R.string.permission_write_storage_rationale),
                    //REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            mSelectPictureDialog.dismiss();

            File tempPhotoFile = new File(mTempPhotoPath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // If over Android7.0, use FileProvider to get Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                String authority = getContext().getPackageName() + ".fileProvider";
                Uri contentUri = FileProvider.getUriForFile(getContext(), authority, tempPhotoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempPhotoFile));
            }
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }

    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            //requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    //getString(R.string.permission_read_storage_rationale),
                    //REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            mSelectPictureDialog.dismiss();
            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
            // If restrict the types of pictures uploaded,can write directly as:"image/jpeg,image/png"
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
        }
    }

    boolean cameraFlag = false;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                // Call the camera to take pictures
                case CAMERA_REQUEST_CODE:
                    File temp = new File(mTempPhotoPath);
                    cameraFlag = true;
                    startCropActivity(Uri.fromFile(temp));
                    break;
                // Get directly from album
                case GALLERY_REQUEST_CODE:
                    startCropActivity(data.getData());
                    break;
                // Cropped picture
                case UCrop.REQUEST_CROP:
                    handleCropResult(data);
                    break;
                // Crop picture error
                case UCrop.RESULT_ERROR:
                    handleCropError(data);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Implementation of picture cropping
     *
     * @param source
     */
    public void startCropActivity(Uri source) {
        UCrop.Options options = new UCrop.Options();
        // change the color of the tool bar
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        // change the color of the status bar
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        // Hide the bottom controller
        options.setHideBottomControls(true);
        // Set the format of  the picture
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        // Set picture compression quality
        options.setCompressionQuality(100);

        options.setToolbarTitle("Crop the picture");


        UCrop.of(source, mDestination)
                // width height ratio
                .withAspectRatio(1, 1)
                // image size demand
                .withMaxResultSize(512, 512)
                // configuration parameter
                .withOptions(options)
                .start(getContext(), this);
    }

    private ImageDatabaseHelper mySQLiteOpenHelper;
    SQLiteDatabase mydb;

    String id="1";//赋值1，2，3...，分别是第一，第二，第三张图片...


    /**
     * Return value of successful processing
     *
     * @param result
     */
    private void handleCropResult(Intent result) {
        deleteTempPhotoFile();
        final Uri resultUri = UCrop.getOutput(result);
        if (null != resultUri && null != mOnPictureSelectedListener) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), resultUri);

                // store the image into the database
                mySQLiteOpenHelper = new ImageDatabaseHelper(getContext(), "image.db", null, 1);
                // Create a read-write database
                mydb = mySQLiteOpenHelper.getWritableDatabase();
                Cursor cursor = mydb.query("imagetable", null, null, null, null, null, null);
                int i = 0;

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        i+=1;
                    }
                    cursor.close();
                }
                System.out.println("Total number of image:");
                System.out.println(i);

                int size = bitmap.getWidth() * bitmap.getHeight() * 4;
                ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                byte[] imagedata = baos.toByteArray();
                ContentValues cv = new ContentValues();
                cv.put("_id", i+1);
                cv.put("image", imagedata);
                mydb.replace("imagetable", null, cv);

                //Close byte array output stream
                baos.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mOnPictureSelectedListener.onPictureSelected(resultUri, bitmap);
        } else {
            Toast.makeText(getContext(), "Unable to crop the selected picture", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Return value of processing crop error
     *
     * @param result
     */
    private void handleCropError(Intent result) {
        deleteTempPhotoFile();
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(getContext(), cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Unable to crop the selected picture", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Delete temporary photo taken before crop
     */
    private void deleteTempPhotoFile() {
        File tempFile = new File(mTempPhotoPath);
        if (tempFile.exists() && tempFile.isFile()) {
            tempFile.delete();
        }
    }

    /**
     * Request permission
     * <p>
     * If permission has been denied, the user will be prompted that permission is required
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (shouldShowRequestPermissionRationale(permission)) {
            showAlertDialog("Not permitted", rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{permission}, requestCode);
                        }
                    }, "OK", null, "Cancel");
        } else {
            requestPermissions(new String[]{permission}, requestCode);
        }
    }

    /**
     * Displays a dialog box that specifies the title and information
     *
     * @param title
     * @param message
     * @param onPositiveButtonClickListener
     * @param positiveText
     * @param onNegativeButtonClickListener
     * @param negativeText
     */
    protected void showAlertDialog(String title, String message,
                                   DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   String positiveText,
                                   DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
    }

    /**
     * set Listener on Picture Selected
     *
     * @param l
     */
    public void setOnPictureSelectedListener(OnPictureSelectedListener l) {
        this.mOnPictureSelectedListener = l;
    }

    /**
     * Callback Listener for picture selection
     */
    public interface OnPictureSelectedListener {
        /**
         * Callback Listener for picture selection
         *
         * @param fileUri
         * @param bitmap
         */
        void onPictureSelected(Uri fileUri, Bitmap bitmap);
    }

}