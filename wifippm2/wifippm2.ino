#include <ESP8266WiFi.h>
//#include <ArduinoOTA.h>
#include <WiFiUdp.h>
#include<SoftwareSerial.h>
#include <Hash.h>
//#include "MSP.h"


SoftwareSerial soft(5,4); //RX,TX -- D1,D2

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
/* Set these to your desired credentials. */

#define CPU_MHZ 80
#define CHANNEL_NUMBER 5  //set the number of chanels
#define CHANNEL_DEFAULT_VALUE 1200  //set the default servo value
#define FRAME_LENGTH 22500  //set the PPM frame length in microseconds (1ms = 1000µs)
#define PULSE_LENGTH 300  //set the pulse length
#define onState 0  //set polarity of the pulses: 1 is positive, 0 is negative
#define sigPin 14  //set PPM signal output pin on the arduino d5
#define DEBUGPIN 16




volatile unsigned long next;
volatile unsigned int ppm_running=1;


int ppm[CHANNEL_NUMBER];

int alivecount =0;

//MSP msp;


void inline ppmISR(void){
  static boolean state = true;

  if (state) {  //start pulse
    digitalWrite(sigPin, onState);
    next = next + (PULSE_LENGTH * CPU_MHZ);
    state = false;
    alivecount++;
  } 
  else{  //end pulse and calculate when to start the next pulse
    static byte cur_chan_numb;
    static unsigned int calc_rest;
  
    digitalWrite(sigPin, !onState);
    state = true;

    if(cur_chan_numb >= CHANNEL_NUMBER){
      cur_chan_numb = 0;
      calc_rest = calc_rest + PULSE_LENGTH;// 

      next = next + ((FRAME_LENGTH - calc_rest) * CPU_MHZ);
      calc_rest = 0;
      
      digitalWrite(DEBUGPIN, !digitalRead(DEBUGPIN));
    }
    else{
      next = next + ((ppm[cur_chan_numb] - PULSE_LENGTH) * CPU_MHZ);
      
      calc_rest = calc_rest + ppm[cur_chan_numb];
      cur_chan_numb++;
    }     
  }
  timer0_write(next);

}




void setup() {
  Serial.begin(115200);
  Serial.println("Succesful BOOT.");
  pinMode(sigPin,OUTPUT);
  digitalWrite(sigPin, !onState); //set the PPM signal pin to the default state (off)
  pinMode(DEBUGPIN,OUTPUT);
  digitalWrite(DEBUGPIN, !onState); //set the PPM signal pin to the default state (off)
   Serial.println();
  Serial.printf("Connecting to %s ",ssid);
 
  WiFi.softAP(ssid,password);
  WiFi.softAPConfig(APlocal_IP,APgateway,APsubnet);
  WiFi.config(APlocal_IP,APgateway,APsubnet);
  Serial.println(" connected");
//   msp.begin(soft);
  udp.begin(UDPPort);
  Serial.printf("Now Listning %s,UDP port %d\n",WiFi.localIP().toString().c_str(),UDPPort);
  pinMode(2, OUTPUT);          // WIFI OnBoard LED Light
  noInterrupts();
  timer0_isr_init();
  timer0_attachInterrupt(ppmISR);
  next=ESP.getCycleCount()+100;
  timer0_write(next);
  for(int i=0; i<CHANNEL_NUMBER; i++){
    ppm[i]= CHANNEL_DEFAULT_VALUE;
  }
  interrupts();
  Serial.begin(115200);
 // msp.begin(Serial);
}


void loop(){
  HandleClients(); 
  yield();
}

void HandleClients(){
  int packetSize = udp.parsePacket();
  if(packetSize){
     digitalWrite(2, LOW);   
      int len = udp.read(incomingPacket,256);
      if(len>0)
      {
        incomingPacket[len] = '\0';
        Serial.println(incomingPacket);
        int j=0;
        for(int i=0;i<5;i++)
          {
            ppm[i]=0;
            while(incomingPacket[j]!='*' && incomingPacket[j] !='\0')
             {
              ppm[i]*=10;
              ppm[i]+=incomingPacket[j++]-'0';
             }
             j++;
           }
       }    
       
      udp.beginPacket(udp.remoteIP(),udp.remotePort());
      udp.write(replyPacket);
      udp.endPacket();                                    
  }
}
