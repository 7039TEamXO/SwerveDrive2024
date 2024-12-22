package frc.robot;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.SubsystemManager;


public class Dashboard {
    private final static SendableChooser<String> m_chooser = new SendableChooser<>();
    private static String m_autoSelected;
    private static ShuffleboardTab driver = Shuffleboard.getTab("Driver");
    private static HttpCamera limelightcamera = new HttpCamera("limelight", "http://10.70.39.11:5800");


    public static void init(){

        Autos[] states = Autos.values();
        for (int i = 0; i < states.length; i++) {
            Autos state = states[i];
            String name_state = state.getAutoName();
            if (i == 0) {
                m_chooser.setDefaultOption(name_state, name_state);
            }
            m_chooser.addOption(name_state, name_state);
        }

        driver.add("Autos", m_chooser).withPosition(0, 0).withSize(5, 3);
        driver.add("LimeLight Camera", limelightcamera).withPosition(17, 0).withSize(9, 6);
    
    }

    public static Autos getSelected() {
        if (m_chooser.getSelected() != null) {
            m_autoSelected = m_chooser.getSelected();
        }
        return Autos.valueOf(m_autoSelected);
    }
}
