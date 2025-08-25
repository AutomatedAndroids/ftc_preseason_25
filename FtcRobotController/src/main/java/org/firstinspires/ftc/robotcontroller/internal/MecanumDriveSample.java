package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MecanumDriveSample extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare and initialize motors
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRight");

        // Reverse the right side motors if needed (depends on motor and wheel orientation)
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Get joystick inputs
            double drive = -gamepad1.left_stick_y; // Forward/backward
            double strafe = gamepad1.left_stick_x; // Strafe left/right
            double turn = gamepad1.right_stick_x;  // Turn clockwise/counter-clockwise

            // Calculate motor power for each wheel
            double frontLeftPower = drive - strafe*1.4 - turn;
            double frontRightPower = -drive - strafe*1.4 + turn;
            double backLeftPower = -drive + strafe - turn;
            double backRightPower = drive + strafe + turn;

            // Normalize motor power to keep values within -1.0 to 1.0
            double maxPower = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            maxPower = Math.max(maxPower, Math.abs(backLeftPower));
            maxPower = Math.max(maxPower, Math.abs(backRightPower));

            if (maxPower > 1.0) {
                frontLeftPower /= maxPower;
                frontRightPower /= maxPower;
                backLeftPower /= maxPower;
                backRightPower /= maxPower;
            }

            // Set motor power
            frontLeftMotor.setPower(frontLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backLeftMotor.setPower(backLeftPower);
            backRightMotor.setPower(backRightPower);
        }
    }
}