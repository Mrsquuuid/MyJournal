package com.example.mydiary.ui;


import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydiary.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoiceMemoActivity extends AppCompatActivity {
    LinearLayout layout;

    RecyclerView msgList;

    VoiceAdapter adapter = new VoiceAdapter();

    private Context context;

    private Button start;

    private Button stop;

    private MediaRecorder mediaRecorder;

    private MediaPlayer mediaPlayer;

    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    private static final String PREFIX_NAME = "recording_";

    private final static String FILE_NAME = "Recording_file";

    private Uri file_path;

    private File recording;

    private List<File> recordingList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_voice_memo);
        this.setTitle("Voice Memo");

        recording = new File(context.getExternalCacheDir(),FILE_NAME);
        if(!recording.exists()){
            recording.mkdirs();
        }
        //delete();

        showRecording();

        layout = findViewById(R.id.layout);
        msgList = findViewById(R.id.msgList);

        // get buttons
        start = findViewById(R.id.startRec);
        stop = findViewById(R.id.stopRec);
        start.setOnClickListener(v -> startRecording());
        stop.setOnClickListener(v -> stopRecording());

        msgList.setLayoutManager(new LinearLayoutManager(this));
        adapter.addChildClickViewIds(R.id.recording_each);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(R.id.recording_each==view.getId()){
                startPlay(position);
            }
        });
        msgList.setAdapter(adapter);

    }
    public void delete(){
        File[] files = recording.listFiles();
        for(File file: files){
            file.delete();
        }
    }

    private void startPlay(int position) {
        File file = recordingList.get(position);
        Uri temp = Uri.fromFile(file);
        mediaPlayer = new MediaPlayer();

        try{
            mediaPlayer.setDataSource(context,temp);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void startRecording() {
        if(isPermissions()){
            String uuid = UUID.randomUUID().toString().replace("-","").substring(0,5);
            String fileName = PREFIX_NAME + uuid+".voice";

            file_path = Uri.fromFile(new File(recording.getAbsolutePath(),fileName));


            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(file_path.getPath());
            try {
                mediaRecorder.prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();
        }
        else{
            getPermissions();
        }
    }

    public void showList(List<File> list) {
        adapter.setData$com_github_CymChad_brvah(list);
    }

    private void getPermissions() {
        ActivityCompat.requestPermissions(VoiceMemoActivity.this,
                new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    private boolean isPermissions() {
        int resWrite = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int resRecord = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return resWrite == PackageManager.PERMISSION_GRANTED &&
                resRecord == PackageManager.PERMISSION_GRANTED;
    }

    private void stopRecording() {

        mediaRecorder.stop();

        mediaRecorder.release();;

        mediaRecorder = null;

        afterStop();

    }
    private void afterStop(){
        File now = new File(file_path.getPath());
        if(now.exists()){
            Toast.makeText(context.getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
            showRecording();
            adapter.notifyItemInserted(recordingList.size() - 1);
            msgList.scrollToPosition(recordingList.size() - 1);
        }
    }
    private void showRecording() {
        if (recording.exists()) {
            recordingList.clear();
            File[] files = recording.listFiles();
            for (File file : files) {
                recordingList.add(file);
            }
        }
        showList(recordingList);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (permissionToRecord && permissionToStore) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
