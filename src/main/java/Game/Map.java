package Game;

import Physics.BoxCollider;
import Render.Camera;
import Utils.Direction;
import org.joml.Vector2i;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.Random;



public class Map {
    int WindowWidth, WindowHeight, RoomsNumber;
    public ArrayList<Room> rooms = new ArrayList<>();
    public Room currentRoom;
    Player player;

    public Map(Player player, int width, int height, int rooms, Renderer renderer){
        this.player = player;
        this.WindowWidth = width;
        this.WindowHeight = height;
        this.RoomsNumber = rooms;
        CreateRooms();
        for(Room room: this.rooms){
            generateDoors(room);
            if(room.index != 0) room.CreateEnemies();
        }
        currentRoom = this.rooms.get(0);
    }

    private boolean RoomExists(Vector2i position){
        for(Room room : rooms){
            if(room.position.equals(position)) return true;
        }
        return false;
    }

    private void CreateRooms(){
        int cnt = 0;
        Room startRoom = new Room(WindowWidth, WindowHeight);
        startRoom.position = new Vector2i(0,0);
        startRoom.isCleared = true;
        startRoom.index = cnt++;
        rooms.add(startRoom);
        Random rand = new Random();
        while(rooms.size() < RoomsNumber){
            Integer randomRoomIndex = rand.nextInt(rooms.size());
            Room currentRoom = rooms.get(randomRoomIndex);
            int newX = currentRoom.position.x;
            int newY = currentRoom.position.y;
            int dir = rand.nextInt(4);
            switch (dir){
                case 0: newY++; break;
                case 1: newY--; break;
                case 2: newX--; break;
                case 3: newX++; break;
            }
            if(RoomExists(new Vector2i(newX, newY))) continue;
            Room newRoom = new Room(WindowWidth, WindowHeight);
            newRoom.position = new Vector2i(newX, newY);
            newRoom.index = cnt++;
            rooms.add(newRoom);
        }
    }

    public void generateDoors(Room currentRoom){
        if(RoomExists(new Vector2i(currentRoom.position.x - 1, currentRoom.position.y))) currentRoom.putDoor(Direction.LEFT);
        else currentRoom.putWall(Direction.LEFT);
        if(RoomExists(new Vector2i(currentRoom.position.x + 1, currentRoom.position.y))) currentRoom.putDoor(Direction.RIGHT);
        else currentRoom.putWall(Direction.RIGHT);
        if(RoomExists(new Vector2i(currentRoom.position.x, currentRoom.position.y + 1))) currentRoom.putDoor(Direction.UP);
        else currentRoom.putWall(Direction.UP);
        if(RoomExists(new Vector2i(currentRoom.position.x, currentRoom.position.y - 1))) currentRoom.putDoor(Direction.DOWN);
        else currentRoom.putWall(Direction.DOWN);
    }

    private Room getRoomAt(Vector2i pos){
        for(Room room : rooms){
            if(room.position.equals(pos)) return room;
        }
        return null;
    }

    public void drawRoom(Renderer renderer){
        currentRoom.updateStatus();
        currentRoom.drawRoom(renderer, player);
        currentRoom.resolveRoomCollisions(player);
        checkDoorCollision(currentRoom);
    }
    

    public void checkDoorCollision(Room room){
        if(!room.isCleared) return;

        if(room.doorUp != null && BoxCollider.isColliding(player, room.doorUp)){
            Room nextRoom = getRoomAt(new Vector2i(room.position.x, room.position.y + 1));
            if(nextRoom != null){
                currentRoom = nextRoom;
                player.position.set(0, Camera.bottom + 230, 0);
            }
        }
        if(room.doorDown != null && BoxCollider.isColliding(player, room.doorDown)){
            Room nextRoom = getRoomAt(new Vector2i(room.position.x, room.position.y - 1));
            if(nextRoom != null){
                currentRoom = nextRoom;
                player.position.set(0, Camera.top - 230, 0);
            }
        }

        if(room.doorLeft != null && BoxCollider.isColliding(player, room.doorLeft)){
            Room nextRoom = getRoomAt(new Vector2i(room.position.x - 1, room.position.y));
            if(nextRoom != null){
                currentRoom = nextRoom;
                player.position.set(Camera.right - 230, 0, 0);
            }
        }

        if(room.doorRight != null && BoxCollider.isColliding(player, room.doorRight)){
            Room nextRoom = getRoomAt(new Vector2i(room.position.x + 1, room.position.y));
            if(nextRoom != null){
                currentRoom = nextRoom;
                player.position.set(Camera.left + 230, 0, 0);
            }
        }
    }
}
