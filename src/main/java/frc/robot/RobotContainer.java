// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final DriveSubsystem m_robotDrive;  

  // The operator's controller
  public final Joystick m_operatorController;

  // The driver's controller
  public final XboxController m_driverController;

  public RobotContainer() {
    m_robotDrive = new DriveSubsystem();

    m_operatorController = new Joystick(OIConstants.kOperatorContollerPort);
    m_driverController = new XboxController(OIConstants.kDriverControllerPort);

    configureDriverBindings();
    configureOperatorBinding();

    // Configure default commands
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                true, DriveConstants.modeValue),
            m_robotDrive));

  }

  private void configureDriverBindings() {
        // Zero Heading
    /*Back */ new JoystickButton(m_driverController, 7).whileTrue(new InstantCommand(m_robotDrive::zeroHeading, m_robotDrive));
  }

  private void configureOperatorBinding() {}

  public Command getAutonomousCommand() {
    return null;
  }
}
