package vn.edu.topica.quanlychitieu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Main_Gui_Activity extends AppCompatActivity {
    EditText txtMoney, txtSdt;
    Button btnGui,btnLuu;
    TextView txtSdtNql;
    Intent intent;
    double m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gui_);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtMoney=(EditText)findViewById(R.id.txtMoney);
        txtSdt=(EditText)findViewById(R.id.txtSdt);
        txtSdtNql=(TextView)findViewById(R.id.txtSdtNql);
        btnGui=(Button)findViewById(R.id.btnGui);
        btnLuu=(Button)findViewById(R.id.btnLuu);
    }

    private void addEvents() {
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gui();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luu();
            }
        });
        displaySdt();
    }

    private void displaySdt() {
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
        if(line!=""){
            txtSdtNql.setText(line);
        }
        else
            txtSdtNql.setText("Chưa đăng ký");
    }

    private void luu() {

        if (!txtSdt.getText().toString().equals("")){
            FileOutputStream fos = null;
            try {

                fos = openFileOutput("sdt.txt", Context.MODE_PRIVATE);
                fos.write(txtSdt.getText().toString().getBytes());
                fos.flush();
                fos.close();

            } catch (Exception e) {

            } finally {
                if (fos != null) {
                    fos = null;
                }
            }
            displaySdt();
            Toast.makeText(Main_Gui_Activity.this,"Đã lưu số điện thoại", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(Main_Gui_Activity.this,"Bạn chưa nhập số điện thoại", Toast.LENGTH_LONG).show();
        }


    }

    private void gui() {
        Intent i=getIntent();
        String money = i.getStringExtra("money");
        String mon=txtMoney.getText().toString();
        Double m1=Double.parseDouble(money);
        Double m2 = Double.parseDouble(mon);
        m=m1+m2;
        BigInteger b1= BigDecimal.valueOf(m).toBigInteger();

        if (!mon.equals("")){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("money.txt", Context.MODE_PRIVATE);
            fos.write(String.valueOf(b1).toString().getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {

        } finally {
            if (fos != null) {
                fos = null;
            }
        }
        Toast.makeText(Main_Gui_Activity.this,"Đã gửi thành công",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Main_Gui_Activity.this,MainActivity.class);
        startActivity(intent);
    }
        else
                Toast.makeText(Main_Gui_Activity.this,"Chưa nhập số tiền cần gửi",Toast.LENGTH_LONG).show();
}


}
