package co.com.etn.arquitecturamvpbase.views.activities.device;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import co.com.etn.arquitecturamvpbase.R;

public class CodeReaderActivity extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_reader);
        init();
    }

    private void init() {
        result = (TextView) findViewById(R.id.barcode_result_barcode);
    }

    public void launchBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null){
            if(intentResult.getContents()==null){
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Código leido", Toast.LENGTH_SHORT).show();
                parseDataCedula(intentResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void parseDataCedula(String barcodeString){
        String data = "";
        if(barcodeString.length()>150) {
            //cedula
            data = barcodeString.substring(48, 48 + 10);
            Toast.makeText(this, "Número de cedula: "+Long.parseLong(data), Toast.LENGTH_LONG).show();

            //PrimerApellido
            data = barcodeString.substring(58, 58 + 23);
            Toast.makeText(this, "Primer Apellido: "+data, Toast.LENGTH_LONG).show();

            //SegundoApellido
            data = barcodeString.substring(81, 81 + 23);
            Toast.makeText(this, "Segundo Apellido: "+data, Toast.LENGTH_LONG).show();

            //PrimerNombre
            data = barcodeString.substring(104, 104 + 23);
            Toast.makeText(this, "Primer Nombre: "+data, Toast.LENGTH_LONG).show();

            //SegundoNombre
            data = barcodeString.substring(127, 127 + 23);
            Toast.makeText(this, "Segundo Nombre: "+data, Toast.LENGTH_LONG).show();

            //Sexo
            data = barcodeString.substring(151, 151 + 1);
            Toast.makeText(this, "Sexo: "+data, Toast.LENGTH_LONG).show();

            //FechaNacimiento
            data = barcodeString.substring(152, 152 + 8);
            Toast.makeText(this, "Fecha Nacimiento: "+data, Toast.LENGTH_LONG).show();

            //Rh
            data = barcodeString.substring(166, 166 + 3).trim().replace("¡", "+");
            Toast.makeText(this, "Rh: "+data, Toast.LENGTH_LONG).show();
        } else {
            data = barcodeString;
        }


        result.setText(data);
    }
}
