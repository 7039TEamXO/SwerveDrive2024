package frc.robot.subsystems;

import javax.xml.transform.Source;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import frc.robot.RobotState;
import frc.robot.subsystems.Conveyor.Conveyor;
import frc.robot.subsystems.Conveyor.ConveyorState;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;

public class SubsystemManager {

    public static CommandPS4Controller ps4Joystick = new CommandPS4Controller(0);

    private static IntakeState intakeState;
    private static ShooterState shooterState;
    private static ConveyorState conveyorState;

    private static RobotState state;
    private static RobotState lastState;

    public static Command intakeCommand = Commands.run(() -> operateAuto(RobotState.INTAKE));
    public static Command travelCommand = Commands.run(() -> operateAuto(RobotState.TRAVEL));
    public static Command depleteCommand = Commands.run(() -> operateAuto(RobotState.DEPLETE));
    public static Command lowShooterConveyorCommand = Commands.run(() -> operateAuto(RobotState.LOW_SHOOTER));
    public static Command highShooterConveyorCommand = Commands.run(() -> operateAuto(RobotState.HIGH_SHOOTER));

    public static void init() {
        Intake.init();
        state = RobotState.TRAVEL;
        lastState = RobotState.TRAVEL;
    }

    public static void operate(boolean onAuto) {
        if (!onAuto) {
            state = ps4Joystick.cross().getAsBoolean() ? RobotState.INTAKE : 
                ps4Joystick.circle().getAsBoolean() ? RobotState.TRAVEL :
                    ps4Joystick.square().getAsBoolean() ? RobotState.HIGH_SHOOTER : 
                        ps4Joystick.triangle().getAsBoolean() ? RobotState.LOW_SHOOTER :
                            ps4Joystick.R1().getAsBoolean() ? RobotState.DEPLETE : lastState;
        }

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

        lastState = state;
        
        Intake.operate(intakeState);
        Shooter.operate(shooterState);
        Conveyor.operate(conveyorState);
    }

    private static void operateAuto(RobotState chosenState) {
        state = chosenState;
        operate(true);
    }
}
