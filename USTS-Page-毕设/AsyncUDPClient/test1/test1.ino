#include "WiFi.h"
#include "AsyncUDP.h"
const char * ssid = "test"; //your ssid
const char * password = "45678910"; //wifi password

AsyncUDP udp;

/**定义引脚**/

void setup()
{
    Serial.begin(115200);
    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid, password);
    if (WiFi.waitForConnectResult() != WL_CONNECTED) {
        Serial.println("WiFi Failed");
    }
    if(udp.connect(IPAddress(47.102.42.105),2345)) {
        Serial.println("UDP connected");
        udp.onPacket([](AsyncUDPPacket packet) {
            Serial.print("UDP Packet Type: ");
            Serial.print(packet.isBroadcast()?"Broadcast":packet.isMulticast()?"Multicast":"Unicast");
            Serial.print(", From: ");
            Serial.print(packet.remoteIP());
            Serial.print(":");
            Serial.print(packet.remotePort());
            Serial.print(", To: ");
            Serial.print(packet.localIP());
            Serial.print(":");
            Serial.print(packet.localPort());
            Serial.print(", Length: ");
            Serial.print(packet.length());
            Serial.print(", Data: ");
            Serial.write(packet.data(), packet.length());
            Serial.println();
            //reply to the client
            packet.printf("Got %u bytes of data", packet.length());
        });
        //Send unicast
        //udp.print("Hello Server!");
    }
}

void loop()
{
    float temp =12.0;
    float light = 12.0;
    float sound = 12.0;
    float hum = 12.0;
    String deviceCode= "device123";
    delay(10000);
    String alldata = "{\"temp\":"+temp;
    alldata+=",\"light\":"+light;
    alldata+=",\"sound\":"+sound;
    alldata+=",\"deviceCode\":"+deviceCode;
   
    Serial.print("alldata: "+alldata);
    //udp.print(alldata);
    
    
}
