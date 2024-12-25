package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class LED {
    private static AddressableLEDBuffer buffer = new AddressableLEDBuffer(2000);
    private static Color color = Color.kOrangeRed;
    private static AddressableLED channel = new AddressableLED(8);


    public static void init(){
        channel.setLength(buffer.getLength());
        channel.setData(buffer);
        channel.start();
    }

    public static void setLedData(){
        
        color = Color.kOrangeRed;
        
        for (int i = 0; i < buffer.getLength(); i++) {

            buffer.setLED(i, color);
        }
        channel.setData(buffer);
    }
}
