package com.mathochiststudios.escapefromuni.ShopStuff;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.powerups.SpeedPowerup;

public class Shop {

    public static ShopItem energyDrink = new ShopItem(2, InventoryObject.ENERGY_DRINK, "Energy Drink");
    public static ShopItem birdFeed = new ShopItem(5, InventoryObject.BIRDSEED, "Bird Feed");

    //specially called off gui button press (item will be unique to the button)
    public static void buyItem(Game game, Player player, ShopItem item) {
//        if (!item.getisBought() && player.spendCoins(item.getCost())){
//
//                player.addItem(item);
//                item.setisBought(true);
//                System.out.println(item.getName());
//                if (Objects.equals(item.getName(), "energyDrink")){
//                    SpeedPowerup drinkPowerUp = new SpeedPowerup(null, null, 0, 0, 1.1f, 30.0f);
//                    player.addSpeedPowerUp(drinkPowerUp);
//                }
//                if (Objects.equals(item.getName(), "birdFeed")){
//                    player.setHasBirdFeed();
//                }
//                return true;
//        }
//        else {
//            //show player you dont have enough coins
//            //could also just do nothing because funny
//            return false;
//        }
        boolean playerHasItem = player.getInventory().hasItem(item.getItemObject());
        boolean canHaveMultiple = item.getItemObject().allowsMultiple();
        boolean isPurchasable = !playerHasItem || canHaveMultiple;
        if (!isPurchasable) {
            Notification notification = new Notification("Item already owned!", 2.0f, NotificationType.SPEECH, game.getTextureManager().getGameSmallFont());
            game.getHud().getNotificationManager().addNotification(notification);
            return;
        }
        if (!player.spendCoins(item.getCost())) {
            Notification notification = new Notification("Not enough coins!", 2.0f, NotificationType.SPEECH, game.getTextureManager().getGameSmallFont());
            game.getHud().getNotificationManager().addNotification(notification);
            return;
        }

        player.getInventory().addItem(item.getItemObject());
        if (item.getItemObject() == InventoryObject.ENERGY_DRINK) {
            SpeedPowerup drinkPowerUp = new SpeedPowerup(null, null, 0, 0, (float) game.getGameDifficulty().getSpeedBuffMultiplier(), 30.0f);
            player.addSpeedPowerUp(drinkPowerUp);
        }
        Notification notification = new Notification(item.getDisplayName() + " purchased!", 2.0f, NotificationType.SPEECH, game.getTextureManager().getGameSmallFont());
        game.getHud().getNotificationManager().addNotification(notification);
    }
}
