package Game;

import Physics.BoxCollider;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    private int width, height;
    private float left, right, top, bottom;
    private float WallSize = 100f;
    private float halfSize = WallSize / 2;
    private ArrayList<GameObject> walls = new ArrayList<>();

    public Room(int width, int height){
        this.width = width;
        this.height = height;

        left = -width / 2 + halfSize;
        right = width / 2 - halfSize;
        top = height / 2 - halfSize;
        bottom = -height / 2 + halfSize;
        CreateWalls();
    }

    public void drawRoom(Renderer renderer){
        for(GameObject wall : walls){
            renderer.draw(wall);
            wall.update();
        }
    }

    private void CreateWalls() {
        Random rand = new Random();

        for (float i = left; i <= right ; i += WallSize) {
            GameObject wall = new GameObject();
            GameObject wall2 = new GameObject();

            if(i == 0 || i == -100 || i == 100) continue;

            wall.setColor(0.52f, 0.37f, 0.26f);
            wall.position.x = i;
            wall.position.y = top;

            wall2.setColor(0.52f, 0.37f, 0.26f);
            wall2.position.x = i;
            wall2.position.y = bottom;

            walls.add(wall);
            walls.add(wall2);
            BoxCollider.addCollider(wall);
            BoxCollider.addCollider(wall2);
        }

        for(float i = top; i >= bottom; i -= WallSize){
            GameObject wall = new GameObject();
            GameObject wall2 = new GameObject();

            if(i == 0 || i == -100 || i == 100) continue;

            wall.setColor(0.52f, 0.37f, 0.26f);
            wall.position.x = left;
            wall.position.y = i;

            wall2.setColor(0.52f, 0.37f, 0.26f);
            wall2.position.x = right;
            wall2.position.y = i;

            walls.add(wall);
            walls.add(wall2);
            BoxCollider.addCollider(wall);
            BoxCollider.addCollider(wall2);
        }
    }
}

