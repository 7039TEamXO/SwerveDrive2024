package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.Intake.Intake;

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
        if(Intake.isGamePieceIn()){
            color = Color.kCyan;
        }
        else if(Intake.isGamePieceIn() && Limelight.isReadyToShoot()){
            color = Color.kGreen;
        }
        else{
            color = Color.kOrangeRed;
        }

        
        for (int i = 0; i < buffer.getLength(); i++) {

            buffer.setLED(i, color);
        }
        channel.setData(buffer);
    }
}
