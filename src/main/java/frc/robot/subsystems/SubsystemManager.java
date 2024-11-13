package frc.robot.subsystems;

import frc.robot.RobotState;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeState;

public class SubsystemManager {

    private static IntakeState intakeState;

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
