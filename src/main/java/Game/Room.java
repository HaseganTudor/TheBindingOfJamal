package Game;

import Physics.BoxCollider;
import Utils.Direction;
import org.joml.Vector2i;

import java.util.ArrayList;

public class Room {

    private int width, height;

    private float left, right, top, bottom;

    private float WallSize = 100f;

    private float halfSize = WallSize / 2;

    private ArrayList<GameObject> walls = new ArrayList<>();

    public ArrayList<GameObject> colliders = new ArrayList<>();

    public ArrayList<GameObject> doorsUp = new ArrayList<>();
    public ArrayList<GameObject> doorsDown = new ArrayList<>();
    public ArrayList<GameObject> doorsLeft = new ArrayList<>();
    public ArrayList<GameObject> doorsRight = new ArrayList<>();

    public Vector2i position;

    public int index;

    public Room(int width, int height) {

        this.width = width;
        this.height = height;

        left = -width / 2 + halfSize;
        right = width / 2 - halfSize;

        top = height / 2 - halfSize;
        bottom = -height / 2 + halfSize;

        CreateWalls();
    }

    public void drawRoom(Renderer renderer) {

        for (GameObject wall : walls) {

            renderer.draw(wall);

            wall.update();
        }

        for (GameObject door : doorsUp) {

            renderer.draw(door);

            door.update();
        }

        for (GameObject door : doorsDown) {

            renderer.draw(door);

            door.update();
        }

        for (GameObject door : doorsLeft) {

            renderer.draw(door);

            door.update();
        }

        for (GameObject door : doorsRight) {

            renderer.draw(door);

            door.update();
        }
    }

    private void CreateWalls() {

        for (float i = left; i <= right; i += WallSize) {

            GameObject wall = new GameObject();

            GameObject wall2 = new GameObject();

            if (i == 0 || i == -100 || i == 100)
                continue;

            wall.setColor(0.52f, 0.37f, 0.26f);

            wall.position.x = i;
            wall.position.y = top;

            wall2.setColor(0.52f, 0.37f, 0.26f);

            wall2.position.x = i;
            wall2.position.y = bottom;

            walls.add(wall);
            walls.add(wall2);

            colliders.add(wall);
            colliders.add(wall2);
        }

        for (float i = top; i >= bottom; i -= WallSize) {

            GameObject wall = new GameObject();

            GameObject wall2 = new GameObject();

            if (i == 0 || i == -100 || i == 100)
                continue;

            wall.setColor(0.52f, 0.37f, 0.26f);

            wall.position.x = left;
            wall.position.y = i;

            wall2.setColor(0.52f, 0.37f, 0.26f);

            wall2.position.x = right;
            wall2.position.y = i;

            walls.add(wall);
            walls.add(wall2);

            colliders.add(wall);
            colliders.add(wall2);
        }
    }

    public void putDoor(Direction dir) {

        GameObject door1 = new GameObject();
        GameObject door2 = new GameObject();
        GameObject door3 = new GameObject();

        door1.setColor(1, 1, 0);
        door2.setColor(1, 1, 0);
        door3.setColor(1, 1, 0);

        switch (dir) {

            case UP:

                door1.position.x = 0;
                door1.position.y = top;

                door2.position.x = -100;
                door2.position.y = top;

                door3.position.x = 100;
                door3.position.y = top;

                doorsUp.add(door1);
                doorsUp.add(door2);
                doorsUp.add(door3);

                break;

            case DOWN:

                door1.position.x = 0;
                door1.position.y = bottom;

                door2.position.x = -100;
                door2.position.y = bottom;

                door3.position.x = 100;
                door3.position.y = bottom;

                doorsDown.add(door1);
                doorsDown.add(door2);
                doorsDown.add(door3);

                break;

            case LEFT:

                door1.position.x = left;
                door1.position.y = 0;

                door2.position.x = left;
                door2.position.y = -100;

                door3.position.x = left;
                door3.position.y = 100;

                doorsLeft.add(door1);
                doorsLeft.add(door2);
                doorsLeft.add(door3);

                break;

            case RIGHT:

                door1.position.x = right;
                door1.position.y = 0;

                door2.position.x = right;
                door2.position.y = -100;

                door3.position.x = right;
                door3.position.y = 100;

                doorsRight.add(door1);
                doorsRight.add(door2);
                doorsRight.add(door3);

                break;
        }
    }

    public void putWall(Direction dir) {

        GameObject wall1 = new GameObject();
        GameObject wall2 = new GameObject();
        GameObject wall3 = new GameObject();

        wall1.setColor(0.52f, 0.37f, 0.26f);
        wall2.setColor(0.52f, 0.37f, 0.26f);
        wall3.setColor(0.52f, 0.37f, 0.26f);

        switch (dir) {

            case UP:

                wall1.position.x = 0;
                wall1.position.y = top;

                wall2.position.x = -100;
                wall2.position.y = top;

                wall3.position.x = 100;
                wall3.position.y = top;

                break;

            case DOWN:

                wall1.position.x = 0;
                wall1.position.y = bottom;

                wall2.position.x = -100;
                wall2.position.y = bottom;

                wall3.position.x = 100;
                wall3.position.y = bottom;

                break;

            case LEFT:

                wall1.position.x = left;
                wall1.position.y = 0;

                wall2.position.x = left;
                wall2.position.y = -100;

                wall3.position.x = left;
                wall3.position.y = 100;

                break;

            case RIGHT:

                wall1.position.x = right;
                wall1.position.y = 0;

                wall2.position.x = right;
                wall2.position.y = -100;

                wall3.position.x = right;
                wall3.position.y = 100;

                break;
        }

        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);

        colliders.add(wall1);
        colliders.add(wall2);
        colliders.add(wall3);
    }

    public void resolveRoomCollisions(GameObject gameObject){
        for(GameObject collider : colliders){
            gameObject.position.add(BoxCollider.getResolution(gameObject, collider));
        }
    }
}