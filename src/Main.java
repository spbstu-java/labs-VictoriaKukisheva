public class Main {
    public static void main(String[] args) {
        Hero hero = new Hero();

        MoveStrategy walk = new WalkStrategy();
        MoveStrategy ride = new RideStrategy();
        MoveStrategy fly = new FlyStrategy();

        System.out.println("The little hero wake up and his day starts...\nHe get out from home.");
        hero.setMoveStrategy(walk);
        hero.move();

        hero.setMoveStrategy(ride);
        hero.move();

        hero.setMoveStrategy(fly);
        hero.move();

        System.out.println("The end.");
    }
}