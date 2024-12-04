package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


public class Dashboard {
    private final static SendableChooser<String> m_chooser = new SendableChooser<>();
    private static String m_autoSelected;
    private static ShuffleboardTab driver = Shuffleboard.getTab("Driver");


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

    }

    public static Autos getSelected() {
        if (m_chooser.getSelected() != null) {
            m_autoSelected = m_chooser.getSelected();
        }
        return Autos.valueOf(m_autoSelected);
    }
}
