package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Shooter {
    private static TalonFX shooterMaster = new TalonFX(54);
    private static TalonFX shooterSlave = new TalonFX(5);

    private static double vel_w = 0;
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
                vel_w = 0;                
                break;
            case AMP_SHOOTING:
                vel_w = -7500;
                break;
            case DEFLECT:
                vel_w = 2500;
                break;
            case DEPLETE:
                vel_w = 0.4;
                break;
            case PODIUM_SHOOTING:
                vel_w = 13000;
                break;
            case SUBWOOFER_SHOOTING:
                vel_w = -12000;
                break;
    
        }
        if (vel_w == 0) {
            shooterMaster.set(ControlMode.PercentOutput, 0);
        } else {
            shooterMaster.set(ControlMode.Velocity, vel_w);
        }
    }
    

}
