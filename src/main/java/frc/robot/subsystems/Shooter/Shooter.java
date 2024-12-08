package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Shooter {
    private static TalonFX shooterMaster = new TalonFX(54);
    private static TalonFX shooterSlave = new TalonFX(5);
    private static ControlMode mode = ControlMode.Velocity;

    private static double wanted = 0;
    private static int counter = 0;

    public static void init() {
        shooterMaster.setInverted(false);
        shooterSlave.setInverted(true);
        shooterMaster.configNeutralDeadband(0);
        shooterSlave.configNeutralDeadband(0);
        shooterSlave.follow(shooterMaster);
        shooterMaster.config_kP(0, 0.15);
        shooterMaster.config_kF(0, 0.063);
        shooterMaster.configClosedloopRamp(0.2);
        shooterSlave.configClosedloopRamp(0.2);

    }

    public static void operate(ShooterState state){
        switch (state) {
            case STOP:
                wanted = 0;
                mode = ControlMode.PercentOutput;               
                break;
            case DEFLECT:
                wanted = 2500;
                mode = ControlMode.Velocity;
                break;
            case DEPLETE:
                wanted = 0.4;
                mode = ControlMode.PercentOutput; 
                break;
            case PODIUM_SHOOTING:
                wanted = 13000;
                mode = ControlMode.Velocity;
                break;
            case SUBWOOFER_SHOOTING:
                wanted = -12000;
                mode = ControlMode.Velocity;
                break;
    
        }
        shooterMaster.set(mode, wanted);

    }

    public static boolean readyToShoot() {
        return Math.abs(shooterMaster.getSelectedSensorVelocity()) + 500 > Math.abs(wanted) && 
            Math.abs(shooterMaster.getSelectedSensorVelocity()) - 500 < Math.abs(wanted) &&
            wanted != 0;
    }
}
