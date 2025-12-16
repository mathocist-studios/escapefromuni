package com.mathochiststudios.escapefromuni.entities.PlayerInventory;

public enum InventoryObject {

    KEYCARD(false, "pixelartkeycard.png"),
    WALLET(false, "wallet.png"),
    RUCKSACK(false, "rucksack.png"),
    BIRDSEED(true, "birdseed"),
    ENERGY_DRINK(false, "energy_drink.png"),
    ROLLERBLADES(false, "roller_skates.png"),
    BASEMENT_KEY(false, "basement_key.png");

    private final boolean allowMultiple;
    private final String objectName;

    InventoryObject(boolean allowMultiple, String objectName) {
        this.objectName = objectName;
        this.allowMultiple = allowMultiple;
    }

    public boolean allowsMultiple() {
        return allowMultiple;
    }

    public String getObjectName() {
        return objectName;
    }

    public static InventoryObject fromString(String name) {
        for (InventoryObject obj : InventoryObject.values()) {
            if (obj.getObjectName().equalsIgnoreCase(name)) {
                return obj;
            }
        }
        return null;
    }

}
