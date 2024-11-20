package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotState;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeState;

public class SubsystemManager {

    private static IntakeState intakeState;

    public static Command intakeCommand = Commands.run(() -> operate(RobotState.INTAKE));
    public static Command travelCommand = Commands.run(() -> operate(RobotState.TRAVEL));
    public static Command depleteCommand = Commands.run(() -> operate(RobotState.DEPLETE));
    public static Command lowShooterConveyorCommand = Commands.run(() -> operate(RobotState.LOW_SHOOTER));
    public static Command highShooterConveyorCommand = Commands.run(() -> operate(RobotState.HIGH_SHOOTER));

    public static void init() {
        Intake.init();

    }

    public static void operate(RobotState state) {
        switch (state) {
            case INTAKE:
                intakeState = IntakeState.INTAKE;
                break;
            case DEPLETE:
                intakeState = IntakeState.DEPLETE;
                break;
            case TRAVEL:
                intakeState = IntakeState.STOP;
                break;
        }
        
        Intake.operate(intakeState);
    }
}
