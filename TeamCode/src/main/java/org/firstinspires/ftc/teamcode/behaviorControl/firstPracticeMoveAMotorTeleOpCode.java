package org.firstinspires.ftc.teamcode.behaviorControl;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


// If you changed Line 6 from TeleOp to Autonomous it would show in Autonomous
@TeleOp()
public class firstPracticeMoveAMotorTeleOpCode extends OpMode {
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;// variable motor is a motor TYPE DcMotor
    //Started, called when driver presses init
    @Override
    public void init(){
        telemetry.addData("Hello", "World");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
        //note: the motor might not actually be named motor... whichever chosen motor
        //sent to DriveStation
    }

    @Override
    public void loop(){
        //intentionally left blank so no looping happens(which was previously so that only the
        //Hello: world can be displayed
        telemetry.addData("A Button", gamepad1.a);
        //repeatedly checking if on controller 1(I think)
        if (gamepad1.a){
            frontLeftDrive.setPower(1);
            frontRightDrive.setPower(1);
            backRightDrive.setPower(1);
            backLeftDrive.setPower(1);
            telemetry.addData("Status", "Motor is running");
        }
        else  {
            frontLeftDrive.setPower(0);
            frontRightDrive.setPower(0);
            backRightDrive.setPower(0);
            backLeftDrive.setPower(0);
            telemetry.addData("Status", "A is not pressed");
        }
        telemetry.update();


    }
}

/* What the code above does, in the DriverStation)after selecting init to start up the robot, and you select teleOp
 to see all the functions(OpMode options? Then it will show Hello: World
 */
