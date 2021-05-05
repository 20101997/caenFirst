package com.grupposts.trasporti

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.caen.RFIDLibrary.CAENRFIDLogicalSource
import com.caen.RFIDLibrary.CAENRFIDPort
import com.caen.RFIDLibrary.CAENRFIDReader
import kotlinx.android.synthetic.main.activity_main_caen_rfid.*

class MainActivityCaenRfid : AppCompatActivity() {

    private val REQUEST_CODE_ENABLE_BT:Int = 1
    //bluetooth adapter
    lateinit var bAdapter:BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_caen_rfid)

        //init bluetooth adapter
        bAdapter = BluetoothAdapter.getDefaultAdapter();
        //check if bluetooth is on/off
/*        if(bAdapter==null){
            statusBluetoothTv.text = "blueTooth not available"
        }
        else{
            statusBluetoothTv.text = "blueTooth is available"

        }
        if(bAdapter.isEnabled){
        }
        else{

        }*/
        //turn on bluetooth
        onBtn.setOnClickListener {
             if(bAdapter.isEnabled){
                 Toast.makeText(this,"Already on", Toast.LENGTH_LONG).show()
             }
            else{
                 var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                 startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
                 Toast.makeText(this,"BlueTooth turned on", Toast.LENGTH_LONG).show()

             }
        }
        //turn off bluetooth
        offBtn.setOnClickListener {
            if(!bAdapter.isEnabled){
                Toast.makeText(this,"Already off", Toast.LENGTH_LONG).show()
            }
            else{
                bAdapter.disable()
                Toast.makeText(this,"BlueTooth off", Toast.LENGTH_LONG).show()
            }
        }

        pairedBtn.setOnClickListener {

            if(bAdapter.isEnabled){
                 // get list of paired devices possible skid
                pairedTv.text = "paired Devices (possible skid)"
                val devices =bAdapter.bondedDevices
                for(device in devices){
                     val deviceName = device.name
                     val deviceAddress = device.address
                     pairedTv.append("\nDevice: $deviceName , $device")

                }
                val myReader = CAENRFIDReader()
                pairedTv.append("CaenPortId" + CAENRFIDPort.CAENRFID_USB.toString() )


             //   myReader.Connect(CAENRFIDPort.CAENRFID_BT, "COM3")


                // val CAENRFIDLogicalSource mySource = myReader.GetSource("Source_0")


            }
            else {
                Toast.makeText(this,"Already on", Toast.LENGTH_LONG).show()
            }
        }




    }
}