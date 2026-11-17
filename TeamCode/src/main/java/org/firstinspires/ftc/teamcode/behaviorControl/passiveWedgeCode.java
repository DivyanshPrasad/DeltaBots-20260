package org.firstinspires.ftc.teamcode.behaviorControl;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class passiveWedgeCode extends OpMode{
    CRServo passive_wedge;

    @Override
    public void init() {
        passive_wedge = hardwareMap.get(CRServo.class, "passive_wedge");
    }

    @Override
    public void loop() {

    }
}

