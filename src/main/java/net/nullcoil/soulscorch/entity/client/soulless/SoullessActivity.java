package net.nullcoil.soulscorch.entity.client.soulless;

public enum SoullessActivity {
    PASSIVE(0),
    NEUTRAL(1),
    HOSTILE(2);

    private final int id;
    SoullessActivity(int id) { this.id = id; }

    public int getId() { return id; }

    public static SoullessActivity fromId(int id) {
        for(SoullessActivity activity : values()) {
            if(activity.getId() == id) { return activity; }
        }
        return PASSIVE;
    }
}
