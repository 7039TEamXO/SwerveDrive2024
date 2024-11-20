package frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Intake {
    private static TalonFX intakeMotor = new TalonFX(4);
    private static double power;


    public static void init(){
    }

    public static void operate(IntakeState state){
        switch (state) {
            case STOP:
                power = 0.0;
                break;
            case INTAKE:
                power = -0.65;
                break;
            case DEPLETE:
                power = 0.5;
                break;
        }

        intakeMotor.set(TalonFXControlMode.PercentOutput , power);

    }
}
