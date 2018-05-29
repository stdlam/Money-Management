package vn.edu.topica.quanlychitieu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailMain2Activity extends AppCompatActivity {

    EditText txtDetail;
    EditText txtGia;
    ImageButton btnSent;
    Calendar calendar=Calendar.getInstance();
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_main2);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtDetail=(EditText)findViewById(R.id.txtDetail);
        btnSent=(ImageButton)findViewById(R.id.btnSent);
        txtGia=(EditText)findViewById(R.id.txtGia);
    }

    private void addEvents() {
        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
                saveGia();
                txtDetail.setText("");
                txtGia.setText("");
            }
        });

    }

    private void saveGia() {
        if (txtGia.getText().toString().equals("")||txtDetail.getText().toString().equals("")){
            Toast.makeText(DetailMain2Activity.this,"Không được để trống",Toast.LENGTH_LONG).show();
        }
        else{
            ArrayList<String> line=new ArrayList();
            //String line="";
            FileInputStream fis = null;
            try {
                fis = openFileInput("gia.txt");
                InputStreamReader isr = new InputStreamReader(fis);
                // READ STRING OF UNKNOWN LENGTH
                StringBuilder sb = new StringBuilder();
                char[] inputBuffer = new char[2048];
                int l;
                // FILL BUFFER WITH DATA

                while ((l = isr.read(inputBuffer)) != -1) {
                    sb.append(inputBuffer, 0, l);
                    line.add(sb.toString());
                }

                // CONVERT BYTES TO STRING
                String readString = sb.toString();
                fis.close();


                //Toast.makeText(this.context, "Đã thêm " + readString + " vào danh sách yêu thích", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (line.size()==0){
                String gia=txtGia.getText().toString();
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput("gia.txt", Context.MODE_APPEND);
                    String date=sdf.format(calendar.getTime());
                    fos.write(date.getBytes());
                    fos.write(",".getBytes());
                    fos.write(gia.getBytes());
                    fos.flush();
                    fos.close();
                    Toast.makeText(DetailMain2Activity.this,"Đã lưu giá tiền",Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                } finally {
                    if (fos != null) {
                        fos = null;
                    }
                }
            }
            else{
                String lastLine = line.get(line.size()-1);
                String gia=txtGia.getText().toString();
                String day=Date(lastLine);
                String date=sdf.format(calendar.getTime());
                String[] toDay=date.split("/");
                if (day.equals(toDay[0])){
                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput("gia.txt", Context.MODE_APPEND);
                        fos.write(",".getBytes());
                        fos.write(gia.getBytes());
                        fos.flush();
                        fos.close();
                        Toast.makeText(DetailMain2Activity.this,"Đã lưu giá tiền",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {

                    } finally {
                        if (fos != null) {
                            fos = null;
                        }
                    }
                }
                else {
                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput("gia.txt", Context.MODE_APPEND);
                        //BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fos));
                        //bw.newLine();
                        OutputStreamWriter osw=new OutputStreamWriter(fos);
                        osw.append("\n\r");
                        osw.append(date);
                        osw.append(",");
                        osw.append(gia);
                        osw.flush();
                        osw.close();

                        //fos.write("\n\r".getBytes());//write new line, dont use this way
                        //fos.write(date.getBytes());
                        //fos.write(",".getBytes());
                        //fos.write(gia.getBytes());
                        //fos.flush();
                        //bw.close();
                        fos.close();
                        Toast.makeText(DetailMain2Activity.this,"Đã lưu giá tiền",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {

                    } finally {
                        if (fos != null) {
                            fos = null;
                        }
                    }
                }

            }

        }

    }

    private void Save() {//bug fix now
        if (txtDetail.getText().toString().equals("")||txtGia.getText().toString().equals("")){
            Toast.makeText(DetailMain2Activity.this,"Không được để trống",Toast.LENGTH_LONG).show();
        }
        else{
            ArrayList<String> line=new ArrayList();
            //String line="";
            FileInputStream fis = null;
            try {
                fis = openFileInput("details.txt");
                InputStreamReader isr = new InputStreamReader(fis);
                // READ STRING OF UNKNOWN LENGTH
                StringBuilder sb = new StringBuilder();
                char[] inputBuffer = new char[2048];
                int l;
                // FILL BUFFER WITH DATA

                while ((l = isr.read(inputBuffer)) != -1) {
                    sb.append(inputBuffer, 0, l);
                    line.add(sb.toString());
                }

                // CONVERT BYTES TO STRING
                String readString = sb.toString();
                fis.close();


                //Toast.makeText(this.context, "Đã thêm " + readString + " vào danh sách yêu thích", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (line.size()==0){
                String deTail=txtDetail.getText().toString();
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput("details.txt", Context.MODE_APPEND);
                    String date=sdf.format(calendar.getTime());
                    fos.write(date.getBytes());
                    fos.write(",".getBytes());
                    fos.write(deTail.getBytes());
                    fos.flush();
                    fos.close();
                    Toast.makeText(DetailMain2Activity.this,"Đã lưu mặt hàng",Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                } finally {
                    if (fos != null) {
                        fos = null;
                    }
                }
            }
            else{
                String lastLine = line.get(line.size()-1);
                String deTail=txtDetail.getText().toString();
                String day=Date(lastLine);
                String date=sdf.format(calendar.getTime());
                String[] toDay=date.split("/");
                if (day.equals(toDay[0])){
                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput("details.txt", Context.MODE_APPEND);
                        fos.write(",".getBytes());
                        fos.write(deTail.getBytes());
                        fos.flush();
                        fos.close();
                        Toast.makeText(DetailMain2Activity.this,"Đã lưu mặt hàng",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {

                    } finally {
                        if (fos != null) {
                            fos = null;
                        }
                    }
                }
                else {
                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput("details.txt", Context.MODE_APPEND);
                        //BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fos));
                        //bw.newLine();
                        OutputStreamWriter osw=new OutputStreamWriter(fos);
                        osw.append("\n\r");
                        osw.append(date);
                        osw.append(",");
                        osw.append(deTail);
                        osw.flush();
                        osw.close();

                        //fos.write("\n\r".getBytes());
                        //fos.write(date.getBytes());
                        //fos.write(",".getBytes());
                        //fos.write(deTail.getBytes());
                        //fos.flush();
                        fos.close();
                        Toast.makeText(DetailMain2Activity.this,"Đã lưu mặt hàng",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {

                    } finally {
                        if (fos != null) {
                            fos = null;
                        }
                    }
                }
            }

        }

    }
    private  String Date(String Detail){
        String[] cut=Detail.split(",");
        String[] day=cut[0].split("/");
        return day[0];
    }
}
