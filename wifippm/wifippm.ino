#include <ESP8266WiFi.h>
//#include <ArduinoOTA.h>
#include <WiFiUdp.h>
#include<SoftwareSerial.h>

SoftwareSerial soft(5,4); //RX,TX -- D1,D2

//#include <WebSocketsServer.h>
#include <Hash.h>
//#include "MSP.h"

  // Authentication Variables
 const char* ssid = "edu_drone";              // SERVER WIFI NAME
 const char* password = "fltdev@123";          // SERVER PASSWORD

  WiFiUDP udp;
  unsigned int UDPPort = 4665;
  char incomingPacket[256];
  char replyPacket[] = "Received";

  IPAddress APlocal_IP(192, 168, 4, 1);
  IPAddress APgateway(192, 168, 4, 1);
  IPAddress APsubnet(255, 255, 255, 0);

  // Some Variables
  char result[10];
  int LED0= 2;
  int RC[5];     //RC data

//MSP msp;




void setup(){

  //Setting Default Values
  for(int i=0;i<5;i++)
  RC[i]=900;
  Serial.begin(115200); // Computer Communication
  soft.begin(115200);

  Serial.println();
  Serial.printf("Connecting to %s ",ssid);
 
  WiFi.softAP(ssid,password);
  WiFi.softAPConfig(APlocal_IP,APgateway,APsubnet);
  WiFi.config(APlocal_IP,APgateway,APsubnet);
  Serial.println(" connected");
//   msp.begin(soft);
  udp.begin(UDPPort);
  Serial.printf("Now Listning %s,UDP port %d\n",WiFi.localIP().toString().c_str(),UDPPort);
  pinMode(LED0, OUTPUT);          // WIFI OnBoard LED Light
   
}

//====================================================================================
 
void loop(){
  HandleClients(); 
}

void HandleClients(){
  int packetSize = udp.parsePacket();
  if(packetSize){
     digitalWrite(LED0, LOW);   
      int len = udp.read(incomingPacket,256);
      if(len>0)
      {
        for(int i=0;i<len;i++)
          soft.write(incomingPacket[i]); 
       }    
       
      udp.beginPacket(udp.remoteIP(),udp.remotePort());
      udp.write(replyPacket);
      udp.endPacket();                                    
  }
}
