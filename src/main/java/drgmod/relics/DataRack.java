package drgmod.relics;

import basemod.interfaces.StartActSubscriber;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.SummonGremlinAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.GremlinFat;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import drgmod.actions.AddCardsToHandAction;
import drgmod.actions.GainMineralsAction;
import drgmod.actions.MineralSpendAction;
import drgmod.character.MinerCharacter;
import drgmod.powers.*;
import drgmod.util.CustomTags;

import static drgmod.MinerMain.makeID;

public class DataRack extends BaseRelic {
    private static final String NAME = "DataRack"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.
    public DataRack() {
        super(ID, NAME, MinerCharacter.Enums.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        this.counter++;
        if (this.counter == 20){
            addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, 25, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            this.counter = 0;
        }
    }

    @Override
    public String getUpdatedDescription(){
        return DESCRIPTIONS[0];
    }
}
