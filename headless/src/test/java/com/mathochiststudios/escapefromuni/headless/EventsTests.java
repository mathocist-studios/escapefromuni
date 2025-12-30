package com.mathochiststudios.escapefromuni.headless;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.mathochiststudios.escapefromuni.GameDifficulty;
import com.mathochiststudios.escapefromuni.Main;
import com.mathochiststudios.escapefromuni.ShopStuff.Shop;
import com.mathochiststudios.escapefromuni.entities.InteractableEntity.InteractableEntity;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.headless.Utils.SimulateKeyPress;
import com.mathochiststudios.escapefromuni.headless.Utils.TestingUtils;
import com.mathochiststudios.escapefromuni.powerups.SpeedPowerup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class EventsTests extends AbstractHeadlessGdxTest {

    private static final int TOTAL_COINS_IN_GAME = 12; // Update this if more coins are added
    private static final int TOTAL_SPEED_POWERUPS_IN_GAME = 2; // Update this if more speed powerups are added

    @Test
    public void testSpeedPositiveEvent() {

        Main app = TestingUtils.createTestGame();

        // Add speed powerup directly to the level at player's position
        SpeedPowerup speedPowerup = new SpeedPowerup(
            mock(Texture.class),
            mock(Sound.class),
            38, 24, 0.1f, 0.1f
        );
        app.getGame().getCurrentLevel().addSpeedPowerup(speedPowerup);

        // Simulate input and logic to pick up the speed powerup
        app.getGame().input();
        app.getGame().logic();

        // Check that the event was registered
        assertEquals(
            1,
            app.getGame().getPlayer().getEventsCounter().getPositiveEventsEncountered(),
            "Speed positive event should be encountered after collecting speed powerup"
        );

    }

    @Test
    public void testFoundLibraryCardEvent() {

        Main app = TestingUtils.createTestGame();

        // Switch to level with library card
        // The library card is located at (26, 24) in the side level
        app.getGame().switchToLevel(app.getGame().getLevels().get(1).getSideLevel(), "Side");
        app.getGame().getPlayer().getMoneySprite().setX(26);
        app.getGame().getPlayer().getMoneySprite().setY(24);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Simulate input and logic to pick up the library card
        app.getGame().input();
        app.getGame().logic();

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasFoundLibraryCard(),
            "Player should have found the library card"
        );

    }

    @Test
    public void testFoundBasementKeyEvent() {

        Main app = TestingUtils.createTestGame();

        // Switch to level with basement key
        // The basement key is located at (38, 26) in the main level
        app.getGame().switchToLevel(app.getGame().getLevels().get(1), "Forward");
        app.getGame().getPlayer().getMoneySprite().setX(38);
        app.getGame().getPlayer().getMoneySprite().setY(26);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Simulate input and logic to pick up the basement key
        app.getGame().input();
        app.getGame().logic();

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasFoundBasementKey(),
            "Player should have found the basement key"
        );
    }

    @Test
    public void testHandedInWalletEvent() {

        Main app = TestingUtils.createTestGame();

        // The wallet is located at (38, 12) in the start level
        app.getGame().getPlayer().getMoneySprite().setX(38);
        app.getGame().getPlayer().getMoneySprite().setY(12);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Simulate input and logic to pick up wallet
        app.getGame().input();
        app.getGame().logic();

        // Switch to level with wallet hand-in point
        // The wallet hand-in point is located at (35, 24) in the main level
        app.getGame().switchToLevel(app.getGame().getLevels().get(1), "Forward");
        app.getGame().getPlayer().getMoneySprite().setX(35);
        app.getGame().getPlayer().getMoneySprite().setY(24);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Simulate input and logic to hand in the wallet
        app.getGame().input();
        app.getGame().logic();

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasHandedInWallet(),
            "Player should have handed in the wallet"
        );
    }

    @Test
    public void testCaughtByDeanEvent() {

        Main app = TestingUtils.createTestGame();

        // Switch to level with dean
        app.getGame().switchToLevel(app.getGame().getLevels().get(1).getSideLevel(), "Side");

        // collect library card to trigger dean appearance
        // The library card is located at (26, 24) in the side level
        app.getGame().switchToLevel(app.getGame().getLevels().get(1).getSideLevel(), "Side");
        app.getGame().getPlayer().getMoneySprite().setX(26);
        app.getGame().getPlayer().getMoneySprite().setY(24);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Simulate input and logic to pick up the library card
        app.getGame().input();
        app.getGame().logic();

        // Move player to dean's position to get caught
        // The dean appears at (1, 15) after library card is collected
        app.getGame().getPlayer().getMoneySprite().setX(1);
        app.getGame().getPlayer().getMoneySprite().setY(15);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Simulate input and logic to get caught by dean
        app.getGame().input();
        app.getGame().logic();

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().getCaughtByDean(),
            "Player should have been caught by the dean"
        );

    }

    @Test
    public void testBoughtRollerSkatesEvent() {

        Main app = TestingUtils.createTestGame();

        // Switch to level with rollerskates shop
        app.getGame().switchToLevel(app.getGame().getLevels().get(1), "Side");

        // add 10 coins to player inventory to buy rollerskates
        app.getGame().getPlayer().addCoins(10);

        // Interact with rollerskates shop to buy rollerskates
        InteractableEntity rollerskatesShop = app.getGame().getCurrentLevel().getLevelInteractableEntities().get(0);
        rollerskatesShop.onInteract(app.getGame().getPlayer(), app.getGame().getCurrentLevel());

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasBoughtRollerSkates(),
            "Player should have bought roller skates"
        );
        // Check that the rollerskates are in the player's inventory
        assertTrue(
            app.getGame().getPlayer().getInventory().hasItem(InventoryObject.ROLLERBLADES),
            "Player should have roller skates in inventory"
        );

    }

    @Test
    public void testIsSlowedByWaterEvent() {

        Main app = TestingUtils.createTestGame();

        // Switch to level with water
        app.getGame().switchToLevel(app.getGame().getLevels().get(2).getSide2Level(), "Forward");

        // Move player into water area to trigger slowing event
        app.getGame().getPlayer().getMoneySprite().setX(7);
        app.getGame().getPlayer().getMoneySprite().setY(9);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Simulate input and logic to trigger water slowing event
        app.getGame().input();
        app.getGame().logic();

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasBeenSlowedByWater(),
            "Player should be slowed by water"
        );

    }

    @Test
    public void testFoundFriendEvent() {

        Main app = TestingUtils.createTestGame();

        // Switch to level with friend
        app.getGame().switchToLevel(app.getGame().getLevels().get(2).getSide2Level(), "Forward");

        // Move player into friend's radius of interaction to trigger found friend event
        app.getGame().getPlayer().getMoneySprite().setX(2);
        app.getGame().getPlayer().getMoneySprite().setY(18);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Simulate input and logic to trigger found friend event
        app.getGame().input();
        app.getGame().logic();

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasFoundFriend(),
            "Player should have found a friend"
        );

    }

    @Test
    public void testBoughtEnergyDrinkEvent() {

        Main app = TestingUtils.createTestGame();

        // Switch to level with energy drink shop
        app.getGame().switchToLevel(app.getGame().getLevels().get(2).getSideLevel(), "Side");

        // add 5 coins to player inventory to buy energy drink
        app.getGame().getPlayer().addCoins(5);

        // Buy energy drink from shop
        Shop.buyItem(app.getGame(), app.getGame().getPlayer(), Shop.energyDrink);

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasBoughtEnergyDrink(),
            "Player should have bought an energy drink"
        );

    }

    @Test
    public void testMovedDucksEvent() {

        Main app = TestingUtils.createTestGame();

        app.getGame().getPlayer().getInventory().addItem(InventoryObject.BIRDSEED);

        // Switch to level with ducks
        app.getGame().switchToLevel(app.getGame().getLevels().get(2).getSide2Level(), "Forward");

        // Move player into duck area to trigger moved ducks event
        app.getGame().getPlayer().getMoneySprite().setX(30);
        app.getGame().getPlayer().getMoneySprite().setY(25);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Mock Gdx.input so the game sees the E key as pressed during input()
        SimulateKeyPress simulateEKeyPress = TestingUtils.simulateGdxInputKeyPress(Input.Keys.E);

        // Simulate input and logic to trigger moved ducks event
        app.getGame().input();
        app.getGame().logic();

        // Restore/release the key state
        simulateEKeyPress.release();

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasMovedDucks(),
            "Player should have moved the ducks"
        );
    }

    @Test
    public void testHasMadeItToBusStopEvent() {

        Main app = TestingUtils.createTestGame();

        // Switch to bus level
        app.getGame().switchToLevel(app.getGame().getLevels().get(3), "ExitForward");

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasMadeItToBusStop(),
            "Player should have made it to the bus stop"
        );

    }

    @Test
    public void testMultipleEventsCounting() {

        Main app = TestingUtils.createTestGame();

        // Simulate encountering 2 positive events
        app.getGame().getPlayer().getEventsCounter().boughtRollerSkates();
        app.getGame().getPlayer().getEventsCounter().boughtEnergyDrink();

        // Simulate encountering 3 negative events
        app.getGame().getPlayer().getEventsCounter().foundFriend();
        app.getGame().getPlayer().getEventsCounter().slowedByWater();
        app.getGame().getPlayer().getEventsCounter().movedDucks();

        // Simulate encountering a hidden event
        app.getGame().getPlayer().getEventsCounter().caughtByDean();
        app.getGame().getPlayer().getEventsCounter().handedInWallet();

        // Simulate encountering the same events again to ensure they are not double-counted
        app.getGame().getPlayer().getEventsCounter().boughtRollerSkates();
        app.getGame().getPlayer().getEventsCounter().boughtEnergyDrink();
        app.getGame().getPlayer().getEventsCounter().foundFriend();
        app.getGame().getPlayer().getEventsCounter().slowedByWater();
        app.getGame().getPlayer().getEventsCounter().movedDucks();

        // Check that the counts are correct
        assertEquals(
            2,
            app.getGame().getPlayer().getEventsCounter().getPositiveEventsEncountered(),
            "Player should have encountered 2 positive events"
        );

        assertEquals(
            3,
            app.getGame().getPlayer().getEventsCounter().getNegativeEventsEncountered(),
            "Player should have encountered 3 negative events"
        );

        assertEquals(
            2,
            app.getGame().getPlayer().getEventsCounter().getHiddenEventsEncountered(),
            "Player should have encountered 2 hidden events"
        );

    }

    @Test
    public void testHasLongBoiPetEvent() {

        Main app = TestingUtils.createTestGame();

        // Switch to level with Long Boi altar
        app.getGame().switchToLevel(app.getGame().getLevels().get(1).getSide2Level(), "Forward");

        // Move player into Long Boi altar area to trigger Long Boi pet event
        app.getGame().getPlayer().getMoneySprite().setX(12);
        app.getGame().getPlayer().getMoneySprite().setY(14);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Mock Gdx.input so the game sees the E key as pressed during input()
        SimulateKeyPress simulateEKeyPress = TestingUtils.simulateGdxInputKeyPress(Input.Keys.E);

        // Simulate input and logic to trigger Long Boi pet event
        app.getGame().input();
        app.getGame().logic();

        // Restore/release the key state
        simulateEKeyPress.release();

        // Check that the event was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().getHasLongBoiPet(),
            "Player should have obtained Long Boi Pet"
        );

    }

    @Test
    public void testHasExitLibraryAchievement() {

        Main app = TestingUtils.createTestGame();

        // Switch to last level with library exit door
        app.getGame().switchToLevel(app.getGame().getLevels().get(1), "ExitForward");

        // Add required items to inventory to exit library
        app.getGame().getPlayer().getInventory().addItem(InventoryObject.KEYCARD);
        app.getGame().getPlayer().getInventory().addItem(InventoryObject.RUCKSACK);

        // Move player to library exit door position to trigger exit event
        app.getGame().getPlayer().getMoneySprite().setX(4);
        app.getGame().getPlayer().getMoneySprite().setY(3);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Simulate input and logic to trigger exit library event
        app.getGame().input();
        app.getGame().logic();

        // Check that the achievement was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().getHasExitLibraryAchieved(),
            "Player should have achieved exiting the library"
        );

    }

    @Test
    public void testCollectedAllCoinsAchievement() {

        Main app = TestingUtils.createTestGame();

        // Simulate collecting all coins in the game
        app.getGame().getPlayer().addCoins(TOTAL_COINS_IN_GAME);

        // Simulate input and logic to register coin collection
        app.getGame().input();
        app.getGame().logic();

        // Check that the achievement was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasCollectedAllCoins(),
            "Player should have achieved collecting all coins"
        );

    }

    @Test
    public void testCollectedAllSpeedPowerupsAchievement() {

        Main app = TestingUtils.createTestGame();

        // Simulate collecting all speed powerups in the game
        for (int i = 0; i < TOTAL_SPEED_POWERUPS_IN_GAME; i++) {
            app.getGame().getPlayer().incrementTotalSpeedPowerupsCollected();
        }

        // give player the rollerskates and energy drink
        app.getGame().getPlayer().getInventory().addItem(InventoryObject.ROLLERBLADES);
        app.getGame().getPlayer().getInventory().addItem(InventoryObject.ENERGY_DRINK);

        // Simulate input and logic to register speed powerup collection
        app.getGame().input();
        app.getGame().logic();

        // Check that the achievement was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasCollectedAllPowerUps(),
            "Player should have achieved collecting all speed powerups"
        );

    }

    @Test
    public void testPerfectionAchievement() {

        Main app = TestingUtils.createTestGame();

        // Simulate collecting all speed powerups in the game
        app.getGame().getPlayer().getEventsCounter().collectedAllPowerUps();

        // Simulate collecting all coins in the game
        app.getGame().getPlayer().getEventsCounter().collectedAllCoins();

        // Simulate has made it to bus stop event
        app.getGame().getPlayer().getEventsCounter().madeItToBusStop();

        // Simulate has exit library achievement
        app.getGame().getPlayer().getEventsCounter().hasExitLibrary();

        // Simulate has long boi pet event
        app.getGame().getPlayer().getEventsCounter().hasLongBoiPet();

        // Simulate input and logic to register all achievements
        app.getGame().input();
        app.getGame().logic();

        // Check that the achievement was registered
        assertTrue(
            app.getGame().getPlayer().getEventsCounter().hasCompletedGame(),
            "Player should have achieved perfection by completing all achievements"
        );
    }

    @Test
    public void testScoreValueCalculation() {

        Main app = TestingUtils.createTestGame();

        // Simulate various events and achievements //

        // test zero score
        app.getGame().setGameDifficulty(GameDifficulty.EASY);
        app.getGame().getPlayer().getGameTimer().removeTime(300);
        app.getGame().getPlayer().setHappiness(0);
        assertEquals(0, app.getGame().getScore(), "Score should be 0 for no events and EASY difficulty");

        // test 2 coins, 1 speed, 50% time, 50% happiness on Normal difficulty
        app.getGame().setGameDifficulty(GameDifficulty.NORMAL);
        app.getGame().getPlayer().addCoins(2);
        app.getGame().getPlayer().incrementTotalSpeedPowerupsCollected();
        app.getGame().getPlayer().getGameTimer().addTime(150);
        app.getGame().getPlayer().setHappiness(50);
        assertEquals(290, app.getGame().getScore(), "Score should be 290 for 2 coins, 1 speed, 50% time, 50% happiness on NORMAL difficulty");

        // test all coins, all speed, full time, full happiness on Impossible difficulty
        app.getGame().setGameDifficulty(GameDifficulty.IMPOSSIBLE);
        app.getGame().getPlayer().addCoins(TOTAL_COINS_IN_GAME - 2); // already had 2 coins
        for (int i = 0; i < TOTAL_SPEED_POWERUPS_IN_GAME - 1; i++) { // already had 1 speed powerup
            app.getGame().getPlayer().incrementTotalSpeedPowerupsCollected();
        }
        app.getGame().getPlayer().getGameTimer().addTime(150); // now full time
        app.getGame().getPlayer().setHappiness(100);
        app.getGame().getPlayer().getInventory().addItem(InventoryObject.ROLLERBLADES);
        app.getGame().getPlayer().getInventory().addItem(InventoryObject.ENERGY_DRINK);
        app.getGame().getPlayer().getEventsCounter().madeItToBusStop();
        app.getGame().getPlayer().getEventsCounter().hasExitLibrary();
        app.getGame().getPlayer().getEventsCounter().hasLongBoiPet();

        // simulate perfection achievement
        app.getGame().input();
        app.getGame().logic();

        assertEquals(1010, app.getGame().getScore(), "Score should be 810 for all coins, all speed, full time, full happiness on IMPOSSIBLE difficulty");
    }

}
