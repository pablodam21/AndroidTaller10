package com.pablo.u6t10sensorball;

public class Ball {
    public final int DIAMETER = 20;
    public final double GRAVITY = 5;
    public double ax;
    public double vx, vy;
    public double x, y;

    public Ball() {
        ax=0;
        vx=0;
        vy=1;
        x = y = 0;
    }
}
