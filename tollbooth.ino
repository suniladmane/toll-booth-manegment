
//213226184 sunil
//162235182 prasad
//9161145   tinsile
//42250189  sachin

#include <Servo.h>
#include <WiFiClient.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WebServer.h>
#include <SPI.h>
#include <MFRC522.h>
String str;
String string;
String str1,str2;

String name1;
    float amount;
  String vehicalno;
  String vehicaltype;   //UID ,Name,Amountfloat,vehical_number,vehical_type
  String str0="s";

Servo servo;

// credentials
const char *ssid = "MEGA-MINDERS";
const char *password = "1234567890";
// create the web server
ESP8266WebServer server(80);

void root() {
  // 200: status code
  // text/plain: content type -> type of content
  // "welcome..": contents
  server.send(200, "text/plain", "Welcome to NodeMCU web server");
}

void turn_red_led_on() {
   digitalWrite(LED_BUILTIN, HIGH);
  server.send(200, "text/plain", "Turned on RED LED");
}

void turn_red_led_off() {
   digitalWrite(LED_BUILTIN, LOW);
    digitalWrite(D0, LOW);
  server.send(200, "text/plain", "Turned off RED LED");
} 
constexpr uint8_t RST_PIN = 5;     // Configurable, see typical pin layout above
constexpr uint8_t SS_PIN = 4;     // Configurable, see typical pin layout above
 
MFRC522 rfid(SS_PIN, RST_PIN); // Instance of the class
 
MFRC522::MIFARE_Key key; 
 
// Init array that will store new NUID 
byte nuidPICC[4];
 
void setup() { 

   

  servo.attach(2);  //D4
  servo.write(0);
  delay(2000);
  
  Serial.begin(115200);
   pinMode(LED_BUILTIN, OUTPUT);
   
  // connect to WiFi
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  // wait for valid IP
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  // you are connected
  Serial.println("connected..");
  Serial.print("IP Address");
  Serial.println(WiFi.localIP());
  server.on("/", HTTP_GET, root);
  server.on("/red_on", HTTP_GET, turn_red_led_on);
  server.on("/red_off", HTTP_GET, turn_red_led_off);
  // start the server
   server.begin();
  SPI.begin(); // Init SPI bus
  rfid.PCD_Init(); // Init MFRC522 
 
  for (byte i = 0; i < 6; i++) {
    key.keyByte[i] = 0xFF;
  }
 
  Serial.println(F("This code scan the MIFARE Classsic NUID."));
  Serial.print(F("Using the following key:"));
  printHex(key.keyByte, MFRC522::MF_KEY_SIZE);
}
 
void loop() {

   // continuously handle the client incoming requests
  server.handleClient();
 
  // Look for new cards
  if ( ! rfid.PICC_IsNewCardPresent())
    return;
 
  // Verify if the NUID has been readed
  if ( ! rfid.PICC_ReadCardSerial())
    return;
 
  Serial.print(F("PICC type: "));
  MFRC522::PICC_Type piccType = rfid.PICC_GetType(rfid.uid.sak);
  Serial.println(rfid.PICC_GetTypeName(piccType));
 
  // Check is the PICC of Classic MIFARE type
  if (piccType != MFRC522::PICC_TYPE_MIFARE_MINI &&  
    piccType != MFRC522::PICC_TYPE_MIFARE_1K &&
    piccType != MFRC522::PICC_TYPE_MIFARE_4K) {
    Serial.println(F("Your tag is not of type MIFARE Classic."));
    return;
  }
 
  if (rfid.uid.uidByte[0] != nuidPICC[0] || 
    rfid.uid.uidByte[1] != nuidPICC[1] || 
    rfid.uid.uidByte[2] != nuidPICC[2] || 
    rfid.uid.uidByte[3] != nuidPICC[3] ) {
    Serial.println(F("A new card has been detected."));
 
    // Store NUID into nuidPICC array
    for (byte i = 0; i < 4; i++) {
      nuidPICC[i] = rfid.uid.uidByte[i];
    }
   
    Serial.println(F("The NUID tag is:"));
    Serial.print(F("In hex: "));
    printHex(rfid.uid.uidByte, rfid.uid.size);
    Serial.println();
    Serial.print(F("In dec: "));
    printDec(rfid.uid.uidByte, rfid.uid.size);
    Serial.println();
  }
  else Serial.println(F("Card read previously."));

  /* if((rfid.uid.uidByte[0]==162)&&(rfid.uid.uidByte[1]==235)&&(rfid.uid.uidByte[2]==182)&&(rfid.uid.uidByte[3]==28))
    {
        Serial.println("========================================================================================================================================");
      Serial.println("                                          IOT BASE TOOL BOOTH MANEGMENT                                                                 ");
      Serial.println("Welcome mister prasad ambavale");
      Serial.println("Your vehical number is MH-11 3515");
      Serial.println("Your amount = 1$");
       servo.write(90);
       delay(5000);
       servo.write(0);
       delay(5000);
    }
     if((rfid.uid.uidByte[0]==42 )&&(rfid.uid.uidByte[1]==250)&&(rfid.uid.uidByte[2]==189)&&(rfid.uid.uidByte[3]==37))   
    {
      Serial.println("========================================================================================================================================");
      Serial.println("                                          IOT BASE TOOL BOOTH MANEGMENT                                                                 ");
      Serial.println("Welcome mister sachin jadhav");
      Serial.println("Your vehical number is MH20 7584");
      Serial.println("Your amount = 2$");
     servo.write(90);
       delay(5000);
       servo.write(0);
       delay(5000);
    }
     if((rfid.uid.uidByte[0]==213)&&(rfid.uid.uidByte[1]==226)&&(rfid.uid.uidByte[2]==184)&&(rfid.uid.uidByte[3]==141))     
    {
      Serial.println("========================================================================================================================================");
      Serial.println("                                          IOT BASE TOOL BOOTH MANEGMENT                                                                 ");
      Serial.println("Welcome mister sunil admane");
      Serial.println("Your vehical number is MH-20 4215");
      Serial.println("Your amount = 2$");
      servo.write(90);
       delay(5000);
       servo.write(0);
       delay(5000);
    }*/
  
   
  str=(String)rfid.uid.uidByte[0];
  str1=(String)rfid.uid.uidByte[1];
  str2=(String)rfid.uid.uidByte[2];
  string=str0+str+str1+str2 ;
  //213226184 sunil
//162235182 prasad
//9161145   tinsile
//42250189  sachin
  String chetana="s9161145";
  String sunil="s213226184";
  String prasad="s162235182";
  String sachin="s42250189";
   if(string==chetana)
  {
    digitalWrite(D0, HIGH);
    delay(1000);
    digitalWrite(D0, LOW);
    
    Serial.println(chetana);
    Serial.println("========================================================================================================================================");
      Serial.println("                                          IOT BASE TOOL BOOTH MANEGMENT                                                                 ");
      Serial.println("Welcome miss Chetana Markad");
      Serial.println("Your vehical number is MH-19 8952");
      Serial.println("Your amount = 50");
      name1="Chetana Markand ";
    amount=50;
     vehicalno="MH-19 8952";
      vehicaltype="Bike";
      servo.write(90);
       delay(5000);
       servo.write(0);
       delay(5000);
  }
   if(string==sunil)
  {
    digitalWrite(D0, HIGH);
    delay(1000);
    digitalWrite(D0, LOW);
      Serial.println("========================================================================================================================================");
      Serial.println("                                          IOT BASE TOOL BOOTH MANEGMENT                                                                 ");
      Serial.println("Welcome mister sunil admane");
      Serial.println("Your vehical number is MH-20 4215");
      Serial.println("Your amount = 56");
      name1="Sunil Adamane ";
     amount=60;
  vehicalno="MH-20 4215";
  vehicaltype="bike";
      servo.write(90);
       delay(5000);
       servo.write(0);
       delay(5000);
  }
  if(string==prasad)
  {
    digitalWrite(D0, HIGH);
    delay(1000);
    digitalWrite(D0, LOW);
      Serial.println("========================================================================================================================================");
      Serial.println("                                          IOT BASE TOOL BOOTH MANEGMENT                                                                 ");
      Serial.println("Welcome mister prasad ambavale");
      Serial.println("Your vehical number is MH-11 3515");
      Serial.println("Your amount = 100");
     name1="Prasad Ambavale";
    amount=100;
   vehicalno="MH-11 4520";
  vehicaltype="car";
       servo.write(90);
       delay(5000);
       servo.write(0);
       delay(5000);
  }
  if(string==sachin)
  {
    digitalWrite(D0, HIGH);
    delay(1000);
    digitalWrite(D0, LOW);
     Serial.println("========================================================================================================================================");
      Serial.println("                                          IOT BASE TOOL BOOTH MANEGMENT                                                                 ");
      Serial.println("Welcome mister sachin jadhav");
      Serial.println("Your vehical number is MH20 7584");
      Serial.println("Your amount = 200");
       name1="Sachin Jadhav";
     amount=200;
  vehicalno="MH20 7584";
  vehicaltype="Truck";
     servo.write(90);
       delay(5000);
       servo.write(0);
       delay(5000);
  }
   Serial.println(string);
  
  //string="12345798";
   String body = "{ \"UID\": \" "+ String(string) + "\",\"Name\": \" "+ name1+ "\",\"Amount\": \" "+ String(amount) + "\",\"vehical_number\": \" "+ String(vehicalno) + "\",\"vehical_type\": \" "+ String(vehicaltype) + "\"}";
  Serial.println("sending :" + body);
  
  // call the API to insert uid in the MySQL DB
  String url = "http://192.168.43.216:5000/UID";


  // create the client
  HTTPClient httpClient;
  httpClient.begin(url);
  httpClient.addHeader("Content-Type", "application/json");
  int code = httpClient.POST(body);

  Serial.println("Status code: " + String(code));

  // end the connection
  httpClient.end();

  delay(2000);
 
  // Halt PICC
  rfid.PICC_HaltA();
 
  // Stop encryption on PCD
  rfid.PCD_StopCrypto1();
}
 
 
/**
 * Helper routine to dump a byte array as hex values to Serial. 
 */
void printHex(byte *buffer, byte bufferSize) {
  for (byte i = 0; i < bufferSize; i++) {
    Serial.print(buffer[i] < 0x10 ? " 0" : " ");
    Serial.print(buffer[i], HEX);
  }
}
 
/**
 * Helper routine to dump a byte array as dec values to Serial.
 */
void printDec(byte *buffer, byte bufferSize) {
  for (byte i = 0; i < bufferSize; i++) {
    Serial.print(buffer[i] < 0x10 ? " 0" : " ");
    Serial.print(buffer[i], DEC);
  }
}
