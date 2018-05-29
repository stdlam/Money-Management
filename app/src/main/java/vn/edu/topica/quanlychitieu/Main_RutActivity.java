package vn.edu.topica.quanlychitieu;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Main_RutActivity extends AppCompatActivity {
    ImageButton btnOne, btnTwo, btnThree, btnFour, btnFive, btnMiOne, btnMiTwo;
    EditText txtSoKhac;
    Button btnOk, btnRutTien;
    TextView txtThongTin, txtThongBao;
    double m;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__rut);
        addControls();
        addEvents();
    }

    private void addEvents() {

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtThongTin.setText(txtSoKhac.getText().toString());
            }
        });
        btnRutTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rutTien();
            }
        });
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtThongTin.setText("100000");
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtThongTin.setText("200000");
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtThongTin.setText("300000");
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtThongTin.setText("400000");
            }
        });
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtThongTin.setText("500000");
            }
        });
        btnMiOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtThongTin.setText("1000000");
            }
        });
        btnMiTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtThongTin.setText("2000000");
            }
        });
        displayInfo();
    }

    private void displayInfo() {
        String line = getSdt();
        if(line!=""){
            txtThongBao.setText("Tụi tui sẽ gửi tin nhắn thay đổi trên thẻ về số điện thoại đã đăng ký: "+line);
        }
        else
            txtThongBao.setText("Vui lòng đăng ký số điện thoại người quản lý");
    }

    private String getSdt() {
        String line="";
        FileInputStream fis = null;
        try {
            fis = openFileInput("sdt.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            // READ STRING OF UNKNOWN LENGTH
            StringBuilder sb = new StringBuilder();
            char[] inputBuffer = new char[2048];
            int l;
            // FILL BUFFER WITH DATA

            while ((l = isr.read(inputBuffer)) != -1) {
                sb.append(inputBuffer, 0, l);
                line=sb.toString();
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
        return line;
    }

    private void nhanTin() {
        final SmsManager smsManager=SmsManager.getDefault();
        Intent msgSent=new Intent("ACTION_MSG_SENT");
        //kiểm tra kết quả trả về
        final PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,msgSent,0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result= getResultCode();
                String msg="GỬI THÀNH CÔNG";
                if (result!= Activity.RESULT_OK)
                    msg="GỬI THẤT BẠI";
                Toast.makeText(Main_RutActivity.this, msg,Toast.LENGTH_LONG).show();

            }
        },new IntentFilter("ACTION_MSG_SENT"));
        String soDu=BigDecimal.valueOf(m).toBigInteger().toString();
        txtThongBao.setText("Số dư TK: "+soDu+"VNĐ");
        smsManager.sendTextMessage(getSdt().toString(),null,"Dịch vụ Internet Banking:\nTK vừa rút "+txtThongTin.getText().toString()+"VNĐ\n"+txtThongBao.getText().toString(),pendingIntent,null);
    }

    private void xacNhan() {
        txtThongTin.setText(txtSoKhac.getText().toString());
        Intent intent=getIntent();
        m=intent.getDoubleExtra("mon",0.0);
        m-=Double.parseDouble(txtThongTin.getText().toString());
        intent.putExtra("Money",m);
        setResult(-1,intent);
        finish();
    }

    private void rutTien() {
        if(txtThongTin.getText().toString()=="100000")
            rut100();
        if(txtThongTin.getText().toString()=="200000")
            rut200();
        if(txtThongTin.getText().toString()=="300000")
            rut300();
        if(txtThongTin.getText().toString()=="400000")
            rut400();
        if(txtThongTin.getText().toString()=="500000")
            rut500();
        if(txtThongTin.getText().toString()=="1000000")
            rut1000();
        if(txtThongTin.getText().toString()=="2000000")
            rut2000();
        if(!txtSoKhac.getText().toString().equals("")){
            Double soKhac=Double.parseDouble(txtThongTin.getText().toString());
            Intent intent=getIntent();
            String mon=intent.getStringExtra("money");
            m=Double.parseDouble(mon);
            if (m>=soKhac) {
                m -= soKhac;
                flag=1;
            }
            else
                flag=0;
        }
        FileOutputStream fos = null;
        try {
            if (flag==1){
                fos = openFileOutput("money.txt", Context.MODE_PRIVATE);
                BigInteger b1= BigDecimal.valueOf(m).toBigInteger();
                fos.write(String.valueOf(b1).toString().getBytes());
                fos.flush();
                fos.close();
                Toast.makeText(Main_RutActivity.this,"Đã rút thành công",Toast.LENGTH_LONG).show();
                nhanTin();
            }
            else{
                Toast.makeText(Main_RutActivity.this,"Số dư hiện tại không đủ, xin kiểm tra lại",Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

        } finally {
            if (fos != null) {
                fos = null;
            }
        }
        Intent intent=new Intent(Main_RutActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void rut2000() {
        Intent intent=getIntent();
        String mon=intent.getStringExtra("money");
        m=Double.parseDouble(mon);
        if(m>=2000000) {
            m -= 2000000;
            flag=1;
        }
        else{
            flag=0;
        }

    }

    private void rut1000() {
        Intent intent=getIntent();
        String mon=intent.getStringExtra("money");
        m=Double.parseDouble(mon);
        if(m>=1000000){
            m-=1000000;
            flag=1;
        }
        else{
            flag=0;
        }

    }

    private void rut500() {
        Intent intent=getIntent();
        String mon=intent.getStringExtra("money");
        m=Double.parseDouble(mon);
        if(m>=500000){
            m-=500000;
            flag=1;
        }
        else{
            flag=0;
        }
    }

    private void rut400() {
        Intent intent=getIntent();
        String mon=intent.getStringExtra("money");
        m=Double.parseDouble(mon);
        if(m>=400000){
            m-=400000;
            flag=1;
        }
        else{
            flag=0;
        }
    }

    private void rut300() {
        Intent intent=getIntent();
        String mon=intent.getStringExtra("money");
        m=Double.parseDouble(mon);
        if(m>=300000){
            m-=300000;
            flag=1;
        }
        else{
            flag=0;
        }
    }

    private void rut200() {
        Intent intent=getIntent();
        String mon=intent.getStringExtra("money");
        m=Double.parseDouble(mon);
        if(m>=200000){
            m-=200000;
            flag=1;
        }
        else{
            flag=0;
        }
    }

    private void rut100() {
        Intent intent=getIntent();
        String mon=intent.getStringExtra("money");
        m=Double.parseDouble(mon);
        if(m>=100000){
            m-=100000;
            flag=1;
        }
        else{
            flag=0;
        }

    }

    private void addControls() {
        btnFive=(ImageButton)findViewById(R.id.btnFive);
        btnOne=(ImageButton)findViewById(R.id.btnOne);
        btnTwo=(ImageButton)findViewById(R.id.btnTwo);
        btnThree=(ImageButton)findViewById(R.id.btnThree);
        btnFour=(ImageButton)findViewById(R.id.btnFour);
        btnMiOne=(ImageButton)findViewById(R.id.btnMiOne);
        btnMiTwo=(ImageButton)findViewById(R.id.btnMiTwo);
        txtSoKhac=(EditText)findViewById(R.id.txtSoKhac);
        btnOk=(Button)findViewById(R.id.btnOk);
        btnRutTien=(Button)findViewById(R.id.btnRutTien);
        txtThongBao=(TextView)findViewById(R.id.txtThongBao);
        txtThongTin=(TextView)findViewById(R.id.txtThongTin);

    }
}
