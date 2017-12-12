#include <Arduino_FreeRTOS.h>
#include <semphr.h>

// Declare a mutex Semaphore Handle which we will use to manage the Serial Port.
// It will be used to ensure only only one Task is accessing this resource at any time.
SemaphoreHandle_t xSerialSemaphore;

// define tasks
void TemperatureSensorOn(void *pvParameters);
void BuzzerOn(void *pvParameters);
void LEDOn(void *pvParameters);

// Declare the virtual/real temperature.
float temp;

void setup() {
   
  Serial.begin(9600);
  
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB, on LEONARDO, MICRO, YUN, and other 32u4 based boards.
  }

  if (xSerialSemaphore == NULL)  // Check to confirm that the Serial Semaphore has not already been created.
  {
    xSerialSemaphore = xSemaphoreCreateMutex();  // Create a mutex semaphore we will use to manage the Serial Port
    if ((xSerialSemaphore) != NULL)
      xSemaphoreGive((xSerialSemaphore));  // Make the Serial Port available for use, by "Giving" the Semaphore.
  }

  // Set up the tasks
  xTaskCreate(
    TemperatureSensorOn
    , (const portCHAR *)"Turn on temperature sensor"
    , 128  // This stack size can be checked & adjusted by reading the Stack Highwater
    , NULL
    , 2  // Priority 2
    , NULL );

  xTaskCreate(
    BuzzerOn
    , (const portCHAR *) "Turn on buzzer"
    , 128  // Stack size
    , NULL
    , 1  // Priority
    , NULL );

  xTaskCreate(
    LEDOn
    , (const portCHAR *) "Turn on LED"
    , 128  // Stack size
    , NULL
    , 3  // Priority
    , NULL );
}

void loop()
{
  
}

/*--------------------------------------------------*/
/*---------------------- Tasks ---------------------*/
/*--------------------------------------------------*/

void TemperatureSensorOn(void *pvParameters __attribute__((unused)))
{
  int pin = A1;

  for (;;)
  {
    if (xSemaphoreTake(xSerialSemaphore, (TickType_t)5) == pdTRUE)
    {
      float vtemp = analogRead(pin); // save the simulated value in A1
      temp = (float) vtemp/1023*500; // convert the virtual temperature to the real one  
      Serial.print("Temporature: ");
      Serial.println(temp);
      xSemaphoreGive(xSerialSemaphore); 
    }
    vTaskDelay(1); 
  }
  
}

void BuzzerOn(void *pvParameters __attribute__((unused)))
{
  int buzzer = A0;
  pinMode(buzzer, OUTPUT);
  
  for (;;)
  {
    if (xSemaphoreTake(xSerialSemaphore, (TickType_t)5) == pdTRUE)
    {
        if (temp < 18 || temp > 30)
        {
          analogWrite(buzzer, 40);
          Serial.println("Turn on buzzer...");
        }
        xSemaphoreGive(xSerialSemaphore);   
    }
    vTaskDelay(5); 
  }
  
}


void LEDOn(void *pvParameters __attribute__((unused)) )
{
  int ledPin = 13;
  pinMode(ledPin, OUTPUT);
  
  for (;;)
  { 
    if (xSemaphoreTake(xSerialSemaphore, (TickType_t)5) == pdTRUE)
    {
      digitalWrite(ledPin, HIGH);
      Serial.println("Turn on LED...");
      xSemaphoreGive(xSerialSemaphore); 
    }

    vTaskDelay(1); 
  }
  
}
