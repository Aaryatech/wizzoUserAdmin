package com.ats.wizzo;

import java.util.ArrayList;

import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import com.ats.wizzo.common.Constants;
import com.ats.wizzo.model.CurrentStatus;

public class MqttStatus implements MqttCallback {

MqttClient client=null;
private  String authKey="";

public List<CurrentStatus>currentStatusList=new ArrayList<>();

public MqttStatus(String authKey) {
	this.authKey=authKey;
}


public void setupMQTT() {
    try {
        client = new MqttClient("tcp://132.148.16.132:1883", "wizzoWebAdminaas");
        client.connect();
        client.setCallback(this);
        Constants.isConnected=true;
       
      
    } catch (MqttException e) {
        e.printStackTrace();
    }
}
public void subscribeToTopics(List<String> macList) {
	
	
	
	 for(String mac :macList) {
         try {
			client.subscribe(mac+"//op_status//dlZ3Ync9");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
	
}


public List<CurrentStatus> getCurrentStatus(List<String> macList) {
	try {
		currentStatusList=new  ArrayList<>();
		MqttMessage message = new MqttMessage();
		String msg =authKey+"status";
		
		message.setPayload(msg.getBytes());

		if (!client.isConnected()) {

			client.connect();

		}
		
		for(String mac :macList) {
		client.publish(mac + "//operations//dlZ3Ync9", message);
		}
		
		
		Thread.sleep(5000);
		
		
	}catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	return currentStatusList;
}

@Override
public void connectionLost(Throwable cause) {
    // TODO Auto-generated method stub
	 System.out.println("connection lost due to "+cause);   
     Constants.isConnected=false;

}

public void disconnect() {
	
	try {
		client.disconnect();
	} catch (MqttException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Override
public void messageArrived(String topic, MqttMessage message)
        throws Exception {
 try {
 JSONObject jsonObj = new JSONObject(message.toString());
 System.out.println("\n"+jsonObj);  

 CurrentStatus  currentStatus= new CurrentStatus();
 currentStatus.setDevMac(jsonObj.getString("mac"));
 
 String status=jsonObj.getString("status");
 
 if(status.equalsIgnoreCase("off1")) {

	 currentStatus.setDevType("1"); 

	 currentStatus.setStatus("off");
 
 }else if(status.equalsIgnoreCase("off2")) {

	 currentStatus.setDevType("2");

	 currentStatus.setStatus("off");
 
 }else if(status.equalsIgnoreCase("off3")) {

	 currentStatus.setDevType("3");

	 currentStatus.setStatus("off");
	 
 }else if(status.equalsIgnoreCase("off4")) {

	 currentStatus.setDevType("4");
	 currentStatus.setStatus("off");

	 
 }else if(status.equalsIgnoreCase("on1")) {

	 currentStatus.setDevType("1"); 
	 currentStatus.setStatus("on");

 }else if(status.equalsIgnoreCase("on2")) {

	 currentStatus.setDevType("2");
	 currentStatus.setStatus("on");

 }else if(status.equalsIgnoreCase("on3")) {

	 currentStatus.setDevType("3");
	 currentStatus.setStatus("on");

 }else if(status.equalsIgnoreCase("on4")) {

	 currentStatus.setDevType("4");
	 currentStatus.setStatus("on");

 }

 currentStatusList.add(currentStatus);
 }catch (Exception e) {
	e.getLocalizedMessage();
}
}

@Override
public void deliveryComplete(IMqttDeliveryToken token) {
   
	 System.out.println("message delivered");   

}

}