#include <compat/deprecated.h>
#include <FlexiTimer2.h>

// All definitions
#define NUMCHANNELS 6
#define HEADERLEN 4
#define PACKETLEN (NUMCHANNELS * 2 + HEADERLEN + 1)
#define SAMPFREQ 256                      // ADC sampling rate 256
#define TIMER2VAL (1024/(SAMPFREQ))       // Set 256Hz sampling frequency                    

// Global constants and variables
volatile unsigned char TXBuf[PACKETLEN];  //The transmission packet
volatile unsigned char TXIndex;           //Next byte to write in the transmission packet.
volatile unsigned char CurrentCh;         //Current channel being sampled.
volatile unsigned char counter = 0;   //Additional divider used to generate CAL_SIG
volatile unsigned int ADC_Value = 0;    //ADC current value

void setup() {

  noInterrupts();  // Disable all interrupts before initialization

  // LED1
  // pinMode(LED1, OUTPUT);  //Setup LED1 direction
  // digitalWrite(LED1,LOW); //Setup LED1 state

  //Write packet header and footer
  TXBuf[0] = 0xa5;    //Sync 0
  TXBuf[1] = 0x5a;    //Sync 1
  TXBuf[2] = 2;       //Protocol version
  TXBuf[3] = 0;       //Packet counter
  TXBuf[4] = 0x02;    //CH1 High Byte
  TXBuf[5] = 0x00;    //CH1 Low Byte
  TXBuf[6] = 0x02;    //CH2 High Byte
  TXBuf[7] = 0x00;    //CH2 Low Byte

  FlexiTimer2::set(TIMER2VAL, Timer2_Overflow_ISR);
  FlexiTimer2::start();

  // Set speed to 57600 bps
  Serial.begin(57600);



  interrupts();  // Enable all interrupts after initialization has been completed
}

void Timer2_Overflow_ISR()
{
  // Toggle LED1 with ADC sampling frequency /2
  // Toggle_LED1();

  //Read the 6 ADC inputs and store current values in Packet
  for (CurrentCh = 0; CurrentCh < 2; CurrentCh++) {
    ADC_Value = analogRead(CurrentCh);
    TXBuf[((2 * CurrentCh) + HEADERLEN)] = ((unsigned char)((ADC_Value & 0xFF00) >> 8)); // Write High Byte
    TXBuf[((2 * CurrentCh) + HEADERLEN + 1)] = ((unsigned char)(ADC_Value & 0x00FF)); // Write Low Byte
  }

  // Send Packet
  for (TXIndex = 0; TXIndex < 8; TXIndex++) {
     Serial.println(TXBuf[TXIndex], DEC); // Saída Decimal (Para o bluetooth)
   // Serial.write(TXBuf[TXIndex]); // Saída Decimal (Para o MATLAB)
  }

  // Increment the packet counter
  TXBuf[3]++;

  // Generate the CAL_SIGnal
  counter++;    // increment the devider counter
  if (counter == 12) { // 250/12/2 = 10.4Hz ->Toggle frequency
    counter = 0;
  }
}


/****************************************************/
/*  Function name: loop                             */
/*  Parameters                                      */
/*    Input   :  No                                 */
/*    Output  :  No                                 */
/*    Action: Puts MCU into sleep mode.             */
/****************************************************/
void loop() {

  __asm__ __volatile__ ("sleep");

}

