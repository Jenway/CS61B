public class Planet {

    // Its current x position
    public double xxPos;
    // Its current y position
    public double yyPos;
    // Its current velocity in the x direction
    public double xxVel;
    // Its current velocity in the y direction
    public double yyVel;
    // Its mass
    public double mass;
    public String imgFileName;

    private static final double GRAVITY = 6.67e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet p){
        double distance_x = this.xxPos - p.xxPos;
        double distance_y = this.yyPos - p.yyPos;
        return Math.sqrt(distance_x * distance_x + distance_y * distance_y);
    }

    public double calcForceExertedBy(Planet p){
        double distance = calcDistance(p);
        if (distance == 0){
            return 0;
        }
        return GRAVITY * mass * p.mass / (distance * distance);
    }

    public double calcForceExertedByX(Planet p){
        double dx = p.xxPos - this.xxPos;
        double r = calcDistance(p);
        if (r == 0){
            return 0;
        }
        return calcForceExertedBy(p) * dx / r;
    }
    public double calcForceExertedByY(Planet p){
        double dy = p.yyPos - this.yyPos;
        double r = calcDistance(p);
        if (r == 0){
            return 0;
        }
        return calcForceExertedBy(p) * dy / r;
    }
    public double calcNetForceExertedByX(Planet[] allPlanets){
        double ret = 0.0;
        for (Planet planet : allPlanets){
            ret += calcForceExertedByX(planet);
        }
        return ret;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets){
        double ret = 0.0;
        for (Planet planet : allPlanets){
            ret += calcForceExertedByY(planet);
        }
        return ret;
    }

    public void update(double dt,double fX,double fY){
        double acceleration_x = fX / this.mass;
        double acceleration_y = fY / this.mass;

        this.xxVel += acceleration_x * dt;
        this.yyVel += acceleration_y * dt;

        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    public void draw(){
        StdDraw.picture(this.xxPos,this.yyPos,"images/" + this.imgFileName);
    }
}
