package vn.edu.topica.quanlychitieu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner spName;
    ArrayList<String> dsNganHang;
    ArrayAdapter<String> adapter;

    ListView lvDetail;

    String[] lineDetail;
    ArrayAdapter<String> adapterDetail;



    int lastClick=0;
    int flag=0;
    ImageView imgBank;
    TextView txtMon;
    ImageButton btnGui;
    ImageButton btnRut;
    ImageButton btnSave;
    ImageButton btnReload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        check();
        listView();
        addEvents();
        //add icon clear, reload, new

    }

    private void check() {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("details.txt", Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {

        } finally {
            if (fos != null) {
                fos = null;
            }
        }

        fos = null;
        try {
            fos = openFileOutput("gia.txt", Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {

        } finally {
            if (fos != null) {
                fos = null;
            }
        }
    }

    private void listView() {

        lvDetail = (ListView) findViewById(R.id.lvDetail);
        ArrayList<String> arrayDetail=new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = openFileInput("details.txt");

            //InputStreamReader isr = new InputStreamReader(fis);
            // READ STRING OF UNKNOWN LENGTH
            //StringBuilder sb = new StringBuilder();
            //char[] inputBuffer = new char[2048];
            //int l;
            // FILL BUFFER WITH DATA
            BufferedReader br= new BufferedReader(new InputStreamReader(fis));
            String line=null;
            while ((line=br.readLine())!=null) {
                //sb.append(inputBuffer, 0, l);
                arrayDetail.add(line);
            }
            br.close();
            fis.close();
            // CONVERT BYTES TO STRING
            //String readString = sb.toString();



            //Toast.makeText(this.context, "Đã thêm " + readString + " vào danh sách yêu thích", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*if (arrayDetail.size() < 1) {
            arrayDetail.add("Không có dữ liệu");
            adapterDetail = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayDetail);
            lvDetail.setAdapter(adapterDetail);
        } else if (arrayDetail.size() >= 1 && arrayDetail.size() <= 7) {
            Double[] sumGia = new Double[arrayDetail.size()];
            ArrayList<String> date = new ArrayList<>();
            for (int i = 0; i < arrayDetail.size(); i++) {
                String[] s = arrayDetail.get(i).split(",");
                date.add(s[0]);
                sumGia[i] = sum(s[0], i);
            }
            String[] dayAndGia = new String[date.size()];
            ArrayList<String> day=new ArrayList<>();
            for (int j = 0; j < date.size(); j++) {
                dayAndGia[j] = date.get(j) + " " + sumGia[j];
                day.add(dayAndGia[j]);
            }
            for(int i=day.size();i<7;i++)
                day.add("Không có dữ liệu");
            adapterDetail = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, day);
            lvDetail.setAdapter(adapterDetail);*/
        //hiển thị listview theo template
        Double[] sumGia = new Double[arrayDetail.size()];
        ArrayList<String> date = new ArrayList<>();
        for (int i = 0; i < arrayDetail.size(); i++) {
            String[] s = arrayDetail.get(i).split(",");
            date.add(s[0]);
            sumGia[i] = sum(s[0], i);
        }
        String[] dayAndGia = new String[date.size()];
        ArrayList<String> day=new ArrayList<>();
        for (int j = 0; j < date.size(); j++) {
            dayAndGia[j] = date.get(j) + " " + sumGia[j];
            day.add(dayAndGia[j]);
        }

        if(day.size()>=7) {
            FileOutputStream fos = null;
            try {
                fos = openFileOutput("details.txt", Context.MODE_PRIVATE);
                fos.write("".getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {

            } finally {
                if (fos != null) {
                    fos = null;
                }
            }
            ArrayList<String> ghiMoi=new ArrayList<>();
            ghiMoi.add("Không có dữ liệu");
            ghiMoi.add("Không có dữ liệu");
            ghiMoi.add("Không có dữ liệu");
            ghiMoi.add("Không có dữ liệu");
            ghiMoi.add("Không có dữ liệu");
            ghiMoi.add("Không có dữ liệu");
            ghiMoi.add("Không có dữ liệu");
            adapterDetail = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ghiMoi);
            lvDetail.setAdapter(adapterDetail);
        }
        else{
            for(int i=day.size();i<7;i++)
                day.add("Không có dữ liệu");
            adapterDetail = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, day);
            lvDetail.setAdapter(adapterDetail);
        }

    }

    private Double sum(String s, int i) {
        ArrayList<String> arrGia=new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = openFileInput("gia.txt");
            BufferedReader br= new BufferedReader(new InputStreamReader(fis));
            String line=null;
            while ((line=br.readLine())!=null) {
                //sb.append(inputBuffer, 0, l);
                arrGia.add(line);
            }
            br.close();
            fis.close();


            //Toast.makeText(this.context, "Đã thêm " + readString + " vào danh sách yêu thích", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Double d=0.0;
        if (arrGia.size()>0){
            String []gia= new String[arrGia.get(i).length()];
            String gia1Day;
            gia1Day=arrGia.get(i);//problem of btnReload
            gia=gia1Day.split(",");
            try{
                for (int k=1;k<gia.length;k++){
                    d+=Double.parseDouble(gia[k]);
                }
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "Size arrGia: "+arrGia.size(),Toast.LENGTH_LONG).show();
            }

            return d;
        }
        return d;
    }

    private void writeBank(String name) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("num.txt", Context.MODE_PRIVATE);
            fos.write(name.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {

        } finally {
            if (fos != null) {
                fos = null;
            }
        }
    }

    private void addEvents() {
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gui();
            }
        });
        btnRut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rut();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, DetailMain2Activity.class);
                startActivity(intent);
            }
        });
        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boxAppear(i);
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check();
                listView();
            }
        });
        displayMoney();
        displayLogo();

    }

    private void boxAppear(int i) {
        ArrayList<String> arrayDetail=new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = openFileInput("details.txt");
            BufferedReader br= new BufferedReader(new InputStreamReader(fis));
            String line=null;
            while ((line=br.readLine())!=null) {
                //sb.append(inputBuffer, 0, l);
                arrayDetail.add(line);
            }
            br.close();
            fis.close();


            //Toast.makeText(this.context, "Đã thêm " + readString + " vào danh sách yêu thích", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if (arrayDetail.size()>=1&&arrayDetail.size()<=7){
        if (i<arrayDetail.size()){
            String []cut=arrayDetail.get(i).split(",");
            Double gia=sum(cut[0],i);
            String s=new String();
            for (int ps=0;ps<cut.length;ps++){
                s=s+cut[ps]+"\n";
            }
            s=s+"\nTổng: "+gia+"VNĐ";
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this).setMessage(s).setTitle("Chi tiết").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

    }

    private void displayLogo() {
        String line="";
        FileInputStream fis = null;
        try {
            fis = openFileInput("num.txt");
            BufferedReader br= new BufferedReader(new InputStreamReader(fis));
            String getLine=null;
            while ((getLine=br.readLine())!=null) {
                //sb.append(inputBuffer, 0, l);
                line=getLine;
            }
            br.close();
            fis.close();

            //Toast.makeText(this.context, "Đã thêm " + readString + " vào danh sách yêu thích", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!line.equals("")){
            lastClick=Integer.parseInt(line);
            flag=1;
            if (lastClick == 1) {
                imgBank.setImageResource(R.drawable.agribank);
            }
            if (lastClick == 2) {
                imgBank.setImageResource(R.drawable.sacombank);
            }
            if (lastClick == 3) {
                imgBank.setImageResource(R.drawable.ab);
            }
            if (lastClick == 4) {
                imgBank.setImageResource(R.drawable.gp);
            }
            if (lastClick == 5) {
                imgBank.setImageResource(R.drawable.pg);
            }
            if (lastClick == 6) {
                imgBank.setImageResource(R.drawable.nama);
            }
            if (lastClick == 7) {
                imgBank.setImageResource(R.drawable.vietin);
            }
            if (lastClick == 8) {
                imgBank.setImageResource(R.drawable.daia);
            }
            if (lastClick == 9) {
                imgBank.setImageResource(R.drawable.vp);
            }
            if (lastClick == 10) {
                imgBank.setImageResource(R.drawable.vib);
            }
            if (lastClick == 11) {
                imgBank.setImageResource(R.drawable.mb);
            }
            if (lastClick == 12) {
                imgBank.setImageResource(R.drawable.ocean);
            }
            if (lastClick == 13) {
                imgBank.setImageResource(R.drawable.bidv);
            }
            if (lastClick == 14) {
                imgBank.setImageResource(R.drawable.tp);
            }
            if (lastClick == 15) {
                imgBank.setImageResource(R.drawable.techcom);
            }
            if (lastClick == 16) {
                imgBank.setImageResource(R.drawable.maritime);
            }
            if (lastClick == 17) {
                imgBank.setImageResource(R.drawable.vietcom);
            }
            if (lastClick == 18) {
                imgBank.setImageResource(R.drawable.vieta);
            }
            if (lastClick == 19) {
                imgBank.setImageResource(R.drawable.baca);
            }
            if (lastClick == 20) {
                imgBank.setImageResource(R.drawable.shb);
            }
            if (lastClick == 21) {
                imgBank.setImageResource(R.drawable.seabank);
            }

        }
        spName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                lastClick=i;
                if (lastClick == 1&&flag!=1) {
                    imgBank.setImageResource(R.drawable.agribank);
                    writeBank("1");
                }
                if (lastClick == 2&&flag!=1) {
                    imgBank.setImageResource(R.drawable.sacombank);
                    writeBank("2");
                }
                if (lastClick == 3&&flag!=1) {
                    imgBank.setImageResource(R.drawable.ab);
                    writeBank("3");
                }
                if (lastClick == 4&&flag!=1) {
                    imgBank.setImageResource(R.drawable.gp);
                    writeBank("4");
                }
                if (lastClick == 5&&flag!=1) {
                    imgBank.setImageResource(R.drawable.pg);
                    writeBank("5");
                }
                if (lastClick == 6&&flag!=1) {
                    imgBank.setImageResource(R.drawable.nama);
                    writeBank("6");
                }
                if (lastClick == 7&&flag!=1) {
                    imgBank.setImageResource(R.drawable.vietin);
                    writeBank("7");
                }
                if (lastClick == 8&&flag!=1) {
                    imgBank.setImageResource(R.drawable.daia);
                    writeBank("8");
                }
                if (lastClick == 9&&flag!=1) {
                    imgBank.setImageResource(R.drawable.vp);
                    writeBank("9");
                }
                if (lastClick == 10&&flag!=1) {
                    imgBank.setImageResource(R.drawable.vib);
                    writeBank("10");
                }
                if (lastClick == 11&&flag!=1) {
                    imgBank.setImageResource(R.drawable.mb);
                    writeBank("11");
                }
                if (lastClick == 12&&flag!=1) {
                    imgBank.setImageResource(R.drawable.ocean);
                    writeBank("12");
                }
                if (lastClick == 13&&flag!=1) {
                    imgBank.setImageResource(R.drawable.bidv);
                    writeBank("13");
                }
                if (lastClick == 14&&flag!=1) {
                    imgBank.setImageResource(R.drawable.tp);
                    writeBank("14");
                }
                if (lastClick == 15&&flag!=1) {
                    imgBank.setImageResource(R.drawable.techcom);
                    writeBank("15");
                }
                if (lastClick == 16&&flag!=1) {
                    imgBank.setImageResource(R.drawable.maritime);
                    writeBank("16");
                }
                if (lastClick == 17&&flag!=1) {
                    imgBank.setImageResource(R.drawable.vietcom);
                    writeBank("17");
                }
                if (lastClick == 18&&flag!=1) {
                    imgBank.setImageResource(R.drawable.vieta);
                    writeBank("18");
                }
                if (lastClick == 19&&flag!=1) {
                    imgBank.setImageResource(R.drawable.baca);
                    writeBank("19");
                }
                if (lastClick == 20&&flag!=1) {
                    imgBank.setImageResource(R.drawable.shb);
                    writeBank("20");
                }
                if (lastClick == 21&&flag!=1) {
                    imgBank.setImageResource(R.drawable.seabank);
                    writeBank("21");
                }
                flag=0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void displayMoney() {
        String line="";
        FileInputStream fis = null;
        try {
            fis = openFileInput("money.txt");
            BufferedReader br= new BufferedReader(new InputStreamReader(fis));
            String getLine=null;
            while ((getLine=br.readLine())!=null) {
                //sb.append(inputBuffer, 0, l);
                line=getLine;
            }
            br.close();
            fis.close();


            //Toast.makeText(this.context, "Đã thêm " + readString + " vào danh sách yêu thích", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(line!=""){
            txtMon.setText(line+" VNĐ");
        }
        else
            txtMon.setText("0 VNĐ");
    }

    private String getSdt() {
        String line="";
        FileInputStream fis = null;
        try {
            fis = openFileInput("sdt.txt");
            BufferedReader br= new BufferedReader(new InputStreamReader(fis));
            String getLine=null;
            while ((getLine=br.readLine())!=null) {
                //sb.append(inputBuffer, 0, l);
                line=getLine;
            }
            br.close();
            fis.close();


            //Toast.makeText(this.context, "Đã thêm " + readString + " vào danh sách yêu thích", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }



    private void rut() {
        Intent intent=new Intent(MainActivity.this,Main_RutActivity.class);
        String cut=txtMon.getText().toString();
        String e[]=cut.split(" ");
        intent.putExtra("money",e[0]);
        startActivity(intent);
    }

    private void gui() {
        Intent intent=new Intent(MainActivity.this,Main_Gui_Activity.class);
        String cut=txtMon.getText().toString();
        String e[]=cut.split(" ");
        intent.putExtra("money",e[0]);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==1){
            txtMon.setText(data.getDoubleExtra("Money",0)+" VNĐ");
            //nhanTin(getSdt(),data.getDoubleExtra("Money",0));
        }
        if (requestCode==2&&resultCode==100){
            txtMon.setText(data.getDoubleExtra("Money",0)+" VNĐ");
        }
        if (requestCode==2&&resultCode==200){
            txtMon.setText(data.getDoubleExtra("Money",0)+" VNĐ");
        }
        if (requestCode==2&&resultCode==300){
            txtMon.setText(data.getDoubleExtra("Money",0)+" VNĐ");
        }
        if (requestCode==2&&resultCode==400){
            txtMon.setText(data.getDoubleExtra("Money",0)+" VNĐ");
        }
        if (requestCode==2&&resultCode==500){
            txtMon.setText(data.getDoubleExtra("Money",0)+" VNĐ");
        }
        if (requestCode==2&&resultCode==1000){
            txtMon.setText(data.getDoubleExtra("Money",0)+" VNĐ");
        }
        if (requestCode==2&&resultCode==2000){
            txtMon.setText(data.getDoubleExtra("Money",0)+" VNĐ");
        }
        if (requestCode==2&&resultCode==-1){
            txtMon.setText(data.getDoubleExtra("Money",0)+" VNĐ");
        }


    }

    /*private void nhanTin(String sdt, double s) {
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
                Toast.makeText(MainActivity.this, msg,Toast.LENGTH_LONG).show();

            }
        },new IntentFilter("ACTION_MSG_SENT"));

        smsManager.sendTextMessage(getSdt().toString(),null," Dịch vụ Internet Banking thông báo:\n Số dư tài khoản: "+ BigDecimal.valueOf(s).toBigInteger().toString(),pendingIntent,null);
    }*/

    private void addControls() {
        txtMon=(TextView)findViewById(R.id.txtMon);
        btnGui=(ImageButton)findViewById(R.id.btnGui);
        btnRut=(ImageButton)findViewById(R.id.btnRut);
        imgBank=(ImageView)findViewById(R.id.imgBank);
        spName=(Spinner)findViewById(R.id.spName);
        btnSave=(ImageButton)findViewById(R.id.btnSave);
        btnReload=(ImageButton)findViewById(R.id.btnReload);
        dsNganHang=new ArrayList<>();
        dsNganHang.add("Chọn ngân hàng khác");
        dsNganHang.add("Agribank");
        dsNganHang.add("Sacombank");
        dsNganHang.add("ABBank");
        dsNganHang.add("GP Bank");
        dsNganHang.add("PG Bank");
        dsNganHang.add("Nam Á Bank");
        dsNganHang.add("VietinBank");
        dsNganHang.add("Đại Á Bank");
        dsNganHang.add("VP Bank");
        dsNganHang.add("VIB");
        dsNganHang.add("Ngân hàng Quân Đội");
        dsNganHang.add("Ocean Bank");
        dsNganHang.add("BIDV");
        dsNganHang.add("TPBank");
        dsNganHang.add("TechcomBank");
        dsNganHang.add("MaritimeBank");
        dsNganHang.add("VietcomBank");
        dsNganHang.add("Việt Á Bank");
        dsNganHang.add("Bắc Á Bank");
        dsNganHang.add("SHB");
        dsNganHang.add("SeaBank");
        adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,dsNganHang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spName.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayMoney();
        getSdt();
        listView();
    }
}
