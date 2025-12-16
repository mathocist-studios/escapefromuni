package com.mathochiststudios.escapefromuni.ShopStuff;

import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;

public class ShopItem {

    private final int cost;
    private final InventoryObject itemObject;
    private final String displayName;

    public ShopItem(int cost, InventoryObject itemObject, String displayName){
        this.cost = cost;
        this.itemObject = itemObject;
        this.displayName = displayName;
    }

    public int getCost(){
        return this.cost;
    }

    public InventoryObject getItemObject(){
        return this.itemObject;
    }

    public String getDisplayName(){
        return this.displayName;
    }

}
