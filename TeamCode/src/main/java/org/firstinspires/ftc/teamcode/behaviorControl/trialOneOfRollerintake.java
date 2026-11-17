package org.firstinspires.ftc.teamcode.behaviorControl;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class trialOneOfRollerintake extends OpMode {
    // motor is just the only intake motor on the starter bot (and this is the dummy name)
    DcMotor intakemotor;
    CRServo left_intake;
    CRServo right_intake;

    CRServo windmill;

    @Override
    public void init() {
        telemetry.addData("motor", "ground");
        intakemotor = hardwareMap.get(DcMotor.class, "intake");
        telemetry.addData("intake", "left");
        left_intake = hardwareMap.get(CRServo.class, "left_intake_servo");
        telemetry.addData("intake", "right");
        right_intake = hardwareMap.get(CRServo.class, "right_intake_servo");
        telemetry.addData("windmill", "carousel");
        windmill = hardwareMap.get(CRServo.class, "windmillServo");

    }
    @Override
    public void loop() {
        telemetry.addData("Right Trigger", gamepad1.right_trigger);

        intakemotor.setPower(gamepad1.right_trigger);
        left_intake.setPower(gamepad1.right_trigger);
        right_intake.setPower(gamepad1.right_trigger);
        windmill.setPower(gamepad1.right_trigger);

        telemetry.update();
    }


}