package org.firstinspires.ftc.teamcode.behaviorControl;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class independentControlsIntakeTeleOp extends OpMode {
    DcMotor intakemotor;
    CRServo left_intake;
    CRServo right_intake;
    CRServo windmill;

    @Override
    public void init() {
        telemetry.addData("Intake Motor", "Ready");
        intakemotor = hardwareMap.get(DcMotor.class, "intake");
        telemetry.addData("Left Servo", "Ready");
        left_intake = hardwareMap.get(CRServo.class, "left_intake_servo");
        telemetry.addData("Right Servo", "Ready");
        right_intake = hardwareMap.get(CRServo.class, "right_intake_servo");
        telemetry.addData("Windmill Servo", "Ready");
        windmill = hardwareMap.get(CRServo.class, "windmillServo");
    }
    @Override
    public void loop(){
        telemetry.addData("Right Trigger Ready", gamepad1.right_trigger);
        telemetry.addData("Left Trigger Ready", gamepad1.left_trigger);

        double intakePower = gamepad1.right_trigger - gamepad1.left_trigger;
        intakemotor.setPower(intakePower);
        left_intake.setPower(intakePower);
        right_intake.setPower(intakePower);



        telemetry.addData("Windmill Ready", gamepad1.right_bumper);

        if(gamepad1.right_bumper && !gamepad1.left_bumper) {
            windmill.setPower(1);
        } else if(gamepad1.left_bumper && !gamepad1.right_bumper) {
            windmill.setPower(-1);
        } else{
            windmill.setPower(0);
        }



    }
}
