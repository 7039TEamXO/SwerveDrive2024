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
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class SubsystemManager {

    // The robot's subsystems and commands are defined here...
    private static final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve/falcon"));

    private static final CommandPS4Controller ps4Joystick = new CommandPS4Controller(0);


    private static boolean isLocked = false;

    private static RobotState state;
    private static RobotState lastState;

   
    public static Command travelCommand = Commands.run(() -> operateAuto(RobotState.TRAVEL));


    public static void init() {

        state = RobotState.TRAVEL;
        lastState = RobotState.TRAVEL;
 
    }

    public static void operate(boolean onAuto) {
        if (!onAuto) {
            state = ps4Joystick.circle().getAsBoolean() ? RobotState.TRAVEL : lastState;


        }   

        switch (state) {
            case TRAVEL:

            isLocked = false;

                break;
        }

        lastState = state;

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
