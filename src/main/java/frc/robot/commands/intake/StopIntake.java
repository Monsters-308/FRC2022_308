package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class StopIntake extends CommandBase {
    private IntakeSubsystem m_intakeSubsystem;

    public StopIntake(IntakeSubsystem intakeSubsystem) {
        m_intakeSubsystem = intakeSubsystem;
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void initialize() {
        m_intakeSubsystem.stopIntake();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    //The FitnessGram™ Pacer Test is a multistage aerobic capacity test that progressively gets more difficult as it continues. The 20 meter pacer test will begin in 30 seconds. Line up at the start. The running speed starts slowly, but gets faster each minute after you hear this signal. beep A single lap should be completed each time you hear this sound. ding Remember to run in a straight line, and run as long as possible. The second time you fail to complete a lap before the sound, your test is over. The test will begin on the word start. On your mark, get ready, start. You feel like it's easy at first. But you are so wrong. You run until you feel like you are going to die right there in the middle of the track, the same gym teacher yelling at you to keep going. But you just can't do it. You make it back to your partner and collapse, breathing heavily. "Dude, you only did like 20 laps."
}
