package frc.robot.subsystems;

import javax.xml.transform.Source;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotState;
import frc.robot.subsystems.Conveyor.Conveyor;
import frc.robot.subsystems.Conveyor.ConveyorState;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;

public class SubsystemManager {

    private static IntakeState intakeState;
    private static ShooterState shooterState;
    private static ConveyorState conveyorState;

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
                shooterState = ShooterState.STOP;
                conveyorState = ConveyorState.STOP;
                break;
            case DEPLETE:
                intakeState = IntakeState.DEPLETE;
                shooterState = ShooterState.DEPLETE;
                conveyorState = ConveyorState.STOP;
                break;
            case TRAVEL:
                intakeState = IntakeState.STOP;
                shooterState = ShooterState.STOP;
                conveyorState = ConveyorState.STOP;
                break;
            case HIGH_SHOOTER:
                shooterState = ShooterState.PODIUM_SHOOTING;
                intakeState = Shooter.readyToShoot() ? IntakeState.INTAKE : IntakeState.STOP;
                conveyorState = Shooter.readyToShoot() ? ConveyorState.HIGH_SHOOTER : ConveyorState.STOP;
                break;
            case LOW_SHOOTER:
                shooterState = ShooterState.SUBWOOFER_SHOOTING;
                intakeState = Shooter.readyToShoot() ? IntakeState.INTAKE : IntakeState.STOP;
                conveyorState = Shooter.readyToShoot() ? ConveyorState.LOW_SHOOTER : ConveyorState.STOP;
                break;
        }
        
        Intake.operate(intakeState);
        Shooter.operate(shooterState);
        Conveyor.operate(conveyorState);
    }
}
