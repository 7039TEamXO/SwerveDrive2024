package frc.robot.subsystems.Conveyor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Conveyor {
    private static TalonFX conveyor = new TalonFX(15);
    private static TalonFX conveyorLower = new TalonFX(20);

    private static double power = 0;

    public static void init() {
        conveyor.setInverted(false);
        conveyorLower.setInverted(true);
    
    }

    public static void operate(ConveyorState state){
        switch (state) {
            case HIGH_SHOOTER:
                power = -0.8;
                break;
            case LOW_SHOOTER:
                power = 0.6;
                break;
            case STOP:
                power = 0;
                break;
    
        }


        conveyor.set(ControlMode.PercentOutput, power);
        conveyorLower.set(ControlMode.PercentOutput, -power);
    }
}
