package drgmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import drgmod.actions.GainMineralsAction;
import drgmod.character.MinerCharacter;

import static drgmod.MinerMain.makeID;

public class Phazyionite extends BaseRelic {
    private static final String NAME = "Phazyionite"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public Phazyionite() {
        super(ID, NAME, MinerCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        addToBot(new GainMineralsAction(AbstractDungeon.player, 2));
    }

    @Override
    public String getUpdatedDescription(){
        return DESCRIPTIONS[0];
    }
}
