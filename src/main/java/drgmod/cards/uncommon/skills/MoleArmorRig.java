package drgmod.cards.uncommon.skills;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.actions.GainMineralsAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.*;
import drgmod.util.CardStats;

public class MoleArmorRig extends BaseCard {
    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;
    private static final int MINERAL_GAIN = 10;

    public static final String ID = makeID(MoleArmorRig.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public MoleArmorRig() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setCustomVar("blockGain", 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainMineralsAction(p, MINERAL_GAIN));
        addToBot(new GainBlockAction(p, customVar("blockGain")));
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(MineralPower.POWER_ID)){
            int mineralCount = AbstractDungeon.player.getPower(MineralPower.POWER_ID).amount + MINERAL_GAIN;
            if (mineralCount > 80){
                mineralCount = 80;
            }
            setCustomVar("blockGain", (mineralCount) / 10 * block);
        }
        else{
            setCustomVar("blockGain", block);
        }
        initializeDescription();
    }
}
