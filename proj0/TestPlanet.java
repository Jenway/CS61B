public class TestPlanet {


    /**
     *  Creates two planets and prints out the `pairwise` force between them
     */
    public static void main(String[] args) {
        System.out.println("Creates two planets and prints out the `pairwise` force between them...");

        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");

        double force = p1.calcForceExertedBy(p2);
        System.out.println(force);

    }

}
