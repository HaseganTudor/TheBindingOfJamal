package Game;

import Physics.BoxCollider;
import Utils.Direction;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    int WindowWidth, WindowHeight;
    int RoomsNumber;
    public ArrayList<Room> rooms = new ArrayList<>();
    Room currentRoom;
    Player player;

    public Map(Player player, int width, int height, int rooms){
        this.player = player;
        WindowWidth = width;
        WindowHeight = height;
        RoomsNumber = rooms;
        CreateRooms();
        for(Room room: this.rooms){
            System.out.println(room.index + " " + room.position);
        }
        for(Room room: this.rooms){
            generateDoors(room);
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
        startRoom.index = cnt++;
        rooms.add(startRoom);
        Random rand = new Random();
        while(rooms.size() < RoomsNumber){
            Integer randomRoomIndex = rand.nextInt(rooms.size());
            Room currentRoom = rooms.get(randomRoomIndex);

            int newX = currentRoom.position.x;
            int newY = currentRoom.position.y;

            int dir = rand.nextInt(4);

            Direction direction;

            switch (dir){
                case 0:
                    direction = Direction.UP;
                    newY++;
                    break;
                case 1:
                    direction = Direction.DOWN;
                    newY--;
                    break;
                case 2:
                    direction = Direction.LEFT;
                    newX--;
                    break;
                case 3:
                    direction = Direction.RIGHT;
                    newX++;
                    break;
            }

            if(RoomExists(new Vector2i(newX, newY)))
                continue;

            Room newRoom = new Room(WindowWidth, WindowHeight);
            newRoom.position = new Vector2i(newX, newY);
            newRoom.index = cnt++;
            rooms.add(newRoom);

        }
    }

    public void generateDoors(Room currentRoom){
        Vector2i posLeft = new Vector2i(currentRoom.position.x - 1, currentRoom.position.y);
        Vector2i posRight = new Vector2i(currentRoom.position.x + 1, currentRoom.position.y);
        Vector2i posUp = new Vector2i(currentRoom.position.x, currentRoom.position.y + 1);
        Vector2i posDown = new Vector2i(currentRoom.position.x, currentRoom.position.y - 1);

        if(RoomExists(posLeft)){
            currentRoom.putDoor(Direction.LEFT);
        }
        else{
            currentRoom.putWall(Direction.LEFT);
        }

        if(RoomExists(posRight)){
            currentRoom.putDoor(Direction.RIGHT);
        }
        else{
            currentRoom.putWall(Direction.RIGHT);
        }

        if(RoomExists(posUp)){
            currentRoom.putDoor(Direction.UP);
        }else{
            currentRoom.putWall(Direction.UP);
        }

        if(RoomExists(posDown)){
            currentRoom.putDoor(Direction.DOWN);
        }
        else{
            currentRoom.putWall(Direction.DOWN);
        }
    }

    public void drawRoom(Renderer renderer){
        currentRoom.drawRoom(renderer);
        currentRoom.resolveRoomCollisions(player);
        checkDoorCollision(currentRoom);
    }

    private Room getRoomAt(Vector2i pos){
        for(Room room : rooms){
            if(room.position.equals(pos)){
                return room;
            }
        }
        return null;
    }

    public void checkDoorCollision(Room room){
        for(GameObject door : room.doorsUp){
            if(BoxCollider.isColliding(player, door)){

                Room nextRoom = getRoomAt(
                        new Vector2i(
                                room.position.x,
                                room.position.y + 1
                        )
                );

                if(nextRoom != null){
                    currentRoom = nextRoom;
                    player.position = new Vector3f(0, 0, 0);
                    System.out.println(currentRoom.index);
                }
            }
        }
        for(GameObject door : room.doorsDown){
            if(BoxCollider.isColliding(player, door)){
                Room nextRoom = getRoomAt(
                        new Vector2i(
                                room.position.x,
                                room.position.y - 1
                        )
                );
                if(nextRoom != null){
                    currentRoom = nextRoom;
                    player.position = new Vector3f(0, 0, 0);
                    System.out.println(currentRoom.index);
                }
            }
        }

        for(GameObject door : room.doorsLeft){
            if(BoxCollider.isColliding(player, door)){
                Room nextRoom = getRoomAt(
                        new Vector2i(
                                room.position.x - 1,
                                room.position.y
                        )
                );
                if(nextRoom != null){
                    currentRoom = nextRoom;
                    player.position = new Vector3f(0, 0, 0);
                    System.out.println(currentRoom.index);
                }
            }
        }
        for(GameObject door : room.doorsRight){
            if(BoxCollider.isColliding(player, door)){
                Room nextRoom = getRoomAt(
                        new Vector2i(
                                room.position.x + 1,
                                room.position.y
                        )
                );
                if(nextRoom != null){
                    currentRoom = nextRoom;
                    player.position = new Vector3f(0, 0, 0);
                    System.out.println(currentRoom.index);
                }
            }
        }
    }
}