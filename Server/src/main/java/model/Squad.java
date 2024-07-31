package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Squad {
    private String name;
    private ArrayList<Map<String, String>> members;
    private String ownerName;
    private String ID;
    public Squad(Client owner, String name){
        ID = UUID.randomUUID().toString();
        this.name = name;
        ownerName = owner.getID();
        members = new ArrayList<>();
        Map<String, String> ownerMap = new HashMap<String, String>(){{put("xp", owner.getXP()+"");
        put("name", owner.getUsername());}};
        members.add(ownerMap);
    }

    public ArrayList<Map<String, String>> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Map<String, String>> members) {
        this.members = members;
    }

    public String getOwner() {
        return ownerName;
    }

    public void setOwner(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getName() {
        return name;
    }
}
