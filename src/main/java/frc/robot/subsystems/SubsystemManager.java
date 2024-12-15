package frc.robot.subsystems;

import java.io.File;

import javax.xml.transform.Source;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import frc.robot.Robot;
import frc.robot.RobotState;
import frc.robot.subsystems.Conveyor.Conveyor;
import frc.robot.subsystems.Conveyor.ConveyorState;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class SubsystemManager {

    // The robot's subsystems and commands are defined here...
    private static final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve/falcon"));

    private static final CommandPS4Controller ps4Joystick = new CommandPS4Controller(0);

    private static IntakeState intakeState;
    private static ShooterState shooterState;
    private static ConveyorState conveyorState;
    private static boolean isLocked = false;

    private static RobotState state;
    private static RobotState lastState;


    private static ShooterState lastShooterState;
    private static ConveyorState lastConveyorState;

    public static Command intakeCommand = Commands.run(() -> operateAuto(RobotState.INTAKE));
    public static Command travelCommand = Commands.run(() -> operateAuto(RobotState.TRAVEL));
    public static Command depleteCommand = Commands.run(() -> operateAuto(RobotState.DEPLETE));
    public static Command shootCommand = Commands.run(() -> operateAuto(RobotState.SHOOT));
    public static Command lowShooterConveyorCommand = Commands.run(() -> operateAuto(RobotState.LOW_SHOOTER));
    public static Command highShooterConveyorCommand = Commands.run(() -> operateAuto(RobotState.HIGH_SHOOTER));

    public static void init() {
        Intake.init();
        state = RobotState.TRAVEL;
        lastState = RobotState.TRAVEL;
        lastShooterState = ShooterState.STOP;
        lastConveyorState = ConveyorState.STOP;
    }

    public static void operate(boolean onAuto) {
        if (!onAuto) {
            state = ps4Joystick.cross().getAsBoolean() ? RobotState.INTAKE : 
                ps4Joystick.circle().getAsBoolean() ? RobotState.TRAVEL :
                    // Shoot or deplete only whenever the game piece is inside the robot
                    ps4Joystick.L2().getAsBoolean() && Shooter.readyToShoot() ? RobotState.SHOOT :
                        ps4Joystick.square().getAsBoolean() ? RobotState.HIGH_SHOOTER : 
                            ps4Joystick.triangle().getAsBoolean() ? RobotState.LOW_SHOOTER :
                          
                          ps4Joystick.R1().getAsBoolean() ? RobotState.DEPLETE : lastState;

            // ps4Joystick.povUp().onTrue(drivebase.rotateToAngle(0, 0.1));
            // if(ps4Joystick.povRight().getAsBoolean()) { drivebase.rotateToAngle(Math.toRadians(-90), 0.05).schedule(); }
            // if(ps4Joystick.povDown().getAsBoolean()) { drivebase.rotateToAngle(Math.toRadians(180), 0.05).schedule(); }
            // if(ps4Joystick.povLeft().getAsBoolean()) { drivebase.rotateToAngle(Math.toRadians(90), 0.05).schedule(); }
        }   

        switch (state) {
            case INTAKE:
                // Switch from Intake to Travel when the robot detects the game piece
                if(Intake.isGamePieceIn()) {
                    state = RobotState.TRAVEL;
                    break;
                }
                intakeState = IntakeState.INTAKE;
                shooterState = ShooterState.STOP;
                conveyorState = ConveyorState.STOP;
                isLocked = false;
                break;
            case DEPLETE:
                intakeState = IntakeState.DEPLETE;
                shooterState = ShooterState.DEPLETE;
                conveyorState = ConveyorState.STOP;
                isLocked = false;
                break;
            case TRAVEL:
                intakeState = IntakeState.STOP;
                shooterState = ShooterState.STOP;
                conveyorState = ConveyorState.STOP;
                isLocked = false;
                break;
            case SHOOT:
                intakeState = IntakeState.INTAKE;
                isLocked = true;
                if(lastState != RobotState.SHOOT) {
                    conveyorState = (lastState == RobotState.HIGH_SHOOTER) ? ConveyorState.HIGH_SHOOTER : ConveyorState.LOW_SHOOTER;
                    shooterState = (lastState == RobotState.HIGH_SHOOTER) ? ShooterState.PODIUM_SHOOTING : ShooterState.SUBWOOFER_SHOOTING;
                } else {
                    shooterState = lastShooterState;
                    conveyorState = lastConveyorState;
                }
                break;
            case HIGH_SHOOTER:
                shooterState = ShooterState.PODIUM_SHOOTING;
                intakeState = Intake.isGamePieceIn() ? IntakeState.STOP : IntakeState.INTAKE;
                conveyorState = ConveyorState.STOP;
                isLocked = false;
                break;
            case LOW_SHOOTER:
                shooterState = ShooterState.SUBWOOFER_SHOOTING;
                intakeState = Intake.isGamePieceIn() ? IntakeState.STOP : IntakeState.INTAKE;
                conveyorState = ConveyorState.STOP;
                isLocked = false;
                break;
        }

        lastState = state;
        lastShooterState = shooterState;
        lastConveyorState = conveyorState;
        
        Intake.operate(intakeState);
        Shooter.operate(shooterState);
        Conveyor.operate(conveyorState);
        if (isLocked) drivebase.lock();
    }

    private static void operateAuto(RobotState chosenState) {
        state = chosenState;
        operate(true);
    }

    public static SwerveSubsystem getDriveBase() {
        return drivebase;
    }

    public static void setDefaultCommand(Command defultCommand){
        drivebase.setDefaultCommand(defultCommand);
    }

    public static CommandPS4Controller getpsJoystick(){
        return ps4Joystick;
    }
}
