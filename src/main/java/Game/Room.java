package Game;

import Game.Enemy.Enemy;
import Game.Enemy.EnemyFactory;
import Game.Enemy.Fly;
import Physics.BoxCollider;
import Utils.Direction;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Room {

    private int width, height;
    private float left, right, top, bottom;
    private float WallSize = 150;
    private float halfSize = WallSize / 2;

    private ArrayList<GameObject> walls = new ArrayList<>();
    public ArrayList<GameObject> colliders = new ArrayList<>();

    public GameObject doorUp = null;
    public GameObject doorDown = null;
    public GameObject doorLeft = null;
    public GameObject doorRight = null;

    public Vector2i position;
    public int index;
    public boolean isCleared = false;

    public ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Vector3f> enemyPositions = new ArrayList<>();

    public Room(int width, int height) {
        this.width = width;
        this.height = height;

        left = -width / 2 + halfSize;
        right = width / 2 - halfSize;
        top = height / 2 - halfSize;
        bottom = -height / 2 + halfSize;

        enemyPositions.add(new Vector3f(-450.0f, 150.0f, 0.0f));
        enemyPositions.add(new Vector3f(400.0f, 200.0f, 0.0f));
        enemyPositions.add(new Vector3f(0.0f, 0.0f, 0.0f));
        enemyPositions.add(new Vector3f(-500.0f, -250.0f, 0.0f));
        enemyPositions.add(new Vector3f(350.0f, -180.0f, 0.0f));

        CreateWalls();
    }

    public void updateStatus() {
        if (enemies.isEmpty() && !isCleared) {
            isCleared = true;
        }
    }

    public void drawRoom(Renderer renderer, Player player) {
        renderer.setSceneLights(isCleared, doorUp, doorDown, doorLeft, doorRight, player, enemies, player.projectiles);

        ArrayList<Projectile> projectiles = player.projectiles;

        for (GameObject wall : walls) {
            renderer.draw(wall);
            wall.update();
        }


        drawDoors(doorUp, renderer);
        drawDoors(doorDown, renderer);
        drawDoors(doorLeft, renderer);
        drawDoors(doorRight, renderer);

        for (Enemy enemy : enemies) {
            renderer.draw(enemy);
            enemy.update();
        }

        for(Projectile projectile : projectiles){
            projectile.update();
            renderer.draw(projectile);
        }

    }

    private void drawDoors(GameObject door, Renderer renderer) {
        if (door == null) return;
        if(isCleared) {
            door.setTexture("res/textures/door_open.png");
        }
        else{
            door.setTexture("res/textures/door_closed.png");
        }
        renderer.draw(door);
        door.update();
    }

    public void CreateEnemies() {
        Random rand = new Random();
        int enemyNumber = rand.nextInt(enemyPositions.size() - 2) + 2;
        ArrayList<Vector3f> availablePositions = new ArrayList<>(enemyPositions);
        Collections.shuffle(availablePositions);

        for(int i = 0; i < enemyNumber; i++) {
            enemies.add(EnemyFactory.createRandomEnemy());
            enemies.get(i).position.set(availablePositions.get(i));
            enemies.get(i).setRotationX(180);
        }
    }

    private void CreateWalls() {
        GameObject cornerLeftUP = new GameObject();
        GameObject cornerRightUP = new GameObject();
        GameObject cornerLeftDOWN = new GameObject();
        GameObject cornerRightDOWW = new GameObject();

        cornerLeftUP.setTexture("res/textures/wall_corner.png");
        cornerRightUP.setTexture("res/textures/wall_corner.png");
        cornerLeftDOWN.setTexture("res/textures/wall_corner.png");
        cornerRightDOWW.setTexture("res/textures/wall_corner.png");

        cornerLeftUP.position.x = left;
        cornerLeftUP.position.y = top;
        cornerLeftUP.setRotationZ(-90);
        cornerLeftUP.setSize(WallSize);

        cornerRightUP.position.x = right;
        cornerRightUP.position.y = top;
        cornerRightUP.setRotationZ(180);
        cornerRightUP.setSize(WallSize);

        cornerLeftDOWN.position.x = left;
        cornerLeftDOWN.position.y = bottom;
        cornerLeftDOWN.setRotationZ(0);
        cornerLeftDOWN.setSize(WallSize);

        cornerRightDOWW.position.x = right;
        cornerRightDOWW.position.y = bottom;
        cornerRightDOWW.setRotationZ(90);
        cornerRightDOWW.setSize(WallSize);

        walls.add(cornerLeftUP);
        walls.add(cornerRightUP);
        walls.add(cornerLeftDOWN);
        walls.add(cornerRightDOWW);

        for (float i = left+WallSize; i < right; i += WallSize) {
            if (i == 0) continue;
            GameObject wall = new GameObject();
            GameObject wall2 = new GameObject();
            wall.setTexture("res/textures/wall.png");
            wall.position.x = i; wall.position.y = top; wall.setRotationZ(180); wall.setSize(WallSize);
            wall2.setTexture("res/textures/wall.png");
            wall2.position.x = i; wall2.position.y = bottom; wall2.setRotationZ(0); wall2.setSize(WallSize);
            walls.add(wall); walls.add(wall2);
            colliders.add(wall); colliders.add(wall2);
        }

        for (float i = top - WallSize; i > bottom; i -= WallSize) {
            if (i == 0) continue;
            GameObject wall = new GameObject();
            GameObject wall2 = new GameObject();
            wall.setTexture("res/textures/wall.png");
            wall.position.x = left; wall.position.y = i; wall.setRotationZ(-90); wall.setSize(WallSize);
            wall2.setTexture("res/textures/wall.png");
            wall2.position.x = right; wall2.position.y = i; wall2.setRotationZ(90); wall2.setSize(WallSize);
            walls.add(wall); walls.add(wall2);
            colliders.add(wall); colliders.add(wall2);
        }

        for(float i = left + WallSize; i < right; i += WallSize){
            for(float j = top-WallSize; j > bottom; j -= WallSize){
                GameObject floor = new GameObject();
                floor.setColor(0.2f, 0.2f, 0.2f);
                floor.position.x = i; floor.position.y = j; floor.setSize(WallSize);
                walls.add(floor);
            }
        }
    }

    public void putDoor(Direction dir) {
        GameObject door = new GameObject();
        door.setSize(WallSize);
        switch (dir) {
            case UP:
                door.position.set(0, top, 0);
                doorUp = door;
                door.setRotationZ(180);
                break;
            case DOWN:
                door.position.set(0, bottom, 0);
                doorDown = door;
                door.setRotationZ(0);
                break;
            case LEFT:
                door.position.set(left, 0, 0);
                doorLeft = door;
                door.setRotationZ(-90);
                break;
            case RIGHT:
                door.position.set(right, 0, 0);
                doorRight = door;
                door.setRotationZ(90);

                break;
        }
    }

    public void putWall(Direction dir) {
        GameObject wall = new GameObject();
        wall.setTexture("res/textures/wall.png");
        wall.setSize(WallSize);
        switch (dir) {
            case UP: wall.position.set(0, top, 0); wall.setRotationZ(180); break;
            case DOWN: wall.position.set(0, bottom, 0); wall.setRotationZ(0); break;
            case LEFT: wall.position.set(left, 0, 0); wall.setRotationZ(-90); break;
            case RIGHT: wall.position.set(right, 0, 0); wall.setRotationZ(90); break;
        }
        walls.add(wall);
        colliders.add(wall);
    }

    public void resolveRoomCollisions(GameObject gameObject){
        for(GameObject collider : colliders){
            gameObject.position.add(BoxCollider.getResolution(gameObject, collider));
        }
        if (!isCleared) {
            if(doorUp != null) checkDoorSolid(gameObject, doorUp);
            if(doorDown != null) checkDoorSolid(gameObject, doorDown);
            if(doorLeft != null) checkDoorSolid(gameObject, doorLeft);
            if(doorRight != null) checkDoorSolid(gameObject, doorRight);
        }
    }

    private void checkDoorSolid(GameObject player, GameObject door) {
        if(door == null) return;
        player.position.add(BoxCollider.getResolution(player, door));
    }
}
