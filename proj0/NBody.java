public class NBody {

    /**
     *  Given a file name, return a double corresponding to the radius of the universe in that file,
     *  e.g. readRadius("./data/planets.txt") should return 2.50e+11.
     */
    public static double readRadius(String filename){
        In in = new In(filename);
        in.readInt();
        return in.readDouble();
    }
    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int num = in.readInt();
        in.readDouble();
        Planet[] ret = new Planet[num];
        for (int i = 0; i < num; i++) {
            ret[i] = new Planet(
                    in.readDouble(),
                    in.readDouble(),
                    in.readDouble(),
                    in.readDouble(),
                    in.readDouble(),
                    in.readString()
            );
        }

        return ret;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();
        // Extra //
        // Adding Audio
        StdAudio.play("audio/2001.mid");
        // Extra//
        double timer = 0;
        int num = planets.length;
        while (timer < T){
            double [] xForces = new double[num];
            double [] yForces = new double[num];

            for (int i = 0; i < num; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < num; i++) {
                planets[i].update(dt,xForces[i],yForces[i]);
            }
            StdDraw.picture(0,0,"images/starfield.jpg");
            for (Planet planet: planets) {
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            timer += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }
}
