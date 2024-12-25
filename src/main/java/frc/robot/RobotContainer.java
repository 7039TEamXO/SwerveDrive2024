// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.SubsystemManager;
import com.pathplanner.lib.auto.NamedCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

  // Replace with CommandPS4Controller or CommandJoystick if needed
  // final CommandPS4Controller SubsystemManager.ps4Joystick = new CommandPS4Controller(0);


  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  

  


    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the rotational velocity 
    // buttons are quick rotation positions to different ways to face
    // WARNING: default buttons are on the same buttons as the ones defined in configureBindings
    // AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(SubsystemManager.getDriveBase(),
    //                                                                () -> -MathUtil.applyDeadband(SubsystemManager.ps4Joystick.getLeftY(),
    //                                                                                              OperatorConstants.LEFT_Y_DEADBAND),
    //                                                                () -> -MathUtil.applyDeadband(SubsystemManager.ps4Joystick.getLeftX(),
    //                                                                                              OperatorConstants.LEFT_X_DEADBAND),
    //                                                                () -> -MathUtil.applyDeadband(-SubsystemManager.ps4Joystick.getRightX(),
    //                                                                                              OperatorConstants.RIGHT_X_DEADBAND),
    //                                                                SubsystemManager.ps4Joystick.povDown(),
    //                                                                SubsystemManager.ps4Joystick.povUp(),
    //                                                                SubsystemManager.ps4Joystick.povRight(),
    //                                                                SubsystemManager.ps4Joystick.povLeft());

    // // Applies deadbands and inverts controls because joysticksy
    // // are back-right positive while robot
    // // controls are front-left positive
    // // left stick controls translation
    // // right stick controls the desired angle NOT angular rotation
    // Command driveFieldOrientedDirectAngle = SubsystemManager.getDriveBase().driveCommand(
    //     () -> MathUtil.applyDeadband(-SubsystemManager.ps4Joystick.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
    //     () -> MathUtil.applyDeadband(-SubsystemManager.ps4Joystick.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
    //     () -> -SubsystemManager.ps4Joystick.getRightX(),
    //     () -> -SubsystemManager.ps4Joystick.getRightY());

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the angular velocity of the robot
    Command driveFieldOrientedAngularVelocity = SubsystemManager.getDriveBase().driveCommand( // default
        () -> MathUtil.applyDeadband(-modifyAxis(SubsystemManager.getpsJoystick().getLeftY()), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(-modifyAxis(SubsystemManager.getpsJoystick().getLeftX()), OperatorConstants.LEFT_X_DEADBAND),
        () -> -modifyAxis(SubsystemManager.getpsJoystick().getRightX()));
        

    // Command driveFieldOrientedDirectAngleSim = SubsystemManager.getDriveBase().simDriveCommand(
    //     () -> MathUtil.applyDeadband(SubsystemManager.ps4Joystick.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
    //     () -> MathUtil.applyDeadband(SubsystemManager.ps4Joystick.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
    //     () -> SubsystemManager.ps4Joystick.getRawAxis(2));

    // drivebase.setDefaultCommand(
    //     false ? driveFieldOrientedDirectAngle : driveFieldOrientedDirectAngleSim);
    

    // SubsystemManager.ps4Joystick.povUp().toggleOnTrue(closedAbsoluteDriveAdv);
    // SubsystemManager.ps4Joystick.povLeft().toggleOnTrue(closedAbsoluteDriveAdv);
    // SubsystemManager.ps4Joystick.povRight().toggleOnTrue(closedAbsoluteDriveAdv);
    // SubsystemManager.ps4Joystick.povDown().toggleOnTrue(closedAbsoluteDriveAdv);

    public RobotContainer()
  {

    // Configure the trigger bindings
    configureBindings();

    NamedCommands.registerCommand("Travel", SubsystemManager.travelCommand);

    SubsystemManager.setDefaultCommand(driveFieldOrientedAngularVelocity);


  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {


    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    SubsystemManager.getpsJoystick().PS().onTrue((Commands.runOnce(SubsystemManager.getDriveBase()::zeroGyro)));
    // SubsystemManager.ps4Joystick.square().onTrue(Commands.runOnce(drivebase::addFakeVisionReading));
    // SubsystemManager.ps4Joystick.circle().whileTrue(
    //     Commands.deferredProxy(() -> drivebase.driveToPose(
    //                                new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
    //                           ));
    // SubsystemManager.ps4Joystick.triangle().whileTrue(drivebase.aimAtSpeaker(2));
    // driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return SubsystemManager.getDriveBase().getAutonomousCommand(Dashboard.getSelected().getAutoName());
    
  }

  public void setDriveMode()
  {
  }

  public void setMotorBrake(boolean brake)
  {
    SubsystemManager.getDriveBase().setMotorBrake(brake);
  }

  private static double modifyAxis(double joystickInput) {
    double scaled_input = 1 - (1 - Math.abs(joystickInput)) * (1 - 0.5);
    return joystickInput * scaled_input;
  }
}
