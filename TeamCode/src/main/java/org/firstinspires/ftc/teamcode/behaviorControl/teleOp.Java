/*   MIT License
 *   Copyright (c) [2026] [Base 10 Assets, LLC]
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:

 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.

 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/*
 * This file includes a teleop (driver-controlled) file for the goBILDA® StarterBot with Mecanum
 * Wheels for the 2026-2027 FIRST® Tech Challenge. On top of a mecanum wheel drivetrain, it uses
 * one motor driving an intake roller, two servos which pull elements out of corners, and a high-speed
 * launcher motor.
 */
@TeleOp(name="BIOBUZZ Mecanum TeleOp", group="Iterative Opmode")
public class BioBuzzMecanumTeleOp extends OpMode {

    // Drivetrain Motors
    private DcMotor leftFront   = null;
    private DcMotor rightFront  = null;
    private DcMotor leftBack    = null;
    private DcMotor rightBack   = null;

    // Mechanism Hardware
    private DcMotor intakeMotor     = null;
    private DcMotor launcherMotor   = null;
    private CRServo cornerServoLeft  = null;
    private CRServo cornerServoRight = null;

    @Override
    public void init() {
        // 1. Map motors/servos to the names configured on the Robot Controller Configuration
        leftFront   = hardwareMap.get(DcMotor.class, "left_front");
        rightFront  = hardwareMap.get(DcMotor.class, "right_front");
        leftBack    = hardwareMap.get(DcMotor.class, "left_back");
        rightBack   = hardwareMap.get(DcMotor.class, "right_back");
        
        intakeMotor   = hardwareMap.get(DcMotor.class, "intake");
        launcherMotor = hardwareMap.get(DcMotor.class, "launcher");
        
        cornerServoLeft  = hardwareMap.get(CRServo.class, "servo_left");
        cornerServoRight = hardwareMap.get(CRServo.class, "servo_right");

        // 2. Reverse left-side drivetrain motors so positive power moves the robot forward
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        // Reverse one corner servo so they spin opposite directions to sweep inwards
        cornerServoLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        cornerServoRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // 3. Set drivetrain zero power behavior to BRAKE for sharp, responsive stopping
        leftFront.setZeroPowerBehavior(BRAKE);
        rightFront.setZeroPowerBehavior(BRAKE);
        leftBack.setZeroPowerBehavior(BRAKE);
        rightBack.setZeroPowerBehavior(BRAKE);

        telemetry.addData("Status", "BIOBUZZ Robot Initialized Successfully");
    }

    @Override
    public void loop() {
        // ====================================================================
        // DRIVETRAIN SECTION (Gamepad 1 Left & Right Joysticks)
        // ====================================================================
        double y  = -gamepad1.left_stick_y;  // Forward/Backward
        double x  =  gamepad1.left_stick_x * 1.1; // Counteract imperfect strafe
        double rx =  gamepad1.right_stick_x; // Rotation/Turning

        // Denominator normalizes power variables so they stay between -1.0 and 1.0
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
        double frontLeftPower  = (y + x + rx) / denominator;
        double backLeftPower   = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower  = (y + x - rx) / denominator;

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);

        // ====================================================================
        // INTAKE SECTION (Gamepad 1 - A to Intake, B to Reverse/Outtake)
        // ====================================================================
        if (gamepad1.a) {
            intakeMotor.setPower(1.0);  // Intake elements inward
        } else if (gamepad1.b) {
            intakeMotor.setPower(-1.0); // Purge/outtake elements outward
        } else {
            intakeMotor.setPower(0.0);  // Stop intake
        }

        // ====================================================================
        // CORNER SERVOS SECTION (Gamepad 1 - Right/Left Bumpers)
        // ====================================================================
        if (gamepad1.right_bumper) {
            cornerServoLeft.setPower(1.0);  // Run continuous rotation sweepers
            cornerServoRight.setPower(1.0);
        } else if (gamepad1.left_bumper) {
            cornerServoLeft.setPower(-1.0); // Reverse sweepers
            cornerServoRight.setPower(-1.0);
        } else {
            cornerServoLeft.setPower(0.0);  // Stop sweepers
        }

        // ====================================================================
        // FLYWHEEL LAUNCHER SECTION (Gamepad 1 - Right Trigger analogue power)
        // ====================================================================
        // Gives the driver variable velocity control over the shooter based on trigger depth
        launcherMotor.setPower(gamepad1.right_trigger);

        // Send feedback to the Driver Station app
        telemetry.addData("Launcher Power", gamepad1.right_trigger);
        telemetry.addData("Intake Running", gamepad1.a ? "FORWARD" : (gamepad1.b ? "REVERSE" : "STOPPED"));
        telemetry.update();
    }
}
