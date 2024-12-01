package frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Intake {
    private static TalonFX intakeMotor = new TalonFX(4);

    private static AnalogInput irInput = new AnalogInput(0);
    private static int irValue = irInput.getAverageValue();

    private static double power;

    public static void init() {}

    public static void operate(IntakeState state) {
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
            case LOADING:
                power = -0.5;
                break;
        }

        intakeMotor.set(TalonFXControlMode.PercentOutput , power);

        irValue = irInput.getValue();
        System.out.println(irValue);
    }

    // we use 1700 because sensor does not detect anything and distance that he get is 1000, and if booblick in robot we get distance 1800 or more
    public static boolean isGamePieceIn() {
        return irValue > 1700;
    }
}
